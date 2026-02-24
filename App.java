import controller.PedidoController;
import repository.PedidoRepository;
import repository.PedidoRepositoryMemory;
import service.PedidoService;

public class App {
    public static void main(String[] args) throws Exception {
        PedidoRepository repository = new PedidoRepositoryMemory();
        PedidoService service = new PedidoService(repository);
        PedidoController controller = new PedidoController(service);

        controller.executar();
    }
}
