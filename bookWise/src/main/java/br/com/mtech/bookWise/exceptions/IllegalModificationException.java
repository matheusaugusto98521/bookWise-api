package br.com.mtech.bookWise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalModificationException extends Exception{
    public IllegalModificationException(String message) {
        super(message);
    }
}
