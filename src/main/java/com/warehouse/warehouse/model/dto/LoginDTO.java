package com.warehouse.warehouse.model.dto;

public class LoginDTO {
    private String name;
    private String password;

    public LoginDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
