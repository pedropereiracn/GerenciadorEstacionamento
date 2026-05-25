package modelo;

public class VagaComum extends Vaga {

    public VagaComum(int numero) {
        super(numero);
    }

    public VagaComum(int numero, TipoPreferencia preferencia) {
        super(numero, preferencia);
    }

    @Override
    public double getMultiplicador() {
        return 1.0;
    }

    @Override
    public String toString() {
        String pref = (getPreferencia() == null) ? "nenhuma" : getPreferencia().toString();
        return String.format("VagaComum %d | Preferência: %s | Multiplicador: %.1f",
                getNumero(), pref, getMultiplicador());
    }
}
