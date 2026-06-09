package modelo;

public class ClienteAvulso extends Cliente implements Pagavel {

    private static final long serialVersionUID = 1L;

    private TipoPreferencia credencial;

    public ClienteAvulso() {
        super("Avulso", "---");
    }

    public ClienteAvulso(TipoPreferencia credencial) {
        super("Avulso", "---");
        this.credencial = credencial;
    }

    public TipoPreferencia getCredencial() {
        return credencial;
    }

    @Override
    public String descricao() {
        String c = (credencial == null) ? "nenhuma" : credencial.toString();
        return "Cliente Avulso | Credencial: " + c;
    }

    @Override
    public double calcularValorPagar() {
        return 0.0;
    }
}
