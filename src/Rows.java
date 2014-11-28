import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by jorge on 27/11/14.
 */
public class Rows extends SwingWorker<Void, Integer> implements ActionListener{
    private JPanel panel1;
    private JProgressBar pbDownload;
    private JButton btStop;
    private JButton btReload;
    private JLabel txtName;
    private JLabel txtDownload;
    private JButton btDelete;
    private boolean stopped;
    private Window window;
    private String path;

    public Rows(Window window){
        this.window = window;
        init();
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void init(){
        btStop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //action listener
        btReload.addActionListener(this);
        btStop.addActionListener(this);
        btDelete.addActionListener(this);

        path = "";
    }

    public void activateDeactivate(boolean activate){
        stopped = activate;
        btReload.setEnabled(activate);
        btStop.setEnabled(!activate);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btStop){
            stop();
            return;
        }
        if (actionEvent.getSource() == btReload){
            reload();
            return;
        }
        if (actionEvent.getSource() == btDelete){
            delete();
            return;
        }
    }

    public void stop(){
        if(JOptionPane.showConfirmDialog(null,"¿Quieres cancelar la descarga?",
                "¿Estas seguro?",JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
            return;
        }
        activateDeactivate(true);
    }

    @Override
    protected Void doInBackground() throws Exception {
        URL url = new URL(window.getUrl().toString());
        URLConnection urlConnection = url.openConnection();

        int fileWeight = urlConnection.getContentLength();
        String fileName = url.getFile().replace("/", "");
        fileName = fileName.replace("%20"," ");
        txtName.setText(fileName);
        txtDownload.setText(fileWeight + " bytes");

        path = window.getPath() + File.separator + fileName;

        InputStream inputStream = url.openStream();
        FileOutputStream fileOutputStream = new FileOutputStream(path);

        byte [] bytes = new byte[2048];
        int weight = 0, progress = 0;
        while ((weight = inputStream.read(bytes)) != -1 && !stopped){
            //Thread.sleep(500);
            fileOutputStream.write(bytes,0,weight);

            progress += weight;
            setProgress((int)(progress*100/fileWeight));

            txtDownload.setText(FileUtils.byteCountToDisplaySize(progress) + " de " + FileUtils.byteCountToDisplaySize(fileWeight));
        }

        inputStream.close();
        fileOutputStream.close();

        if (stopped){
            setProgress(0);

            txtName.setForeground(Color.decode("#F44336"));
            txtDownload.setForeground(Color.decode("#F44336"));
        }else {
            setProgress(100);

            txtName.setForeground(Color.decode("#1565C0"));
            txtDownload.setForeground(Color.decode("#1565C0"));
            activateDeactivate(true);
        }
        return null;
    }

    public void delete(){
        activateDeactivate(true);
        int option = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el archivo origen y borrarlo de la lista?",
                "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);

        if (option == JOptionPane.CANCEL_OPTION){
            reload();
            return;
        }

        if (option == JOptionPane.YES_OPTION){
            new File(path).delete();
        }
        //todo eliminar de jlist
    }

    //todo reload
    public void reload (){
        activateDeactivate(false);

        txtName.setForeground(Color.decode("#000"));
        txtDownload.setForeground(Color.decode("#000"));


    }

    public JProgressBar getPbDownload() {
        return pbDownload;
    }
}
