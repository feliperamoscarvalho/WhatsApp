package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.fecarvalho.whatsapp.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastrar;
    private Usuario usuario;
    private FirebaseAuth firebaseAutenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtNome = (EditText) findViewById(R.id.edtCadastroNome);
        edtEmail = (EditText) findViewById(R.id.edtCadastroEmail);
        edtSenha = (EditText) findViewById(R.id.edtCadastroSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new Usuario();
                usuario.setNome(edtNome.getText().toString());
                usuario.setEmail(edtEmail.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario(){

        firebaseAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAutenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //task é o retorno da autenticação
                if(task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG).show();
                    FirebaseUser usuarioFirebase = task.getResult().getUser(); //obtém o usuário cadastrado

                    //A chave será o e-mail convertido para Base64
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());

                    //usuario.setId(usuarioFirebase.getUid()); //seta o id do usuário cadastrado no Firebase no meu objeto usuário
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    abrirLoginUsuario();

                }else{

                    String erroExcecao ="";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já está cadastrado no App!";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
