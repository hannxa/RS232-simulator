package posk4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class Dictionary {
private HashSet<String> badWords;

    public String ifBadWord(String givenText){
        readFile();
        String[] words = givenText.split("\\s+");
        StringBuilder resultText = new StringBuilder();

        for(String word : words){
            if(badWords.contains(word.toLowerCase())){ //porownujemy w formacie malych literek
                word = censoring(word);
            }
            resultText.append(word).append(" ");
        }
        return resultText.toString().trim();
    }

    private String censoring(String givenWord){
        char first = givenWord.charAt(0);
        char last = givenWord.charAt(givenWord.length()-1);
        String stars = "*".repeat(givenWord.length()-2);
        return first + stars + last;
    }

    private void readFile(){
        badWords = new HashSet<>();
        try{
            String content = (Files.readString(Paths.get("słownikGrubieństw.txt")));
            String[] words = content.split("\\r?\\n");
            for (String word : words) {
                badWords.add(word.trim().toLowerCase());
            }
        } catch(IOException e){
            System.out.println("Błąd podczas odczytu z pliku " + e.getMessage());
        }
    }
}
