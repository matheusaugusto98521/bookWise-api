package br.com.mtech.bookWise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullInformationException extends Exception{
    public NullInformationException(String message){
        super(message);
    }
}
