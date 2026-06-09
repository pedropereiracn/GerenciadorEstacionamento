package modelo;

public class ClienteMensalista extends Cliente implements Pagavel {

    private static final long serialVersionUID = 1L;

    public static final double MENSALIDADE_COMUM = 200.0;
    public static final double MENSALIDADE_COBERTA = 300.0;

    private double valorMensalidade;
    private boolean usaVagaCoberta;
    private String placa;
    private TipoPreferencia credencial;

    public ClienteMensalista(String nome, String cpf, String placa, boolean usaVagaCoberta) {
        super(nome, cpf);
        this.placa = placa;
        this.usaVagaCoberta = usaVagaCoberta;
        this.valorMensalidade = usaVagaCoberta ? MENSALIDADE_COBERTA : MENSALIDADE_COMUM;
    }

    public ClienteMensalista(String nome, String cpf, String placa, boolean usaVagaCoberta, TipoPreferencia credencial) {
        this(nome, cpf, placa, usaVagaCoberta);
        this.credencial = credencial;
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

    public TipoPreferencia getCredencial() {
        return credencial;
    }

    @Override
    public String descricao() {
        String tipo = usaVagaCoberta ? "Coberta" : "Comum";
        String cred = (credencial == null) ? "nenhuma" : credencial.toString();
        return String.format("Cliente Mensalista: %s | CPF: %s | Placa: %s | Vaga %s | Credencial: %s | Mensalidade: R$ %.2f",
                getNome(), getCpf(), placa, tipo, cred, valorMensalidade);
    }

    @Override
    public double calcularValorPagar() {
        return valorMensalidade;
    }
}
