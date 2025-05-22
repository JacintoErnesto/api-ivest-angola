package ucan.edu.api_sig_invest_angola.exceptions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ucan.edu.api_sig_invest_angola.dtos.exception.ErroDeFormularioDto;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.enums.response.ResponseCode;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.Utils;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;
import java.util.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErroDeValidacaoHandler extends Resource {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(ErroDeValidacaoHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestDataReturnDTO> handleValidacao(MethodArgumentNotValidException exception) {

        Map<String, String> errosUnicos = new LinkedHashMap<>();

        for (FieldError erro : exception.getBindingResult().getFieldErrors()) {
            String campo = erro.getField();
            String mensagem = erro.getDefaultMessage();
            errosUnicos.putIfAbsent(campo, mensagem);
        }

        List<ErroDeFormularioDto> erros = errosUnicos.entrySet().stream()
                .map(entry -> new ErroDeFormularioDto(entry.getKey(), entry.getValue()))
                .toList();

        RestDataReturnDTO resposta = new RestDataReturnDTO(
                erros,
                erros.size(),
                ResponseCode.REGRA_NEGOCIO.getDescricao(),
                "Corrija os erros de validação nos campos informados."
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }



    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestDataReturnDTO> handle(ConstraintViolationException constraintViolationException) {

        List<ErroDeFormularioDto> erros = new ArrayList<>();

        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation ->{
                builder.append("\n").append(violation.getMessage());
                ErroDeFormularioDto erro = new ErroDeFormularioDto(violation.getPropertyPath().toString(), MessageUtils.getMessage(violation.getMessage(), Utils.getLastWordAfterDot(violation.getPropertyPath().toString())));
                erros.add(erro);
            } );
            errorMessage = builder.toString();

        } else {
            errorMessage = "ConstraintViolationException occured.";
        }
        logger.error(errorMessage);
        RestDataReturnDTO objectDataReturnDTO = new RestDataReturnDTO(erros.get(0), erros.size(), ResponseCode.REGRA_NEGOCIO.getDescricao(), erros.get(0).getMensagemErro());
        return new ResponseEntity<>(objectDataReturnDTO,HttpStatus.OK);
    }

    @ExceptionHandler(value = PortalBusinessException.class)
    public ResponseEntity<RestDataReturnDTO> regraDeNegocioException(PortalBusinessException exception) {
        return super.businesRuleRequest(exception.getMessage());
    }

    @ExceptionHandler(value = BadRequestAlertException.class)
    public ResponseEntity<RestDataReturnDTO> badRequestException(BadRequestAlertException exception) {
        return super.BadRuleRequest(exception.getMessage());
    }
    @ExceptionHandler(value = PortalBusinessTokenRejectException.class)
    public ResponseEntity<RestDataReturnDTO> badTokenRequestException(PortalBusinessTokenRejectException exception) {
        return businesRuleRequestToken(exception.getMessage());
    }

}
