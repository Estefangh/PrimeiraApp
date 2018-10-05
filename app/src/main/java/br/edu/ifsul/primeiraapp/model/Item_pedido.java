package br.edu.ifsul.primeiraapp.model;

public class Item_pedido {

    private Integer quantidadePedido;
    private Double totalItem;
    private boolean situacao;

    private Produto produto;

    public Item_pedido(){

    }//construtor vazio

    public Item_pedido(Integer quantidadePedido, Double totalItem, boolean situacao, Produto produto) {
        this.quantidadePedido = quantidadePedido;
        this.totalItem = totalItem;
        this.situacao = situacao;
        this.produto = produto;
    }

    public Integer getQuantidadePedido() {
        return quantidadePedido;
    }

    public void setQuantidadePedido(Integer quantidadePedido) {
        this.quantidadePedido = quantidadePedido;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Item_pedido{" +
                "quantidadePedido=" + quantidadePedido +
                ", totalItem=" + totalItem +
                ", situacao=" + situacao +
                ", produto=" + produto +
                '}';
    }
}
