package com.SnakeApp.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceFoundException (String message)
    {
        super(message);
    }
}
