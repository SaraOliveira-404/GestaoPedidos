package entity;

import entity.StatusPedido;
import java.time.LocalDateTime;

public class Pedido {
    private static int contadorId = 1;

    private int id;
    private String cliente;
    private String descricao;
    private StatusPedido status;
    private double valor;
    private LocalDateTime dataCriacao;


    public Pedido(String cliente, String descricao, double valor) {
        this.id = contadorId++;
        setCliente(cliente);
        setDescricao(descricao);
        setValor(valor);
        this.status = StatusPedido.CRIADO;
        this.dataCriacao = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }


    public void setCliente(String cliente) {
        if (cliente == null || cliente == " " || cliente.trim().length() < 3){
            throw new IllegalArgumentException("Nome deve ter no mínimo tres caracteres");
        }
        this.cliente = cliente;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        if (descricao == null ||  descricao == " " || cliente.trim().length() < 5){
            throw new IllegalArgumentException("Nome deve ter no mínimo tres caracteres");
        }
        this.descricao = descricao;
    }


    public StatusPedido getStatus() {
        return status;
    }


    public void setStatus(StatusPedido status) {
        if (this.status == status.CANCELADO){
            throw new IllegalArgumentException("Pedido cancelado não pode ser alterado");
        }
        if(status == StatusPedido.ENVIADO && this.status != StatusPedido.PAGO){
            throw new IllegalArgumentException("Pedido não pode ser enviado se não estiver pago");
        }
        this.status = status;
    }


    public double getValor() {
        return valor;
    }


    public void setValor(double valor) {
        if (valor < 0){
            throw new IllegalArgumentException("O valor deve ser maior que zero");
        }
        this.valor = valor;
    }


    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", cliente=" + cliente + ", descricao=" + descricao + ", status=" + status
                + ", valor=" + valor + ", dataCriacao=" + dataCriacao + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        long temp;
        temp = Double.doubleToLongBits(valor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
        return result;
    }

}
