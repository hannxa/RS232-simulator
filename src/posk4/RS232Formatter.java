package posk4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RS232Formatter {

    public String format(String text) {
        String formattedString;
        StringBuilder formatted = new StringBuilder();
        for(char character : text.toCharArray()) {
            //zmiana na kodA SCII
            String binaryChar = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0');

            String reservedBinary = new StringBuilder(binaryChar).reverse().toString(); //od LBM do MSB
            //dodanie bitu startu i stopu
            formattedString = "0" + reservedBinary + "11";

            formatted.append(formattedString).append(" ");
            writeToFile(formatted.toString());
        }
        return formatted.toString().trim();
    }
    private void writeToFile(String text) {
        try{
            Files.write(Paths.get("formattedFile.txt"), text.getBytes());
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisu pliku" + e.getMessage());
        }
    }
}
