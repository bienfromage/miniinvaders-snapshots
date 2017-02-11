import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;

public class Tutorial extends JPanel{
    BufferedImage instructions;
    public Tutorial(){
        URL resource = getClass().getResource("Instructions.png");
        try{
            instructions = ImageIO.read(resource);
        }catch(IOException e){
            System.out.println("File \"Instructions.png\" improperly installed");
        }        
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(instructions,0,0,null);
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame("MiniInvaders");
        frame.setSize(1132,768);
        Tutorial tut = new Tutorial();
        frame.add(tut);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}