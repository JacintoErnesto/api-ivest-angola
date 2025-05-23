package ucan.edu.api_sig_invest_angola.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthContaReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.PaginatorDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestMessageReturnDTO;
import ucan.edu.api_sig_invest_angola.enums.response.ResponseCode;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.PrintWriter;
import java.io.Serial;
import java.io.StringWriter;
import java.util.Objects;
import org.slf4j.Logger;


public class Resource extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private Logger log;

    //@Autowired
    protected HttpServletRequest request;

    public Resource() {
        super();
    }

    public Resource(String message) {
        super(message);
    }


    public Resource(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseEntity<AuthReturnDTO> okAuthRequestOne(Object object, String message, Object... params) {
        String msg = MessageUtils.getMessage("sucesso");
        if (object != null) {
            AuthReturnDTO rest = new AuthReturnDTO(object, 1, ResponseCode.SUCESSO.getDescricao(), msg);
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        AuthReturnDTO rest = new AuthReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage(message, params));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }
    public ResponseEntity<RestDataReturnDTO> okRequestOne(Object object, String message, Object... params) {
        String msg = MessageUtils.getMessage("sucesso");
        if (object != null) {
            RestDataReturnDTO rest = new RestDataReturnDTO(object, 1, ResponseCode.SUCESSO.getDescricao(), msg);
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage(message, params));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    //RETORNO ADAPTADO PARA SUPORTAR E LEVAR AO RADIS O RECURSO CONSUMIDO.
    public ResponseEntity<RestDataReturnDTO> okRequestOneCaching(Object object, String message, Object... params) {
        String msg = MessageUtils.getMessage("sucesso");
        if(object!= null) {
            RestDataReturnDTO rest = new RestDataReturnDTO(object, 1, ResponseCode.SUCESSO.getDescricao(), msg);
            return new ResponseEntity<>(rest, HttpStatus.OK);

        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage(message,params));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    //RETORNO ADAPTADO PARA SUPORTAR E LEVAR AO RADIS O RECURSO CONSUMIDO.
    public ResponseEntity<RestDataReturnDTO> okRequestCaching(Object object) {
        String msg = MessageUtils.getMessage("sucesso");

        if(object!= null) {
            RestDataReturnDTO rest = new RestDataReturnDTO(object, ResponseCode.SUCESSO.getDescricao(), msg);
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage("sem.nada.no.retorno"));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> okRequestOneMensage(Object object, String message, Object... params) {
        String msg = MessageUtils.getMessage(message, params);
        if (object != null) {
            RestDataReturnDTO rest = new RestDataReturnDTO(object, 1, ResponseCode.SUCESSO.getDescricao(), msg);
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), msg);
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> okRequest(Object object) {
        String msg = MessageUtils.getMessage("sucesso");

        if (object != null) {
            RestDataReturnDTO rest = new RestDataReturnDTO(object, ResponseCode.SUCESSO.getDescricao(), msg);
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage("sem.nada.no.retorno"));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> notFoundRequest(String msgCode, Object object) {
        RestDataReturnDTO rest = new RestDataReturnDTO(null, 0,
                ResponseCode.NENHUM_RESGISTRO.getDescricao(), msgCode);
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<AuthReturnDTO> notFoundAuthRequest(String msgCode, Object object) {
        AuthReturnDTO rest = new AuthReturnDTO(null, 0,
                ResponseCode.NENHUM_RESGISTRO.getDescricao(), msgCode);
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> businesRuleRequest(String msgCode) {
        RestDataReturnDTO rest = new RestDataReturnDTO(null, 0, ResponseCode.REGRA_NEGOCIO.getDescricao(), msgCode);
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> businesRuleRequestToken(String msgCode) {
        RestDataReturnDTO rest = new RestDataReturnDTO(null, 0, ResponseCode.TOKEN_REJECT.getDescricao(), msgCode);
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> BadRuleRequest(String msgCode) {
        RestDataReturnDTO rest = new RestDataReturnDTO(null, 0, ResponseCode.ERRO_INTERNO.getDescricao(), msgCode);
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> okRequest(Object object, int pageNumber, long pageNumberOfElements, int totalPages, int totalElements, String msgCode) {
        if (object != null) {
            RestDataReturnDTO rest = new RestDataReturnDTO(object, new PaginatorDTO(pageNumber, pageNumberOfElements, totalPages), totalElements, ResponseCode.SUCESSO.getDescricao(), msgCode);
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage("sem.nada.no.retorno"));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }

    public ResponseEntity<RestDataReturnDTO> okRequestGenericPaginado(Page<Object> pageObject) {
        String msg = MessageUtils.getMessage("sucesso");
        pageObject.getContent();
        if (pageObject.getTotalElements() > 0) {
            RestDataReturnDTO rest = new RestDataReturnDTO(pageObject.getContent(), ResponseCode.SUCESSO.getDescricao(), msg);
            rest.setQuantidadeTotalItens(pageObject.getContent().size());
            rest.setPaginator(new PaginatorDTO(pageObject.getNumber(), pageObject.getTotalElements(), pageObject.getTotalPages()));
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage("sem.nada.no.retorno"));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }
    public <T> ResponseEntity<RestDataReturnDTO> okRequestPaginado(Page<T> pageObject) {
        String msg = MessageUtils.getMessage("sucesso");

        if (pageObject.getTotalElements() > 0) {
            RestDataReturnDTO rest = new RestDataReturnDTO(
                    pageObject.getContent(),
                    ResponseCode.SUCESSO.getDescricao(),
                    msg
            );
            rest.setQuantidadeTotalItens(pageObject.getContent().size());
            rest.setPaginator(new PaginatorDTO(
                    pageObject.getNumber(),
                    pageObject.getTotalElements(),
                    pageObject.getTotalPages()
            ));
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }

        RestDataReturnDTO rest = new RestDataReturnDTO(
                ResponseCode.NENHUM_RESGISTRO.getDescricao(),
                MessageUtils.getMessage("sem.nada.no.retorno")
        );
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }
    public ResponseEntity<RestDataReturnDTO> okRequestAuthPaginado(Page<AuthContaReturnDTO> pageObject) {
        String msg = MessageUtils.getMessage("sucesso");
        pageObject.getContent();
        if (pageObject.getTotalElements() > 0) {
            RestDataReturnDTO rest = new RestDataReturnDTO(pageObject.getContent(), ResponseCode.SUCESSO.getDescricao(), msg);
            rest.setQuantidadeTotalItens(pageObject.getContent().size());
            rest.setPaginator(new PaginatorDTO(pageObject.getNumber(), pageObject.getTotalElements(), pageObject.getTotalPages()));
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage("sem.nada.no.retorno"));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }


    private boolean preencheBusinessException(Exception exception, RestDataReturnDTO objectDataReturnDTO) {
        RestMessageReturnDTO messageReturnDTO;
        if (exception instanceof PortalBusinessException businessException) {

            if (businessException.getCodigo() == ResponseCode.REGRA_NEGOCIO.getDescricao()) {
                messageReturnDTO = new RestMessageReturnDTO(businessException.getCodigo(), businessException.getMessage());
                objectDataReturnDTO.setRetorno(messageReturnDTO);
                return true;
            }
        }
        return false;
    }

    private boolean preencheAuthBusinessException(Exception exception, AuthReturnDTO objectDataReturnDTO) {
        RestMessageReturnDTO messageReturnDTO;
        if (exception instanceof PortalBusinessException businessException) {

            if (businessException.getCodigo() == ResponseCode.REGRA_NEGOCIO.getDescricao()) {
                messageReturnDTO = new RestMessageReturnDTO(businessException.getCodigo(), businessException.getMessage());
                objectDataReturnDTO.setRetorno(messageReturnDTO);
                return true;
            }
        }
        return false;
    }
    public ResponseEntity<RestDataReturnDTO> badRequest(String messageKey, Object[] params, Exception exception) {
        RestDataReturnDTO objectDataReturnDTO = new RestDataReturnDTO();
        RestMessageReturnDTO messageReturnDTO = null;
        String msg = null;

        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        this.getLog().error("Ocorreu um erro: " + sw.toString());

        if (preencheBusinessException(exception, objectDataReturnDTO))
            return new ResponseEntity<>(objectDataReturnDTO,HttpStatus.OK);


        if (!Utils.isEmpty(messageKey)) {
            messageKey = "erro";
            msg = MessageUtils.getMessage(messageKey, params);
        }

        messageReturnDTO = new RestMessageReturnDTO(ResponseCode.ERRO_INTERNO.getDescricao(), msg);
        objectDataReturnDTO.setRetorno(messageReturnDTO);
        return new ResponseEntity<>(objectDataReturnDTO, HttpStatus.OK);
    }

    public ResponseEntity<AuthReturnDTO> badAuthRequest(String messageKey, Object[] params, Exception exception) {
        AuthReturnDTO objectDataReturnDTO = new AuthReturnDTO();
        RestMessageReturnDTO messageReturnDTO = null;
        String msg = null;

        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        this.getLog().error("Ocorreu um erro: " + sw.toString());

        if (preencheAuthBusinessException(exception, objectDataReturnDTO))
            return new ResponseEntity<>(objectDataReturnDTO,HttpStatus.OK);


        if (!Utils.isEmpty(messageKey)) {
            messageKey = "erro";
            msg = MessageUtils.getMessage(messageKey, params);
        }

        messageReturnDTO = new RestMessageReturnDTO(ResponseCode.ERRO_INTERNO.getDescricao(), msg);
        objectDataReturnDTO.setRetorno(messageReturnDTO);
        return new ResponseEntity<>(objectDataReturnDTO, HttpStatus.OK);
    }
    public Logger getLog() {
        if (log == null) {
            log = (Logger) LoggerFactory.getLogger(getClassName());
        }
        return log;
    }

    @SuppressWarnings("rawtypes")
    public Class getClassName() {
        Class<?> enclosingClass = getClass().getEnclosingClass();
        return Objects.requireNonNullElseGet(enclosingClass, this::getClass);
    }

    public ResponseEntity<RestDataReturnDTO> okRequest(Object object,int totalElements, String msgCode) {
        if (object != null) {
            RestDataReturnDTO rest = new RestDataReturnDTO(object, totalElements, ResponseCode.SUCESSO.getDescricao(), msgCode);
            return new ResponseEntity<>(rest, HttpStatus.OK);
        }
        RestDataReturnDTO rest = new RestDataReturnDTO(ResponseCode.NENHUM_RESGISTRO.getDescricao(), MessageUtils.getMessage("sem.nada.no.retorno"));
        return new ResponseEntity<>(rest, HttpStatus.OK);
    }
}
