package br.com.whatsappandroid.fecarvalho.whatsapp.model;

/**
 * Created by Felipe on 04/07/2017.
 */

public class Contato {

    private String identificadorUsuario;
    private String nome;
    private String email;

    public Contato() {
        //É requisito do Firebase ter a classe com construtor
    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String identificadorUsuario) {
        this.identificadorUsuario = identificadorUsuario;
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

}
