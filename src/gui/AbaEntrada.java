package gui;

import static gui.Estilo.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import excecoes.VagaIncompativelException;
import excecoes.VagaOcupadaException;
import modelo.Carro;
import modelo.Cliente;
import modelo.ClienteAvulso;
import modelo.ClienteMensalista;
import modelo.Moto;
import modelo.Ticket;
import modelo.TipoPreferencia;
import modelo.Vaga;
import modelo.VagaCoberta;
import modelo.VagaComum;
import modelo.Veiculo;

// aba principal: le a placa e decide o que fazer.
// mensalista -> puxa a ficha pela placa (sem ticket). avulso -> ocupa vaga e gera ticket.
public class AbaEntrada extends JPanel {

    private final List<ClienteMensalista> mensalistas;     // ficha dos mensalistas
    private final List<Vaga> vagas = new ArrayList<>();    // 30 vagas do estacionamento
    private final List<Ticket> tickets = new ArrayList<>(); // tickets dos avulsos
    private int proximoTicket = 1;

    private JTextField placa;
    private JToggleButton btCarro;
    private JToggleButton btMoto;
    private JToggleButton btCredNenhuma;
    private JToggleButton btCredIdoso;
    private JToggleButton btCredPcd;
    private JToggleButton btCredAutista;
    private JLabel resultado;
    private JTextField numTicket;
    private JLabel mensagem;
    private JTextArea patio;
    private JLabel contador;

    public AbaEntrada(List<ClienteMensalista> mensalistas) {
        this.mensalistas = mensalistas;
        criarVagas();

        setBackground(BG);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(cardEntrada());
        add(Box.createVerticalStrut(16));
        add(cardPatio());
        add(Box.createVerticalGlue());
    }

    // monta as 30 vagas iguais ao Main: 15 comuns, 5 cobertas, 5 idoso, 3 pcd, 2 autista
    private void criarVagas() {
        for (int i = 1; i <= 15; i++) {
            vagas.add(new VagaComum(i));
        }
        for (int i = 16; i <= 20; i++) {
            vagas.add(new VagaCoberta(i));
        }
        for (int i = 21; i <= 25; i++) {
            vagas.add(new VagaComum(i, TipoPreferencia.IDOSO));
        }
        for (int i = 26; i <= 28; i++) {
            vagas.add(new VagaComum(i, TipoPreferencia.PCD));
        }
        for (int i = 29; i <= 30; i++) {
            vagas.add(new VagaComum(i, TipoPreferencia.AUTISTA));
        }
    }

    private JComponent cardEntrada() {
        CardPanel card = new CardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(26, 26, 26, 26));

        placa = campo();
        aplicarMascaraPlaca(placa);

        btCarro = new ChipToggle("Carro");
        btMoto = new ChipToggle("Moto");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(btCarro);
        grupoTipo.add(btMoto);
        btCarro.setSelected(true);
        JPanel segTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        segTipo.setOpaque(false);
        segTipo.add(btCarro);
        segTipo.add(btMoto);
        segTipo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        esquerda(segTipo);

        btCredNenhuma = new ChipToggle("Nenhuma");
        btCredIdoso = new ChipToggle("Idoso");
        btCredPcd = new ChipToggle("PCD");
        btCredAutista = new ChipToggle("Autista");
        ButtonGroup grupoCred = new ButtonGroup();
        grupoCred.add(btCredNenhuma);
        grupoCred.add(btCredIdoso);
        grupoCred.add(btCredPcd);
        grupoCred.add(btCredAutista);
        btCredNenhuma.setSelected(true);
        JPanel segCred = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        segCred.setOpaque(false);
        segCred.add(btCredNenhuma);
        segCred.add(btCredIdoso);
        segCred.add(btCredPcd);
        segCred.add(btCredAutista);
        segCred.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        esquerda(segCred);

        PrimaryButton bt = new PrimaryButton("Entrar");
        bt.addActionListener(e -> entrar());

        resultado = new JLabel(" ");
        resultado.setFont(fonte(13, Font.PLAIN));
        resultado.setForeground(TEXTO);
        esquerda(resultado);

        card.add(titulo("Entrada de veículo"));
        card.add(Box.createVerticalStrut(4));
        card.add(subtitulo("Digite a placa — o sistema reconhece mensalista ou avulso"));
        card.add(Box.createVerticalStrut(22));
        card.add(rotulo("Placa"));
        card.add(Box.createVerticalStrut(6));
        card.add(placa);
        card.add(Box.createVerticalStrut(16));
        card.add(rotulo("Tipo (se for avulso)"));
        card.add(Box.createVerticalStrut(8));
        card.add(segTipo);
        card.add(Box.createVerticalStrut(16));
        card.add(rotulo("Credencial (se for avulso)"));
        card.add(Box.createVerticalStrut(8));
        card.add(segCred);
        card.add(Box.createVerticalStrut(22));
        card.add(bt);
        card.add(Box.createVerticalStrut(14));
        card.add(resultado);
        return card;
    }

    private JComponent cardPatio() {
        CardPanel card = new CardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        JLabel secao = new JLabel("PÁTIO (TICKETS)");
        secao.setFont(fonte(11, Font.BOLD));
        secao.setForeground(CINZA);

        contador = new JLabel("No pátio: 0");
        contador.setFont(fonte(11, Font.BOLD));
        contador.setForeground(ACENTO);

        JPanel cabecalho = new JPanel();
        cabecalho.setOpaque(false);
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.X_AXIS));
        cabecalho.add(secao);
        cabecalho.add(Box.createHorizontalGlue());
        cabecalho.add(contador);
        cabecalho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        esquerda(cabecalho);

        patio = areaLista();

        numTicket = campo();
        numTicket.setMaximumSize(new Dimension(90, 42));
        GhostButton btSaida = new GhostButton("Registrar saída");
        btSaida.addActionListener(e -> registrarSaida());

        JPanel linhaSaida = new JPanel();
        linhaSaida.setOpaque(false);
        linhaSaida.setLayout(new BoxLayout(linhaSaida, BoxLayout.X_AXIS));
        linhaSaida.add(numTicket);
        linhaSaida.add(Box.createHorizontalStrut(8));
        linhaSaida.add(btSaida);
        linhaSaida.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        esquerda(linhaSaida);

        mensagem = new JLabel(" ");
        mensagem.setFont(fonte(13, Font.BOLD));
        esquerda(mensagem);

        card.add(cabecalho);
        card.add(Box.createVerticalStrut(8));
        card.add(scrollLista(patio, 120));
        card.add(Box.createVerticalStrut(14));
        card.add(rotulo("Saída — nº do ticket"));
        card.add(Box.createVerticalStrut(6));
        card.add(linhaSaida);
        card.add(Box.createVerticalStrut(10));
        card.add(mensagem);
        return card;
    }

    private void entrar() {
        String limpo = placa.getText().toUpperCase().replaceAll("[^A-Z0-9]", "");
        if (!limpo.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}")) {
            mostrarResultado("⚠ Placa inválida (padrão Mercosul AAA-9A99).", VERMELHO);
            return;
        }
        String placaFmt = formatarPlaca(limpo);

        // 1) e mensalista? procura a placa nas fichas
        ClienteMensalista m = buscarMensalista(placaFmt);
        if (m != null) {
            String vaga = m.usaVagaCoberta() ? "coberta" : "comum";
            mostrarResultado(String.format(
                    "<html>✓ <b>Mensalista:</b> %s<br>Vaga %s · mensalidade R$ %.2f em dia · pode entrar.</html>",
                    m.getNome(), vaga, m.getValorMensalidade()), VERDE);
            placa.setText("");
            return;
        }

        // 2) nao achou -> avulso -> ocupa vaga e gera ticket
        TipoPreferencia credencial = credencialEscolhida();
        Cliente cliente = (credencial == null)
                ? new ClienteAvulso()
                : new ClienteAvulso(credencial);

        Veiculo v = btCarro.isSelected()
                ? new Carro(placaFmt, "Avulso", 0)
                : new Moto(placaFmt, "Avulso", 0);

        Vaga vagaEscolhida = buscarVagaLivre(credencial);
        if (vagaEscolhida == null) {
            mostrarResultado("⚠ Sem vagas disponíveis no momento.", VERMELHO);
            return;
        }

        try {
            vagaEscolhida.podeOcupar(cliente);
            vagaEscolhida.ocupar();
        } catch (VagaIncompativelException | VagaOcupadaException ex) {
            mostrarResultado("⚠ " + ex.getMessage(), VERMELHO);
            return;
        }

        Ticket t = new Ticket(proximoTicket++, v, vagaEscolhida, cliente);
        tickets.add(t);
        atualizarPatio();
        mostrarResultado(String.format(
                "<html>🎫 <b>Avulso</b> — ticket #%03d gerado.<br>Vaga %d · paga por tempo na saída.</html>",
                t.getNumero(), vagaEscolhida.getNumero()), ACENTO);
        placa.setText("");
    }

    private void registrarSaida() {
        String txt = numTicket.getText().replaceAll("[^0-9]", "");
        if (txt.isEmpty()) {
            feedback("⚠ Informe o nº do ticket.", VERMELHO);
            return;
        }
        int num = Integer.parseInt(txt);
        for (Ticket t : tickets) {
            if (t.getNumero() == num && t.estaAberto()) {
                double valor = t.registrarSaida();
                atualizarPatio();
                numTicket.setText("");
                feedback(String.format("✓ Saída #%03d — %d min — R$ %.2f",
                        num, t.minutos(), valor), VERDE);
                return;
            }
        }
        feedback("⚠ Ticket #" + num + " não está no pátio.", VERMELHO);
    }

    // procura a primeira vaga livre adequada a credencial.
    // se for credenciado, tenta primeiro vaga da preferencia. se nao tiver, cai pra comum.
    private Vaga buscarVagaLivre(TipoPreferencia credencial) {
        if (credencial != null) {
            for (Vaga v : vagas) {
                if (!v.isOcupada() && v.getPreferencia() == credencial) {
                    return v;
                }
            }
        }
        for (Vaga v : vagas) {
            if (!v.isOcupada() && v.getPreferencia() == null) {
                return v;
            }
        }
        return null;
    }

    private TipoPreferencia credencialEscolhida() {
        if (btCredIdoso.isSelected()) return TipoPreferencia.IDOSO;
        if (btCredPcd.isSelected()) return TipoPreferencia.PCD;
        if (btCredAutista.isSelected()) return TipoPreferencia.AUTISTA;
        return null;
    }

    private ClienteMensalista buscarMensalista(String placaFmt) {
        for (ClienteMensalista m : mensalistas) {
            if (m.getPlaca() != null && m.getPlaca().equalsIgnoreCase(placaFmt)) {
                return m;
            }
        }
        return null;
    }

    private void atualizarPatio() {
        patio.setText("");
        int abertos = 0;
        for (Ticket t : tickets) {
            patio.append(t.toString() + "\n");
            if (t.estaAberto()) abertos++;
        }
        contador.setText("No pátio: " + abertos);
    }

    private void mostrarResultado(String txt, Color cor) {
        resultado.setText(txt);
        resultado.setForeground(cor);
    }

    private void feedback(String txt, Color cor) {
        mensagem.setText(txt);
        mensagem.setForeground(cor);
    }
}
