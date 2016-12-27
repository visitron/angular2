package home.maintenance.dao;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vsoshyn on 27/12/2016.
 */
public class StringTokenizer {

    private Set<String> dictionary;
    private List<String> result = new ArrayList<>();

    @Test
    public void tokenize() throws Exception {
        dictionary = generateDictionary("d:/temp/vedmino okno.txt");

        analyze("актаалкоголизмаамулетов");

        result.forEach(System.out::println);
    }

    private Set<String> generateDictionary(String path) throws IOException {
        Set<String> rawWords = new TreeSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("windows-1251")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("[\\s\\d]+");
                rawWords.addAll(Arrays.stream(words).map(this::normalize).collect(Collectors.toList()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        rawWords.add("акта");

        return rawWords;
    }

    private String normalize(String val) {
        StringBuilder builder = new StringBuilder();
        StringCharacterIterator iterator = new StringCharacterIterator(val);
        char letter;

        while ((letter = iterator.current()) != StringCharacterIterator.DONE) {
            if (Character.isAlphabetic(letter)) {
                builder.append(letter);
            }
            iterator.next();
        }

        return builder.toString();
    }

    private boolean analyze(String letters) {

        for (int j = 1; j <= letters.length(); j++) {
            String tmp = letters.substring(0, j);
            if (dictionary.contains(tmp)) {
                if (j != letters.length()) {
                    result.add(tmp);
                    boolean res = analyze(letters.substring(j));
                    if (!res) {
                        result.remove(result.size() - 1);
                    } else {
                        return true;
                    }
                } else {
                    result.add(tmp);
                    return true;
                }
            }
        }

        return false;
    }

}
