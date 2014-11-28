import org.apache.commons.validator.routines.UrlValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by jorge on 27/11/14.
 */
public class Window extends Component implements ActionListener{

    private JPanel panel1;
    private JButton btPlus;
    private JScrollPane scrollPane;
    private JPanel rowPanel;
    private JTextField txtURL;
    private String path,url;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JTaiVe");
        MenuBar menuBar = new MenuBar();
        Window window = new Window();
        frame.setJMenuBar(menuBar.menuBar());
        menuBar.setWindow(window);
        frame.setContentPane(window.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Window(){
        init();
    }

    public void init(){
        btPlus.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rowPanel.setLayout(new BoxLayout(rowPanel,BoxLayout.Y_AXIS));
        //listeners
        btPlus.addActionListener(this);

        txtURL.setText("http://192.168.1.3/El%20arte%20de%20la%20guerra.epub");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(txtURL.getText())){
            JOptionPane.showMessageDialog(null, "URL no válida",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar en ...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        path = (fileChooser.getSelectedFile().getAbsolutePath());
        url = txtURL.getText();

        txtURL.setText("http://192.168.1.3/practical%20django%20projects.pdf");
        startDownload();
    }
    //http://192.168.1.3/debian-testing-amd64-DVD-1.iso
    //http://192.168.1.3/Seven.mkv
    //http://192.168.1.3/Transformers%203%20El%20Lado%20Oscuro%20De%20La%20Luna.avi

    public void startDownload(){
        final Rows rows = new Rows(this);

        rows.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                if (propertyChangeEvent.getPropertyName().equals("progress"))
                    rows.getPbDownload().setValue((Integer) propertyChangeEvent.getNewValue());
            }
        });
        rowPanel.add(rows.getPanel1());

        rowPanel.updateUI();
        rows.execute();
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }
}
