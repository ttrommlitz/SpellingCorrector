package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String lowerCase = inputWord.toLowerCase();
        if (dict.find(lowerCase) != null) { return lowerCase; }
        Set<String> set = new HashSet<>();
        addEdits(lowerCase, set);
        String result = Tiebreaker(set);
        if(result != null) { return result; }
        Set<String> set2 = new HashSet<>();
        for (String element : set) {
            addEdits(element, set2);
        }
        return Tiebreaker(set2);
    }
    //helpers
    private void addDeletionEdits(String word, Set<String> set) {
        for (int i = 0; i < word.length(); i++) {
            StringBuilder sb = new StringBuilder(word);
            sb.deleteCharAt(i);
            set.add(sb.toString());
        }
    }

    private void addTranspositionEdits(String word, Set<String> set) {
        for (int i = 0; i < word.length() - 1; i++) {
            StringBuilder sb = new StringBuilder(word);
            sb.setCharAt(i, word.charAt(i + 1));
            sb.setCharAt(i + 1, word.charAt(i));
            set.add(sb.toString());
        }
    }

    private void addAlterationEdits(String word, Set<String> set) {
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char replacement = (char)('a' + j);
                if (replacement == word.charAt(i)) { continue; }
                StringBuilder sb = new StringBuilder(word);
                sb.setCharAt(i, replacement);
                set.add(sb.toString());
            }
        }
    }

    private void addInsertionEdits(String word, Set<String> set) {
        for (int i = 0; i < word.length() + 1; i++) {
            for (int j = 0; j < 26; j++) {
                char insertion = (char)('a' + j);
                StringBuilder sb = new StringBuilder(word);
                sb.insert(i, insertion);
                set.add(sb.toString());
            }
        }
    }

    private String Tiebreaker(Set<String> set) {
        ArrayList<String> allStrings = new ArrayList<>();
        Map<String, Integer> distOneElements = new HashMap<>();
        for (String element : set) {
            INode result = dict.find(element);
            if (result != null) {
                distOneElements.put(element, result.getValue());
                allStrings.add(element);
            }
        }
        if (distOneElements.isEmpty()) { return null; }
        if (distOneElements.size() == 1) { return allStrings.get(0); }

        String bestString = allStrings.get(0);
        int maxCount = distOneElements.get(bestString);
        ArrayList<String> bestStrings = new ArrayList<>();
        bestStrings.add(bestString);
        for (int i = 1; i < allStrings.size(); i++) {
            if (distOneElements.get(allStrings.get(i)) == maxCount) {
                bestStrings.add(allStrings.get(i));
            }
            else if (distOneElements.get(allStrings.get(i)) >= maxCount) {
                bestStrings = new ArrayList<>();
                bestString = allStrings.get(i);
                bestStrings.add(bestString);
                maxCount = distOneElements.get(allStrings.get(i));
            }
        }
        if (bestStrings.size() == 1) { return bestStrings.get(0); }
        Collections.sort(bestStrings);
        return bestStrings.get(0);
    }

    private void addEdits(String word, Set<String> set) {
        addDeletionEdits(word, set);
        addTranspositionEdits(word, set);
        addAlterationEdits(word, set);
        addInsertionEdits(word, set);
    }
}
