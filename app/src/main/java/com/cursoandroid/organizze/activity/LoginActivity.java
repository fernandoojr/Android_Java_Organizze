package com.cursoandroid.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.config.ConfiguracaoFirebase;
import com.cursoandroid.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        validarLogin();
                    }else{
                        Toast.makeText(LoginActivity.this, "Preencha a senha!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha o email!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            abrirTelaPrincipal();
                        }else{
                            String excecao = "";
                            try{
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "Senha inválida";
                            }
                            catch (FirebaseAuthInvalidUserException e){
                                excecao = "Usuário não está cadastrado";
                            }catch (Exception e){
                                excecao = "Erro ao fazer login: "+e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }
}
