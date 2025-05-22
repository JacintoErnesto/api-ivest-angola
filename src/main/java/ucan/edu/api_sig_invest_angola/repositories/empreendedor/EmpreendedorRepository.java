package ucan.edu.api_sig_invest_angola.repositories.empreendedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.api_sig_invest_angola.models.empreendedor.Empreendedor;

import java.util.Optional;

public interface EmpreendedorRepository extends JpaRepository<Empreendedor, Long> {
    Optional<Empreendedor> findByContaId(Long contaId);

    Page<Empreendedor> findAllByEnderecoLocalidadeIdOrderByNomeEmpreendedor(String localidadeId, PageRequest of);
}
