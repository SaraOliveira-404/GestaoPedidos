package service;

import java.util.List;
import java.util.Optional;

import entity.Pedido;
import entity.StatusPedido;
import repository.PedidoRepository;

public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public Pedido cadastrarPedido(String cliente, String descricao, double valor){
        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException("Cliente inválido");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição inválida");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Pedido pedido = new Pedido(cliente, descricao, valor);

        repository.salvar(pedido); 
        return pedido;
    }

    public List<Pedido>listarPedidos(){
        return repository.listarTodos();
    }

    public Optional<Pedido> buscarPedidoPorId(int id){
        return repository.buscarPorId(id);
    }

    public boolean  atualizarPedido(Pedido pedido){
        return repository.atualizar(pedido);
    }

    public boolean removerPedido(int id){
        return repository.deletar(id);
    }

    public boolean marcarComoPago(int id){
        Optional<Pedido> pedidoExiste = repository.buscarPorId(id);
        if( pedidoExiste.isEmpty()){
            return false;
        } 

        Pedido pedido = pedidoExiste.get();

        if(pedido.getStatus() == StatusPedido.CANCELADO){
            throw new IllegalArgumentException("Pedido cancelado não pode ser alterado");
        }

        pedido.setStatus(StatusPedido.PAGO);
        repository.atualizar(pedido);
        return true;
    }

    public boolean marcarComoEnviado(int id){
        Optional<Pedido> pedidoExiste = repository.buscarPorId(id);
        if( pedidoExiste.isEmpty()){
            return false;
        } 

        Pedido pedido = pedidoExiste.get();

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedido cancelado não pode ser enviado.");
        }

        if(pedido.getStatus() != StatusPedido.PAGO){
            throw new IllegalArgumentException("Pedido deve ser pago para ser enviado");
        }

        pedido.setStatus(StatusPedido.ENVIADO);
        repository.atualizar(pedido);
        return true;
    }

    public boolean cancelarPedido(int id){
                Optional<Pedido> pedidoExiste = repository.buscarPorId(id);
        if( pedidoExiste.isEmpty()){
            return false;
        } 

        Pedido pedido = pedidoExiste.get();

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedido já foi cancelado");
        }

        if(pedido.getStatus() == StatusPedido.ENVIADO){
            throw new IllegalArgumentException("Pedido enviado não pode ser cancelado");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        repository.atualizar(pedido);
        return true;
    }

}
