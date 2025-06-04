package com.rubeusufv.sync.Features.Domain.Models;

public class User {

    private int id;
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

    public User(int id, int idRubeus, int idGoogle, String name, String password) {
        this.id = id;
        this.idRubeus = idRubeus;
        this.idGoogle = idGoogle;
        this.name = name;
        this.password = password;
    }

    public static User getMock() {
        return new User(1, 1, 1, "Joao", "123");
    }
}
