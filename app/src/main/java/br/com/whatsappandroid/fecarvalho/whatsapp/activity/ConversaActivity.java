package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.adapter.MensagemAdapter;
import br.com.whatsappandroid.fecarvalho.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Conversa;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    //Usuário de teste: joao@gmail.com/ 123456789

    private Toolbar toolbar;
    private EditText edtMensagem;
    private ImageButton btnEnviar;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;

    //Dados do destinatário da mensagem
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //Dados do remetente
    private String idUsuarioRemetente;
    private String nomeUsuarioRemetente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar)findViewById(R.id.tobConversa);
        edtMensagem = (EditText) findViewById(R.id.edtMensagem);
        btnEnviar = (ImageButton) findViewById(R.id.btnEnviar);
        listView = (ListView)findViewById(R.id.lisConversas);

        //Dados do usuário logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();
        nomeUsuarioRemetente = preferencias.getNome();

        Bundle extra = getIntent().getExtras();

        if (extra != null){
            nomeUsuarioDestinatario = extra.getString("nome");
            String emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64(emailDestinatario);
        }

        //Configurar toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        //Configurando essa propriedade, e adicionando no Manifest quem é a activity pai dessa activity,
        //o botão já funciona como voltar
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Monta ListView e Adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);

        listView.setAdapter(adapter);

        //Recuper mensagens do Firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("mensagens")
                .child(idUsuarioRemetente)
                .child(idUsuarioDestinatario);

        //Criar listener para mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar mensagens
                mensagens.clear();

                //Recupera mensagens
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        firebase.addValueEventListener(valueEventListenerMensagem);

        //Enviar mensagem
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoMensagem = edtMensagem.getText().toString();
                if (textoMensagem.isEmpty()) {

                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar!", Toast.LENGTH_SHORT).show();;

                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(textoMensagem);

                    //As mensagens são salvas para o remetente e para o destinatário
                    //Salvar mensagem para o remetente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem );
                    if(! retornoMensagemRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem, tente novamente!", Toast.LENGTH_SHORT).show();
                    }else{
                        //Salvar mensagem para o destinatário
                        Boolean retornoMensagemDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem );
                        if(!retornoMensagemDestinatario){
                            Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem para o destinatário, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //Salvar Conversa para o remetente
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioDestinatario);
                    conversa.setNome(nomeUsuarioDestinatario);
                    conversa.setMensagem(textoMensagem);
                    Boolean retornoConversaRemetente = salvarConversa(idUsuarioRemetente, idUsuarioDestinatario, conversa);
                    if(! retornoConversaRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa, tente novamente!", Toast.LENGTH_SHORT).show();
                    }else{
                        //Salvar Conversa para o destinatario
                        Conversa conversaDestinatario = new Conversa();
                        conversaDestinatario.setIdUsuario(idUsuarioRemetente);
                        conversaDestinatario.setNome(nomeUsuarioRemetente);
                        conversaDestinatario.setMensagem(textoMensagem);

                        Boolean retornoConversaDestinatario = salvarConversa(idUsuarioDestinatario, idUsuarioRemetente, conversaDestinatario);
                        if(!retornoConversaDestinatario){
                            Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa para o destinatário, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    edtMensagem.setText("");

                }

            }
        });
    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){

        /* lembrando que usuário é o email em base 64
            mensagens com identificadores únicos para armazenar mais de uma
        mensagens
            +usuarioRemetente
                +usuarioDestinatario
                    01 mensagem
                    02 mensaem
                +usuarioDestinatario2
                    +mensagem
         */

        try{

            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");
            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .push() //o push cria um identificador único para o nó
                    .setValue(mensagem); //seta o valor do push

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("conversas");
            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .setValue(conversa);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        firebase.removeEventListener(valueEventListenerMensagem);
    }
}
