package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtCodPais;
    private EditText edtCodArea;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtCodPais = (EditText) findViewById(R.id.edtCodPais);
        edtCodArea = (EditText) findViewById(R.id.edtCodArea);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        //Adicionar máscaras aos campos do telefone
        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher maskCodPais = new MaskTextWatcher(edtCodPais, simpleMaskCodPais);
        MaskTextWatcher maskCodArea = new MaskTextWatcher(edtCodArea, simpleMaskCodArea);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(edtTelefone, simpleMaskTelefone);

        edtCodPais.addTextChangedListener(maskCodPais);
        edtCodArea.addTextChangedListener(maskCodArea);
        edtTelefone.addTextChangedListener(maskTelefone);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeUsuario = edtNome.getText().toString();
                String telefoneCompleto =
                        edtCodPais.getText().toString() +
                        edtCodArea.getText().toString() +
                        edtTelefone.getText().toString();

                telefoneCompleto = telefoneCompleto.replace("+", "");
                telefoneCompleto = telefoneCompleto.replace("-", "");
                //Log.i("TELEFONE", "T:" + telefoneCompleto);

                //Gerar Token (de 4 dígitos entre 1000 e 9999)
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico); //converte o inteiro em String
                String mensagemEnvio = "Whatsapp Código de Confirmação: " + token;
                //Log.i("TOKEN", "T:" + token);

                //Salvar os dados para validação (em SharedPreferences)
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuariosPreferencias(nomeUsuario, telefoneCompleto, token);

                //Envio de SMS (o "+" é necessário no número para envio do SMS)
                telefoneCompleto = "8135"; //número do emulador, para teste
                boolean enviadoSMS = enviaSMS("+" + telefoneCompleto, mensagemEnvio);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("NOME", "T:" + usuario.get("nome"));
                Log.i("TELEFONE", "T:" + usuario.get("telefone"));
                Log.i("TOKEN", "T:" + usuario.get("token"));

            }
        });

    }

    private boolean enviaSMS(String telefone, String mensagem){

        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return  true;

        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }

    }
}
