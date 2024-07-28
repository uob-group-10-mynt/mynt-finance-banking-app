package com.mynt.banking.auth;

import com.mynt.banking.currency_cloud.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.CurrencyCloudRepository;
import com.mynt.banking.user.User;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class TokenService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Value("${application.security.jwt.private-key-path}")
    private String privateKeyPath;

    @Value("${application.security.jwt.public-key-path}")
    private String publicKeyPath;

    private CurrencyCloudRepository currencyCloudRepository;

    public String generateToken(@NotNull User user) {
        return generateTokenWithExpiration(user, jwtExpiration);
    }

    public String generateRefreshToken(@NotNull User user) {
        return generateTokenWithExpiration(user, refreshExpiration);
    }

    // Generate a JWT token for UserDetails
    private String generateTokenWithExpiration(@NotNull User user, long expiration) {
        try {
            // Fetch the uuid from CurrencyCloudEntity using usersId
            Optional<CurrencyCloudEntity> currencyCloudEntityOptional = Optional.ofNullable(
                    currencyCloudRepository.findByUsersId(user.getId()));

            String userUUID;
            if (currencyCloudEntityOptional.isPresent()) {
                userUUID = currencyCloudEntityOptional.get().getUuid();
            } else {
                throw new TokenException.TokenGenerationException("UUID not found for user ID: " + user.getId());
            }

            // Add authorities and uuid to the JWT claims
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("authorities", user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            extraClaims.put("uuid", userUUID);

            return encryptToken(buildToken(extraClaims, user.getUsername(), expiration));
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
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
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
    public List<String> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("authorities");
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Check if the token is valid
    public boolean isTokenValid(String token, @NotNull UserDetails userDetails) {
        final String username = extractUsername(token);
        final String uuid = extractUUID(token);
        final List<String> authorities = extractAuthorities(token);

        return (username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token) &&
                isUUIDValid(uuid, userDetails) &&
                areAuthoritiesValid(authorities, userDetails));
    }

    // Check if the UUID is valid
    private boolean isUUIDValid(String uuid, @NotNull UserDetails userDetails) {
        if (userDetails instanceof User user) {
            CurrencyCloudEntity currencyCloudEntity = currencyCloudRepository.findByUsersId(user.getId());
            return currencyCloudEntity != null && currencyCloudEntity.getUuid().equals(uuid);
        }
        return false;
    }

    // Check if the authorities are valid
    private boolean areAuthoritiesValid(List<String> tokenAuthorities, @NotNull UserDetails userDetails) {
        List<String> userAuthorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return new HashSet<>(tokenAuthorities).containsAll(userAuthorities) && new HashSet<>(userAuthorities).containsAll(tokenAuthorities);
    }

    // Encrypt the JWT token
    public String encryptToken(String token) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyPath);
            return encryptJWT(token, publicKey);
        } catch (Exception e) {
            throw new TokenException.TokenGenerationException("Failed to encrypt token", e);
        }
    }

    // Decrypt the JWT token
    public String decryptToken(String encryptedToken) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyPath);
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

    // Read the private key from a file
    private PrivateKey getPrivateKey(String filename) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filename)));
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    // Read the public key from a file
    private PublicKey getPublicKey(String filename) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filename)));
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keySpec);
    }
}
