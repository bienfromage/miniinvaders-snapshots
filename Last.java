import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class Last extends JPanel implements MouseListener{
    int width = width();
    int height = height();
    static JFrame frame;
    static String player1,player2,score1,score2;

    public Last(){
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(x > width/2-100 && x<width/2+100&&y>height/2+60&&y<height/2+90){
            try{
                File file = new File("score.txt");
                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(player1 +" vs "+player2);
                bw.newLine();
                bw.write(player1 +": "+score1);
                bw.newLine();
                bw.write(player2+": "+score2);
                bw.newLine();
                bw.newLine();

                bw.close();
            }catch(IOException i){
                System.out.println(e);
            }
            score1 = "0";
            score2 = "0";
            repaint();
        }else if(x > width/2-100 && x<width/2+100&&y>height/2+110&&y<height/2+140){
            String[] arguments = new String[]{player1,player2,score1,score2};
            Online.main(arguments);
        }else if(x > width/2-100 && x<width/2+100 && y > height/2-40 && y < height/2+40){
            frame.dispose();
            String[] arguments = new String[]{player1,player2,score1,score2};
            BattleGround.main(arguments);
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args){
        player1 = args[0];
        player2 = args[1];
        score1 = args[2];
        score2 = args[3];

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Last background = new Last();
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
        g.fillRect(width/2-100,height/2+60,200,30);//save locally
        g.fillRect(width/2-100,height/2+110,200,30);//save online
        g.fillRect(width/2-100,height/2-40,200,80);//replay button

        g.setColor(Color.BLACK);
        g.drawString("FINISH & SAVE LOCALLY", width/2-65, height/2+80);//save locally
        g.drawString("FINISH & SAVE ONLINE", width/2-60, height/2+130);//save online
        g.setFont(new Font("Arial", Font.PLAIN, 20));//replay button text
        g.drawString("PLAY AGAIN", width/2-55, height/2+10);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString(player1+": "+score1,20,30);
        g.drawString(player2+": "+score2,20,70);
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
