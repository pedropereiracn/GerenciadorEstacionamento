package excecoes;

public class VagaIncompativelException extends EstacionamentoException {

    public VagaIncompativelException(String motivo) {
        super("Vaga incompativel: " + motivo);
    }
}
