import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Online{
    int width = width();
    int height = height();
    static JFrame frame;
    static String player1,player2,score1,score2;
    static int[] chain = {10,10,10,10,10,10,10,10,10,10};

    public static void main(String[] args){
        player1 = args[0];
        player2 = args[1];
        score1 = args[2];
        score2 = args[3];

        String numberString = "";
        String letterString = "";
        String score,player;
        if(Integer.parseInt(score1)>Integer.parseInt(score2)){
            score = score1;
            player = player1;
        }else if(Integer.parseInt(score2)>Integer.parseInt(score1)){
            score = score2;
            player = player2;
        }else{
            score = score1;
            player = "tie round";
        }

        Random fromage = new Random();

        for(int i = 0; i<10;i++){
            int c;
            boolean done;
            do{
                c = fromage.nextInt(10);
                done = makeChain(c);
            }while(done == false);
            chain[i] = c;
            String cs = c + "";
            numberString += cs;
        }

        for(int i = 0; i<10;i++){
            switch(numberString.charAt(i)){
                case '0':
                letterString += "A";
                break;
                case '1':
                letterString += "B";
                break;
                case '2':
                letterString += "C";
                break;
                case '3':
                letterString += "D";
                break;
                case '4':
                letterString += "E";
                break;
                case '5':
                letterString += "F";
                break;
                case '6':
                letterString += "G";
                break;
                case '7':
                letterString += "H";
                break;
                case '8':
                letterString += "I";
                break;
                case '9':
                letterString += "J";
                break;
                default:
                System.out.println("uhh");
                break;
            }
        }

        for(int i = 0; i<score.length();i++){
            letterString += letterString.charAt(Integer.parseInt(score.charAt(i) + ""));
        }
        String code = letterString +"à"+player;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("MiniInvaders");
        frame.setSize(450,150);
        frame.setLocation(200,200);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField textField = new JTextField(code,20);
        textField.setEditable(false);
        textField.selectAll();
        frame.add(textField);

        JButton copy = new JButton("Copy Code");
        copy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent le) {
                    textField.copy();
                    frame.add(new JLabel("Code copied to clipboard"));
                    frame.revalidate();
                }
            });
        frame.add(copy);
        frame.add(new JLabel("Go to bienfromage.github.io/MiniInvaders to post your score online"));
        
        frame.setVisible(true);
    }

    public static boolean makeChain(int c){
        for(int j = 0; j <chain.length;j++){
            if(chain[j] == c)
                return false;
        }
        return true;
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
