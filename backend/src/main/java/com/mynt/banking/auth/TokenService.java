package com.mynt.banking.auth;

import com.mynt.banking.user.Role;
import com.mynt.banking.util.exceptions.authentication.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.*;
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

    private final String expectedIssuer = "mynt-backend-application";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Setter
    private Clock clock = Clock.systemDefaultZone();

    @PostConstruct
    private void init() {
        try {
            publicKey = loadPublicKey();
            privateKey = loadPrivateKey();
        } catch (Exception e) {
            throw new TokenException.TokenGenerationException("Failed to load public key", e);
        }
    }

    public String generateToken(@NotNull MyntUserDetails userDetails) {
        return generateTokenWithExpiration(userDetails, jwtExpiration);
    }

    public String generateRefreshToken(@NotNull MyntUserDetails userDetails) {
        return generateTokenWithExpiration(userDetails, refreshExpiration);
    }

    private String generateTokenWithExpiration(@NotNull MyntUserDetails userDetails, long expiration) {
        return encryptToken(buildJWS(userDetails, expiration));
    }

    private String buildJWS(@NotNull MyntUserDetails userDetails, long expiration) {
        return Jwts.builder()
                .subject(userDetails.username())
                .claim("uuid", userDetails.uuid())
                .claim("role", userDetails.role().name())
                .issuer(expectedIssuer)
                .issuedAt(new Date(clock.millis()))
                .expiration(new Date(clock.millis() + expiration * 1000))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() { return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)); }

    public MyntUserDetails extractUserDetails(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();
            String uuid = claims.get("uuid", String.class);
            Role role = Role.valueOf(claims.get("role", String.class));

            if (!expectedIssuer.equals(claims.getIssuer()) || username.isEmpty() ||
                        username.isBlank() || uuid.isEmpty() || uuid.isBlank()) {
                throw new TokenException.TokenValidationException("JWT: token validation failed");
            }

            return new MyntUserDetails(username, uuid, role);
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new TokenException.TokenValidationException("JWT: token validation failed", e);
        }
    }

    // Encrypt the JWT token
    private String encryptToken(String token) {
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
    private String loadKey(String resourcePath) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new TokenException.TokenGenerationException("Key resource not found: " + resourcePath);
        }
        return new String(inputStream.readAllBytes());
    }
}
