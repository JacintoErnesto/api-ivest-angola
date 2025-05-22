package ucan.edu.api_sig_invest_angola.services.endereco;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoReturnDTO;
import ucan.edu.api_sig_invest_angola.models.endereco.Endereco;

import java.util.List;

@Service
public interface EnderecoService {
    EnderecoReturnDTO create(EnderecoRequestDTO enderecoRequestDTO);

    EnderecoReturnDTO update(Long id, EnderecoRequestDTO enderecoRequestDTO);

    void delete(Long id);

    Page<EnderecoReturnDTO> buscarTodos(PageRequest pageRequest);

    EnderecoReturnDTO buscarPorId(Long id);

    Endereco buscarModelPeloId(Long id);

    Page<EnderecoReturnDTO> buscarPorLocalidade(String localidade, PageRequest pageRequest);

    Page<EnderecoReturnDTO> buscarPorNomeRua(String nomeRua, PageRequest pageRequest);
    Page<EnderecoReturnDTO> buscarPorNomeBairo(String nomeBairro, PageRequest pageRequest);

    public Endereco mapParaEndereco(EnderecoReturnDTO enderecoReturnDTO);
    EnderecoReturnDTO mapParaEnderecoDTO(Endereco endereco);
}
