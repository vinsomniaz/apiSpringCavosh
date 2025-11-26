package com.cavosh.model;

public class Cliente {
    private Long id;
    private String nombres;
    private String correo;
    private String passwordd;

    public Cliente() {
    }

    public Cliente(Long id, String nombres, String correo, String passwordd) {
        this.id = id;
        this.nombres = nombres;
        this.correo = correo;
        this.passwordd = passwordd;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPasswordd() {
        return passwordd;
    }
    public void setPasswordd(String passwordd) {
        this.passwordd = passwordd;
    }

    
}