import java.awt.*;

class Message implements Runnable{
    final static int GAME_WIDTH = 480;
    Graphics2D g2D;
    Font f;
    String[] text;
    int yPos;
    int textIndex;
    int duration;
    boolean visible;
    Thread t;
    boolean loop;
    
    Message(String[] text, int duration, int yPos) {
        this.g2D = g2D;
        this.f = f;
        this.text = text;
        this.duration = duration;
        this.yPos = yPos;
        this.loop = true;
        t = new Thread(this);
        t.start();
    }
    
    public void run(){
        try {
            while (loop) {
                for (int x = 0 ; x < text.length ; x++) {
                    t.sleep(duration);
                    textIndex = x;
                }
            }
        }
        catch(InterruptedException e) {
            System.out.println("Message thead Interrupted!");
        }
    }
    
    public void show(Graphics2D g2D, Font f) {
        FontMetrics metrics = g2D.getFontMetrics(f);
        g2D.drawString(text[textIndex], (GAME_WIDTH - metrics.stringWidth(text[textIndex])) / 2, yPos);
    }
}
