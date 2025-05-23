package ucan.edu.api_sig_invest_angola.repositories.plano_negocio.template;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.plano_negocio.template.TemplatePlanoNegocio;

import java.util.List;

public interface TemplatePlanoNegocioRepository extends JpaRepository<TemplatePlanoNegocio, Long> {

    List<TemplatePlanoNegocio> findAllByAreaDeAtuacaoId(Long areAtuacaoId);
    Page<TemplatePlanoNegocio> findAllByAreaDeAtuacaoId(Long areAtuacaoId, PageRequest pageRequest);
}
