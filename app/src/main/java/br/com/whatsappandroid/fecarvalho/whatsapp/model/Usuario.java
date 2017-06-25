package br.com.whatsappandroid.fecarvalho.whatsapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.whatsappandroid.fecarvalho.whatsapp.config.ConfiguracaoFirebase;

/**
 * Created by Felipe on 24/06/2017.
 */

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){
        //o construtor vazio é uma exigência do Firebase para permitir inserir objetos em seus nós
        //os métodos get confgurados com a anotação @exclude serão desconsiderados no momento de
        //salvar no firebase
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child(getId()).setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
