import java.awt.*;

class PressStart {
    final int GAME_WIDTH = 480;
    final int DURATION = 40;
    String[] text = {"---Click to Start---", ""};
    int time;
    PressStart() {
        time = 0;
    }
    void show(Graphics2D g2D, Font f) {
        time++;
        if (time == (DURATION * 2)) time = 0;
        FontMetrics metrics = g2D.getFontMetrics(f);
        int index = (int) (time/DURATION);
        g2D.drawString(text[index], (GAME_WIDTH - metrics.stringWidth(text[index])) / 2, 320);
    }

}
