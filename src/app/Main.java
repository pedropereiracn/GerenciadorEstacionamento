package app;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Carro;
import modelo.Moto;
import modelo.Veiculo;
import persistencia.PersistenciaService;

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

        // ===== Demo de persistência via serialização =====
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        veiculos.add(civic);
        veiculos.add(cb);

        PersistenciaService<ArrayList<Veiculo>> servico = new PersistenciaService<>();
        String caminho = "veiculos.ser";

        try {
            servico.salvar(veiculos, caminho);
            System.out.println("\n--- Persistência ---");
            System.out.println("Salvos " + veiculos.size() + " veículos em " + caminho);

            ArrayList<Veiculo> recuperados = servico.carregar(caminho);
            System.out.println("Recuperados " + recuperados.size() + " veículos:");
            for (Veiculo v : recuperados) {
                System.out.println("  - " + v);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro de persistência: " + e.getMessage());
        }
    }
}
