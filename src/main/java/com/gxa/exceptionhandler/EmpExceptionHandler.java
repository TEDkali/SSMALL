package com.gxa.exceptionhandler;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ControllerAdvice
public class EmpExceptionHandler {

    @ExceptionHandler({Exception.class})
    public String error(Exception e){
        System.out.println("Exception happen");
        e.printStackTrace();
        return "error";
    }
}
