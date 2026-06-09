package gui;

import static gui.Estilo.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import modelo.ClienteMensalista;
import modelo.TipoPreferencia;

// aba de cadastro de mensalista: a pessoa tem ficha (nome, cpf, placa) e paga por mes.
public class AbaMensalistas extends JPanel {

    private final List<ClienteMensalista> mensalistas; // lista compartilhada com a janela

    private JTextField nome;
    private JTextField cpf;
    private JTextField placa;
    private JToggleButton btComum;
    private JToggleButton btCoberta;
    private JToggleButton btCredNenhuma;
    private JToggleButton btCredIdoso;
    private JToggleButton btCredPcd;
    private JToggleButton btCredAutista;
    private JLabel previa;   // mostra a mensalidade conforme o tipo de vaga
    private JLabel mensagem; // feedback de sucesso/erro
    private JTextArea lista;
    private JLabel contador;

    public AbaMensalistas(List<ClienteMensalista> mensalistas) {
        this.mensalistas = mensalistas;

        setBackground(BG);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(cardFormulario());
        add(Box.createVerticalStrut(16));
        add(cardLista());
        add(Box.createVerticalGlue());
    }

    private JComponent cardFormulario() {
        CardPanel card = new CardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(26, 26, 26, 26));

        nome = campo();
        cpf = campo();
        aplicarMascaraCpf(cpf);
        placa = campo();
        aplicarMascaraPlaca(placa);

        btComum = new ChipToggle("Comum");
        btCoberta = new ChipToggle("Coberta");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(btComum);
        grupo.add(btCoberta);
        btComum.setSelected(true);
        btComum.addItemListener(e -> atualizarPrevia());
        btCoberta.addItemListener(e -> atualizarPrevia());

        JPanel seg = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        seg.setOpaque(false);
        seg.add(btComum);
        seg.add(btCoberta);
        seg.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        esquerda(seg);

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

        previa = new JLabel();
        previa.setFont(fonte(13, Font.BOLD));
        previa.setForeground(ACENTO);
        esquerda(previa);
        atualizarPrevia();

        PrimaryButton bt = new PrimaryButton("Cadastrar mensalista");
        bt.addActionListener(e -> cadastrar());

        mensagem = new JLabel(" ");
        mensagem.setFont(fonte(13, Font.BOLD));
        esquerda(mensagem);

        card.add(titulo("Cadastrar mensalista"));
        card.add(Box.createVerticalStrut(4));
        card.add(subtitulo("Cliente com ficha e mensalidade fixa"));
        card.add(Box.createVerticalStrut(22));
        card.add(rotulo("Nome"));
        card.add(Box.createVerticalStrut(6));
        card.add(nome);
        card.add(Box.createVerticalStrut(16));
        card.add(rotulo("CPF"));
        card.add(Box.createVerticalStrut(6));
        card.add(cpf);
        card.add(Box.createVerticalStrut(16));
        card.add(rotulo("Placa"));
        card.add(Box.createVerticalStrut(6));
        card.add(placa);
        card.add(Box.createVerticalStrut(18));
        card.add(rotulo("Tipo de vaga"));
        card.add(Box.createVerticalStrut(8));
        card.add(seg);
        card.add(Box.createVerticalStrut(16));
        card.add(rotulo("Credencial"));
        card.add(Box.createVerticalStrut(8));
        card.add(segCred);
        card.add(Box.createVerticalStrut(10));
        card.add(previa);
        card.add(Box.createVerticalStrut(20));
        card.add(bt);
        card.add(Box.createVerticalStrut(12));
        card.add(mensagem);
        return card;
    }

    private JComponent cardLista() {
        CardPanel card = new CardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        JLabel secao = new JLabel("MENSALISTAS");
        secao.setFont(fonte(11, Font.BOLD));
        secao.setForeground(CINZA);

        contador = new JLabel("Total: 0");
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

        lista = areaLista();

        card.add(cabecalho);
        card.add(Box.createVerticalStrut(8));
        card.add(scrollLista(lista, 150));
        return card;
    }

    private void atualizarPrevia() {
        double valor = btCoberta.isSelected()
                ? ClienteMensalista.MENSALIDADE_COBERTA
                : ClienteMensalista.MENSALIDADE_COMUM;
        previa.setText(String.format("Mensalidade: R$ %.2f", valor));
    }

    private TipoPreferencia credencialEscolhida() {
        if (btCredIdoso.isSelected()) return TipoPreferencia.IDOSO;
        if (btCredPcd.isSelected()) return TipoPreferencia.PCD;
        if (btCredAutista.isSelected()) return TipoPreferencia.AUTISTA;
        return null;
    }

    private void cadastrar() {
        String n = nome.getText().trim();
        String c = cpf.getText().replaceAll("[^0-9]", "");
        String p = placa.getText().toUpperCase().replaceAll("[^A-Z0-9]", "");

        if (n.isEmpty() || c.isEmpty() || p.isEmpty()) {
            feedback("⚠ Preencha todos os campos.", VERMELHO);
            return;
        }
        if (c.length() != 11) {
            feedback("⚠ CPF precisa ter 11 dígitos.", VERMELHO);
            return;
        }
        if (!p.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}")) {
            feedback("⚠ Placa inválida (padrão Mercosul AAA-9A99).", VERMELHO);
            return;
        }

        TipoPreferencia credencial = credencialEscolhida();
        ClienteMensalista m = (credencial == null)
                ? new ClienteMensalista(n, formatarCpf(c), formatarPlaca(p), btCoberta.isSelected())
                : new ClienteMensalista(n, formatarCpf(c), formatarPlaca(p), btCoberta.isSelected(), credencial);
        mensalistas.add(m);
        lista.append(m.descricao() + "\n");
        contador.setText("Total: " + mensalistas.size());

        nome.setText("");
        cpf.setText("");
        placa.setText("");
        btCredNenhuma.setSelected(true);
        feedback("✓ Mensalista " + n + " cadastrado.", VERDE);
    }

    private void feedback(String txt, Color cor) {
        mensagem.setText(txt);
        mensagem.setForeground(cor);
    }

    // reconstrói a lista na tela (usado depois de carregar do arquivo)
    public void recarregar() {
        lista.setText("");
        for (ClienteMensalista m : mensalistas) {
            lista.append(m.descricao() + "\n");
        }
        contador.setText("Total: " + mensalistas.size());
    }
}
