package br.com.whatsappandroid.fecarvalho.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.activity.ConversaActivity;
import br.com.whatsappandroid.fecarvalho.whatsapp.adapter.ConversaAdapter;
import br.com.whatsappandroid.fecarvalho.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Conversa;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerConversas;


    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        //Monta ListView e Adapter
        conversas = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.lisConversas);
        adapter = new ConversaAdapter(getActivity(), conversas);
        listView.setAdapter(adapter);

        //Recupera dados do usuário
        Preferencias preferencias = new Preferencias(getActivity());
        String idUsuarioLogado = preferencias.getIdentificador();

        //Recupera conversas do Firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                    .child("conversas")
                    .child(idUsuarioLogado);

        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                conversas.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Conversa conversa = dados.getValue(Conversa.class);
                    conversas.add(conversa);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //Adicionar evento de clique na lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Conversa conversa = conversas.get(position);
                Intent intent = new Intent(getActivity(), ConversaActivity.class);
                String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());
                intent.putExtra("nome", conversa.getNome());
                intent.putExtra("email", email);

                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Cria o listener para ficar escutando se houve alguma modificação nas conversas
        firebase.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Remove o listener
        firebase.removeEventListener(valueEventListenerConversas);
    }
}
