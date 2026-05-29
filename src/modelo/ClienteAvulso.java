package modelo;

public class ClienteAvulso extends Cliente implements Pagavel {

    private static final long serialVersionUID = 1L;

    public ClienteAvulso(String nome, String cpf) {
        super(nome, cpf);
    }

    @Override
    public String descricao() {
        return "Cliente Avulso: " + getNome() + " | CPF: " + getCpf();
    }

    @Override
    public double calcularValorPagar() {
        return 0.0;
    }
}
