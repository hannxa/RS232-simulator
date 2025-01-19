package posk4;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nadajnik");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,200);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.add(panel);

        placeComponents(panel);
        frame.setVisible(true);
    }
    private static void placeComponents (JPanel panel){
        JLabel label = new JLabel(" Wprowadź text: ");
        panel.add(label);
        label.setFont(new Font("Nunito", Font.BOLD, 18));

        JTextArea textArea = new JTextArea(1,20);
        panel.add(textArea);
        textArea.setFont(new Font("Nunito", Font.ROMAN_BASELINE, 18));

        JButton formatButton = new JButton("Wyslij");
        panel.add(formatButton);
        formatButton.setFont(new Font("Nunito", Font.BOLD, 18));
        formatButton.setForeground(Color.RED);


        JLabel formatedTextLabel = new JLabel();
        panel.add(formatedTextLabel);
        formatedTextLabel.setVisible(false);
        formatedTextLabel.setFont(new Font("Nunito", Font.ITALIC, 14));

        JLabel odbiornikProgress = new JLabel("~~~Nadawanie do odbiornika~~~");
        panel.add(odbiornikProgress);
        odbiornikProgress.setVisible(false);
        odbiornikProgress.setFont(new Font("Nunito", Font.ITALIC, 16));


        //akcja do przycisku
        formatButton.addActionListener(_ -> {
            //sprawdzanie grubianstwa przed dalszym prztwarzaniem
            Dictionary dictionary = new Dictionary();
            String newWord = dictionary.ifBadWord(textArea.getText());
            textArea.setText(newWord);

            //formatowanie tekstu
            RS232Formatter formatter = new RS232Formatter();
            String result = formatter.format(textArea.getText());
            formatedTextLabel.setText("Łańcuch: " + result);
            formatedTextLabel.setVisible(true);
            odbiornikProgress.setVisible(true);
            createSecondWindow();
        });
    }
    private static void createSecondWindow(){
        JFrame secondFrame = new JFrame("Odbiornik");
        secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secondFrame.setSize(700,200);
        secondFrame.setResizable(false);


        JLabel formatedTextLabel2 = new JLabel("~~~przesyłanie do odbiornika w toku~~~");
        secondFrame.add(formatedTextLabel2,BorderLayout.CENTER);
        formatedTextLabel2.setHorizontalAlignment(SwingConstants.CENTER); //wysrodkowanie tekstu
        formatedTextLabel2.setFont(new Font("Nunito", Font.ITALIC, 16));

        JButton decodingButton = new JButton("Nacisnij, aby z powrotem zamienić na ciąg znaków ASCII:");
        secondFrame.add(decodingButton,BorderLayout.NORTH);
        decodingButton.setFont(new Font("Nunito", Font.BOLD, 18));


        decodingButton.addActionListener(_ -> {
            RS232Decoding decoding = new RS232Decoding();
            String result = decoding.decoding();
            formatedTextLabel2.setText("Odebrane dane: " + result);
            decodingButton.setText("Sukcesywnie przesłano do odbiornika");
        });
        secondFrame.setVisible(true);
    }
}
