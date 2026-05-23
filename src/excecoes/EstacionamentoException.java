package excecoes;

public class EstacionamentoException extends Exception {

    public EstacionamentoException(String mensagem) {
        super(mensagem);
    }

    public EstacionamentoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
