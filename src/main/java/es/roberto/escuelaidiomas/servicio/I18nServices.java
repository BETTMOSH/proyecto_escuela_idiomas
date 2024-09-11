package es.roberto.escuelaidiomas.servicio;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@Data
public class I18nServices {
    private final MessageSource messageSource;

   public String getMessage(String label) {
     return messageSource.getMessage(label, null, LocaleContextHolder.getLocale());
   }

   public String getMessage(String label, Object[] args) {
     return messageSource.getMessage(label, args, LocaleContextHolder.getLocale());
   }

}
