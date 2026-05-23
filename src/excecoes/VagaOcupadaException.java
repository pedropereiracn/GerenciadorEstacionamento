package excecoes;

public class VagaOcupadaException extends EstacionamentoException {

    public VagaOcupadaException(int numeroVaga) {
        super("A vaga " + numeroVaga + " já está ocupada.");
    }
}
