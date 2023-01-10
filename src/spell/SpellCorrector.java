package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        Trie dict = new Trie();
        while (scanner.hasNext()) {
            dict.add(scanner.next());
        }
        String output = dict.toString();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
