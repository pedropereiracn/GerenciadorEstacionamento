package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import excecoes.VagaOcupadaException;
import modelo.Carro;
import modelo.Moto;
import modelo.TipoPreferencia;
import modelo.Vaga;
import modelo.VagaCoberta;
import modelo.VagaComum;
import modelo.Veiculo;
import persistencia.PersistenciaService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Sistema de Gerenciamento de Estacionamento ===\n");

        System.out.println("Escolha o tipo de veículo:");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");

        int opcao = sc.nextInt();
        sc.nextLine(); // limpar buffer

        Veiculo veiculo;

        System.out.print("Digite a placa: ");
        String placa = sc.nextLine();

        System.out.print("Digite o modelo: ");
        String modelo = sc.nextLine();

        if (opcao == 1) {

            System.out.print("Digite o número de portas: ");
            int portas = sc.nextInt();

            veiculo = new Carro(placa, modelo, portas);

        } else if (opcao == 2) {

            System.out.print("Digite as cilindradas: ");
            int cilindradas = sc.nextInt();

            veiculo = new Moto(placa, modelo, cilindradas);

        } else {
            System.out.println("Opção inválida.");
            sc.close();
            return;
        }

        System.out.println("\n--- Veículo cadastrado ---");
        System.out.println(veiculo);
        System.out.println("Tarifa para 2 horas: R$ " + String.format("%.2f", veiculo.calcularTarifa(120)));

        // ===== Persistência via serialização =====
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        veiculos.add(veiculo);

        PersistenciaService<ArrayList<Veiculo>> servico = new PersistenciaService<>();
        String caminho = "veiculos.ser";

        try {
            servico.salvar(veiculos, caminho);
            System.out.println("\n--- Persistência ---");
            System.out.println("Salvos " + veiculos.size() + " veículo(s) em " + caminho);

            ArrayList<Veiculo> recuperados = servico.carregar(caminho);
            System.out.println("Recuperados " + recuperados.size() + " veículo(s):");
            for (Veiculo v : recuperados) {
                System.out.println("  - " + v);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro de persistência: " + e.getMessage());
        }

        // ===== Demonstração de Vagas =====
        System.out.println("\n=== Demonstração de Vagas ===");

        ArrayList<Vaga> vagas = new ArrayList<>();
        vagas.add(new VagaComum(1));
        vagas.add(new VagaCoberta(2));
        vagas.add(new VagaComum(3, TipoPreferencia.IDOSO));
        vagas.add(new VagaCoberta(4, TipoPreferencia.CADEIRANTE));

        for (Vaga v : vagas) {
            System.out.println(v);
        }

        System.out.println("\n--- Teste de ocupação ---");
        Vaga primeira = vagas.get(0);
        try {
            primeira.ocupar();
            System.out.println("Vaga " + primeira.getNumero() + " ocupada com sucesso.");
            primeira.ocupar(); // deve lançar exceção
        } catch (VagaOcupadaException e) {
            System.out.println("Capturada exceção: " + e.getMessage());
        }

        primeira.liberar();
        System.out.println("Vaga " + primeira.getNumero() + " liberada.");

        sc.close();
    }
}
