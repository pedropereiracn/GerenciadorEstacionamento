package modelo;

import java.io.Serializable;

import excecoes.VagaIncompativelException;
import excecoes.VagaOcupadaException;

public abstract class Vaga implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numero;
    private boolean ocupada;
    private TipoPreferencia preferencia;
    private ClienteMensalista reservadaPara;

    public Vaga(int numero) {
        this.numero = numero;
        this.ocupada = false; // começa livre
    }

    public Vaga(int numero, TipoPreferencia preferencia) {
        this(numero);
        this.preferencia = preferencia;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public TipoPreferencia getPreferencia() {
        return preferencia;
    }

    public ClienteMensalista getReservadaPara() {
        return reservadaPara;
    }

    public void setReservadaPara(ClienteMensalista mensalista) {
        this.reservadaPara = mensalista;
    }

    public void podeOcupar(Cliente cliente) throws VagaIncompativelException {
        if (reservadaPara != null && cliente != reservadaPara) {
            throw new VagaIncompativelException(
                "vaga " + numero + " reservada para outro mensalista");
        }

        if (preferencia != null) {
            TipoPreferencia credencial = null;

            if (cliente instanceof ClienteAvulso) {
                credencial = ((ClienteAvulso) cliente).getCredencial();
            }

            if (credencial != preferencia) {
                throw new VagaIncompativelException(
                    "vaga " + numero + " e reservada para " + preferencia);
            }
        }
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
