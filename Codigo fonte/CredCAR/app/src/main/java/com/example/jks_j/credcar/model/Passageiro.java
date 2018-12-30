package com.example.jks_j.credcar.model;

import android.content.ContentValues;

public class Passageiro {

    private int id;
    private String nome;
    private int avatar;

    public Passageiro(String nome, int avatar) {
        this.nome = nome;
        this.avatar = avatar;
    }

    public Passageiro(int id,String nome, int avatar) {
        this.id = id;
        this.nome = nome;
        this.avatar = avatar;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("nome", this.nome);
        cv.put("avatar",this.avatar);
        return cv;
    }

    public ContentValues getContentValuesEditar() {
        ContentValues cv = new ContentValues();
        cv.put("id", this.id);
        cv.put("nome", this.nome);
        cv.put("avatar",this.avatar);

        return cv;
    }

}
