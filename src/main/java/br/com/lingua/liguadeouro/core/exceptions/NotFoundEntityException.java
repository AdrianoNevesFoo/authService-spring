package br.com.lingua.liguadeouro.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundEntityException extends Exception {
    public NotFoundEntityException(String message) {
        super(message);
    }
}
