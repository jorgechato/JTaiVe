import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 15/12/14.
 */
public class Settings implements ActionListener{
    private JPanel panel1;
    private JTextField textField1;
    private JButton btSave;
    private JTextArea textArea1;
    private Window window;

    public Settings(Window window) {
        this.window = window;
        btSave.addActionListener(this);
    }

    public Settings(){}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Settings");
        frame.setContentPane(new Settings().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int number = 1;
        try{
            number = Integer.parseInt(textField1.getText());
        }catch (NumberFormatException e){
            number = 1;
        }
        window.setNumberThreads(number);
    }
}
