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
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText txtData, txtDescricao, txtCategoria;
    private EditText txtValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double receitaTotal, receitaPreenchida, receitaAtualizada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        txtData = findViewById(R.id.txtData);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtValor = findViewById(R.id.txtValor);

        //data atual no txtData
        txtData.setText(DateCustom.dataAtual());

        SimpleMaskFormatter smfDt = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwDt = new MaskTextWatcher(txtData, smfDt);
        txtData.addTextChangedListener(mtwDt);

        recuperarReceitaTotal();
    }

    public void salvarReceita(View view){
        if(validarCamposReceita()) {
            receitaPreenchida = Double.parseDouble(txtValor.getText().toString().replace(",", "."));
            movimentacao = new Movimentacao();
            movimentacao.setValor(receitaPreenchida);
            movimentacao.setDescricao(txtDescricao.getText().toString());
            movimentacao.setData(txtData.getText().toString());
            movimentacao.setTipo("r");

            receitaAtualizada = receitaPreenchida+receitaTotal;
            atualizarReceita();

            movimentacao.salvar();
            finish();
        }
    }

    public Boolean validarCamposReceita(){
        try {
            Double valor = Double.parseDouble(txtValor.getText().toString().replace(",","."));
            String data = txtData.getText().toString();
            String descricao = txtDescricao.getText().toString();
            if (valor > 0) {
                if (!data.isEmpty()) {
                    if (!descricao.isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(this, "Descrição não foi preenchida", Toast.LENGTH_LONG).show();
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
        } catch (Exception e) {
            Toast.makeText(this, "Algum campo está preenchido incorretamente! ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void recuperarReceitaTotal(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void atualizarReceita(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(receitaAtualizada);
    }
}
