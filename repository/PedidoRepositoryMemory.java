package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import entity.Pedido;

public class PedidoRepositoryMemory implements PedidoRepository{

    private final Map<Integer, Pedido> pedidos = new HashMap<>();


    @Override
    public Pedido salvar(Pedido pedido) {
        if (pedido == null){
            throw new IllegalArgumentException("Pedido não pode ser nulo");
        }

        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public boolean atualizar(Pedido pedido) {
        if (!pedidos.containsKey(pedido.getId())) {
            return false;
        }

        pedidos.put(pedido.getId(), pedido);
        return true;
    }

    @Override
    public boolean deletar(int id) {
        return pedidos.remove(id) != null;
    }

        @Override
    public int contar() {
        return pedidos.size();
    }
    

}
