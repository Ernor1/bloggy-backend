package rw.global.qt.bloggy.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import rw.global.qt.bloggy.exceptions.JWTVerificationException;
import rw.global.qt.bloggy.models.Role;
import rw.global.qt.bloggy.security.user.UserSecurityDetails;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecretKey;
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_EMAIL = "email";
    private static final String CLAIM_KEY_ROLE = "role";

    public String extractUsername(String token){
        return extractClaim(token , Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }

    public boolean hasClaim(String token     , String claimName ){
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        Date expirationDate = extractExpiration(token);
        Date currentTime  = new Date(System.currentTimeMillis());
        return !currentTime.before(expirationDate);
    }

        public String createToken(UUID userId , String email , List<GrantedAuthority> roles){

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH , 1);

        return  Jwts.builder()
                .claim(CLAIM_KEY_USER_ID , userId)
                .claim(CLAIM_KEY_EMAIL , email)
                .claim(CLAIM_KEY_ROLE , roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256 , jwtSecretKey).compact();
    }

    public JwtUserInfo decodeToken(String token) throws JWTVerificationException {
        Claims claims = extractAllClaims(token);
        String userId = (String) claims.get(CLAIM_KEY_USER_ID);
        String email = (String) claims.get(CLAIM_KEY_EMAIL);
        List <Role> roles = (List<Role>) claims.get(CLAIM_KEY_ROLE);
        return new JwtUserInfo().setEmail(email)
                .setRole(roles)
                .setUserId(userId);
    }

    public Boolean isTokenValid(String token , UserSecurityDetails userSecurityDetails){
        Claims claims = extractAllClaims(token);
        String email = (String) claims.get(CLAIM_KEY_EMAIL);
        final String username = email;
        return (username.equals(userSecurityDetails.getUsername()) && !isTokenExpired(token));
    }
}
