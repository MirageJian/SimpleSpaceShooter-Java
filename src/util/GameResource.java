package util;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameResource {
    // When resource didn't load, this will be loaded
    public static GameResource sBlankTexture = new GameResource("res/blankSprite.png");
    // Texture
    public Image imageTexture;
    // Sound
    AudioInputStream ais;
    private Clip sound;
    // Draw size or Texture size
    public int drawWidth;
    public int drawHeight;
    // Different direction of bullets is different. For player
    public int textureStart;

    public GameResource(String texturePath) {
        File TextureToLoad = new File(texturePath);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {
            imageTexture = ImageIO.read(TextureToLoad);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameResource(String texturePath, int drawWidth, int drawHeight) {
        this(texturePath);
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
    }
    public GameResource(String texturePath, int drawWidth, int drawHeight, String soundPath) {
        this(texturePath, drawWidth, drawHeight);
        // Get sound file
        try {
            ais = AudioSystem.getAudioInputStream(new File(soundPath));
            sound = AudioSystem.getClip();
            sound.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public GameResource(String texturePath, int drawWidth, int drawHeight, int textureStart) {
        this(texturePath, drawWidth, drawHeight);
        this.textureStart = textureStart;
    }
    // This is the place for sound playing. It's unstoppable
    public void startSound() {
        if (sound.isRunning()){
            sound.stop();
        }
        sound.setFramePosition(0);
        sound.start();
        try {
            Clip newClip = AudioSystem.getClip();
            newClip.open(ais);
            newClip.setFramePosition(0);
            newClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // If loop sound, it means the sound will play repeatedly until call stopSound()
    public void loopSound() {
        sound.setFramePosition(0);
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stopSound() {
        sound.stop();
    }
}
