import org.apache.commons.validator.routines.UrlValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

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
    private boolean programmDownload;
    private Timer timer;
    private int pos;

    public boolean isProgrammDownload() {
        return programmDownload;
    }

    public void setProgrammDownload(boolean programmDownload) {
        this.programmDownload = programmDownload;
    }

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

        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
        //listeners
        btPlus.addActionListener(this);

        txtURL.setText("http://www.dominiopublico.es/libros/T/Sun_Tzu/Sun%20Tzu%20-%20El%20Arte%20de%20la%20Guerra.pdf");
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
        pos = -1;
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(txtURL.getText())){
            JOptionPane.showMessageDialog(null, "URL no válida",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        takeURL(txtURL.getText());
    }

    public void takeURL(String txtUrl){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar en ...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
            return;
        path = (fileChooser.getSelectedFile().getAbsolutePath());
        url = txtUrl;

        txtURL.setText("http://www.dominiopublico.es/libros/T/Sun_Tzu/Sun%20Tzu%20-%20El%20Arte%20de%20la%20Guerra.pdf");

        startDownload();
    }

    public void startDownload(){
        final Rows rows = new Rows(this);

        rows.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                if (propertyChangeEvent.getPropertyName().equals("progress"))
                    rows.getPbDownload().setValue((Integer) propertyChangeEvent.getNewValue());
            }
        });
        if (pos == -1) {
            rowPanel.add(rows.getPanel1());
        }else{
            rowPanel.add(rows.getPanel1(),pos);
        }
        rowPanel.updateUI();
        if (programmDownload){
            String txtdate = JOptionPane.showInputDialog(null,"Programa la descarga hh:mm");
            if (txtdate == null || txtdate.equals(""))
                return;
            final String[] date = txtdate.split(":");
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Calendar calendar = Calendar.getInstance();
                    if (Integer.parseInt(date[0]) == calendar.get(Calendar.HOUR_OF_DAY) &&
                            Integer.parseInt(date[1]) == calendar.get(Calendar.MINUTE)){
                        rows.execute();
                        timer.stop();
                    }
                }
            });
            timer.start();
        }else {
            rows.execute();
        }
    }

    public void deleteRow(Rows row){
        rowPanel.remove(row.getPanel1());
        rowPanel.updateUI();
    }

    public void reloadRow(Rows row){
        url = row.getUrl().toString();
        path = row.getPrimaryPath();
        pos = rowPanel.getComponentZOrder(row.getPanel1());
        deleteRow(row);
        startDownload();
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
