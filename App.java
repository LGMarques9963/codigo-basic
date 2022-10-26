import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) throws Exception {
        loadProgram();
    }

    public static void loadProgram() {
        String caminhoAtual = Paths.get("basic.bas").toAbsolutePath().toString();
        Path caminho = Paths.get(caminhoAtual);
        TreeMap<Integer, String[]> map = new TreeMap<>();
        try (Scanner sc = new Scanner(Files.newBufferedReader(caminho, Charset.defaultCharset()))) {
            
            int c = 10;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] comandos = line.split(" ");
                int chave = Integer.parseInt(comandos[0]);

                comandos[0] = Integer.toString(c);
                c+=10;
                
                map.put(chave, comandos);
            }
            writeProgram(map);
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }

    }

    public static void writeProgram(SortedMap<Integer, String[]> input) throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("arquivo-rn.bas"))) {
            for (var item : input.values()) {
                StringBuilder concat = new StringBuilder();
                for (int i = 0; i < item.length; i++) {
                    String comando = item[i];
                    concat.append(comando + " ");
                    if (comando.equals("gosub") || comando.equals("goto")) {

                        int comandoSeguinte = Integer.parseInt(item[i + 1]);
                        item[i + 1] = input.get(comandoSeguinte)[0];
                    }
                }
                System.out.println(concat);
                writer.write(concat+"\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("Erro de E/S");
        }
    }

}
