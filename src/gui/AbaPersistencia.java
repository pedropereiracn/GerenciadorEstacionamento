package gui;

import static gui.Estilo.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.ClienteMensalista;
import persistencia.LeitorCsvMensalistas;
import persistencia.PersistenciaService;

// aba de dados: salva/carrega (serializacao) e importa mensalistas de um csv.
public class AbaPersistencia extends JPanel {

    private static final String ARQUIVO = "mensalistas.dat";
    private static final String CSV = "mensalistas.csv";

    private final List<ClienteMensalista> mensalistas;
    private final Runnable aoCarregar; // avisa a aba de mensalistas pra atualizar a lista
    private final PersistenciaService<ArrayList<ClienteMensalista>> servico = new PersistenciaService<>();

    private JLabel status;

    public AbaPersistencia(List<ClienteMensalista> mensalistas, Runnable aoCarregar) {
        this.mensalistas = mensalistas;
        this.aoCarregar = aoCarregar;

        setBackground(BG);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(cardDados());
        add(Box.createVerticalGlue());
    }

    private JComponent cardDados() {
        CardPanel card = new CardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(26, 26, 26, 26));

        PrimaryButton btSalvar = new PrimaryButton("Salvar mensalistas");
        btSalvar.addActionListener(e -> salvar());

        GhostButton btCarregar = new GhostButton("Carregar do arquivo");
        btCarregar.addActionListener(e -> carregar());

        GhostButton btImportar = new GhostButton("Importar CSV");
        btImportar.addActionListener(e -> importarCsv());

        status = new JLabel(" ");
        status.setFont(fonte(13, Font.BOLD));
        esquerda(status);

        JLabel caminho = new JLabel("Arquivo: " + new File(ARQUIVO).getAbsolutePath());
        caminho.setFont(fonte(11, Font.PLAIN));
        caminho.setForeground(CINZA);
        esquerda(caminho);

        card.add(titulo("Dados"));
        card.add(Box.createVerticalStrut(4));
        card.add(subtitulo("Serialização (.dat) e importação de mensalistas via .csv"));
        card.add(Box.createVerticalStrut(24));
        card.add(btSalvar);
        card.add(Box.createVerticalStrut(10));
        card.add(btCarregar);
        card.add(Box.createVerticalStrut(10));
        card.add(btImportar);
        card.add(Box.createVerticalStrut(16));
        card.add(status);
        card.add(Box.createVerticalStrut(10));
        card.add(caminho);
        return card;
    }

    private void salvar() {
        try {
            servico.salvar(new ArrayList<>(mensalistas), ARQUIVO);
            mostrar("✓ " + mensalistas.size() + " mensalista(s) salvos.", VERDE);
        } catch (IOException ex) {
            mostrar("⚠ Erro ao salvar: " + ex.getMessage(), VERMELHO);
        }
    }

    private void carregar() {
        try {
            ArrayList<ClienteMensalista> lidos = servico.carregar(ARQUIVO);
            mensalistas.clear();
            mensalistas.addAll(lidos);
            aoCarregar.run(); // atualiza a aba de mensalistas
            mostrar("✓ " + lidos.size() + " mensalista(s) carregados.", VERDE);
        } catch (IOException | ClassNotFoundException ex) {
            mostrar("⚠ Nada para carregar ainda (salve primeiro).", VERMELHO);
        }
    }

    // le os mensalistas de um arquivo .csv e adiciona na lista
    private void importarCsv() {
        try {
            List<ClienteMensalista> lidos = new LeitorCsvMensalistas().ler(CSV);
            mensalistas.addAll(lidos);
            aoCarregar.run();
            mostrar("✓ " + lidos.size() + " mensalista(s) importados do CSV.", VERDE);
        } catch (IOException ex) {
            mostrar("⚠ Não achei o arquivo " + CSV, VERMELHO);
        }
    }

    private void mostrar(String txt, Color cor) {
        status.setText(txt);
        status.setForeground(cor);
    }
}
