package br.com.whatsappandroid.fecarvalho.whatsapp.helper;

import android.util.Base64;

/**
 * Created by Felipe on 02/07/2017.
 * Vai usar o email como chave no Firebase, como ele não aceita caracteres especiais (@),
 * é necessário converter esse texto para Base64
 */

public class Base64Custom {

    public static String codificarBase64(String texto){
        //O replace é para remover caracteres indesejados no Base64 gerado
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r", "");
    }

    public static String decodificarBase64(String textoCodificado){
        //O retorno vem em bytes, por isso é chamado o construtor da classe String
        //para já fazer a conversão
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}
