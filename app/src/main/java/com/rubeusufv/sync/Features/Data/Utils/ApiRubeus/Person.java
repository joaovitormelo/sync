package com.rubeusufv.sync.Features.Data.Utils.ApiRubeus;

public class Person {
    private String codigo;
    private String id;

    public Person(String codigo, String id) {
        this.codigo = codigo;
        this.id = id;
    }

    public String getCodigo() {return codigo;}

    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

}
