package posk4;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RS232Decoding {
    public String decoding(){
        String content = readFile();
        StringBuilder decoded = new StringBuilder();
        String[] frames = content.split("\\s+");

       for(String frame : frames) {
           String dataBits = frame.substring(1, 9);
           try{
               String reservedBinary = new StringBuilder(dataBits).reverse().toString(); //od LBM do MSB
               int asciiValue = Integer.parseInt(reservedBinary, 2);
               decoded.append((char)asciiValue);
           } catch(NumberFormatException e){
               System.out.println("Nieprawidłowe dane w ramce " + frame);
           }
       }
        return decoded.toString().trim();
    }

    private String readFile(){
        String fileContent = "";
        try{
            fileContent = Files.readString(Paths.get("formattedFile.txt"));
            System.out.println(fileContent);
        } catch(IOException e){
            System.out.println("Błąd podczas odczytu z pliku " + e.getMessage());
        }

        return fileContent;
    }

}
