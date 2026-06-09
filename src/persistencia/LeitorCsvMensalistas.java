package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import modelo.ClienteMensalista;

// le mensalistas de um arquivo .csv (uma pessoa por linha).
// formato: nome,cpf,placa,coberta   ->   ex: Joao Vitor,12345678901,ABC1D23,sim
public class LeitorCsvMensalistas {

    public List<ClienteMensalista> ler(String caminho) throws IOException {
        List<ClienteMensalista> lista = new ArrayList<>();
        List<String> linhas = Files.readAllLines(Paths.get(caminho));

        for (String linha : linhas) {
            if (linha.isBlank()) continue;
            // pula o cabecalho, se existir
            if (linha.toLowerCase().startsWith("nome")) continue;

            String[] col = linha.split(",");
            if (col.length < 4) continue; // linha incompleta, ignora

            String nome = col[0].trim();
            String cpf = formatarCpf(col[1].trim());
            String placa = formatarPlaca(col[2].trim());
            boolean coberta = col[3].trim().equalsIgnoreCase("sim")
                    || col[3].trim().equalsIgnoreCase("coberta")
                    || col[3].trim().equalsIgnoreCase("true");

            lista.add(new ClienteMensalista(nome, cpf, placa, coberta));
        }
        return lista;
    }

    // deixa o cpf no padrao 000.000.000-00
    private String formatarCpf(String bruto) {
        String d = bruto.replaceAll("[^0-9]", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d.length(); i++) {
            if (i == 3 || i == 6) sb.append('.');
            else if (i == 9) sb.append('-');
            sb.append(d.charAt(i));
        }
        return sb.toString();
    }

    // deixa a placa no padrao AAA-9A99
    private String formatarPlaca(String bruto) {
        String p = bruto.toUpperCase().replaceAll("[^A-Z0-9]", "");
        return p.length() > 3 ? p.substring(0, 3) + "-" + p.substring(3) : p;
    }
}
