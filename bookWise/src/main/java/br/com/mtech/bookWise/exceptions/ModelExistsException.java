package br.com.mtech.bookWise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ModelExistsException extends Exception{
    public ModelExistsException(String message){
        super(message);
    }
}
