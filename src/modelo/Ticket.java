package modelo;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("dd/MM HH:mm");

    private int numero;
    private Veiculo veiculo;
    private Vaga vaga;
    private Cliente cliente;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;

    public Ticket(int numero, Veiculo veiculo, Vaga vaga, Cliente cliente) {
        this.numero = numero;
        this.veiculo = veiculo;
        this.vaga = vaga;
        this.cliente = cliente;
        this.entrada = LocalDateTime.now();
    }

    public int getNumero() {
        return numero;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getPlaca() {
        return veiculo.getPlaca();
    }

    public boolean estaAberto() {
        return saida == null;
    }

    public double getValorPago() {
        return valorPago;
    }

    public long minutos() {
        LocalDateTime fim = (saida == null) ? LocalDateTime.now() : saida;
        long min = Duration.between(entrada, fim).toMinutes();
        return min < 1 ? 1 : min; // cobra no minimo 1 minuto
    }

    public double calcularValor() {
        if (cliente instanceof ClienteMensalista) {
            return 0.0; // mensalista ja paga via mensalidade
        }
        double base = veiculo.calcularTarifa((int) minutos());
        return base * vaga.getMultiplicador();
    }

    public double registrarSaida() {
        this.saida = LocalDateTime.now();
        this.valorPago = calcularValor();
        this.vaga.liberar();
        return valorPago;
    }

    @Override
    public String toString() {
        String base = String.format("Ticket #%03d | %s | vaga %d | entrada %s",
                numero, getPlaca(), vaga.getNumero(), entrada.format(HORA));
        if (estaAberto()) {
            return base + " | no patio";
        }
        return base + String.format(" | %d min | R$ %.2f", minutos(), valorPago);
    }
}
