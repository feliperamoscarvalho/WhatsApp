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
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Conversa;

/**
 * Created by Felipe on 28/08/2017.
 */

public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(@NonNull Context c, @NonNull ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.context = c;
        this.conversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(conversas != null){

            //Inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta a view a partir do XML
            view = inflater.inflate(R.layout.lista_conversas, parent, false);

            //Recupera elemento para exibição
            TextView txtNome = (TextView) view.findViewById(R.id.txtTitulo);
            TextView txtUltimaMensagem = (TextView) view.findViewById(R.id.txtSubtitulo);

            Conversa conversa = conversas.get(position);
            txtNome.setText(conversa.getNome());
            txtUltimaMensagem.setText(conversa.getMensagem());

        }

        return view;

    }
}
