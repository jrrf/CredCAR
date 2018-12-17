package com.example.jks_j.credcar.model;

import android.content.ContentValues;

import java.text.SimpleDateFormat;

public class Situacao {
    private int id_passageiro;
    private String nome_passageiro;
    private double valor;
    private String arquivado;
    private String data;
    private int avatar;

    public Situacao(int id_passageiro, String nome_passageiro, double valor, String arquivado, String data, int avatar) {
        this.id_passageiro = id_passageiro;
        this.nome_passageiro = nome_passageiro;
        this.valor = valor;
        this.arquivado = arquivado;
        this.data = data;
        this.avatar = avatar;
    }

    public int getId_passageiro() {
        return id_passageiro;
    }

    public void setId_passageiro(int id_passageiro) {
        this.id_passageiro = id_passageiro;
    }

    public String getNome_passageiro() {
        return nome_passageiro;
    }

    public void setNome_passageiro(String nome_passageiro) {
        this.nome_passageiro = nome_passageiro;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getArquivado() {
        return arquivado;
    }

    public void setArquivado(String arquivado) {
        this.arquivado = arquivado;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put("id_passageiro",this.id_passageiro);
        cv.put("nome_passageiro",this.nome_passageiro);
        cv.put("valor",this.valor);
        cv.put("arquivado",this.arquivado);
        cv.put("data",this.data);

        return cv;
    }

    public ContentValues getContentValuesHistorico(){
        ContentValues cv = new ContentValues();
        cv.put("id_passageiro",this.id_passageiro);
        cv.put("nome_passageiro",this.nome_passageiro);
        cv.put("valor",this.valor);
        cv.put("data",this.data);

        return cv;
    }
}
