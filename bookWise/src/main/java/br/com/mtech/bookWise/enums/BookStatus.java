package br.com.mtech.bookWise.enums;

public enum BookStatus {
    OCCUPED("Ocupado"),
    AVAILABLE("Disponível");

    private String status;

    BookStatus(String status){
        this.status = status;
    }
}
