import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Scoreboard extends JPanel{
    int width = width();
    int height = height();
    
    public Scoreboard(){}
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,width,height);
        g.setColor(Color.WHITE);
        Random fromage = new Random();
        for(int i = 0; i <= (width/3); i++){//randomly generate stars
            g.fillOval(fromage.nextInt(width), fromage.nextInt(height), 5, 5);
        }
        
        File file = new File("score.txt");
        try{
            Scanner doc = new Scanner(file);
            int start = 10;
            while(doc.hasNextLine()){
                g.drawString(doc.nextLine(),10,start);
                start+= 20;
            }
            doc.close();
        }catch(FileNotFoundException e){
            System.out.println("File \"score.txt\" improperly installed");
        }
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame("Local Scores");
        Scoreboard score = new Scoreboard();
        frame.setSize(600,600);
        frame.add(score);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
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