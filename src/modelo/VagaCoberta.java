package modelo;

public class VagaCoberta extends Vaga {

    public VagaCoberta(int numero) {
        super(numero);
    }

    public VagaCoberta(int numero, TipoPreferencia preferencia) {
        super(numero, preferencia);
    }

    @Override
    public double getMultiplicador() {
        return 1.5;
    }

    @Override
    public String toString() {
        String pref = (getPreferencia() == null) ? "nenhuma" : getPreferencia().toString();
        return String.format("VagaCoberta %d | Preferência: %s | Multiplicador: %.1f",
                getNumero(), pref, getMultiplicador());
    }
}
