package app;

import java.util.Scanner;

import Modelo.Veiculo;
import Modelo.Carro;
import Modelo.Moto;

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
