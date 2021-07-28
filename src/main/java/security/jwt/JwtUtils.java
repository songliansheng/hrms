package security.jwt;

import icu.debris.hrms.security.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtUtils {



    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);



    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final String jwtIssuer = "example.io";

    public String generateJwtToken(User user) {
        System.out.println(Encoders.BASE64.encode(key.getEncoded()));
        return Jwts.builder()
                .setSubject(format("%s", user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(key)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        System.out.println(Encoders.BASE64.encode(key.getEncoded()));
//        try
//        {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
//        }
//        catch (SecurityException ex) {
//
//            logger.error("Invalid JWT signature - {}", ex.getMessage());
//        } catch (MalformedJwtException ex) {
//            logger.error("Invalid JWT token - {}", ex.getMessage());
//        } catch (ExpiredJwtException ex) {
//            logger.error("Expired JWT token - {}", ex.getMessage());
//        } catch (UnsupportedJwtException ex) {
//            logger.error("Unsupported JWT token - {}", ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            logger.error("JWT claims string is empty - {}", ex.getMessage());
//        }
//        catch (SignatureException ex){
//            logger.error("JWT signature 無效 - {}", ex.getMessage());
//        }
//        return false;
    }

}
