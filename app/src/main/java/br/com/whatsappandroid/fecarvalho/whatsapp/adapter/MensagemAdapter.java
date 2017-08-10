package br.com.whatsappandroid.fecarvalho.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Mensagem;

/**
 * Created by Felipe on 09/08/2017.
 */

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    ArrayList<Mensagem> mensagens;

    public MensagemAdapter(@NonNull Context context, @NonNull ArrayList<Mensagem> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mensagens= objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verifica se a lista está preenchida
        if(mensagens != null){

            //Recupera dados do usuário remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRemetente = preferencias.getIdentificador();

            //Inicializa o objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Recupera a mensagem
            Mensagem mensagem = mensagens.get(position);

            //Monta a view a partir do XML
            if(idUsuarioRemetente.equals(mensagem.getIdUsuario())){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            //Recupera o elemento para exibição
            TextView textoMensagem = (TextView)view.findViewById(R.id.txtMensagem);
            textoMensagem.setText(mensagem.getMensagem());

        }


        return view;

    }
}
