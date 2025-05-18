package ucan.edu.api_sig_invest_angola.services.serviceImpls.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.services.auth.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration}") // em milissegundos
    private Long jwtExpirationMs;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (secretKeyString == null || secretKeyString.isEmpty()) {
            throw new IllegalArgumentException("Palavra chave nao foi recuperada");
        }
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
        log.info("Palavra chave JWT inicializada");
    }

    @Override
    public String extrairUsernameToken(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extrairExpiration(String token) {
        return extrairClaim(token, Claims::getExpiration);
    }


    private Claims extrairAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpirado(String token) {
        return extrairExpiration(token).before(new Date());
    }

    public String gerarToken(Map<String, Object> claims,UserDetails userDetails) {
        return gerarandoToken(claims, userDetails);
    }

    private String gerarandoToken(Map<String, Object> extractClams,UserDetails userDetails) {
        Date agora = new Date(System.currentTimeMillis());
        Date expiracao = new Date(agora.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(extractClams)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        final String usernameDoToken = extrairUsernameToken(token);
        return (usernameDoToken.equals(userDetails.getUsername()) && !isTokenExpirado(token));
    }
}

