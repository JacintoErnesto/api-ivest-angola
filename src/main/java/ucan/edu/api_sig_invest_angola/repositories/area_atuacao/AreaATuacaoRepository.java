package ucan.edu.api_sig_invest_angola.repositories.area_atuacao;


import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.area_atuacao.AreaAtuacao;

import java.util.List;
import java.util.Optional;

public interface AreaATuacaoRepository extends JpaRepository<AreaAtuacao, Long> {
    List<AreaAtuacao> findAllByOrderByDesignacaoAsc();

    Optional<AreaAtuacao> findByDesignacao(String areaAtuacao);
}
