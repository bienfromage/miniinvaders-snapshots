//Human = player 1 Alien = player 2
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
    int humanX = 100, velHumanX = 0, humanY = 100, velHumanY = 0, humanHeight = 0, humanWidth = 0, humanDeathSequence = 0;
    BufferedImage humanImage;
    BufferedImage alienImage;
    int width = width();
    int height = height();
    int alienX = 500, velAlienX = 0, alienY = 500, velAlienY = 0, alienHeight = 0, alienWidth = 0, alienDeathSequence = 0;
    boolean fieldReady = false;
    ArrayList<Integer> starX = new ArrayList<>();
    ArrayList<Integer> starY = new ArrayList<>();
    ArrayList<Integer> bulletX = new ArrayList<>();
    ArrayList<Integer> bulletY = new ArrayList<>();
    ArrayList<Integer> bulletVelX = new ArrayList<>();
    ArrayList<Integer> bulletVelY = new ArrayList<>();
    ArrayList<Integer> bulletDuration = new ArrayList<>();
    ArrayList<Integer> asteroidX = new ArrayList<>();
    ArrayList<Integer> asteroidY = new ArrayList<>();
    boolean bulletsActive = false, humanDied = false, alienDied = false;
    int asteroids = 0;
    public BattleGround(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        URL resource = getClass().getResource("HumanShip.png");//get human fighter
        try{
            humanImage = ImageIO.read(resource);
        }catch(IOException e){
            System.out.println("This file was improperly installed.\nResource \'" + resource + "\' not found.");
        }

        URL resource2 = getClass().getResource("AlienShip.png");//get alien fighter
        try{
            alienImage = ImageIO.read(resource2);
        }catch(IOException e){
            System.out.println("This file was improperly installed.\nResource \'" + resource2 + "\' not found.");
        }

        humanHeight = humanImage.getHeight();
        humanWidth = humanImage.getWidth();
        alienHeight = alienImage.getHeight();
        alienWidth = alienImage.getWidth();
        tm.start();
    }

    public void actionPerformed(ActionEvent e){
        humanX += velHumanX;
        humanY += velHumanY;
        alienX += velAlienX;
        alienY += velAlienY;
        if(humanX < -humanWidth){//wrap screen for human player
            humanX = width - humanWidth;
        }else if(humanX > width - humanWidth){//test
            humanX = -humanWidth;
        }else if(humanY < -humanHeight){
            humanY = height - humanHeight;
        }else if(humanY > height - humanHeight){
            humanY = -humanHeight;
        }
        if(alienX < -alienWidth){//wrap screen for human player
            alienX = width - alienWidth;
        }else if(alienX > width - alienWidth){
            alienX = -alienWidth;
        }else if(alienY < -alienHeight){
            alienY = height - alienHeight;
        }else if(alienY > height - alienHeight){
            alienY = -alienHeight;
        }
        theEnd();
        repaint();
    }

    public void keyPressed(KeyEvent e){
        int c = e.getKeyCode();
        if(!humanDied) {
            if (c == KeyEvent.VK_LEFT) {//if left arrow key pressed
                velHumanX = -4;
                velHumanY = 0;
            }
            if (c == KeyEvent.VK_UP) {
                velHumanX = 0;
                velHumanY = -4;
            }
            if (c == KeyEvent.VK_RIGHT) {
                velHumanX = 4;
                velHumanY = 0;
            }
            if (c == KeyEvent.VK_DOWN) {
                velHumanX = 0;
                velHumanY = 4;
            }
            if (c == 47) {//? to fire
                fire(1);
            }
        }
        //alien controls
        if(!alienDied) {
            if (c == 65) {//a
                velAlienX = -4;
                velAlienY = 0;
            }
            if (c == 87) {//w
                velAlienX = 0;
                velAlienY = -4;
            }
            if (c == 68) {//d
                velAlienX = 4;
                velAlienY = 0;
            }
            if (c == 83) {//s
                velAlienX = 0;
                velAlienY = 4;
            }
            if (c == 81) {//q to fire
                fire(2);
            }
        }
    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        if(!fieldReady){//generate stars and asteroids
            g.setColor(Color.WHITE);
            Random fromage = new Random();
            for(int i = 0; i <= (width/3); i++){//randomly generate stars
                starX.add(fromage.nextInt(width));
                starY.add(fromage.nextInt(height));
                g.fillOval(starX.get(i), starY.get(i), 5, 5);
            }
            asteroids = fromage.nextInt(5) + 2;//randomly generate asteroids

            int rockX, rockY;
            rockX = fromage.nextInt(width) - 180;
            rockY = fromage.nextInt(height) - 180;

            while((rockX > humanX - 181 && rockX < humanX + humanWidth && rockY < humanY + humanHeight && rockY > humanY - 181) || (rockX > alienX - 181 && rockX < alienX + alienWidth && rockY < alienY + alienHeight && rockY > alienY - 181)){
                rockX = fromage.nextInt(width)- 180;
                rockY = fromage.nextInt(height) - 180;
            }

            asteroidX.add(rockX);//pt 1
            asteroidX.add(rockX - fromage.nextInt(41)+20);//2
            asteroidX.add(rockX - fromage.nextInt(31)+40);//3
            asteroidX.add(rockX + fromage.nextInt(4)-2);//4
            asteroidX.add(rockX + fromage.nextInt(31)+40);//5
            asteroidX.add(rockX + fromage.nextInt(31)+150);//6
            asteroidX.add(rockX + fromage.nextInt(31)+130);//7
            asteroidX.add(rockX + fromage.nextInt(31)+80);//8
            asteroidX.add(rockX + fromage.nextInt(31)+40);//9
            asteroidX.add(rockX);//10
            asteroidY.add(rockY);//pt 1
            asteroidY.add(rockY + fromage.nextInt(31)+40);//2
            asteroidY.add(rockY + fromage.nextInt(31)+80);//3
            asteroidY.add(rockY + fromage.nextInt(31)+150);//4
            asteroidY.add(rockY + fromage.nextInt(31)+150);//5
            asteroidY.add(rockY + fromage.nextInt(31)+80);//6
            asteroidY.add(rockY + fromage.nextInt(31)+60);//7
            asteroidY.add(rockY + fromage.nextInt(31)+60);//8
            asteroidY.add(rockY + fromage.nextInt(31)+40);//9
            asteroidY.add(rockY);//10

            Polygon asteroid = new Polygon();
            for(int i = 0; i < asteroidX.size(); i++) {
                asteroid.addPoint(asteroidX.get(i), asteroidY.get(i));
            }
            g.setColor(Color.GRAY);
            g.fillPolygon(asteroid);

            fieldReady = true;
        }else{
            g.setColor(Color.WHITE);
            for(int i = 0; i < starX.size(); i++){
                g.fillOval(starX.get(i), starY.get(i), 5, 5);
            }

            Polygon asteroid = new Polygon();
            for(int i = 0; i < asteroidX.size(); i++) {
                asteroid.addPoint(asteroidX.get(i), asteroidY.get(i));
            }
            g.setColor(Color.GRAY);
            g.fillPolygon(asteroid);

        }
        if(humanDied && humanDeathSequence <= 400) {
            g.setColor(Color.RED);
            g.fillOval((humanX + (humanWidth + humanX)) / 2 - humanDeathSequence, (humanY + (humanHeight + humanY)) / 2 - humanDeathSequence, 10, 10);
            g.fillOval((humanX + (humanWidth + humanX)) / 2, (humanY + (humanHeight + humanY)) / 2 - humanDeathSequence, 10, 10);
            g.fillOval((humanX + (humanWidth + humanX)) / 2 + humanDeathSequence, (humanY + (humanHeight + humanY)) / 2 - humanDeathSequence, 10, 10);
            g.fillOval((humanX + (humanWidth + humanX)) / 2 + humanDeathSequence, (humanY + (humanHeight + humanY)) / 2, 10, 10);
            g.fillOval((humanX + (humanWidth + humanX)) / 2 + humanDeathSequence, (humanY + (humanHeight + humanY)) / 2 + humanDeathSequence, 10, 10);
            g.fillOval((humanX + (humanWidth + humanX)) / 2, (humanY + (humanHeight + humanY)) / 2 + humanDeathSequence, 10, 10);
            g.fillOval((humanX + (humanWidth + humanX)) / 2 - humanDeathSequence, (humanY + (humanHeight + humanY)) / 2 + humanDeathSequence, 10, 10);
            g.fillOval((humanX + (humanWidth + humanX)) / 2 - humanDeathSequence, (humanY + (humanHeight + humanY)) / 2, 10, 10);
            humanDeathSequence+=8;
        }
        if(alienDied && alienDeathSequence <= 400) {
            g.setColor(Color.RED);
            g.fillOval((alienX + (alienWidth + alienX)) / 2 - alienDeathSequence, (alienY + (alienHeight + alienY)) / 2 - alienDeathSequence, 10, 10);
            g.fillOval((alienX + (alienWidth + alienX)) / 2, (alienY + (alienHeight + alienY)) / 2 - alienDeathSequence, 10, 10);
            g.fillOval((alienX + (alienWidth + alienX)) / 2 + alienDeathSequence, (alienY + (alienHeight + alienY)) / 2 - alienDeathSequence, 10, 10);
            g.fillOval((alienX + (alienWidth + alienX)) / 2 + alienDeathSequence, (alienY + (alienHeight + alienY)) / 2, 10, 10);
            g.fillOval((alienX + (alienWidth + alienX)) / 2 + alienDeathSequence, (alienY + (alienHeight + alienY)) / 2 + alienDeathSequence, 10, 10);
            g.fillOval((alienX + (alienWidth + alienX)) / 2, (alienY + (alienHeight + alienY)) / 2 + alienDeathSequence, 10, 10);
            g.fillOval((alienX + (alienWidth + alienX)) / 2 - alienDeathSequence, (alienY + (alienHeight + alienY)) / 2 + alienDeathSequence, 10, 10);
            g.fillOval((alienX + (alienWidth + alienX)) / 2 - alienDeathSequence, (alienY + (alienHeight + alienY)) / 2, 10, 10);
            alienDeathSequence+=8;
        }
        if(!humanDied)
            g.drawImage(humanImage, humanX, humanY, null);//draw human ship
        if(!alienDied)
            g.drawImage(alienImage, alienX, alienY, null);//draw alien ship
        if(bulletsActive){
            for(int i = 0; i < bulletX.size(); i++){
                g.setColor(Color.WHITE);
                g.drawOval(bulletX.get(i), bulletY.get(i), 15, 15);
                g.setColor(Color.WHITE);
                g.fillOval(bulletX.get(i), bulletY.get(i), 15, 15);
                bulletX.set(i, bulletX.get(i) + bulletVelX.get(i));
                bulletY.set(i, bulletY.get(i) + bulletVelY.get(i));

                if((bulletX.get(i) > humanX + 15 && bulletX.get(i) < (humanX + humanWidth - 15)) && (bulletY.get(i) > humanY + 15 && bulletY.get(i) < (humanY + humanHeight - 15)) && !humanDied){
                    humanDied = true;
                    bulletDuration.set(i, 500);
                }
                if((bulletX.get(i) > alienX + 15 && bulletX.get(i) < (alienX + alienWidth - 15)) && (bulletY.get(i) > alienY + 8 && bulletY.get(i) < (alienY + alienHeight - 15)) && !alienDied){
                    alienDied = true;
                    bulletDuration.set(i, 500);
                }
                if(bulletX.get(i) > asteroidX.get(0) && bulletX.get(i) < asteroidX.get(0) + 181 && bulletY.get(i) > asteroidY.get(0) && bulletY.get(i) < asteroidY.get(0) + 181){//bullet explodes on asteroid
                    bulletDuration.set(i, 500);
                }
                if(bulletDuration.get(i) >= 500){
                    bulletX.remove(i);
                    bulletY.remove(i);
                    bulletVelX.remove(i);
                    bulletVelY.remove(i);
                    bulletDuration.remove(i);
                }else{
                    bulletDuration.set(i, bulletDuration.get(i) + 1);
                }
            }
            if(bulletDuration.size() == 0)
                bulletsActive = false;
        }

    }

    public static void main(String[] args){
        BattleGround battlescreen = new BattleGround();
        JFrame frame = new JFrame("Mini Invaders 1.0");
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

    public void fire(int whoShot){
        bulletsActive = true;
        bulletDuration.add(0);
        switch(whoShot){
            case 1:
                if (velHumanX > 0) {
                    bulletX.add(humanX + humanWidth + 6);
                    bulletY.add(humanY + (humanHeight / 2));
                    bulletVelX.add(velHumanX + 1);
                    bulletVelY.add(0);
                } else if (velHumanX < 0) {
                    bulletX.add(humanX - 6);
                    bulletY.add(humanY + (humanHeight / 2));
                    bulletVelX.add(velHumanX - 1);
                    bulletVelY.add(0);
                } else if (velHumanY > 0) {
                    bulletY.add(humanY + humanHeight + 6);
                    bulletX.add(humanX + (humanWidth / 2));
                    bulletVelY.add(velHumanY + 1);
                    bulletVelX.add(0);
                } else if (velHumanY < 0) {
                    bulletY.add(humanY - 6);
                    bulletX.add(humanX + (humanWidth / 2));
                    bulletVelY.add(velHumanY - 1);
                    bulletVelX.add(0);
                }
                break;
            case 2:
                if (velAlienX > 0) {
                    bulletX.add(alienX + alienWidth + 4);
                    bulletY.add(alienY + (alienHeight / 2));
                    bulletVelX.add(velAlienX + 1);
                    bulletVelY.add(0);
                } else if (velAlienX < 0) {
                    bulletX.add(alienX - 4);
                    bulletY.add(alienY + (alienHeight / 2));
                    bulletVelX.add(velAlienX - 1);
                    bulletVelY.add(0);
                } else if (velAlienY > 0) {
                    bulletY.add(alienY + alienHeight + 4);
                    bulletX.add(alienX + (alienWidth / 2));
                    bulletVelY.add(velAlienY + 1);
                    bulletVelX.add(0);
                } else if (velAlienY < 0) {
                    bulletY.add(alienY - 4);
                    bulletX.add(alienX + (alienWidth / 2));
                    bulletVelY.add(velAlienY - 1);
                    bulletVelX.add(0);
                }
                break;
        }
    }
    public void theEnd(){
        if((alienDeathSequence > 400 && !humanDied) || (humanDeathSequence > 400 && !alienDied) || (alienDeathSequence > 400 && humanDeathSequence > 400))
            System.exit(0);
    }
}
