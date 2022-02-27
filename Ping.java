public class Ping {

    public static void main(String args[]) {
        Thread.currentThread().setPriority(10);
        new PingFrame();
    }

}
