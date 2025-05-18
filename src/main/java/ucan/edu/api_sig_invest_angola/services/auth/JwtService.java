package ucan.edu.api_sig_invest_angola.services.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService {
    public String extrairUsernameToken(String token);
    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver);
    public Date extrairExpiration(String token);
    public String gerarToken(Map<String, Object> extractClams,UserDetails userDetails);
    public boolean validarToken(String token, UserDetails userDetails);
}
