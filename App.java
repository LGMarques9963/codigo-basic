import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;

public class App {
    public static void main(String[] args) throws Exception {
        loadProgram();
    }

    public static void loadProgram() {
        String currDir = Paths.get("basic.bas").toAbsolutePath().toString();
        String nameComplete = currDir;
        Path path2 = Paths.get(nameComplete);
        TreeMap<Integer, String[]> map = new TreeMap<Integer, String[]>();
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))) {
            
            int c = 10;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] tokens = line.split(" ");
                int chave = Integer.parseInt(tokens[0]);
                tokens[0] = Integer.toString(c);
                c+=10;
                System.out.println(tokens[0].toString());
                map.put(chave, tokens);
            }
            System.out.println(map);
            writeProgram(map);
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }

    }

    public static void writeProgram(TreeMap<Integer, String[]> input) throws IOException {
        
        BufferedWriter writer = new BufferedWriter(new FileWriter("arquivo-rn.bas"));
        for (var item : input.entrySet()) {
            String concat = "";
            for (int i = 0; i < item.getValue().length; i++) {
                String comando = item.getValue()[i];
                concat += comando + " ";
                if (comando.equals("gosub") || comando.equals("goto")) {

                    int comandoSeguinte = Integer.parseInt(item.getValue()[i + 1]);
                    item.getValue()[i + 1] = input.get(comandoSeguinte)[0];
                }
            }
            System.out.println(concat);
            writer.write(concat+"\n");
        }
        writer.close();
    }

}
/*
 * String frase = "";
 * for(int i =0; i < item.getValue().length; i++){
 * frase += new String(comando+" ");
 * if(i == item.getValue().length-1){
 * frase += "\n";
 * }
 * }
 */ // writer.write(frase);
