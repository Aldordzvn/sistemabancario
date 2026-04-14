package com.rdzvn.banking.model;

public enum TipoCuenta {
    AHORRO,
    CORRIENTE,
    NOMINA;

    public java.math.BigDecimal montoMinimoApertura(){
        return switch (this){
            case AHORRO -> new java.math.BigDecimal("500.00");
            case CORRIENTE -> new java.math.BigDecimal("1000.00");
            case NOMINA -> new java.math.BigDecimal("0.00");
        };
    }
}
