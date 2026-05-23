package modelo;

import java.io.Serializable;

public abstract class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

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