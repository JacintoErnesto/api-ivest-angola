package ucan.edu.api_sig_invest_angola.repositories.localidade;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.localidade.Localidade;

import java.util.List;
import java.util.Optional;

public interface LocalidadeRepository extends JpaRepository<Localidade, String> {
    Optional<Localidade> findByDesignacao(String designacao);
    Optional<Localidade> findById(String id);
    List<Localidade> findAllByLocalidadeId(String localidadeId);
    List<Localidade> findAllByOrderByDesignacaoAsc();

    List<Localidade> findAllByLocalidadeIsNullOrderByDesignacaoAsc();
}
