package Objek.Controller;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public Clip clip;
    public URL soundURL[] = new URL[50];

    public Sound() {
        try {
            soundURL[1] = getClass().getResource("/sound/eating_sound_effect.wav").toURI().toURL();
            soundURL[2] = getClass().getResource("/sound/inventory_move_effect.wav").toURI().toURL();
            soundURL[3] = getClass().getResource("/sound/sand-step.wav").toURI().toURL();
            soundURL[4] = getClass().getResource("/sound/sword_slash_SE.wav").toURI().toURL();
            soundURL[5] = getClass().getResource("/sound/walk_grass_SE.wav").toURI().toURL();
            soundURL[6] = getClass().getResource("/sound/chop _sound_effect.wav").toURI().toURL();
            soundURL[7] = getClass().getResource("/sound/backsound.wav").toURI().toURL();
            soundURL[8] = getClass().getResource("/sound/armor-equip.wav").toURI().toURL();
            soundURL[9] = getClass().getResource("/sound/attacked.wav").toURI().toURL();
            soundURL[10] = getClass().getResource("/sound/breath-out.wav").toURI().toURL();
            soundURL[11] = getClass().getResource("/sound/build-place.wav").toURI().toURL();
            soundURL[12] = getClass().getResource("/sound/caged.wav").toURI().toURL();
            soundURL[13] = getClass().getResource("/sound/chased.wav").toURI().toURL();
            soundURL[14] = getClass().getResource("/sound/chest-close.wav").toURI().toURL();
            soundURL[15] = getClass().getResource("/sound/chest-open.wav").toURI().toURL();
            soundURL[16] = getClass().getResource("/sound/craft.wav").toURI().toURL();
            soundURL[17] = getClass().getResource("/sound/drink.wav").toURI().toURL();
            soundURL[18] = getClass().getResource("/sound/fishing-theme.wav").toURI().toURL();
            soundURL[19] = getClass().getResource("/sound/grab.wav").toURI().toURL();
            soundURL[20] = getClass().getResource("/sound/grass-step.wav").toURI().toURL();
            soundURL[21] = getClass().getResource("/sound/levelup.wav").toURI().toURL();
            soundURL[22] = getClass().getResource("/sound/pickup.wav").toURI().toURL();
            soundURL[23] = getClass().getResource("/sound/plant.wav").toURI().toURL();
            soundURL[24] = getClass().getResource("/sound/poisoned.wav").toURI().toURL();
            soundURL[25] = getClass().getResource("/sound/rowing.wav").toURI().toURL();
            soundURL[26] = getClass().getResource("/sound/sand-step.wav").toURI().toURL();
            soundURL[27] = getClass().getResource("/sound/cave-sound.wav").toURI().toURL();
            soundURL[28] = getClass().getResource("/sound/shop-theme.wav").toURI().toURL();
            soundURL[29] = getClass().getResource("/sound/winter-crown.wav").toURI().toURL();
           
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

