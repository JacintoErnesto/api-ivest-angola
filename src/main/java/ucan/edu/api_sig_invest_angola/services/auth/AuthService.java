package ucan.edu.api_sig_invest_angola.services.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.auth.*;

@Service
public interface AuthService {
    public AuthReturnDTO criarConta(AuthContaRequestDTO authRequestDTO);
    public AuthReturnDTO login(AutenticatioRequestDTO autenticatioRequest);
    public AuthContaReturnDTO buscarContaPorUsername(String username);
    public boolean resetarPassword(Long id, ResetPasswordRequestDTO resetPasswordRequestDTO);
    public Page<AuthContaReturnDTO> buscarContasPorTipoConta(String tipoConta, PageRequest pageable);
    public Page<AuthContaReturnDTO> buscartodas(PageRequest pageable);
}
