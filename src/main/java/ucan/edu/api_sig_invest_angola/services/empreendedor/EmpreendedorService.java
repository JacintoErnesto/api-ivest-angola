package ucan.edu.api_sig_invest_angola.services.empreendedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRetornoDTO;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;
import ucan.edu.api_sig_invest_angola.models.empreendedor.Empreendedor;

@Service
public interface EmpreendedorService {
    public Empreendedor create(EmpreendedorRequestDTO empreendedorResquetDTO, Conta conta);

    Page<EmpreendedorRetornoDTO> buscarTodos(PageRequest pageRequest);

    EmpreendedorRetornoDTO atualizar(Long id, String empreendedorJson,
                                     MultipartFile anexoBilhete, MultipartFile anexoNif,
                                     MultipartFile anexoCartaoMunicipe, MultipartFile anexoRegistroCriminal);

    EmpreendedorRetornoDTO buscarPorId(Long id);

    Page<EmpreendedorRetornoDTO> buscarPorLocalidade(String LocalidadeId, PageRequest request);

    EmpreendedorRetornoDTO buscarEmpreendedorPorConta(Long contaId);

    void deletar(Long EmpreendedorId);
    Empreendedor buscarModelPeloId(Long id);

}
