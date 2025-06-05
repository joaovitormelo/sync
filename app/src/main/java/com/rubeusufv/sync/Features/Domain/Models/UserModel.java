package com.rubeusufv.sync.Features.Domain.Models;

public class UserModel {

    private int id;
    private String tokenRubeus;
    private int idRubeus;
    private int idGoogle;
    private String name;
    private String password;

    public int getIdRubeus() {
        return idRubeus;
    }

    public void setIdRubeus(int idRubeus) {
        this.idRubeus = idRubeus;
    }

    public int getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(int idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTokenRubeus() {
        return tokenRubeus;
    }

    public void setTokenRubeus(String tokenRubeus) {
        this.tokenRubeus = tokenRubeus;
    }

    public UserModel(
        int id, String tokenRubeus, int idRubeus, int idGoogle, String name, String password
    ) {
        this.id = id;
        this.tokenRubeus = tokenRubeus;
        this.idRubeus = idRubeus;
        this.idGoogle = idGoogle;
        this.name = name;
        this.password = password;
    }

    public static UserModel getMock() {
        return new UserModel(1, "123",1, 1, "Joao", "123");
    }
}
