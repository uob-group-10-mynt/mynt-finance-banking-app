package com.mynt.banking.auth;

import com.mynt.banking.currency_cloud.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.CurrencyCloudRepository;
import com.mynt.banking.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private CurrencyCloudRepository currencyCloudRepository;

    public String generateToken(@NotNull User user) {
        return generateTokenWithExpiration(user, jwtExpiration);
    }

    public String generateRefreshToken(@NotNull User user) {
        return generateTokenWithExpiration(user, refreshExpiration);
    }

    // Generate a JWT token for UserDetails
    private String generateTokenWithExpiration(@NotNull User user, long expiration) {
        // Fetch the uuid from CurrencyCloudEntity using usersId
        Optional<CurrencyCloudEntity> currencyCloudEntityOptional = Optional.ofNullable(
                currencyCloudRepository.findByUsersId(user.getId()));

        String userUUID;
        if (currencyCloudEntityOptional.isPresent()) {
            userUUID = currencyCloudEntityOptional.get().getUuid();
        } else {
            throw new RuntimeException("UUID not found for user ID: " + user.getId());
        }

        // Add authorities and uuid to the JWT claims
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        extraClaims.put("uuid", userUUID);

        return buildToken(extraClaims, user.getUsername(), expiration);
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
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
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
    public Long extractUUID(String token) {
        Claims claims = extractAllClaims(token);
        return (Long) claims.get("uuid");
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
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Encrypt the JWT token
    public String encryptToken(String token) {
        // Implement JWE encryption logic here
        return token; // Placeholder
    }

    // Decrypt the JWT token
    public String decryptToken(String encryptedToken) {
        // Implement JWE decryption logic here
        return encryptedToken; // Placeholder
    }
}
