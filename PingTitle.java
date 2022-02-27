import java.awt.*;

class PingTitle {
    static int NUMBER_OF_TITLE;
    static int time = 0;
    static int index = 0;
    
    String text;
    int duration;
    int R;
    int G;
    int B;
    
    PingTitle (String text, int duration) {
        this.text = text;
        this.duration = duration;
    }
    
    static void show(Graphics2D g2D, Font f, PingTitle title) {
        time++;
        if (time > title.duration){
            index++;
            time = 0;
        }
        if (index == NUMBER_OF_TITLE) index = 0;
        FontMetrics metrics = g2D.getFontMetrics(f);
        g2D.drawString(title.text, ((480 - metrics.stringWidth(title.text)) / 2), 250);
    }
    
    static void setNumberOfTitle(int n){
        NUMBER_OF_TITLE = n;
        return;
    }
}
