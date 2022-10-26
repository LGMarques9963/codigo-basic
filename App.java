import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
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
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] tokens = line.split(" ");
                // TRATAR A LINHA LIDA AQUI !!
                System.out.println(tokens[0].toString());
                map.put(Integer.parseInt(tokens[0]), tokens);
            }
            System.out.println(map);
            writeProgram(map);
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }

    }

    public static void writeProgram(TreeMap<Integer, String[]> input) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("arquivo-rn.bas"));
        int c = 10;
        for (var item : input.entrySet()) {
            item.getValue()[0] = Integer.toString(c);
            c += 10;
        }
        for (var item : input.entrySet()) {
            int linha = Integer.parseInt(item.getValue()[0]);
            String concat = "";
            for (int i = 0; i < item.getValue().length; i++) {
                concat=concat + item.getValue()[i] + " ";
                if(item.getValue()[i].equals("gosub") || item.getValue()[i].equals("goto")){
                    item.getValue()[i+1] = input.get(Integer.parseInt(item.getValue()[i+1]))[0];
                }
            }
            System.out.println(concat);
        }
        writer.close();
    }

}
/*
 * String frase = "";
 * for(int i =0; i < item.getValue().length; i++){
 * frase += new String(item.getValue()[i]+" ");
 * if(i == item.getValue().length-1){
 * frase += "\n";
 * }
 * }
 */ // writer.write(frase);
