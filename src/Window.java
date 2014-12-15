import org.apache.commons.validator.routines.UrlValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;

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
    private int numberThreads;

    public int getNumberThreads() {
        return numberThreads;
    }

    public void setNumberThreads(int numberThreads) {
        this.numberThreads = numberThreads;
    }

    public static void main(String[] args) {
        //todo splash
        //splashScreen();
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
        numberThreads = 1;
        btPlus.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rowPanel.setLayout(new BoxLayout(rowPanel,BoxLayout.Y_AXIS));
        //listeners
        btPlus.addActionListener(this);

        txtURL.setText("http://www.dominiopublico.es/libros/T/Sun_Tzu/Sun%20Tzu%20-%20El%20Arte%20de%20la%20Guerra.pdf");
        //http://192.168.1.3/test/El%20arte%20de%20la%20guerra.epub
    }

    public static void splashScreen(){
        JWindow window = new JWindow();
        try {
            window.getContentPane().add(
                    new JLabel("", new ImageIcon(new URL("http://docs.oracle.com/javase/tutorial/uiswing/examples/misc/SplashDemoProject/src/misc/images/splash.gif")), SwingConstants.CENTER));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        window.setBounds(500, 150, 300, 200);
        window.setVisible(true);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
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

        txtURL.setText("http://localhost/test/book-of-vaadin-vaadin7.epub");
        //http://diskdejorge.myds.me/test/practical%20django%20projects.pdf
        startDownload();
    }
    //http://diskdejorge.myds.me/debian-testing-amd64-DVD-1.iso
    //http://diskdejorge.myds.me/Seven.mkv
    //http://diskdejorge.myds.me/Transformers%203%20El%20Lado%20Oscuro%20De%20La%20Luna.avi

    public void startDownload(){
        final Rows rows = new Rows(this);
        /*ExecutorService threadPool = Executors.newFixedThreadPool(getNumberThreads());
        threadPool.submit(rows);*/

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

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
