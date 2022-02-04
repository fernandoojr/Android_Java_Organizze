package com.cursoandroid.organizze.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;


    public static DatabaseReference getFirebaseDatabase(){
        if(firebase == null)
            firebase = FirebaseDatabase.getInstance("https://organizze-42473-default-rtdb.firebaseio.com/").getReference();

        return firebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null)
            autenticacao = FirebaseAuth.getInstance();

        return autenticacao;
    }

}
