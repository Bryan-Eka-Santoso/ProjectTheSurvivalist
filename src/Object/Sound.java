package Object;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public Clip clip;
    public URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/coin.wav");
        soundURL[1] = getClass().getResource("/sound/eating_sound_effect.wav");
        soundURL[2] = getClass().getResource("/sound/inventory_move_effect.wav");
        soundURL[3] = getClass().getResource("/sound/sand_step-87182.wav");
        soundURL[4] = getClass().getResource("/sound/sword_slash_SE.wav");
        soundURL[5] = getClass().getResource("/sound/walk_grass_SE.wav");
        soundURL[6] = getClass().getResource("/sound/chop _sound_effect.wav");
        soundURL[7] = getClass().getResource("/sound/happy-relaxing-loop-275487.wav");

    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) {
                System.out.println("Sound URL untuk index " + i + " adalah null!");
                return;
            }
            System.out.println("Mencoba load: " + soundURL[i]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            System.out.println("Sukses load clip!");
        } catch (Exception e) {
            System.out.println("Gagal load clip:");
            e.printStackTrace(); // TAMPILKAN error-nya!
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

