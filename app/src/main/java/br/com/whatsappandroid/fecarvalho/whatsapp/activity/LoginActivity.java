package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }
}



/*Backup da versão antiga da tela*/

/* Atributos */

/*
    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtCodPais;
    private EditText edtCodArea;
    private Button btnCadastrar;
    private String[] permissoesNecessarias = new String[]{
        Manifest.permission.SEND_SMS
    };
 */

/*

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

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

                if(enviadoSMS){

                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, "Problema ao enviar o SMS, tente novamente!", Toast.LENGTH_LONG).show();
                }

                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("NOME", "T:" + usuario.get("nome"));
                Log.i("TELEFONE", "T:" + usuario.get("telefone"));
                Log.i("TOKEN", "T:" + usuario.get("token"));

            }
        });

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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        //Sobrescrevendo o método original
        //Método callBack que é executado sempre que o usuário seleciona uma permissão (deny ou allow)
        //grantResults: todos os resultados das permissões, se deram certo ou se foram negadas

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int resultado: grantResults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

 */
