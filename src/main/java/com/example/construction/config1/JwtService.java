package com.example.construction.config1;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "815a4e86ba1043179e6b11cc02454f2cd041a22ce6becdba4bd639e0cf3c1492";
    private String secret;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 72))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                //.build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    private Key getSignInKey() {
//        byte[] keyBytes = SECRET_KEY.getBytes();
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

//    public static String generateSecretKey() {
//        // 256 bits correspondent à 32 bytes
//        int keySize = 32;
//

//        // Créer une instance de générateur de nombres aléatoires sécurisé
//        SecureRandom secureRandom = new SecureRandom();
//
//        // Créer un tableau pour stocker les bytes aléatoires
//        byte[] secretKeyBytes = new byte[keySize];
//
//        // Remplit le tableau avec des bytes aléatoires cryptographiquement sécurisés
//        secureRandom.nextBytes(secretKeyBytes);
//
//        // Convertit le tableau de bytes en chaîne Base64
//        String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKeyBytes);
//
//        // Retourner la clé secrète en Base64
//        return secretKeyBase64;
//    }
//
//    private static final String USERNAME = "username";
//    private static final String ROL = "roles";



    /**
     * Tries to get JwtUser from received token.
     *
     * @param token
     * @return jwtUser
     */
//    public JwtUser parseToken(String token) {
//        try {
//            Claims body = Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            return new JwtUser(
//                    (String) body.get(USERNAME),
//                    (String) body.get(ROL));
//
//        } catch (JwtException | ClassCastException e) {
//            return null;
//        }
//    }

    /**
     * Generate the token from a JwtUser object.
     *
     * @param jwtUser
     * @return the JWT token
     */
//    public String generateToken(JwtUser jwtUser) {
//        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
//        claims.put(USERNAME, jwtUser.getUsername());
//        claims.put(ROL, jwtUser.getRoles());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, secret)
//                //expiration
//                .compact();
//    }


    /**
     * Valid that the token belong to the user.
     *
     * @param token
     * @param userDetails
     * @return boolean
     */
//    public boolean validateToken(String token, UserDetails userDetails){
//        return parseToken(token)
//                .getUsername()
//                .equals(userDetails.getUsername());
//    }



}


