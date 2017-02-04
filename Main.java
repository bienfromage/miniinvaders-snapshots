import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;

public class Main extends JPanel implements MouseListener{
    int width = width();
    int height = height();
    static JFrame frame;

    public Main(){
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(x > width/2-100 && x<width/2+100 && y > height/2-40 && y < height/2+40){
            String player1 = JOptionPane.showInputDialog(null,"Player 1 enter username");
            if(player1 != null && !("".equals(player1))){
                String player2 = JOptionPane.showInputDialog(null,"Player 2 enter username");
                if(player2 != null && !("".equals(player2))){
                    frame.dispose();
                    String[] arguments = new String[]{player1,player2,"0","0"};
                    BattleGround.main(arguments);
                }
            }
        }    
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Main background = new Main();
        frame = new JFrame("MiniInvaders 0.0");
        frame.setSize(screenSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
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

        g.setColor(Color.YELLOW);
        g.fillRect(width/2-120,height/2+60,90,30);//tutorial button
        g.fillRect(width/2+24,height/2+60,90,30);//scores button
        g.fillRect(width/2-100,height/2-40,200,80);//play button

        g.setColor(Color.BLACK);
        g.drawString("CONTROLS", width/2-110, height/2+80);//tutorial
        g.drawString("SCORES", width/2+43, height/2+80);//scores
        g.setFont(new Font("Arial", Font.PLAIN, 30));//play button text
        g.drawString("PLAY", width/2-40, height/2+10);
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
