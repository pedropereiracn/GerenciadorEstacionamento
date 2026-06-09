package modelo;

public class ClienteMensalista extends Cliente implements Pagavel {

    private static final long serialVersionUID = 1L;

    public static final double MENSALIDADE_COMUM = 200.0;
    public static final double MENSALIDADE_COBERTA = 300.0;

    private double valorMensalidade;
    private boolean usaVagaCoberta;
    private String placa;

    public ClienteMensalista(String nome, String cpf, String placa, boolean usaVagaCoberta) {
        super(nome, cpf);
        this.placa = placa;
        this.usaVagaCoberta = usaVagaCoberta;
        this.valorMensalidade = usaVagaCoberta ? MENSALIDADE_COBERTA : MENSALIDADE_COMUM;
    }

    public double getValorMensalidade() {
        return valorMensalidade;
    }

    public boolean usaVagaCoberta() {
        return usaVagaCoberta;
    }

    public String getPlaca() {
        return placa;
    }

    @Override
    public String descricao() {
        String tipo = usaVagaCoberta ? "Coberta" : "Comum";
        return String.format("Cliente Mensalista: %s | CPF: %s | Placa: %s | Vaga %s | Mensalidade: R$ %.2f",
                getNome(), getCpf(), placa, tipo, valorMensalidade);
    }

    @Override
    public double calcularValorPagar() {
        return valorMensalidade;
    }
}
