package com.cursoandroid.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.helper.DateCustom;
import com.cursoandroid.organizze.model.Movimentacao;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText txtData, txtDescricao, txtCategoria;
    private EditText txtValor;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        txtData = findViewById(R.id.txtData);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtCategoria = findViewById(R.id.txtCategoria);
        txtValor = findViewById(R.id.txtValor);

        //data atual no txtData
        txtData.setText(DateCustom.dataAtual());
    }

    public void salvarDespesa(View view){
        movimentacao = new Movimentacao();
        movimentacao.setValor(Double.parseDouble(txtValor.getText().toString()));
        movimentacao.setCategoria(txtCategoria.getText().toString());
        movimentacao.setDescricao(txtDescricao.getText().toString());
        movimentacao.setData(txtData.getText().toString());
        movimentacao.setTipo("d");

        movimentacao.salvar(txtData.getText().toString());
    }
}
