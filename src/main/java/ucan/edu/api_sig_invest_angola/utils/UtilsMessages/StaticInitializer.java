package ucan.edu.api_sig_invest_angola.utils.UtilsMessages;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


@Component
public class StaticInitializer {

    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    public void init() {
        MessageUtils.setMessageSource(messageSource);
    }
}


