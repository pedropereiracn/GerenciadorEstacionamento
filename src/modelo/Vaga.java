package modelo;

import excecoes.VagaOcupadaException;

public abstract class Vaga {
    private int numero;
    private boolean ocupada;

    public Vaga(int numero) {
        this.numero = numero;
        this.ocupada = false; // começa livre
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocupar() throws VagaOcupadaException {
        if (this.ocupada) {
            throw new VagaOcupadaException(this.numero);
        }
        this.ocupada = true;
    }

    public void liberar() {
        this.ocupada = false;
    }

    public abstract double getMultiplicador();
}
