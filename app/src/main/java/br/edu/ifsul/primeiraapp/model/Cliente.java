package br.edu.ifsul.primeiraapp.model;

import java.io.Serializable;

public class Cliente implements Serializable{

    private Long codigoDeBarras;
    private String nome;
    private String sobrenome;
    private Boolean situacao;
    private String url_foto;
    private String cpf;

    public Cliente() {
    }

    public Long getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Long codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigoDeBarras=" + codigoDeBarras +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", situacao=" + situacao +
                ", url_foto='" + url_foto + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
