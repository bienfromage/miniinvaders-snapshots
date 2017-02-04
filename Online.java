import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Online extends JPanel{
    int width = width();
    int height = height();
    static JFrame frame;
    static String player1,player2,score1,score2;
    
    public Online(){}
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,width,height);
    }
    
    public static void main(String[] args){
        player1 = args[0];
        player2 = args[1];
        score1 = args[2];
        score2 = args[3];
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Online background = new Online();
        frame = new JFrame("MiniInvaders 0.0");
        frame.setSize(400,400);
        frame.setLocation(200,200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
        frame.add(background);
        frame.setVisible(true);
    }
    
    public void calcScore(){
        String code = "";
        boolean[] letters = new boolean[10];
        Random fromage = new Random();
        for(int i = 0;i<10;i++){
            int chosen;
            do{
                chosen = fromage.nextInt(10);
            }while(!letters[chosen]);
            code += chosen;
            letters[chosen] = true;
        }
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