package ucan.edu.api_sig_invest_angola.utils.UtilsMessages;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtils {

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static String getMessage(String code, Object... params) {
        return MessageUtils.messageSource.getMessage(
                code,
                params,
                LocaleContextHolder.getLocale());
    }
}
