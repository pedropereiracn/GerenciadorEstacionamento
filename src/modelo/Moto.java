package modelo;

public class Moto extends Veiculo {

    private int cilindradas;

    public Moto(String placa, String modelo, int cilindradas) {
        super(placa, modelo);
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    @Override
    public double calcularTarifa(int minutos) {
        return (minutos / 60.0) * 3.0; // R$ 3,00 por hora
    }

    @Override
    public String toString() {
        return String.format("Moto %s | Placa: %s | %dcc",
                getModelo(), getPlaca(), cilindradas);
    }
}