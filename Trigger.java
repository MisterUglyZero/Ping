public class Trigger extends Cord2D {
    static int NUMBER_OF_BALLS; //to be assigned by PingPanel
    Boolean[] ballCollisionIndex;
    Boolean collision;
    int points;
    int hitPoints;
    int agro;
    String type;
//    String amount;
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ""};
    int ones;
    int tens;
    int hund;
    int thou;
    int ttho;
    SoundEffect churp;
    
    Trigger (double xPos, double yPos, int points, String type) {
        agro = 0;
        ballCollisionIndex = new Boolean[NUMBER_OF_BALLS];
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            ballCollisionIndex[i] = false;
        }
        this.xPos = xPos;
        this.yPos = yPos;
        this.points = points;
        this.hitPoints = this.points;
        this.type = type;
        churp = new SoundEffect("future_01-441-192-16.wav");
    }
    void checkCollision(Ball ball, Score score) {
        double xBallCenter = ball.xPos + ball.radius;
        double yBallCenter = ball.yPos + ball.radius;
        if (distanceBetween(this.xPos, this.yPos, xBallCenter, yBallCenter) < ball.radius && ballCollisionIndex[ball.index] == false) {
            if (agro != 0) this.hitPoints = this.points;
            this.ballCollisionIndex[ball.index] = true;
            score.increase(this.points);
            agro = 200;
            churp.play();
            if (this.type == "pot") this.points = this.points + 1000;
//            if (this.type == "suck") this.points = this.points - 2;
            if (this.type == "okay") this.points = this.points + 50;
        }
        if (distanceBetween(this.xPos, this.yPos, xBallCenter, yBallCenter) > ball.radius) {
            this.ballCollisionIndex[ball.index] = false;
        }
    }
    Boolean state() {
        this.collision = false;
        for (int i = 0 ; i < NUMBER_OF_BALLS ; i++) {
            if (this.ballCollisionIndex[i] == true) this.collision = true;
        }
        return this.collision;
    }
    public static void assignNumberOfBalls(int n) {
        NUMBER_OF_BALLS = n;
    }
    private double distanceBetween(double x1, double y1, double x2, double y2) {
        return java.lang.Math.sqrt(square(x1 - x2) + square(y1 - y2));
    }
    private double square(double value) {
        return java.lang.Math.pow(value, 2);
    }
    public String renderAmount() {
        int p = this.hitPoints;
        int ttho = (int) (p / 10000);
        p = p - (ttho * 10000);
        int thou = (int) (p / 1000);
        p = p - (thou * 1000);
        int hund = (int) (p / 100);
        p = p - (hund * 100);
        int tens = (int) (p / 10);
        p = p - (tens * 10);
        int ones = (int) (p / 1);
        if (ttho == 0) ttho = 10;
        if (ttho == 10 && thou == 0) thou = 10;
        if (thou == 10 && hund == 0) hund = 10;
        if (hund == 10 && tens == 0) tens = 10;
        if (tens == 10 && ones == 0) return "-0-";
        return numbers[ttho] + numbers[thou] + numbers[hund] + numbers[tens] + numbers[ones];
    }
}
