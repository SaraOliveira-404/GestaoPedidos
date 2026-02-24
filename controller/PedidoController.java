package controller;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import entity.Pedido;
import service.PedidoService;

public class PedidoController {

    private final PedidoService service;
    private final Scanner scanner;

    public PedidoController(PedidoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void executar() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== SISTEMA DE GESTÃO DE PEDIDOS ===");
            System.out.println("1. Cadastrar Pedido");
            System.out.println("2. Listar Todos os Pedidos");
            System.out.println("3. Buscar Pedido por ID");
            System.out.println("4. Atualizar Pedido");
            System.out.println("5. Marcar como Pago");
            System.out.println("6. Marcar como Enviado");
            System.out.println("7. Cancelar Pedido");
            System.out.println("8. Remover Pedido");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1: cadastrarPedido();
                case 2: listarPedidos();
                case 3: buscarPedidoPorId();
                case 4: atualizarPedido();
                case 5: marcarComoPago();
                case 6: marcarComoEnviado();
                case 7: cancelarPedido();
                case 8: removerPedido();
                case 0: System.out.println("Saindo...");
                default: System.out.println("Opção inválida!");
            }
        }
    }

    // --- Métodos do menu ---
    private void cadastrarPedido() {
        System.out.print("Cliente: ");
        String cliente = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor: ");
        double valor = lerDouble();

        try {
            Pedido pedido = service.cadastrarPedido(cliente, descricao, valor);
            System.out.println("Pedido cadastrado! ID: " + pedido.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarPedidos() {
        List<Pedido> pedidos = service.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        } else {
            pedidos.forEach(p -> System.out.println(
                    "ID: " + p.getId() +
                    " | Cliente: " + p.getCliente() +
                    " | Descrição: " + p.getDescricao() +
                    " | Valor: " + p.getValor() +
                    " | Status: " + p.getStatus()
            ));
        }
    }

    private void buscarPedidoPorId() {
        System.out.print("Digite o ID do pedido: ");
        int id = lerInteiro();

        Optional<Pedido> pedidoOpt = service.buscarPedidoPorId(id);
        pedidoOpt.ifPresentOrElse(
                p -> System.out.println(
                        "ID: " + p.getId() +
                        " | Cliente: " + p.getCliente() +
                        " | Descrição: " + p.getDescricao() +
                        " | Valor: " + p.getValor() +
                        " | Status: " + p.getStatus()
                ),
                () -> System.out.println("Pedido não encontrado.")
        );
    }

    private void atualizarPedido() {
        System.out.print("Digite o ID do pedido que deseja atualizar: ");
        int id = lerInteiro();

        Optional<Pedido> pedidoOpt = service.buscarPedidoPorId(id);
        if (pedidoOpt.isEmpty()) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        Pedido pedido = pedidoOpt.get();
        System.out.print("Nova descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Novo valor: ");
        double valor = lerDouble();

        pedido.setDescricao(descricao);
        pedido.setValor(valor);

        try {
            service.atualizarPedido(pedido);
            System.out.println("Pedido atualizado!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void marcarComoPago() {
        System.out.print("Digite o ID do pedido: ");
        int id = lerInteiro();
        try {
            if (service.marcarComoPago(id)) {
                System.out.println("Pedido marcado como PAGO.");
            } else {
                System.out.println("Pedido não encontrado.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void marcarComoEnviado() {
        System.out.print("Digite o ID do pedido: ");
        int id = lerInteiro();
        try {
            if (service.marcarComoEnviado(id)) {
                System.out.println("Pedido marcado como ENVIADO.");
            } else {
                System.out.println("Pedido não encontrado.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cancelarPedido() {
        System.out.print("Digite o ID do pedido: ");
        int id = lerInteiro();
        try {
            if (service.cancelarPedido(id)) {
                System.out.println("Pedido CANCELADO.");
            } else {
                System.out.println("Pedido não encontrado.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerPedido() {
        System.out.print("Digite o ID do pedido: ");
        int id = lerInteiro();
        if (service.removerPedido(id)) {
            System.out.println("Pedido removido.");
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    // --- Métodos auxiliares para leitura segura ---
    private int lerInteiro() {
        while (true) {
            try {
                int num = Integer.parseInt(scanner.nextLine());
                return num;
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
    }

    private double lerDouble() {
        while (true) {
            try {
                double num = Double.parseDouble(scanner.nextLine());
                return num;
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um valor válido: ");
            }
        }
    }
}