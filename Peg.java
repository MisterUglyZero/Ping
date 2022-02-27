import java.util.*;

public class Peg extends Cord2D {

//    int xMax;
//    int yMax;
//    double xPos, yPos;
    double xPegCenter, yPegCenter;
    int radius;
    double dotProduct;
    double agroLevel;
    double agroDecay;
    int agroIndex;
    int variety;
    Random rand = new Random();

    Peg (int xPos, int yPos, int variety) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.variety = variety;
        this.radius = 8;
        xPegCenter = this.xPos + this.radius;
        yPegCenter = this.yPos + this.radius;
        agroLevel = 0;
        agroIndex = 0;
        agroDecay = 1;
    }
    
    public void checkCollision (Ball ball, Score score) {
        double xBallCenter = ball.xPos + ball.radius;
        double yBallCenter = ball.yPos + ball.radius;
        //get distance and see if it is less than the radius added
        if (distanceBetween(xPegCenter, yPegCenter, xBallCenter, yBallCenter) < (this.radius + ball.radius)) {
            //determine normal vector
            double xNorm = xBallCenter - xPegCenter;
            double yNorm = yBallCenter - yPegCenter;
            double lengthNorm = squareRoot(square(xNorm) + square(yNorm));
            double xNormUnit = xNorm/lengthNorm;
            double yNormUnit = yNorm/lengthNorm;
            //determine Vi projection onto normal vector
            double xProj = (((xNormUnit * ball.xVel) + (yNormUnit * ball.yVel)))*xNormUnit;
            double yProj = (((xNormUnit * ball.xVel) + (yNormUnit * ball.yVel)))*yNormUnit;
            //determine Vf = Vi - 2*Vi proj onto normal vector
            ball.xVel = (ball.xVel - (2 * xProj))*.9;
            ball.yVel = (ball.yVel - (2 * yProj))*.9;
            //in rare cases, the vf has zero xVel and the ball bounces indef
            if (java.lang.Math.abs(ball.xVel) < 0.001) ball.xVel = 0.01;
            //the following makes it so the balls don't get stuck on the pegs when vf < vi
            while (distanceBetween(xPegCenter, yPegCenter, xBallCenter, yBallCenter) < (this.radius + ball.radius)) {
                ball.xPos = ball.xPos + ball.xVel;
                ball.yPos = ball.yPos + ball.yVel;
                xBallCenter = ball.xPos + ball.radius;
                yBallCenter = ball.yPos + ball.radius;
            }
            score.increase(1);
            //peg gets visual effect
            this.agroLevel = this.agroLevel + 64;
        }
        if (agroLevel > 200) agroLevel = 200;
        if (agroLevel > 0) agroLevel = agroLevel - agroDecay;
        if (agroLevel < 0) agroLevel = 0;
        agroIndex = (int) (agroLevel / 20);
        if (agroIndex > 3) agroIndex = 3;
    }
    
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

//Vf = Vi - 2(Proj Vi onto N)
