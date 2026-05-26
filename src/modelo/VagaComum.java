package modelo;

public class VagaComum extends Vaga {

    private static final double MULTIPLICADOR = 1.0;

    public VagaComum(int numero) {
        super(numero);
    }

    public VagaComum(int numero, TipoPreferencia preferencia) {
        super(numero, preferencia);
    }

    @Override
    public double getMultiplicador() {
        return MULTIPLICADOR;
    }

    @Override
    public String toString() {
        String pref = (getPreferencia() == null) ? "nenhuma" : getPreferencia().toString();
        return String.format("VagaComum %d | Preferência: %s | Multiplicador: %.1f",
                getNumero(), pref, getMultiplicador());
    }
}
