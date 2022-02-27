import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class PingFrame extends JFrame {

    PingPanel panel;

    PingFrame() {
        panel = new PingPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}
