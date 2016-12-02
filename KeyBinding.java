/**
 * Created by traweeka18 on 10/28/2016.
 */
import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;

public class KeyBinding extends JPanel{
    public KeyBinding() {
        CutAction action = new CutAction();
        String keyStrokeAndKey = "control Z";
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
        getInputMap().put(keyStroke, keyStrokeAndKey);
        getActionMap().put(keyStrokeAndKey, action);
    }

    public class CutAction extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            System.out.println("Cut cut cut");
        }
    }

    public static void main(String[] args){
        KeyBinding bind = new KeyBinding();
        JFrame frame = new JFrame();
        frame.add(bind);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
}
