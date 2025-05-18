package ucan.edu.api_sig_invest_angola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByUsername(String username);
}
