package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;

public class ConversaActivity extends AppCompatActivity {

    //Usuário de teste: joao@gmail.com/ 123456789

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar)findViewById(R.id.tobConversa);

        //Configurar toolbar
        toolbar.setTitle("Usuário");
        //Configurando essa propriedade, e adicionando no Manifest quem é a activity pai dessa activity,
        //o botão já funciona como voltar
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);
    }
}
