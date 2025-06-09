package com.rubeusufv.sync.Features.Domain.Models;

import android.content.ContentValues;

import com.rubeusufv.sync.Tools.Database.DatabaseEntry;

public class UserModel {

    private int id;
    private String tokenRubeus;
    private int idRubeus;
    private int idGoogle;

    private String email;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserModel () {
        // TODO: adicionar valores padrao
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

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", this.id);
        values.put("tokenRubeus", this.tokenRubeus);
        values.put("idRubeus", this.idRubeus);
        values.put("idGoogle", this.idGoogle);
        values.put("name", this.name);
        values.put("email", this.email);
        values.put("password", this.password);

        return values;
    }

    public static UserModel fromDatabaseEntry(DatabaseEntry entry) {
        UserModel user = new UserModel();

        // Trata inteiros
        Integer idRubeus = entry.getAsInteger("idRubeus");
        Integer idGoogle = entry.getAsInteger("idGoogle");

        user.setId(entry.getAsInteger("id"));
        user.setIdRubeus(idRubeus != null ? idRubeus : 0);
        user.setIdGoogle(idGoogle != null ? idGoogle : 0);

        user.setName(entry.getAsString("name"));
        user.setEmail(entry.getAsString("email"));
        user.setPassword(entry.getAsString("password"));

        return user;
    }
    public static UserModel getMock() {
        return new UserModel(1, "123",1, 1, "Joao", "123");
    }
}
