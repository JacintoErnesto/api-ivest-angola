package ucan.edu.api_sig_invest_angola.repositories.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.enums.auth.TipoConta;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByUsername(String username);

    Page<Conta> findAllByTipoContaOrderById(TipoConta tipoConta, PageRequest pageable);

}
