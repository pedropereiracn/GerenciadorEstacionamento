package app;

import javax.swing.SwingUtilities;

import gui.TelaPrincipal;

public class Main {

    public static void main(String[] args) {
        // texto mais nitido no macOS
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
