import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jorge on 27/11/14.
 */
public class MenuBar extends JMenuBar implements ActionListener {
    private JMenuBar jmb;
    private JMenuItem log;
    private Window window;

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
        log = new JMenuItem("Historico");
        jmb.add(log);

        log.addActionListener(this);
        log.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return jmb;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
