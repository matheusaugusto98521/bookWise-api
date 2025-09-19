package br.com.mtech.bookWise.enums;

public enum Roles {
    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    Roles(String role){
        this.role = role;
    }
}
