package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.adapter.TabAdapter;
import br.com.whatsappandroid.fecarvalho.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.SlidingTabLayout;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Contato;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button btnSair;
    private FirebaseAuth firebaseAutenticacao;
    private Toolbar toolbar;//tem que ser a v7!
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Whatsapp");
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stlTabs);
        viewPager = (ViewPager) findViewById(R.id.vpPagina);

        //Configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        //Configurar Adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                return true;
            case R.id.item_adicionar:
                abrirCadastroContato();
                return true;
            default:
                //executa a implementação padrão do método
                return super.onOptionsItemSelected(item);
        }

    }

    private void abrirCadastroContato(){

        AlertDialog.Builder alertDialoag = new AlertDialog.Builder(MainActivity.this);

        //Configurações do Dialog
        alertDialoag.setTitle("Novo Contato");
        alertDialoag.setMessage("E-mail do usuário");
        alertDialoag.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);
        alertDialoag.setView(editText);

        //Configurar botões
        alertDialoag.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String emailContato = editText.getText().toString();

                //Valida se o email foi digitado
                if(emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                }else{

                    //Verifica se o contato já existe no Firebase
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //Recuperar instância do Firebase
                    firebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato); //busca o nó do identificador do contato;
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.getValue() != null){

                                /*  Exemplo da estrutura: contatos de Felipe
                                    +contatos
                                        +felipe@gmail.com (usuário logado, email Base64)
                                            +pedro@gmail.com (identificador do usuário que quer adicionar
                                                dados
                                            +maria@hotmail.com
                                  */

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class); //Retorna um objeto já populado

                                //Recuperar identificador do usuário logado (Base64)
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                firebase = ConfiguracaoFirebase.getFirebase();
                                firebase = firebase.child("contatos")
                                                    .child(identificadorUsuarioLogado)
                                                    .child(identificadorContato);
                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                firebase.setValue(contato);


                            }else{
                                Toast.makeText(MainActivity.this, "Usuário não possui cadastro!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });

        alertDialoag.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialoag.create();
        alertDialoag.show();

    }

    private void deslogarUsuario(){
        firebaseAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
