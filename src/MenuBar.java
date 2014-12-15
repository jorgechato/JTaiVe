import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by jorge on 27/11/14.
 */
public class MenuBar extends JMenuBar implements ActionListener {
    private JMenuBar jmb;
    private JMenuItem importFile,settings;
    private Window window;
    private JMenu file;
    private JCheckBoxMenuItem check;

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
        check = new JCheckBoxMenuItem("Programar descarga");
        jmb.add(file);
        file.add(settings);
        file.add(check);
        file.add(importFile);

        importFile.addActionListener(this);
        settings.addActionListener(this);
        check.addActionListener(this);
        importFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return jmb;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == settings){
            final Settings settings = new Settings(window);
            return;
        }
        if (actionEvent.getSource() == importFile){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Descargar de fichero");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
                return;

            readFile((fileChooser.getSelectedFile().getAbsolutePath()));
            return;
        }
        if (actionEvent.getSource() == check){
            if (check.isSelected()){
                window.setProgrammDownload(true);
            }
        }
    }

    private void readFile(String path) {
        try{
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                window.takeURL(strLine);
            }
            br.close();
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
