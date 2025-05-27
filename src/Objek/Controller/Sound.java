package Objek.Controller;

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
            soundURL[8] = new File("ProjectTheSurvivalist/res/sound/armor-equip.mp3").toURI().toURL();
            soundURL[9] = new File("ProjectTheSurvivalist/res/sound/attacked.mp3").toURI().toURL();
            soundURL[10] = new File("ProjectTheSurvivalist/res/sound/breath-out.mp3").toURI().toURL();
            soundURL[11] = new File("ProjectTheSurvivalist/res/sound/build.place.mp3").toURI().toURL();
            soundURL[12] = new File("ProjectTheSurvivalist/res/sound/caged.mp3").toURI().toURL();
            soundURL[13] = new File("ProjectTheSurvivalist/res/sound/chased.mp3").toURI().toURL();
            soundURL[14] = new File("ProjectTheSurvivalist/res/sound/chest-close.mp3").toURI().toURL();
            soundURL[15] = new File("ProjectTheSurvivalist/res/sound/chest-open.mp3").toURI().toURL();
            soundURL[16] = new File("ProjectTheSurvivalist/res/sound/craft.mp3").toURI().toURL();
            soundURL[17] = new File("ProjectTheSurvivalist/res/sound/drink.mp3").toURI().toURL();
            soundURL[18] = new File("ProjectTheSurvivalist/res/sound/fishing-theme.mp3").toURI().toURL();
            soundURL[19] = new File("ProjectTheSurvivalist/res/sound/grab.mp3").toURI().toURL();
            soundURL[20] = new File("ProjectTheSurvivalist/res/sound/grass-step.mp3").toURI().toURL();
            soundURL[21] = new File("ProjectTheSurvivalist/res/sound/levelup.mp3").toURI().toURL();
            soundURL[22] = new File("ProjectTheSurvivalist/res/sound/pickup.mp3").toURI().toURL();
            soundURL[23] = new File("ProjectTheSurvivalist/res/sound/plant.mp3").toURI().toURL();
            soundURL[24] = new File("ProjectTheSurvivalist/res/sound/poisoned.mp3").toURI().toURL();
            soundURL[25] = new File("ProjectTheSurvivalist/res/sound/rowing.mp3").toURI().toURL();
            soundURL[26] = new File("ProjectTheSurvivalist/res/sound/sand-step.mp3").toURI().toURL();

           

           
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
            // System.out.println("Sukses load clip!");
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

