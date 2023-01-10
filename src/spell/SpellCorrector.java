package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    private Trie dict;
    public SpellCorrector() {
        dict = new Trie();
    }
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            dict.add(scanner.next());
        }
        String output = dict.toString();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String lowerCase = inputWord.toLowerCase();
        if (dict.find(lowerCase) != null) { return lowerCase; }
        return null;
    }
}
