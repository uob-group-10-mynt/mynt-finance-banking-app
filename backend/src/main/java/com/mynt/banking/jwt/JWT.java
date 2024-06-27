//package com.mynt.banking.jwt;
//
//import java.security.NoSuchAlgorithmException;
//import java.text.ParseException;
//import java.util.Base64;
//import java.util.Date;
//import javax.crypto.KeyGenerator;
//
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jose.crypto.MACVerifier;
//import com.nimbusds.jwt.SignedJWT;
//import com.nimbusds.jwt.JWTClaimsSet;
//import io.github.cdimascio.dotenv.Dotenv;
//
//import static io.github.cdimascio.dotenv.DslKt.dotenv;
//
//// create function too
//// 1.create header that has algo and type
//// 2.add infomation(claims) into data
//// 3.hash steps 1 and 2 to create the secret key
//
//// ==========================================
//// Notes:
//// Recommended Java Libraries for JWT
//// Java JWT (jjwt)
//// Nimbus JOSE+JWT
//// Auth0 Java JWT
//
//public class JWT {
//
//    private static byte[] secretKey;
//    private static long expirationTimeMillis = 30000;
//    private Dotenv dotenv;
//
//    // Generate a secret key for HMAC-SHA256 (HS256)
//    // copy and paste secutity key into .env file into the JWT_SECRET_KEY fild
//    public static String generateSecretKey() {
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//            keyGenerator.init(256); // Specify the key size
//            byte[] secretKey =keyGenerator.generateKey().getEncoded();
//            String base64SecretKey = Base64.getEncoder().encodeToString(secretKey);
//            return base64SecretKey;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void loadSecretKey()  {
//        Dotenv dotenv = Dotenv.configure().filename(".env").load();
//        secretKey = dotenv.get("JWT_SECRET_KEY").getBytes();
//    }
//
//    public void setExpirationTimeMillis(long time) {
//        expirationTimeMillis = time;
//    }
//
//    public String createJWT(String clientName) {
//        loadSecretKey();
//        try {
//
//            // 1.create header that has algo and type
//            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
//
//            // 2.add infomation(claims) into data
//            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                    .subject(clientName)
//                    .expirationTime(new Date(new Date().getTime() + expirationTimeMillis))
//                    .build();
//
//            SignedJWT jwt = new SignedJWT(header, claimsSet);
//
//            // Create HMAC signer
//            // 3. create signature
//            JWSSigner signer = new MACSigner(secretKey);
//
//            // Apply the HMAC protection
//            jwt.sign(signer);
//
//            // Serialize to compact form
//            return jwt.serialize();
//
//        } catch (JOSEException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public boolean authenticateJWT(String token) {
//        loadSecretKey();
//
//        try {
//
//            // Parse the token
//            SignedJWT jwt = SignedJWT.parse(token);
//
//            // Create HMAC verifier
//            JWSVerifier verifier =  new MACVerifier(secretKey);
//            System.out.println(new MACVerifier(secretKey).toString());
//
//            // Verify the token's HMAC
//            if (jwt.verify(verifier)) {
//
//                // Check expiration time
//                Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
//                if (!new Date().before(expirationTime)) { return false; }
//
//                // add rules here to check the payload/data of the JWT
//
//                return true;
//            } else {
//                return false;
//            }
//        } catch (ParseException | JOSEException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//}
