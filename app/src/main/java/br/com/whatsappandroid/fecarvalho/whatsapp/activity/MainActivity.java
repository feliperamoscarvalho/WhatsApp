package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;
import br.com.whatsappandroid.fecarvalho.whatsapp.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
