import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by jorge on 15/12/14.
 */
public class Settings implements ActionListener{
    private JPanel panel1;
    private JButton btSave;
    private JTextArea textArea1;
    private JSpinner spinner1;
    private Window window;

    public Settings(Window window) {
        this.window = window;
        JFrame frame = new JFrame("Settings");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        btSave.addActionListener(this);

        SpinnerNumberModel model1 = new SpinnerNumberModel(1,1,10,1);
        spinner1.setModel(model1);

        loadLog();
    }

    private void loadLog() {
        try{
            FileInputStream fstream = new FileInputStream("JTaiVe.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                textArea1.setText(textArea1.getText()+"\n"+strLine);
            }
            br.close();
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        window.setNumberThreads((Integer) spinner1.getValue());

    }
}
