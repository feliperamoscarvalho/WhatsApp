package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.helper.Preferencias;

public class ValidadorActivity extends AppCompatActivity {

    //Está tela não está em uso, mantive no código apenas para estudo

    private EditText edtCodValidacao;
    private Button btnValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        edtCodValidacao = (EditText) findViewById(R.id.edtCodValidacao);
        btnValidar = (Button) findViewById(R.id.btnValidar);

        //Adiciona máscara no Edit do código de validação
        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mascaraCodigoValidacao = new MaskTextWatcher(edtCodValidacao, simpleMaskCodigoValidacao);
        edtCodValidacao.addTextChangedListener(mascaraCodigoValidacao);

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recuperar dados das preferências do usuário
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);

            }
        });
    }
}
