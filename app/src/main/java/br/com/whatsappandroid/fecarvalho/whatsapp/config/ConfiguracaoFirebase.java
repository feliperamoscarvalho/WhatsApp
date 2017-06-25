package br.com.whatsappandroid.fecarvalho.whatsapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Felipe on 24/06/2017.
 */

public final class ConfiguracaoFirebase {

    //O final impede que essa classe seja extendida

    //Atributo static irá fazer com o que valor seja o mesmo independente das instâncias dessa classe
    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth firebaseAutenticacao;

    public static DatabaseReference getFirebase(){
        //Métodos static permitem a execução diretamente, sem precisar instanciar a classe onde ele se encontra

        if (referenciaFirebase == null){
            //A configuração do banco de dados é feita quando vincula o projeto Firebase ao projeto Android,
            //dessa forma, não é necessária uma "string de conexão", ao obter a referência já está obtendo
            //o acesso ao banco de dados desejado
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return referenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        //obtém a instância do Firebase responsável pela autenticação

        if(firebaseAutenticacao == null){
            firebaseAutenticacao = FirebaseAuth.getInstance();
        }

        return firebaseAutenticacao;
    }
}
