package modelo;

public class Carro extends Veiculo {

    // ===== ATRIBUTO PRÓPRIO DO CARRO =====
    private int numeroPortas;

    // ===== CONSTRUTOR =====
    public Carro(String placa, String modelo, int numeroPortas) {
        super(placa, modelo); 
        this.numeroPortas = numeroPortas;
    }

    public int getNumeroPortas() {
        return numeroPortas;
    }

    // ===== IMPLEMENTAÇÃO DO MÉTODO ABSTRATO =====
    @Override
    public double calcularTarifa(int minutos) {
        return (minutos / 60.0) * 5.0;
    }

    @Override
    public String toString() {
        return String.format("Carro %s | Placa: %s | %d portas",
            getModelo(), getPlaca(), numeroPortas);
    }

}
