package com.example.demo.jwt;

import com.example.demo.entities.Empleado;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${app.jwt.expires}")
    private long EXPIRE_DURATION; // 1 hora

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(Empleado empleado) {
        return Jwts.builder()
                .setSubject(String.format("%s, %s", empleado.getId(), empleado.getEmail()))
                .setIssuer("v1llaDEV")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("rol", empleado.getRol().getNombre())
                .compact();

    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expirado", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token es nulo o vacío", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("JWT es inválido", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("JWT no es soportado", ex);
        } catch (SignatureException ex) {
            log.error("Ha fallado la firma");
        }

        return false;
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
