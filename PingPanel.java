import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PingPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {


////DECLARATIONS

    final int BOUNCE_WIDTH = 480;
    final int BOUNCE_HEIGHT = 600;
    final int NUMBER_OF_BALLS = 5;
    final int NUMBER_OF_PEGS = 18;
    final int NUMBER_OF_PEG_VARIETY = 4;
    final int NUMBER_OF_TRIGGERS = 10;
    final int NUMBER_OF_ARTIFACTS = 5;
    int gameState;
    double mouseXpos;
    double mouseYpos;
    String[] message1Text = {
        "P  I  N  G  !", "Programming - George B!", "Graphics - Basia C!", "P  I  N  G  !", "Programmed in Java", "2021", "P  I  N  G  !", "School is Cool", "Drinking Coffee is Okay"
    };
    String[] message2Text = {
        "--Click to Start--", ""
    };
    Message message1;
    Message message2;
    
    Ball[] ball;
    Peg[] peg;
    int[] pegData = {
        88, 500, 0, 184, 460, 0, 280, 460, 0, 376, 500, 0,
        40, 360, 1, 136, 370, 1, 232, 380, 1, 328, 370, 1, 420, 360, 1,
        88, 260, 2, 184, 265, 2, 280, 265, 2, 376, 260, 2,
        40, 160, 3, 136, 160, 3, 232, 160, 3, 328, 160, 3, 420, 160, 3
    };
    Trigger[] trigger;
    double[] starX = { -0.951, -0.182,  0,  0.182, 0.951, 0.341, 0.588,     0,-0.588,-0.341,-0.951};
    double[] starY = { -0.309, -0.309, -1, -0.309,-0.309, 0.111, 0.809, 0.359, 0.809, 0.111,-0.309};
    
    Score score;
    Score highScore;
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    Image ballImage;
    Image[][] pegImage;
    Font f;
    FontMetrics metrics;
    Timer timer;

////INITIALIZATION    

    PingPanel() {
        gameState = 0; // demoScreen
        addMouseListener(this);
        addMouseMotionListener(this);
        
        message1 = new Message(message1Text, 3000, 240);
        message2 = new Message(message2Text, 300, 300);
        f = new Font("Arial", Font.BOLD, 32);
        ballImage = new ImageIcon("images/ball.png").getImage();
        pegImage = new Image[4][4];
        for (int i = 0 ; i < 4 ; i++) {
            for (int j = 0 ; j < 4 ; j++) {
                int a = i + 48; //'0' is ASCII48
                int b = j + 48;
                pegImage[i][j] = new ImageIcon("images/peg" + (char) a + (char) b + ".png").getImage();
            }
        }
        ball = new Ball[NUMBER_OF_BALLS];
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            ball[i] = new Ball(BOUNCE_WIDTH, BOUNCE_HEIGHT, i);
        }
        peg = new Peg[NUMBER_OF_PEGS];
        for (int i = 0 ; i < NUMBER_OF_PEGS ; i++) {
            peg[i] = new Peg(pegData[(3 * i) + 0], pegData[(3 * i) + 1], pegData[(3 * i) + 2]);
        }
        Trigger.assignNumberOfBalls(NUMBER_OF_BALLS);
        trigger = new Trigger[NUMBER_OF_TRIGGERS];
        trigger[0] = new Trigger(240, 260, 100, "ping");
        trigger[1] = new Trigger(332, 260, 100, "ping");
        trigger[2] = new Trigger(148, 260, 100, "ping");
        trigger[3] = new Trigger(184, 375, 200, "ping");
        trigger[4] = new Trigger(280, 375, 200, "ping");
        trigger[5] = new Trigger(240, 540,1000, "pot");
        trigger[6] = new Trigger(332, 540,   0, "suck");
        trigger[7] = new Trigger(148, 540,   0, "suck");
        trigger[8] = new Trigger( 44, 540, 200, "okay");
        trigger[9] = new Trigger(440, 540, 200, "okay");
        score = new Score(50, 30);
//        }
        highScore = new Score(BOUNCE_WIDTH - 150, 30);
        this.setPreferredSize(new Dimension(BOUNCE_WIDTH, BOUNCE_HEIGHT));
        this.setBackground(Color.BLACK);
        timer = new Timer(16, this);
        timer.start();
    }

////DRAW THE SCREEN
            
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(f);
        metrics = g2D.getFontMetrics(f);
        g2D.setColor(Color.PINK);
        g2D.fillRect(0,0,8,600);
        g2D.fillRect(472,0,8,600);
        g2D.fillRect(88,508,16,132);
        g2D.fillRect(184,468,16,132);
        g2D.fillRect(280,468,16,132);
        g2D.fillRect(376,508,16,132);
        g2D.setColor(Color.BLUE);
        for (int i = 0 ; i < NUMBER_OF_TRIGGERS ; i++) {
            if (trigger[i].agro > 0) trigger[i].agro--;
            if (trigger[i].agro == 0) {
                trigger[i].hitPoints = trigger[i].points;
                g2D.drawRect((int) trigger[i].xPos - 1, (int) trigger[i].yPos - 1, 3, 3);
                g2D.setColor(Color.GRAY);
                if (trigger[i].type != "ping") g2D.drawString(trigger[i].renderAmount(), (int) trigger[i].xPos - (metrics.stringWidth(trigger[i].renderAmount())) / 2, (int) trigger[i].yPos);
            }
            else {
                if (trigger[i].type == "ping") {
                    for (int j = 0 ; j < NUMBER_OF_ARTIFACTS ; j++) {
                        generateStar(g2D, trigger[i], j, 0.7, 0);
                    }
                }
                if (trigger[i].type == "pot") {
                    g2D.setColor(Color.YELLOW);
                    for (int j = 0 ; j < NUMBER_OF_ARTIFACTS ; j++) {
                        //generateStar(Graphics2D g2D, Trigger trigger, double scale, double yMod) {
                        generateStar(g2D, trigger[i], j, 0.4, 5);
                        generateStar(g2D, trigger[i], j, 0.2, 3);
                        generateStar(g2D, trigger[i], j, 0.1, 1);
                    }
                }
                g2D.setColor(Color.WHITE);
                g2D.drawString(trigger[i].renderAmount(), (int) trigger[i].xPos - (metrics.stringWidth(trigger[i].renderAmount())) / 2, (int) trigger[i].yPos - 200 + trigger[i].agro);
            }
        }
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            g2D.drawImage(ballImage, (int) ball[i].xPos, (int) ball[i].yPos, null);
        }
        for (int i = 0 ; i < NUMBER_OF_PEGS ; i++) {
            g2D.drawImage(pegImage[peg[i].variety][peg[i].agroIndex], (int) peg[i].xPos, (int) peg[i].yPos, null);
        }
//show score
        g2D.setColor(Color.WHITE);
        g2D.drawString(score.render(), (int) score.xPos, (int) score.yPos);
        g2D.drawString(highScore.render(), (int) highScore.xPos, (int) highScore.yPos);
        if (gameState == 0) {
            message1.show(g2D, f);
            message2.show(g2D, f);
        }
        checkBalls(); // ARE ALL BALLS OUT OF PLAY?
    }
    
////UPDATE THE GAME

    public void actionPerformed(ActionEvent e) {
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            for (int j = 0 ; j < NUMBER_OF_PEGS ; j++) {
                peg[j].checkCollision(ball[i], score);
            }
            if (gameState == 2) { // playGame
                for (int j = 0 ; j < NUMBER_OF_TRIGGERS ; j++ ) {
                    trigger[j].checkCollision(ball[i], score);
                }
            }
        }
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            ball[i].Move();
        }
        highScore.increaseHighScore(score);
        repaint();
    }
    
////MOUSE ACTIONS
    @Override
    public void mouseClicked(MouseEvent me) {
        if (gameState == 0) {
            gameState = 1; // setGame
            for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
                ball[i].xPos = (((BOUNCE_WIDTH - 100) / NUMBER_OF_BALLS) * i) + 50;
                ball[i].yPos = 10;
                ball[i].status.set(1);
                ball[i].xVel = 0;
                ball[i].yVel = 0;
            }
            gameState = 2; // playGame
            score.reset();
            score.setState(2); //playGame`
        }
    }
    @Override
    public void mousePressed(MouseEvent me) {
        mouseXpos = (double) me.getX();
        mouseYpos = (double) me.getY();
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            double d = distanceBetween(mouseXpos, mouseYpos, ball[i].xPos + ball[i].radius, ball[i].yPos + ball[i].radius);
            if (d < ball[i].radius) {
                ball[i].status.set(2);
                ball[i].xPos = mouseXpos - ball[i].radius;
                ball[i].yPos = mouseYpos - ball[i].radius;
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            if (ball[i].status.get() == 2) ball[i].status.set(3);
        }
    }
    @Override
    public void mouseEntered(MouseEvent me) {
    }
    @Override
    public void mouseExited(MouseEvent me) {
    }
    @Override
    public void mouseDragged(MouseEvent me) {
        mouseXpos = (double) me.getX();
        mouseYpos = (double) me.getY();
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            if (ball[i].status.get() == 2) {
                ball[i].xPos = mouseXpos - ball[i].radius;
                ball[i].yPos = mouseYpos - ball[i].radius;
            }
        }

    }
    @Override
    public void mouseMoved(MouseEvent me) {
    }
    public void checkBalls() {
        int count = 0;
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            if (ball[i].status.get() == 4) count++;
        }
        if (count == NUMBER_OF_BALLS) {
            gameState = 0;
            score.setState(0);
            resetSlots();
            for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
                ball[i].status.set(0);
            }
        }
    }
////METHOD TO GENERATE STARS
    public void generateStar(Graphics2D g2D, Trigger trigger, int j, double scale, double yMod) {
        double bojaz = scale*(200-trigger.agro)*(j+1);
        for (int k = 0 ; k < 10 ; k++) {
            g2D.drawLine(
                (int) ((trigger.xPos) + starX[k]*bojaz),
                (int) ((trigger.yPos - (yMod*(200 - trigger.agro))) + starY[k]*bojaz),
                (int) ((trigger.xPos) + starX[k+1]*bojaz),
                (int) ((trigger.yPos - (yMod*(200 - trigger.agro))) + starY[k+1]*bojaz)
            );
        }
    }
    public void resetSlots(){
        trigger[5].points = 1000;
        trigger[6].points = 0;
        trigger[7].points = 0;
        trigger[8].points = 200;
        trigger[9].points = 200;
    }
////METHODS FOR CALCULATIONS
    private double squareRoot (double value){
        return java.lang.Math.sqrt(value);
    }
    private double distanceBetween(double x1, double y1, double x2, double y2) {
        return java.lang.Math.sqrt(square(x1 - x2) + square(y1 - y2));
    }
    private double square(double value) {
        return java.lang.Math.pow(value, 2);
    }

}
