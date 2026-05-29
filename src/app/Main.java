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

        // ==========================
        // Cadastro do veículo
        // ==========================
        System.out.println("Escolha o tipo de veículo:");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");
        System.out.print("Opção: ");

        int opcao = sc.nextInt();
        sc.nextLine();

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

        // ==========================
        // Persistência
        // ==========================
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        veiculos.add(veiculo);

        PersistenciaService<ArrayList<Veiculo>> servico =
                new PersistenciaService<>();

        String caminho = "veiculos.ser";

        try {

            servico.salvar(veiculos, caminho);

            System.out.println("\n--- Persistência ---");
            System.out.println("Salvos "
                    + veiculos.size()
                    + " veículo(s) em "
                    + caminho);

            ArrayList<Veiculo> recuperados =
                    servico.carregar(caminho);

            System.out.println("Recuperados "
                    + recuperados.size()
                    + " veículo(s):");

            for (Veiculo v : recuperados) {
                System.out.println(" - " + v);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(
                    "Erro de persistência: "
                            + e.getMessage());
        }

        // ==========================
        // Criação das 30 vagas
        // ==========================
        ArrayList<Vaga> vagas = new ArrayList<>();

        // 15 vagas comuns
        for (int i = 1; i <= 15; i++) {
            vagas.add(new VagaComum(i));
        }

        // 5 vagas cobertas
        for (int i = 16; i <= 20; i++) {
            vagas.add(new VagaCoberta(i));
        }

        // 5 vagas para idosos
        for (int i = 21; i <= 25; i++) {
            vagas.add(
                    new VagaComum(
                            i,
                            TipoPreferencia.IDOSO
                    )
            );
        }

        // 3 vagas para PCD
        for (int i = 26; i <= 28; i++) {
            vagas.add(
                    new VagaComum(
                            i,
                            TipoPreferencia.PCD
                    )
            );
        }

        // 2 vagas para autistas
        for (int i = 29; i <= 30; i++) {
            vagas.add(
                    new VagaComum(
                            i,
                            TipoPreferencia.AUTISTA
                    )
            );
        }

        System.out.println("\n=== Vagas Disponíveis ===");

        for (Vaga vaga : vagas) {

            String preferencia;

            if (vaga.getPreferencia() == null) {
                preferencia = "Nenhuma";
            } else {
                preferencia = vaga.getPreferencia().toString();
            }

            System.out.println(
                    "Vaga "
                            + vaga.getNumero()
                            + " | Preferência: "
                            + preferencia
                            + " | Ocupada: "
                            + vaga.isOcupada()
            );
        }

        // ==========================
        // Escolha da vaga
        // ==========================
        System.out.print(
                "\nDigite o número da vaga desejada: ");

        int numeroVaga = sc.nextInt();

        Vaga vagaEscolhida = null;

        for (Vaga vaga : vagas) {

            if (vaga.getNumero() == numeroVaga) {
                vagaEscolhida = vaga;
                break;
            }
        }

        if (vagaEscolhida == null) {

            System.out.println("Vaga inexistente.");

        } else {

            try {

                vagaEscolhida.ocupar();

                System.out.println(
                        "\nVaga ocupada com sucesso!"
                );

                System.out.println(
                        "Veículo: " + veiculo
                );

                System.out.println(
                        "Número da vaga: "
                                + vagaEscolhida.getNumero()
                );

                System.out.println(
                        "Preferência: "
                                + vagaEscolhida.getPreferencia()
                );

            } catch (VagaOcupadaException e) {

                System.out.println(
                        "Erro: " + e.getMessage()
                );
            }
        }

        sc.close();
    }
}
