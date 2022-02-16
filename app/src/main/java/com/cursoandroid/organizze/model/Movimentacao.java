package com.cursoandroid.organizze.model;

import android.content.Intent;

import com.cursoandroid.organizze.config.ConfiguracaoFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
import com.cursoandroid.organizze.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Movimentacao implements Comparable<Movimentacao>{
    private String data, descricao, tipo, key;
    private Double valor;

    public Movimentacao() {
    }

    public void salvar(){
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

    @Override
    public int compareTo(Movimentacao movimentacao) {
        String aux [] = this.data.split("/");
        int diaThis = Integer.parseInt(aux[0]);
        String aux2[] = movimentacao.data.split("/");
        int diaMovimentacao = Integer.parseInt(aux2[0]);
        if(diaThis < diaMovimentacao){
            return -1;
        }
        else if(diaThis > diaMovimentacao){
            return 1;
        }
        return this.getData().compareToIgnoreCase(movimentacao.getData());
    }
}
