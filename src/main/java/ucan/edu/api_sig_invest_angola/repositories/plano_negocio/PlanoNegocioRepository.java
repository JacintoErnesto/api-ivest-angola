package ucan.edu.api_sig_invest_angola.repositories.plano_negocio;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.plano_negocio.PlanoNegocio;

public interface PlanoNegocioRepository extends JpaRepository<PlanoNegocio, Long> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Exemplo: List<PlanoNegocio> findByNome(String nome);
}
