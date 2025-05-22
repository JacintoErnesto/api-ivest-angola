package ucan.edu.api_sig_invest_angola.models.empreendedor;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;
import ucan.edu.api_sig_invest_angola.models.endereco.Endereco;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_empreendedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empreendedor {
    public Empreendedor(String nomeEmpreendedor, String nifEmpreendedor, Conta conta, LocalDateTime dataRegistro) {
        this.nomeEmpreendedor = nomeEmpreendedor;
        this.nifEmpreendedor = nifEmpreendedor;
        this.conta = conta;
        this.dataRegistro = dataRegistro;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_empreendedor")
    private String nomeEmpreendedor;
    @Column(name = "nif_empreendedor")
    private String nifEmpreendedor;
    @Column(name = "telefone_empreendedor")
    private String telefoneEmpreendedor;
    @Column(name = "email_empreendedor")
    private String emailEmpreendedor;
    @OneToOne
    @JoinColumn(name = "conta_id", referencedColumnName = "id")
    private Conta conta;
    @ManyToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "anexo_bilhete")
    private byte[] anexoBilhete;

    @Column(name = "nome_anexo_bilhete")
    private String nomeAnexoBilhete;

    @Column(name = "tipo_mime_anexo_bilhete")
    private String tipoMimeAnexoBilhete;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "anexo_nif")
    private byte[] anexoNif;

    @Column(name = "nome_anexo_nif")
    private String nomeAnexoNif;

    @Column(name = "tipo_mime_anexo_nif")
    private String tipoMimeAnexoNif;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "anexo_cartao_municipe")
    private byte[] anexoCartaoMunicipe;

    @Column(name = "nome_anexo_cartao_municipe")
    private String nomeAnexoCartaoMunicipe;

    @Column(name = "tipo_mime_anexo_cartao_municipe")
    private String tipoMimeAnexoCartaoMunicipe;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "anexo_registro_criminal")
    private byte[] anexoRegistroCriminal;

    @Column(name = "nome_anexo_registro_criminal")
    private String nomeAnexoRegistroCriminal;

    @Column(name = "tipo_mime_anexo_registro_criminal")
    private String tipoMimeAnexoRegistroCriminal;

    @Timestamp
    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;
}
