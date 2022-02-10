package com.cursoandroid.organizze.model;

import com.cursoandroid.organizze.config.ConfiguracaoFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
import com.cursoandroid.organizze.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Movimentacao {
    private String data, categoria, descricao, tipo, key;
    private Double valor;

    public Movimentacao() {
    }

    public void salvar(String dataEscolhida){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        firebase.child("movimentacao")
                .child(idUsuario)
                .child(DateCustom.mesAnoDataEscolhida(this.data))
                .push()
                .setValue(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
