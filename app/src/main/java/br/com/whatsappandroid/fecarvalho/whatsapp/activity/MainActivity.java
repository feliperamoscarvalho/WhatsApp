package br.com.whatsappandroid.fecarvalho.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.whatsappandroid.fecarvalho.whatsapp.R;

public class MainActivity extends AppCompatActivity {

    //private DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referenciaFirebase.child("pontos").setValue(100);
    }
}
