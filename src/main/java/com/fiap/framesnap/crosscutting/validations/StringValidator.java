package com.fiap.framesnap.crosscutting.validations;

public class StringValidator {

    public static boolean isNullOrEmpty(String value){
        return value == null || value.isBlank();
    }

}
