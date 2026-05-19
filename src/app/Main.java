package app; // pacote do Main

import modelo.Veiculo; 
import modelo.Carro;
import modelo.Moto;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Gerenciamento de Estacionamento ===\n");

        // ===== Criando objetos concretos =====
        Carro civic = new Carro("ABC-1234", "Honda Civic", 4);
        Moto cb = new Moto("XYZ-9876", "Honda CB 300", 300);

        Veiculo v1 = civic;
        Veiculo v2 = cb;

        
        System.out.println("--- Tarifa para 2 horas (120 minutos) ---");
        System.out.println(v1 + " → R$ " + String.format("%.2f", v1.calcularTarifa(120)));
        System.out.println(v2 + " → R$ " + String.format("%.2f", v2.calcularTarifa(120)));
    }
}