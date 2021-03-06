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
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.sound.sampled.*;

public class BattleGround extends JPanel implements ActionListener{
    static JFrame frame;
    static String player1,player2,score1,score2;
    int timepassed = 0;
    Timer tm = new Timer(5, this);
    static String startTime;
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

        Random cow = new Random();
        humanX = cow.nextInt(width-humanWidth);
        humanY = cow.nextInt(height-humanHeight);
        do{
            alienX = cow.nextInt(width-alienWidth);
            alienY = cow.nextInt(height-alienHeight);
        }while((alienX+alienWidth >= humanX && alienX <= humanX+humanWidth && alienY+alienHeight >= humanY && alienY <= humanY+humanHeight));

        //Human Input
        getInputMap().put(KeyStroke.getKeyStroke("released ENTER"), "enter without modifiers");
        getActionMap().put("enter without modifiers", new AbstractAction(){public void actionPerformed(ActionEvent a){fire(1);}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed UP"), "up arrow");
        getActionMap().put("up arrow", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanY = -4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released UP"), "up arrow released");
        getActionMap().put("up arrow released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanY = 0;}}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT"), "left arrow");
        getActionMap().put("left arrow", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanX = -4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "left arrow released");
        getActionMap().put("left arrow released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanX = 0;}}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed DOWN"), "down arrow");
        getActionMap().put("down arrow", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanY = 4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "down arrow released");
        getActionMap().put("down arrow released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanY = 0;}}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT"), "right arrow");
        getActionMap().put("right arrow", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanX = 4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "right arrow released");
        getActionMap().put("right arrow released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!humanDied){velHumanX = 0;}}});

        //Alien Input
        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "space without modifiers");
        getActionMap().put("space without modifiers", new AbstractAction(){public void actionPerformed(ActionEvent a){fire(2);}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed W"), "up");
        getActionMap().put("up", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienY = -4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released W"), "up released");
        getActionMap().put("up released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienY = 0;}}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed A"), "left");
        getActionMap().put("left", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienX = -4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released A"), "left released");
        getActionMap().put("left released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienX = 0;}}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed S"), "down");
        getActionMap().put("down", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienY = 4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released S"), "down released");
        getActionMap().put("down released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienY = 0;}}});

        getInputMap().put(KeyStroke.getKeyStroke("pressed D"), "right");
        getActionMap().put("right", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienX = 4;}}});

        getInputMap().put(KeyStroke.getKeyStroke("released D"), "right released");
        getActionMap().put("right released", new AbstractAction(){public void actionPerformed(ActionEvent a){if(!alienDied){velAlienX = 0;}}});

        tm.start();
    }

    public void actionPerformed(ActionEvent e){
        humanX += velHumanX;
        humanY += velHumanY;
        alienX += velAlienX;
        alienY += velAlienY;
        if(humanX < -humanWidth){//wrap screen for human player
            humanX = width - humanWidth;
        }else if(humanX > width){
            humanX = -humanWidth;
        }else if(humanY < -humanHeight){
            humanY = height - humanHeight;
        }else if(humanY > height - humanHeight){
            humanY = -humanHeight;
        }
        if(alienX < -alienWidth){//wrap screen for human player
            alienX = width - alienWidth;
        }else if(alienX > width){
            alienX = -alienWidth;
        }else if(alienY < -alienHeight){
            alienY = height - alienHeight;
        }else if(alienY > height - alienHeight){
            alienY = -alienHeight;
        }
        timepassed++;
        theEnd();
        repaint();
    }

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

            int rockX, rockY, rockA, rockB;

            do{
                rockX = fromage.nextInt(width)- 180;
                rockY = fromage.nextInt(height) - 180;
            } while((rockX > humanX - 181 && rockX < humanX + humanWidth && rockY < humanY + humanHeight && rockY > humanY - 181) || (rockX > alienX - 181 && rockX < alienX + alienWidth && rockY < alienY + alienHeight && rockY > alienY - 181) || (rockX < 0 || rockY <0));

            asteroidX.add(rockX);//pt 0
            asteroidX.add(rockX - fromage.nextInt(41)+20);//1
            asteroidX.add(rockX - fromage.nextInt(31)+40);//2
            asteroidX.add(rockX + fromage.nextInt(4)-2);//3
            asteroidX.add(rockX + fromage.nextInt(31)+40);//4
            asteroidX.add(rockX + fromage.nextInt(31)+150);//5
            asteroidX.add(rockX + fromage.nextInt(31)+130);//6
            asteroidX.add(rockX + fromage.nextInt(31)+80);//7
            asteroidX.add(rockX + fromage.nextInt(31)+40);//8
            asteroidX.add(rockX);//9
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

            do{
                rockA = fromage.nextInt(width) - 180;
                rockB = fromage.nextInt(height) - 180;
            }while((rockA > humanX - 181 && rockA < humanX + humanWidth && rockB < humanY + humanHeight && rockB > humanY - 181) || (rockA > alienX - 181 && rockA < alienX + alienWidth && rockB < alienY + alienHeight && rockB > alienY - 181) || (rockA < 0 || rockB <0));

            asteroidX.add(rockA);//10
            asteroidX.add(rockA-(fromage.nextInt(21) + 20));//11
            asteroidX.add(rockA-(fromage.nextInt(31) + 20));//12
            asteroidX.add(rockA-fromage.nextInt(4) - 2);//13
            asteroidX.add(rockA+fromage.nextInt(31) + 30);//14
            asteroidX.add(rockA+fromage.nextInt(31) + 150);//15
            asteroidX.add(rockA+fromage.nextInt(31) + 130);//16
            asteroidX.add(rockA+fromage.nextInt(31) + 80);//17
            asteroidX.add(rockA+fromage.nextInt(31) + 40);//18
            asteroidX.add(rockA);//19
            asteroidY.add(rockB);
            asteroidY.add(rockB+fromage.nextInt(31) + 40);
            asteroidY.add(rockB+fromage.nextInt(31) + 80);
            asteroidY.add(rockB+ fromage.nextInt(31) + 150);
            asteroidY.add(rockB+ fromage.nextInt(31) + 150);
            asteroidY.add(rockB+ fromage.nextInt(31) + 80);
            asteroidY.add(rockB+ fromage.nextInt(31) + 20);
            asteroidY.add(rockB+ fromage.nextInt(31) + 5);
            asteroidY.add(rockB+ fromage.nextInt(31));
            asteroidY.add(rockB);

            Polygon asteroid = new Polygon();
            for(int i = 0; i < 10; i++) {
                asteroid.addPoint(asteroidX.get(i), asteroidY.get(i));
            }
            g.setColor(Color.GRAY);
            g.fillPolygon(asteroid);

            Polygon asteroid1 = new Polygon();
            for(int i=10;i<20;i++)
                asteroid1.addPoint(asteroidX.get(i), asteroidY.get(i));
            g.fillPolygon(asteroid1);

            fieldReady = true;
        }else{
            g.setColor(Color.WHITE);
            for(int i = 0; i < starX.size(); i++){
                g.fillOval(starX.get(i), starY.get(i), 5, 5);
            }

            Polygon asteroid = new Polygon();
            for(int i = 0; i < 10; i++) {
                asteroid.addPoint(asteroidX.get(i), asteroidY.get(i));
            }
            g.setColor(Color.GRAY);
            g.fillPolygon(asteroid);

            Polygon asteroid1 = new Polygon();
            for(int i=10;i<20;i++)
                asteroid1.addPoint(asteroidX.get(i), asteroidY.get(i));
            g.fillPolygon(asteroid1);
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
                    bulletDuration.set(i, 100);//erase bullet
                }
                if((bulletX.get(i) > alienX + 15 && bulletX.get(i) < (alienX + alienWidth - 15)) && (bulletY.get(i) > alienY + 8 && bulletY.get(i) < (alienY + alienHeight - 15)) && !alienDied){
                    alienDied = true;
                    bulletDuration.set(i, 100);//erase bullet
                }
                if(bulletX.get(i) < -15)    //wrap bullets around screen
                    bulletX.set(i,width-15);
                if(bulletX.get(i) > width)
                    bulletX.set(i, -15);
                if(bulletY.get(i) < -15)
                    bulletY.set(i,height-15);
                if(bulletY.get(i) > height)
                    bulletY.set(i, -15);
                if((bulletX.get(i) > asteroidX.get(0) && bulletX.get(i) < asteroidX.get(0) + 181 && bulletY.get(i) > asteroidY.get(0) && bulletY.get(i) < asteroidY.get(0) + 181)||(bulletX.get(i) > asteroidX.get(10) && bulletX.get(i) < asteroidX.get(10) + 150 && bulletY.get(i) > asteroidY.get(10)+20 && bulletY.get(i) < asteroidY.get(10) + 150)) {//bullet explodes on asteroid
                    bulletDuration.set(i, 500);
                }
                if(bulletDuration.get(i) >= 100){
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

        if(((humanX+humanWidth) > asteroidX.get(0) && humanX < (asteroidX.get(0)+120) && (humanY+humanHeight) > asteroidY.get(0)+50 && humanY<(asteroidY.get(0)+130))||((humanX+humanWidth) > asteroidX.get(10) && humanX < (asteroidX.get(10)+160) && (humanY+humanHeight) > asteroidY.get(10)-20 && humanY<(asteroidY.get(10)+120))){//Did player1 hit asteroid?
            humanDied = true;
        }
        if(((alienX+alienWidth) > asteroidX.get(0) && alienX < (asteroidX.get(0)+120) && (alienY+alienHeight) > asteroidY.get(0)+50 && alienY<(asteroidY.get(0)+130))||((alienX+alienWidth) > asteroidX.get(10) && alienX < (asteroidX.get(10)+160) && (alienY+alienHeight) > asteroidY.get(10)-20 && alienY<(asteroidY.get(10)+120))){//Did player1 hit asteroid?
            alienDied = true;
        }
    }

    public static void main(String[] args){
        player1 = args[0];
        player2 = args[1];
        score1 = args[2];
        score2 = args[3];

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BattleGround battlescreen = new BattleGround();
        frame = new JFrame("MiniInvaders");
        frame.setSize(screenSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(battlescreen);
        frame.setVisible(true);
        frame.setResizable(false);
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
            if(!(velHumanX == 0 && velHumanY == 0)) {
                bulletVelX.add(velHumanX*2);
                bulletVelY.add(velHumanY*2);
                bulletX.add((humanX + humanWidth / 2) + (velHumanX * 40));
                bulletY.add((humanY + humanHeight / 2) + (velHumanY * 40));
            }
            break;
            case 2:
            if(!(velAlienX == 0 && velAlienY == 0)) {
                bulletVelX.add(velAlienX*2);
                bulletVelY.add(velAlienY*2);
                bulletX.add((alienX + alienWidth / 2) + (velAlienX * 40));
                bulletY.add((alienY + alienHeight / 2) + (velAlienY * 40));
            }
            break;
        }
    }

    public void theEnd(){
        if((alienDeathSequence > 400 && !humanDied) || (humanDeathSequence > 400 && !alienDied) || (alienDeathSequence > 400 && humanDeathSequence > 400)){
            tm.stop();
            if(alienDeathSequence > 400 && !humanDied){
                int score = Integer.parseInt(score1);
                score=score+10000-timepassed;
                if(score<0)
                    score = 0;
                score1 = Integer.toString(score);
            }else if(humanDeathSequence > 400 && !alienDied){
                int score = Integer.parseInt(score2);
                score=score+10000-timepassed;
                if(score<0)
                    score = 0;
                score2 = Integer.toString(score);
            }
            frame.dispose();
            String[] arguments = new String[]{player1,player2,score1,score2};
            Last.main(arguments);
        }
    }
}
