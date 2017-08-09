package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    //Usuário de teste: joao@gmail.com/ 123456789

    private Toolbar toolbar;
    private EditText edtMensagem;
    private ImageButton btnEnviar;
    private DatabaseReference firebase;

    //Dados do destinatário da mensagem
    private String nomeUsuarioDestinatario;

    //Dados do remetente
    private String idUsuarioRemetente;
    private String idUsuarioDestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar)findViewById(R.id.tobConversa);
        edtMensagem = (EditText) findViewById(R.id.edtMensagem);
        btnEnviar = (ImageButton) findViewById(R.id.btnEnviar);

        //Dados do usuário logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();

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

                    salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem );
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
}
