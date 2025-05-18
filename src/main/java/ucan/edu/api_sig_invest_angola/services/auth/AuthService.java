package ucan.edu.api_sig_invest_angola.services.auth;

import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.auth.AutenticatioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthrReturnDTO;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;

@Service
public interface AuthService {
    public AuthrReturnDTO criarConta(AuthRequestDTO authRequestDTO);
    public AuthrReturnDTO login(AutenticatioRequestDTO autenticatioRequest);
    public Conta buscarContaPorUsername(String username);
}
