package com.neelav.patienthub.exceptions;

import java.security.SecureRandom;

public class PatientNotFoundException  extends  RuntimeException{

    public PatientNotFoundException(){
        super();
    }

    public PatientNotFoundException(String message){
        super(message);
    }

    public PatientNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
