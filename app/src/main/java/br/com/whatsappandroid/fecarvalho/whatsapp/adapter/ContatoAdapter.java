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
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Contato;

/**
 * Created by Felipe on 10/07/2017.
 */

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(@NonNull Context c, ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verifica se a lista está vazia
        if(contatos != null){

            //Inisicalizar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta a view a partir do XML
            view = inflater.inflate(R.layout.lista_contato, parent, false);
            
            //Recupera elemento para exibição
            TextView txtNomeContato = (TextView) view.findViewById(R.id.txtNome);
            TextView txtEmailContato = (TextView) view.findViewById(R.id.txtEmail);

            Contato contato = contatos.get(position);
            txtNomeContato.setText(contato.getNome());
            txtEmailContato.setText(contato.getEmail());

        }

        return view;

    }
}
