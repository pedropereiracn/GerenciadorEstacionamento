package gui;

import static gui.Estilo.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import modelo.ClienteMensalista;

// janela principal: junta as abas (Entrada, Mensalistas, Dados) num visual so.
// uso um seletor de pilulas em cima + CardLayout embaixo (mais limpo que o JTabbedPane padrao).
public class TelaPrincipal extends JFrame {

    // lista compartilhada entre as abas (associacao: a tela "tem" os mensalistas)
    private final List<ClienteMensalista> mensalistas = new ArrayList<>();

    private final CardLayout cards = new CardLayout();
    private final JPanel conteudo = new JPanel(cards);

    public TelaPrincipal() {
        setTitle("Estacionamento");
        setSize(480, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        // monta as abas
        AbaMensalistas abaMensalistas = new AbaMensalistas(mensalistas);
        AbaEntrada abaEntrada = new AbaEntrada(mensalistas);
        AbaPersistencia abaDados = new AbaPersistencia(mensalistas, abaMensalistas::recarregar);

        conteudo.setOpaque(false);
        conteudo.add(abaEntrada, "entrada");
        conteudo.add(abaMensalistas, "mensalistas");
        conteudo.add(abaDados, "dados");

        root.add(barraAbas(), BorderLayout.NORTH);
        root.add(conteudo, BorderLayout.CENTER);
        setContentPane(root);
    }

    // barra de pilulas que troca o card mostrado
    private JComponent barraAbas() {
        JPanel topo = new JPanel();
        topo.setBackground(BG);
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.setBorder(BorderFactory.createEmptyBorder(24, 24, 4, 24));

        JLabel marca = new JLabel("🅿  Estacionamento");
        marca.setFont(fonte(18, Font.BOLD));
        marca.setForeground(TEXTO);
        esquerda(marca);

        ChipToggle tEntrada = new ChipToggle("Entrada");
        ChipToggle tMensal = new ChipToggle("Mensalistas");
        ChipToggle tDados = new ChipToggle("Dados");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(tEntrada);
        grupo.add(tMensal);
        grupo.add(tDados);
        tEntrada.setSelected(true);

        tEntrada.addActionListener(e -> cards.show(conteudo, "entrada"));
        tMensal.addActionListener(e -> cards.show(conteudo, "mensalistas"));
        tDados.addActionListener(e -> cards.show(conteudo, "dados"));

        JPanel abas = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        abas.setOpaque(false);
        abas.add(tEntrada);
        abas.add(tMensal);
        abas.add(tDados);
        abas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        esquerda(abas);

        topo.add(marca);
        topo.add(Box.createVerticalStrut(14));
        topo.add(abas);
        return topo;
    }

    public static void main(String[] args) {
        // texto mais nitido no macOS
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
