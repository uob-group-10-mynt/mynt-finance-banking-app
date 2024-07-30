package com.mynt.banking.auth;

import com.mynt.banking.currency_cloud.CurrencyCloudRepository;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.Clock;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Value("${application.security.jwt.public-key-path}")
    private String PUBLIC_KEY_PATH;

    @Value("${application.security.jwt.private-key-path}")
    private String PRIVATE_KEY_PATH;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private final CurrencyCloudRepository currencyCloudRepository;

    private final UserRepository userRepository;

    @Setter
    private Clock clock = Clock.systemDefaultZone();

    @PostConstruct
    public void init() {
        try {
            publicKey = loadPublicKey();
            privateKey = loadPrivateKey();
        } catch (Exception e) {
            throw new TokenException.TokenGenerationException("Failed to load public key", e);
        }
    }

    public String generateToken(@NotNull JwtUserDetails userDetails) {
        return generateTokenWithExpiration(userDetails, jwtExpiration);
    }

    public String generateRefreshToken(@NotNull JwtUserDetails userDetails) {
        return generateTokenWithExpiration(userDetails, refreshExpiration);
    }

    // Generate a JWT token for UserDetails
    private String generateTokenWithExpiration(@NotNull JwtUserDetails userDetails, long expiration) {
        try {
            String userUUID = userDetails.getUuid();

            // Add authorities and uuid to the JWT claims
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("authorities", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            extraClaims.put("uuid", userUUID);

            return encryptToken(buildToken(extraClaims, userDetails.getUsername(), expiration));
        } catch (Exception e) {
            throw new TokenException.TokenGenerationException("Failed to generate token", e);
        }
    }

    // Helper method to build a JWT token
    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        long expirationMillis = expiration * 1000;
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(clock.millis()))
                .expiration(new Date(clock.millis() + expirationMillis))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    // Get the signing key for JWT
    @NotNull
    @Contract(" -> new")
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // Extract claims from a JWT token
    public Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new TokenException.TokenValidationException("JWT: token validation failed", e);
        } catch (Exception e) {
            throw new TokenException.TokenValidationException("Failed to parse claims from token", e);
        }
    }

    // Extract specific claim
    public <T> T extractClaim(String token, @NotNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract expiration date from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUUID(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("uuid");
    }

    // Extract authorities from token
    @SuppressWarnings("unchecked")
    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<String> authorities = (List<String>) claims.get("authorities");
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Extract JwtUserDetails
    public JwtUserDetails extractUserDetails(String token) {
        String username = extractUsername(token);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new JwtUserDetails(user, currencyCloudRepository);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(clock.instant()));
    }

    // Check if the token is valid
    public boolean isTokenValid(String token, @NotNull JwtUserDetails userDetails) {
        final String username = extractUsername(token);
        final String uuid = extractUUID(token);
        final List<GrantedAuthority> authoritiesFromToken = extractAuthorities(token);

        // Convert to Set for comparison
        Set<GrantedAuthority> authoritiesSetFromToken = new HashSet<>(authoritiesFromToken);
        Set<GrantedAuthority> authoritiesSetFromUserDetails = new HashSet<>(userDetails.getAuthorities());

        return (username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token) &&
                uuid.equals(userDetails.getUuid()) &&
                authoritiesSetFromToken.equals(authoritiesSetFromUserDetails));
    }

    // Encrypt the JWT token
    public String encryptToken(String token) {
        try {
            return encryptJWT(token, publicKey);
        } catch (Exception e) {
            throw new TokenException.TokenGenerationException("Failed to encrypt token", e);
        }
    }

    // Decrypt the JWT token
    public String decryptToken(String encryptedToken) {
        try {
            return decryptJWT(encryptedToken, privateKey);
        } catch (Exception e) {
            throw new TokenException.TokenValidationException("Failed to decrypt token", e);
        }
    }

    // Encrypt JWT using JWE
    private String encryptJWT(String signedJWT, PublicKey publicKey) throws JOSEException {
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM)
                .contentType("JWT")
                .build();

        JWEObject jweObject = new JWEObject(header, new Payload(signedJWT));
        jweObject.encrypt(new RSAEncrypter((RSAPublicKey) publicKey));

        return jweObject.serialize();
    }

    // Decrypt JWE to get the signed JWT
    private String decryptJWT(String jwe, PrivateKey privateKey) throws JOSEException, ParseException {
        JWEObject jweObject = JWEObject.parse(jwe);
        jweObject.decrypt(new RSADecrypter(privateKey));

        return jweObject.getPayload().toSignedJWT().serialize();
    }

    private PublicKey loadPublicKey() throws Exception {
        String key = loadKey(PUBLIC_KEY_PATH);
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keySpec);
    }

    private PrivateKey loadPrivateKey() throws Exception {
        String key = loadKey(PRIVATE_KEY_PATH);
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    @NotNull
    @Contract("_ -> new")
    private String loadKey(String resourcePath) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new TokenException.TokenGenerationException("Key resource not found: " + resourcePath);
        }
        return new String(inputStream.readAllBytes());
    }
}
