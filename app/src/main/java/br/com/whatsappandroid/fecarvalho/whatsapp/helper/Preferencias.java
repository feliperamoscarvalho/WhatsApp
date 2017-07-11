package br.com.whatsappandroid.fecarvalho.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Felipe on 17/06/2017.
 */

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "whatsapp.preferencias";
    private final int MODE = 0; //somente o meu app terá acesso a esse arquivo de preferências
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";

    public Preferencias(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE); //nome do arquivo, modo de abertura do arquivo
        editor = preferences.edit(); //a partir desse editor, vai ser possível alterar o conteúdo do arquivo

    }

    public void salvarDados(String identificadorUsuario){

        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.commit();

    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

}
