package br.com.whatsappandroid.fecarvalho.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 18/06/2017.
 */

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){

        if(Build.VERSION.SDK_INT >= 23){

            List<String> listaPermissoes = new ArrayList<String>();

            //Percorre as permissões passadas, verificando uma a uma se já tem a permissão liberada
            for(String permissao: permissoes){
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) listaPermissoes.add(permissao); //guarda na listaPermissoes a permissão que o usuário não tem
            }

            //Caso a lista esteja vazia, não é necessário solicitar a permissão
            if(listaPermissoes.isEmpty()) return true;

            //Converte a List em String[], pois o método requestPermissions pede um Array de string
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //Solicita permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }
        return true;

    }

}
