package br.com.whatsappandroid.fecarvalho.whatsapp.model;

/**
 * Created by Felipe on 08/08/2017.
 */

public class Mensagem {

    private String idUsuario;
    private String mensagem;

    public Mensagem() {
        //Construtor vazio é necessário para salvar o objeto no Firebase
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
