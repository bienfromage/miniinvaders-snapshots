import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;

public class TestAsteroid extends JPanel{
    int x = 60, y = 160;
    ArrayList<Integer> rockX = new ArrayList<>();
    public TestAsteroid(){

    }

    public void paintComponent(Graphics g){
        Random fromage = new Random();
        super.paintComponent(g);
        Polygon spaceRock = new Polygon();
        spaceRock.addPoint(x, y);//1
        spaceRock.addPoint(x - fromage.nextInt(41)+20, y + fromage.nextInt(31)+40);//2
        spaceRock.addPoint(x - fromage.nextInt(31)+40, y + fromage.nextInt(31)+80);//3
        spaceRock.addPoint(x + fromage.nextInt(4)-2, y + fromage.nextInt(31)+150);//4
        spaceRock.addPoint(x + fromage.nextInt(31)+40, y + fromage.nextInt(31)+150);//5
        spaceRock.addPoint(x + fromage.nextInt(31)+150, y + fromage.nextInt(31)+80);//6
        spaceRock.addPoint(x + fromage.nextInt(31)+130, y + fromage.nextInt(31)+60);//7
        spaceRock.addPoint(x + fromage.nextInt(31)+80, y + fromage.nextInt(31)+60);//8
        spaceRock.addPoint(x + fromage.nextInt(31)+40, y + fromage.nextInt(31)+40);//9
        spaceRock.addPoint(x, y);
        g.setColor(Color.GRAY);
        g.fillPolygon(spaceRock);

        //for(int i = 0; i < 2; i++){
            int a = fromage.nextInt(1000);
            int b = fromage.nextInt(1000);
            Polygon inverseRock = new Polygon();
            rockX.add(a);
            inverseRock.addPoint(a, b);//1
            inverseRock.addPoint(a - (fromage.nextInt(21) + 20), b + fromage.nextInt(31) + 40);//2
            inverseRock.addPoint(a - (fromage.nextInt(31) + 20), b + fromage.nextInt(31) + 80);//3
            inverseRock.addPoint(a - fromage.nextInt(4) - 2, b + fromage.nextInt(31) + 150);//4
            inverseRock.addPoint(a + fromage.nextInt(31) + 30, b + fromage.nextInt(31) + 150);//5
            inverseRock.addPoint(a + fromage.nextInt(31) + 150, b + fromage.nextInt(31) + 80);//6
            inverseRock.addPoint(a + fromage.nextInt(31) + 130, b + fromage.nextInt(31) + 20);//7
            inverseRock.addPoint(a + fromage.nextInt(31) + 80, b + fromage.nextInt(31) + 5);//8
            inverseRock.addPoint(a + fromage.nextInt(31) + 40, b + fromage.nextInt(31));//9
            inverseRock.addPoint(a, b);
            g.fillPolygon(inverseRock);
        //}
    }

    public static void main(String[] args){
        TestAsteroid asteroid = new TestAsteroid();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.add(asteroid);
    }
}
