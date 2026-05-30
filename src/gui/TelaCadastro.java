package gui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import modelo.Carro;
import modelo.Moto;
import modelo.Veiculo;

public class TelaCadastro extends JFrame {

    // ===== paleta estilo Notion / Figma =====
    private static final Color BG     = new Color(247, 247, 245); // fundo
    private static final Color CARD   = Color.WHITE;              // card
    private static final Color TEXTO  = new Color(55, 53, 47);    // texto forte
    private static final Color CINZA  = new Color(120, 119, 116); // texto leve
    private static final Color BORDA  = new Color(229, 229, 224); // linha fina
    private static final Color ACENTO = new Color(35, 131, 226);  // azul
    private static final Color ACENTO_HOVER = new Color(24, 116, 205);

    private static final String FAMILIA = escolherFonte();

    private JTextField placa;
    private JTextField modelo;
    private JTextField extra;
    private JToggleButton btCarro;
    private JToggleButton btMoto;
    private JTextArea lista;

    private final java.util.ArrayList<Veiculo> veiculos = new java.util.ArrayList<>();

    public TelaCadastro() {
        setTitle("Estacionamento");
        setSize(480, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setBackground(BG);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        root.add(cardCadastro());
        root.add(Box.createVerticalStrut(16));
        root.add(cardLista());
        root.add(Box.createVerticalGlue());

        setContentPane(root);
    }

    // ---------- card do formulario ----------
    private JComponent cardCadastro() {
        CardPanel card = new CardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(26, 26, 26, 26));

        JLabel titulo = new JLabel("Cadastrar veículo");
        titulo.setFont(fonte(20, Font.BOLD));
        titulo.setForeground(TEXTO);
        esquerda(titulo);

        JLabel sub = new JLabel("Adicione um carro ou moto ao estacionamento");
        sub.setFont(fonte(13, Font.PLAIN));
        sub.setForeground(CINZA);
        esquerda(sub);

        btCarro = new ChipToggle("Carro");
        btMoto = new ChipToggle("Moto");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(btCarro);
        grupo.add(btMoto);
        btCarro.setSelected(true);
        JPanel seg = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        seg.setOpaque(false);
        seg.add(btCarro);
        seg.add(btMoto);
        seg.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        esquerda(seg);

        placa = campo();
        modelo = campo();
        extra = campo();

        PrimaryButton bt = new PrimaryButton("Cadastrar");
        bt.addActionListener(e -> cadastrarVeiculo());

        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(22));
        card.add(rotulo("Tipo"));
        card.add(Box.createVerticalStrut(8));
        card.add(seg);
        card.add(Box.createVerticalStrut(18));
        card.add(rotulo("Placa"));
        card.add(Box.createVerticalStrut(6));
        card.add(placa);
        card.add(Box.createVerticalStrut(16));
        card.add(rotulo("Modelo"));
        card.add(Box.createVerticalStrut(6));
        card.add(modelo);
        card.add(Box.createVerticalStrut(16));
        card.add(rotulo("Portas / Cilindradas"));
        card.add(Box.createVerticalStrut(6));
        card.add(extra);
        card.add(Box.createVerticalStrut(26));
        card.add(bt);
        return card;
    }

    // ---------- card da lista ----------
    private JComponent cardLista() {
        CardPanel card = new CardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        JLabel secao = new JLabel("VEÍCULOS CADASTRADOS");
        secao.setFont(fonte(11, Font.BOLD));
        secao.setForeground(CINZA);
        esquerda(secao);

        lista = new JTextArea();
        lista.setEditable(false);
        lista.setFont(fonte(13, Font.PLAIN));
        lista.setForeground(TEXTO);
        lista.setBackground(CARD);
        lista.setBorder(BorderFactory.createEmptyBorder(4, 2, 0, 2));

        JScrollPane sp = new JScrollPane(lista,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        sp.getViewport().setBackground(CARD);
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        sp.setPreferredSize(new Dimension(10, 150));
        sp.setAlignmentX(LEFT_ALIGNMENT);

        card.add(secao);
        card.add(Box.createVerticalStrut(8));
        card.add(sp);
        return card;
    }

    // ---------- logica ----------
    private void cadastrarVeiculo() {
        // formata a placa: tira traco/espaco e deixa em maiusculo
        String p = placa.getText().toUpperCase().replaceAll("[^A-Z0-9]", "");
        String m = modelo.getText().trim();

        if (p.isEmpty() || m.isEmpty() || extra.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        // valida o formato da placa: ABC1234 (antigo) ou ABC1D23 (Mercosul)
        if (!p.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {
            JOptionPane.showMessageDialog(this,
                    "Placa inválida. Use o formato ABC1234 ou ABC1D23.");
            return;
        }

        try {
            int numero = Integer.parseInt(extra.getText().trim());

            Veiculo v;
            if (btCarro.isSelected()) {
                v = new Carro(p, m, numero);
            } else {
                v = new Moto(p, m, numero);
            }

            veiculos.add(v);
            lista.append(v.toString() + "\n"); // toString polimorfico

            placa.setText("");
            modelo.setText("");
            extra.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Portas/Cilindradas precisa ser um número.");
        }
    }

    // ---------- helpers de estilo ----------
    private JLabel rotulo(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(fonte(12, Font.BOLD));
        l.setForeground(CINZA);
        esquerda(l);
        return l;
    }

    private JTextField campo() {
        JTextField f = new JTextField();
        f.setFont(fonte(14, Font.PLAIN));
        f.setForeground(TEXTO);
        f.setBackground(CARD);
        f.setCaretColor(TEXTO);
        f.setBorder(new RoundedBorder(10, BORDA, 11, 12));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        f.setAlignmentX(LEFT_ALIGNMENT);
        return f;
    }

    private static void esquerda(JComponent c) {
        c.setAlignmentX(LEFT_ALIGNMENT);
    }

    private static Font fonte(int size, int style) {
        return new Font(FAMILIA, style, size);
    }

    private static String escolherFonte() {
        List<String> disp = Arrays.asList(GraphicsEnvironment
                .getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        for (String f : new String[]{"SF Pro Text", "Helvetica Neue", "Helvetica", "Arial"}) {
            if (disp.contains(f)) return f;
        }
        return "SansSerif";
    }

    private static void aa(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    // ===== card branco arredondado com sombra suave =====
    private static class CardPanel extends JPanel {
        private static final int ARC = 14;
        private static final int SOMBRA = 10;

        CardPanel() {
            setOpaque(false);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
        }

        @Override
        public Insets getInsets() {
            Insets i = super.getInsets();
            return new Insets(i.top, i.left, i.bottom + SOMBRA, i.right + SOMBRA);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            int w = getWidth() - SOMBRA;
            int h = getHeight() - SOMBRA;
            // sombra suave em camadas (shadows > borders)
            for (int i = 1; i <= 6; i++) {
                g2.setColor(new Color(0, 0, 0, 6));
                g2.fillRoundRect(i / 2, i, w, h, ARC, ARC);
            }
            // card
            g2.setColor(CARD);
            g2.fillRoundRect(0, 0, w, h, ARC, ARC);
            g2.setColor(BORDA);
            g2.drawRoundRect(0, 0, w - 1, h - 1, ARC, ARC);
            g2.dispose();
        }
    }

    // ===== botao primario (azul, hover/press, mao) =====
    private static class PrimaryButton extends JButton {
        PrimaryButton(String txt) {
            super(txt);
            setForeground(Color.WHITE);
            setFont(fonte(14, Font.BOLD));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(13, 16, 13, 16)); // >= 40px de altura
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
            setAlignmentX(LEFT_ALIGNMENT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            Color base = ACENTO;
            if (getModel().isPressed()) base = ACENTO_HOVER.darker();
            else if (getModel().isRollover()) base = ACENTO_HOVER;
            int d = getModel().isPressed() ? 1 : 0; // leve "press"
            g2.setColor(base);
            g2.fillRoundRect(d, d, getWidth() - 1 - 2 * d, getHeight() - 1 - 2 * d, 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== chip de selecao (pilula) =====
    private static class ChipToggle extends JToggleButton {
        ChipToggle(String txt) {
            super(txt);
            setFont(fonte(13, Font.BOLD));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            int w = getWidth(), h = getHeight();
            if (isSelected()) {
                g2.setColor(ACENTO);
                g2.fillRoundRect(0, 0, w - 1, h - 1, h, h);
                setForeground(Color.WHITE);
            } else {
                g2.setColor(CARD);
                g2.fillRoundRect(0, 0, w - 1, h - 1, h, h);
                g2.setColor(BORDA);
                g2.drawRoundRect(0, 0, w - 1, h - 1, h, h);
                setForeground(CINZA);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== borda arredondada com padding interno =====
    private static class RoundedBorder extends AbstractBorder {
        private final int arc, padV, padH;
        private final Color cor;

        RoundedBorder(int arc, Color cor, int padV, int padH) {
            this.arc = arc;
            this.cor = cor;
            this.padV = padV;
            this.padH = padH;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            g2.setColor(cor);
            g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(padV, padH, padV, padH);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets i) {
            i.set(padV, padH, padV, padH);
            return i;
        }
    }

    public static void main(String[] args) {
        // texto mais nitido no macOS
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new TelaCadastro().setVisible(true));
    }
}
