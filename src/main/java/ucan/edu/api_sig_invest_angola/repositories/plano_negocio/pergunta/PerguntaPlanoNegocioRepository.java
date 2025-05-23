package ucan.edu.api_sig_invest_angola.repositories.plano_negocio.pergunta;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.plano_negocio.pergunta.PerguntaPlanoNegocio;

import java.util.Optional;

public interface PerguntaPlanoNegocioRepository extends JpaRepository<PerguntaPlanoNegocio, Long> {

    Optional<PerguntaPlanoNegocio> findByPergunta(String pergunta);
}