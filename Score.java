public class Score extends Cord2D {

    int points;
    int state;
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    int ones;
    int tens;
    int hund;
    int thou;
    int ttho;
    
    Score(double xPos, double yPos) {
        state = 0; //free, 
        this.xPos = xPos;
        this.yPos = yPos;
        points = 0;
    }
    void increase(int value) {
        if (this.state == 2) {
            this.points += value;
        }
    }
    void setState(int value) {
        this.state = value;
    }
    void reset() {
        this.points = 0;
    }
    String render() {
        int p = points;
        ttho = (int) (p / 10000);
        p = p - (ttho * 10000);
        thou = (int) (p / 1000);
        p = p - (thou * 1000);
        hund = (int) (p / 100);
        p = p - (hund * 100);
        tens = (int) (p / 10);
        p = p - (tens * 10);
        ones = (int) (p / 1);
        return numbers[ttho] + numbers[thou] + numbers[hund] + numbers[tens] + numbers[ones];
    }
    void increaseHighScore(Score currentScore) {
        if (currentScore.points > this.points) this.points = currentScore.points;
    }
}
