package br.com.lingua.liguadeouro.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UpdateConflictException extends Exception {
    public UpdateConflictException(String message) {
        super(message);
    }
}
