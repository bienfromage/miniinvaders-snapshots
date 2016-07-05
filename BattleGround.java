import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.Random;
import java.util.ArrayList;

public class BattleGround extends JPanel implements ActionListener, KeyListener{
    Timer tm = new Timer(5, this);
    int humanX = 0, velHumanX = 0, humanY = 0, velHumanY = 0;
    BufferedImage image;
    int width = width();
    int height = height();
    boolean starsReady = false;
    ArrayList<Integer> starX = new ArrayList<Integer>();
    ArrayList<Integer> starY = new ArrayList<Integer>();
    
    public BattleGround(){
        tm.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        URL resource = getClass().getResource("HumanShip.png");//get human fighter
        try{
            image = ImageIO.read(resource);
        }catch(IOException e){
            System.out.println("This file was improperly installed.\n Resource \'" + resource + "\' not found.");
        }
    }
    
    public void actionPerformed(ActionEvent e){
        humanX += velHumanX;
        humanY += velHumanY;
        repaint();
    }
    
    public void keyPressed(KeyEvent e){
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){//if left arrow key pressed
            velHumanX = -2;
            velHumanY = 0;
        }
        if(c == KeyEvent.VK_UP){
            velHumanX = 0;
            velHumanY = -2;
        }
        if(c == KeyEvent.VK_RIGHT){
            velHumanX = 2;
            velHumanY = 0;
        }
        if(c == KeyEvent.VK_DOWN){
            velHumanX = 0;
            velHumanY = 2;
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(0, 0, width, height);        
        if(!starsReady){//generate stars
            g.setColor(Color.WHITE);
            for(int i = 0; i <= 29; i++){//randomly generate stars
                Random fromage = new Random();
                starX.add(fromage.nextInt(width + 1));
                starY.add(fromage.nextInt(height + 1));
                g.fillOval(starX.get(i), starY.get(i), 5, 5);
            }
            starsReady = true;
        }else{
            g.setColor(Color.WHITE);
            for(int i = 0; i <= 29; i++){
                g.fillOval(starX.get(i), starY.get(i), 5, 5);                
            }
        }
        g.drawImage(image, humanX, humanY, null);//draw human ship
    }
    
    public static void main(String[] args){
        BattleGround battlescreen = new BattleGround();
        JFrame frame = new JFrame("Mini Invaders 1.0");
        frame.setSize(1, 1);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(battlescreen);
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
