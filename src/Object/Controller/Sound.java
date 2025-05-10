package Object.Controller;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public Clip clip;
    public URL soundURL[] = new URL[30];

    public Sound() {
        try {
            soundURL[0] = new File("ProjectTheSurvivalist/res/sound/coin.wav").toURI().toURL();
            soundURL[1] = new File("ProjectTheSurvivalist/res/sound/eating_sound_effect.wav").toURI().toURL();
            soundURL[2] = new File("ProjectTheSurvivalist/res/sound/inventory_move_effect.wav").toURI().toURL();
            soundURL[3] = new File("ProjectTheSurvivalist/res/sound/sand_step-87182.wav").toURI().toURL();
            soundURL[4] = new File("ProjectTheSurvivalist/res/sound/sword_slash_SE.wav").toURI().toURL();
            soundURL[5] = new File("ProjectTheSurvivalist/res/sound/walk_grass_SE.wav").toURI().toURL();
            soundURL[6] = new File("ProjectTheSurvivalist/res/sound/chop _sound_effect.wav").toURI().toURL();
            soundURL[7] = new File("ProjectTheSurvivalist/res/sound/happy-relaxing-loop-275487.wav").toURI().toURL();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) {
                System.out.println("Sound URL untuk index " + i + " adalah null!");
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            System.out.println("Sukses load clip!");
        } catch (Exception e) {
            System.out.println("Gagal load clip:");
            e.printStackTrace(); // Menampilkan error
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}

