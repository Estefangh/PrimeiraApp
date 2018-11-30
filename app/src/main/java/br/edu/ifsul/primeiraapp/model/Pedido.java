package br.edu.ifsul.primeiraapp.model;

import java.util.Date;
import java.util.List;

public class Pedido {
    private Double totalpedido;
    private Long id_pedido;
    private String estadoPedido;
    private Date dataCriacao;
    private boolean situacaoPedido;
    private String formaPagamento;
    private Date dataModificacao;
    private List<Item_pedido> itensPedido;
    private Cliente cliente;

    public Pedido(Double totalPedido, Long idPedido, String estadoPedido, Date dataCriacao, boolean situacaoPedido, Date dataModificacaoPedido, String formaPagamento, List<Item_pedido> itensPedido, Cliente cliente) {
        this.totalpedido = totalPedido;
        this.id_pedido = idPedido;
        this.estadoPedido = estadoPedido;
        this.dataCriacao = dataCriacao;
        this.situacaoPedido = situacaoPedido;
        this.dataModificacao = dataModificacaoPedido;
        this.formaPagamento = formaPagamento;
        this.itensPedido = itensPedido;
        this.cliente = cliente;
    }


    public Pedido(){

    } //construtor vazio


    public Double getTotalpedido() {
        return totalpedido;
    }

    public void setTotalpedido(Double totalpedido) {
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

    public List<Item_pedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<Item_pedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
                ", itensPedido=" + itensPedido +
                ", cliente=" + cliente +
                '}';
    }
}
