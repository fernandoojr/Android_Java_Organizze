package com.cursoandroid.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.config.ConfiguracaoFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
import com.cursoandroid.organizze.helper.DateCustom;
import com.cursoandroid.organizze.model.Movimentacao;
import com.cursoandroid.organizze.model.Usuario;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText txtData, txtDescricao, txtCategoria;
    private EditText txtValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal, despesaPreenchida, despesaAtualizada;


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
        recuperarDespesaTotal();
    }

    public void salvarDespesa(View view){
        if(validarCamposDespesa()) {
            despesaPreenchida = Double.parseDouble(txtValor.getText().toString());
            movimentacao = new Movimentacao();
            movimentacao.setValor(despesaPreenchida);
            movimentacao.setCategoria(txtCategoria.getText().toString());
            movimentacao.setDescricao(txtDescricao.getText().toString());
            movimentacao.setData(txtData.getText().toString());
            movimentacao.setTipo("d");

            despesaAtualizada = despesaPreenchida+despesaTotal;
            atualizarDespesa();

            movimentacao.salvar(txtData.getText().toString());
            finish();
        }
    }

    public Boolean validarCamposDespesa(){
        String valor = txtValor.getText().toString();
        String data = txtData.getText().toString();
        String categoria = txtCategoria.getText().toString();
        String descricao = txtDescricao.getText().toString();
        if(!valor.isEmpty()){
            if(!data.isEmpty()){
                if(!categoria.isEmpty()){
                    if(!descricao.isEmpty()){
                        return true;
                    } else {
                        Toast.makeText(this, "Descrição não foi preenchida", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Categoria não foi preenchida", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(this, "Data não foi preenchida", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Valor não foi preenchido", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void recuperarDespesaTotal(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void atualizarDespesa(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesaAtualizada);
    }
}
