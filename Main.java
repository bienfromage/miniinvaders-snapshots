import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JPanel implements MouseListener{
    int width = width();
    int height = height();

    public Main(){
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(x > width/2-50 && x<width/2+50 && y > height/2-10 && y < height/2+50){
            System.out.println("on point");
        }    
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("MiniInvaders 0.0");
        frame.setSize(screenSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        Main background = new Main();
        frame.add(background);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,width,height);
        g.setColor(Color.WHITE);
        Random fromage = new Random();
        for(int i = 0; i <= (width/3); i++){//randomly generate stars
            g.fillOval(fromage.nextInt(width), fromage.nextInt(height), 5, 5);
        }

        g.setColor(Color.RED);
        g.fillRect(width/2-50,height/2-10,100,20);
    }

    public int width(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int)screenSize.getWidth() + 1;
    }

    public int height(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int)screenSize.getHeight() + 1;
    }
}