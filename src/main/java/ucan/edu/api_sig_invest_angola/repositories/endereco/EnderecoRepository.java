package ucan.edu.api_sig_invest_angola.repositories.endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.endereco.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Page<Endereco> findAllByNomeBairroContainingIgnoreCase(String nomeBairro, PageRequest pageRequest);

    Page<Endereco> findAllByNomeRuaContainingIgnoreCase(String nomeRua, PageRequest pageRequest);

    Page<Endereco> findAllByLocalidadeContainingIgnoreCase(String localidade, PageRequest pageRequest);
}
