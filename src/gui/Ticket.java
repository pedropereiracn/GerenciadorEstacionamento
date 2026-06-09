package gui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modelo.Veiculo;

// ticket de um cliente avulso: entra agora, paga por tempo na saida.
// guarda o veiculo pra usar o calcularTarifa (que e polimorfico: carro x moto).
public class Ticket {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("dd/MM HH:mm");

    private final int numero;
    private final Veiculo veiculo;
    private final LocalDateTime entrada;
    private LocalDateTime saida; // null enquanto o carro nao saiu

    public Ticket(int numero, Veiculo veiculo) {
        this.numero = numero;
        this.veiculo = veiculo;
        this.entrada = LocalDateTime.now();
    }

    public int getNumero() {
        return numero;
    }

    public String getPlaca() {
        return veiculo.getPlaca();
    }

    public boolean estaAberto() {
        return saida == null;
    }

    // fecha o ticket e devolve quantos minutos o veiculo ficou
    public long registrarSaida() {
        this.saida = LocalDateTime.now();
        return minutos();
    }

    public long minutos() {
        LocalDateTime fim = (saida == null) ? LocalDateTime.now() : saida;
        long min = Duration.between(entrada, fim).toMinutes();
        return min < 1 ? 1 : min; // cobra no minimo 1 minuto
    }

    // usa o calcularTarifa do veiculo (polimorfismo): carro R$5/h, moto R$3/h
    public double valor() {
        return veiculo.calcularTarifa((int) minutos());
    }

    @Override
    public String toString() {
        String base = String.format("Ticket #%03d | %s | entrada %s",
                numero, getPlaca(), entrada.format(HORA));
        if (estaAberto()) {
            return base + " | no patio";
        }
        return base + String.format(" | %d min | R$ %.2f", minutos(), valor());
    }
}
