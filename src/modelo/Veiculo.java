package modelo;

public abstract class Veiculo {

    // ===== ATRIBUTOS =====
    private String placa;
    private String modelo;

    // ===== CONSTRUTOR =====
    public Veiculo(String placa, String modelo) {
        this.placa = placa;
        this.modelo = modelo;
    }

    // ===== GETTERS  =====
    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    // ===== MÉTODO ABSTRATO =====
    public abstract double calcularTarifa(int minutos);
}