package gui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

// reune a paleta, as fontes e os componentes visuais usados pelas telas.
// deixei tudo aqui pra cada aba ficar curtinha e o visual ficar igual em todas.
public final class Estilo {

    private Estilo() { } // so utilitario, nao instancia

    // ===== paleta estilo Notion / Figma =====
    public static final Color BG     = new Color(247, 247, 245); // fundo
    public static final Color CARD   = Color.WHITE;              // card
    public static final Color TEXTO  = new Color(55, 53, 47);    // texto forte
    public static final Color CINZA  = new Color(120, 119, 116); // texto leve
    public static final Color BORDA  = new Color(229, 229, 224); // linha fina
    public static final Color ACENTO = new Color(35, 131, 226);  // azul
    public static final Color ACENTO_HOVER = new Color(24, 116, 205);
    public static final Color VERDE   = new Color(22, 138, 73);  // sucesso
    public static final Color VERMELHO = new Color(204, 67, 61); // erro

    private static final String FAMILIA = escolherFonte();

    // ---------- fontes ----------
    public static Font fonte(int size, int style) {
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

    // ---------- helpers de montagem ----------
    public static void esquerda(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static JLabel titulo(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(fonte(20, Font.BOLD));
        l.setForeground(TEXTO);
        esquerda(l);
        return l;
    }

    public static JLabel subtitulo(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(fonte(13, Font.PLAIN));
        l.setForeground(CINZA);
        esquerda(l);
        return l;
    }

    public static JLabel rotulo(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(fonte(12, Font.BOLD));
        l.setForeground(CINZA);
        esquerda(l);
        return l;
    }

    public static JTextField campo() {
        JTextField f = new JTextField();
        f.setFont(fonte(14, Font.PLAIN));
        f.setForeground(TEXTO);
        f.setBackground(CARD);
        f.setCaretColor(TEXTO);
        f.setBorder(new RoundedBorder(10, BORDA, 11, 12));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }

    // area de texto somente-leitura usada nas listas
    public static JTextArea areaLista() {
        JTextArea a = new JTextArea();
        a.setEditable(false);
        a.setLineWrap(true);      // quebra a linha em vez de cortar texto longo
        a.setWrapStyleWord(true); // quebra na palavra, nao no meio dela
        a.setFont(fonte(13, Font.PLAIN));
        a.setForeground(TEXTO);
        a.setBackground(CARD);
        a.setBorder(BorderFactory.createEmptyBorder(4, 2, 0, 2));
        return a;
    }

    // embrulha a area num scroll com altura fixa (evita a lista "esmagar" no BoxLayout)
    public static JComponent scrollLista(JTextArea area, int altura) {
        JScrollPane sp = new JScrollPane(area,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        sp.getViewport().setBackground(CARD);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(sp, BorderLayout.CENTER);
        wrap.setPreferredSize(new Dimension(10, altura));
        wrap.setMinimumSize(new Dimension(10, altura));
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, altura));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        return wrap;
    }

    static void aa(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    // coloca o hifen do padrao Mercosul: ABC1D23 -> ABC-1D23
    public static String formatarPlaca(String limpo) {
        return limpo.length() > 3 ? limpo.substring(0, 3) + "-" + limpo.substring(3) : limpo;
    }

    // ===== card branco arredondado com sombra suave (sombra > borda) =====
    public static class CardPanel extends JPanel {
        private static final int ARC = 14;
        private static final int SOMBRA = 10;

        public CardPanel() {
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
            for (int i = 1; i <= 6; i++) {
                g2.setColor(new Color(0, 0, 0, 6));
                g2.fillRoundRect(i / 2, i, w, h, ARC, ARC);
            }
            g2.setColor(CARD);
            g2.fillRoundRect(0, 0, w, h, ARC, ARC);
            g2.setColor(BORDA);
            g2.drawRoundRect(0, 0, w - 1, h - 1, ARC, ARC);
            g2.dispose();
        }
    }

    // ===== botao primario (azul, hover/press, mao) =====
    public static class PrimaryButton extends JButton {
        public PrimaryButton(String txt) {
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
            setAlignmentX(Component.LEFT_ALIGNMENT);
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

    // ===== botao secundario (branco com borda) =====
    public static class GhostButton extends JButton {
        public GhostButton(String txt) {
            super(txt);
            setForeground(TEXTO);
            setFont(fonte(14, Font.BOLD));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(13, 16, 13, 16));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            g2.setColor(getModel().isRollover() ? BG : CARD);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g2.setColor(BORDA);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== chip de selecao (pilula) =====
    public static class ChipToggle extends JToggleButton {
        public ChipToggle(String txt) {
            super(txt);
            setFont(fonte(13, Font.BOLD));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18)); // ~40px de alvo
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
    public static class RoundedBorder extends AbstractBorder {
        private final int arc, padV, padH;
        private final Color cor;

        public RoundedBorder(int arc, Color cor, int padV, int padH) {
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

    // liga a mascara de placa Mercosul (AAA-9A99) num campo de texto
    public static void aplicarMascaraPlaca(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new MascaraPlaca());
    }

    // liga a mascara de CPF (000.000.000-00) num campo de texto
    public static void aplicarMascaraCpf(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new MascaraCpf());
    }

    // formata 11 digitos como 000.000.000-00
    public static String formatarCpf(String digitos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digitos.length(); i++) {
            if (i == 3 || i == 6) sb.append('.');
            else if (i == 9) sb.append('-');
            sb.append(digitos.charAt(i));
        }
        return sb.toString();
    }

    // ===== mascara de CPF: so numeros, ate 11 digitos, com pontos e traco =====
    private static class MascaraCpf extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int off, String txt, AttributeSet a)
                throws BadLocationException {
            aplicar(fb, off, 0, txt, a);
        }

        @Override
        public void replace(FilterBypass fb, int off, int len, String txt, AttributeSet a)
                throws BadLocationException {
            aplicar(fb, off, len, txt, a);
        }

        @Override
        public void remove(FilterBypass fb, int off, int len) throws BadLocationException {
            aplicar(fb, off, len, "", null);
        }

        private void aplicar(FilterBypass fb, int off, int len, String txt, AttributeSet a)
                throws BadLocationException {
            var doc = fb.getDocument();
            String atual = doc.getText(0, doc.getLength());
            String novo = atual.substring(0, off) + (txt == null ? "" : txt)
                    + atual.substring(off + len);
            novo = novo.replaceAll("[^0-9]", "");
            if (novo.length() > 11) novo = novo.substring(0, 11);
            fb.replace(0, doc.getLength(), formatarCpf(novo), a);
        }
    }

    // ===== mascara de placa Mercosul: AAA-9A99 (letras, numero, letra, numero, numero) =====
    private static class MascaraPlaca extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int off, String txt, AttributeSet a)
                throws BadLocationException {
            aplicar(fb, off, 0, txt, a);
        }

        @Override
        public void replace(FilterBypass fb, int off, int len, String txt, AttributeSet a)
                throws BadLocationException {
            aplicar(fb, off, len, txt, a);
        }

        @Override
        public void remove(FilterBypass fb, int off, int len) throws BadLocationException {
            aplicar(fb, off, len, "", null);
        }

        private void aplicar(FilterBypass fb, int off, int len, String txt, AttributeSet a)
                throws BadLocationException {
            var doc = fb.getDocument();
            String atual = doc.getText(0, doc.getLength());
            String novo = atual.substring(0, off) + (txt == null ? "" : txt)
                    + atual.substring(off + len);
            novo = novo.toUpperCase().replaceAll("[^A-Z0-9]", "");
            if (novo.length() > 7) novo = novo.substring(0, 7);
            for (int i = 0; i < novo.length(); i++) {
                if (!posicaoOk(novo.charAt(i), i)) return;
            }
            fb.replace(0, doc.getLength(), formatarPlaca(novo), a);
        }

        private boolean posicaoOk(char ch, int i) {
            if (i <= 2) return Character.isLetter(ch); // AAA (letras)
            if (i == 3) return Character.isDigit(ch);  // 9  (numero)
            if (i == 4) return Character.isLetter(ch); // A  (letra - Mercosul)
            return Character.isDigit(ch);              // 99 (numeros)
        }
    }
}
