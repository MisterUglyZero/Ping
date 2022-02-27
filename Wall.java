import java.util.*;

public class Wall {
    double xPos1, yPos1;
    double xPos2, yPos2;
    double xNorm, yNorm;

    Wall(double xPos1, double yPos1, double xPos2, double yPos2) {
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
    }
    
    public void checkCollision() {
    }
}
