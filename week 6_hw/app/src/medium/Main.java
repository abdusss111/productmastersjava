package medium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> pairs = new HashMap<>();
        HashSet<String> words = new HashSet<>();
        String path = "/Users/abduss/Desktop/productmastersjava/week 6/untitled/src/medium/read.txt";
        File f = new File(path);

        List<String> lines = Files.readAllLines(f.toPath());
        String linesString = String.join(" ", lines);
        StringTokenizer st = new StringTokenizer(linesString);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            words.add(word);
            if (pairs.containsKey(word)) {
                pairs.put(word, pairs.get(word) + 1);
            }
            else {
                pairs.put(word, 1);
            }
        }
        System.out.println(pairs);
        System.out.println(words);
    }
}
