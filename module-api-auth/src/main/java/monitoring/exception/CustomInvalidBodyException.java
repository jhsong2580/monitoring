package monitoring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class CustomInvalidBodyException extends RuntimeException{
    private final BindingResult bindingResult;
}
