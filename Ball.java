import java.util.*;

public class Ball extends Cord2D {
    final int VEL_MAX = 30;
    double yAccel;
    double radius;
    int index;
    Random rand = new Random();
    Status status;
        
    Ball(int xSize, int ySize, int index) {
        xMin = 8;
        xMax = xSize - 64 - 8;
        yMax = ySize - 64 - 8;
        xPos = (rand.nextInt(xMax));
        yPos = (rand.nextInt(100));
        xVel = (rand.nextInt(VEL_MAX) - (VEL_MAX / 2)) / 2;
        yVel = (rand.nextInt(VEL_MAX) - (VEL_MAX / 2)) / 2;
        yAccel = .3;
        radius = 32;
        this.index = index;
        status = new Status();
        status.set(0);
    }
    void Set(int xPos, int yPos) {
        status.set(1);
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    
    void Move() {
        if (status.get() == 0 || status.get() == 3) { 
            //bounce off the side walls
            if (xPos < xMin) xVel = java.lang.Math.abs(xVel);
            if (xPos > xMax) xVel = -java.lang.Math.abs(xVel);
            //if it falls off the bottom
            if (yPos > 600) {
                if (status.get() == 0) {
                    xPos = (rand.nextInt(xMax));
                    xVel = (rand.nextInt(VEL_MAX) - (VEL_MAX / 2)) / 5;
                    yPos = -64;
                    yVel = 0;
                }
                if (status.get() == 3) status.set(4);
            }
            //if it is in the bottom slots ..this could probably be made more OOP next time
            if (yPos > 468) {
                if (xPos > 120 && xPos < 150) xVel = -xVel;
                if (xPos > 170 && xPos < 200) xVel = -xVel;//center
                if (xPos > 216 && xPos < 246) xVel = -xVel;
                if (xPos > 266 && xPos < 296) xVel = -xVel;//2nd from right
                if (yPos > 500) {
                    if (xPos > 24 && xPos < 54) xVel = -xVel; //leftmost slot
                    if (xPos > 59 && xPos < 104) xVel = -xVel; //2nd from left
                    if (xPos > 312 && xPos < 342) xVel = -xVel;
                    if (xPos > 362 && xPos < 392) xVel = -xVel;//rightmost slot
                }
            }
            xPos = xPos + xVel;
            yPos = yPos + yVel;
            yVel = yVel + yAccel;
        }
    }
    public class Status {
        String[] conditionOptions = {"free", "set", "grab", "inPlay", "outPlay"};
        int condition;
        Status () {
            condition = 0;
        }
        public void set (int condition) {
            this.condition = condition;
        }
        public int get() {
            return this.condition;
        }
    }
}
