package br.edu.ifsul.primeiraapp.model;

import java.util.Date;

public class Pedido {
    private Long totalpedido;
    private Long id_pedido;
    private String estadoPedido;
    private Date dataCriacao;
    private boolean situacaoPedido;
    private String formaPagamento;
    private Date dataModificacao;

    public Pedido(){

    } //construtor vazio

    public Long getTotalpedido() {
        return totalpedido;
    }

    public void setTotalpedido(Long totalpedido) {
        this.totalpedido = totalpedido;
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isSituacaoPedido() {
        return situacaoPedido;
    }

    public void setSituacaoPedido(boolean situacaoPedido) {
        this.situacaoPedido = situacaoPedido;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "totalpedido=" + totalpedido +
                ", id_pedido=" + id_pedido +
                ", estadoPedido='" + estadoPedido + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", situacaoPedido=" + situacaoPedido +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", dataModificacao=" + dataModificacao +
                '}';
    }
}
