package Gruppo4BW2BE.BW2.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public class ValidationsException extends RuntimeException {
    public ValidationsException(List<String> message) {
        super((Throwable) message);
    }
}
