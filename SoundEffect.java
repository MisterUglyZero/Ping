import java.io.File;
import javax.sound.sampled.*;
public class SoundEffect {
    File soundFile;
    Clip clip;
    SourceDataLine sourceLine;
    SoundEffect(String soundFileName) {
        soundFile = new File(soundFileName);
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            AudioFormat audioFormat = sound.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Couldn't load: " + soundFileName + "\n");
        }
    }
    public void play() {
//        clip.setFramePosition(0);
//        clip.start();
    }
}
