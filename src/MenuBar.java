import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 27/11/14.
 */
public class MenuBar extends JMenuBar implements ActionListener {
    private JMenuBar jmb;
    private JMenuItem importFile,settings;
    private Window window;
    private JMenu file;

    public MenuBar(){

    }

    public void setWindow(Window window) {
        this.window = window;
    }

    /**
     * Create menuBar with 2 menus. One with 3 items and another with 2 items.
     */
    public JMenuBar menuBar(){
        jmb = new JMenuBar();
        file = new JMenu("Archivo");
        importFile = new JMenuItem("Importar");
        settings = new JMenuItem("Ajustes");
        jmb.add(file);
        file.add(settings);
        file.add(importFile);

        importFile.addActionListener(this);
        settings.addActionListener(this);
        importFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return jmb;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == settings){
            final Settings settings = new Settings(window);
            /*JFrame frame = new JFrame();
        frame.add(new JLabel("Welcome"));
        frame.setVisible(true);
        frame.setSize(300,100);
        window.dispose();*/
            return;
        }
        if (actionEvent.getSource() == importFile){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar en ...");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
                return;
            String path = (fileChooser.getSelectedFile().getAbsolutePath());
            return;
        }
    }
}
