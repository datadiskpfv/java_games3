package uk.co.datadisk.mycommonmethods;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class FileIO {

    public static Clip playClip(Object requestor, String fileName) {
        Clip clip = null;

        try {
            URL url = requestor.getClass().getResource(fileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException e) {
            String message = fileName + " cannot be opened";
            JOptionPane.showMessageDialog(null, message);
        } catch (UnsupportedAudioFileException e) {
            String message = fileName + " is not a valid audio file";
            JOptionPane.showMessageDialog(null, message);
        } catch (LineUnavailableException e) {
            String message = "No audio resources available to open or play " + fileName ;
            JOptionPane.showMessageDialog(null, message);
        }

        return clip;
    }

    public static BufferedImage readImageFile(Object requestor, String fileName) {
        BufferedImage image = null;
        try {
            InputStream input = requestor.getClass().getResourceAsStream(fileName);
            image = ImageIO.read(input);
        } catch (IOException e) {
            String message = fileName + " cannot be opened";
            JOptionPane.showMessageDialog(null, message);
        }
        return image;
    }

    public static ArrayList<String> readTextFile(Object requestor, String fileName) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            InputStream input = requestor.getClass().getResourceAsStream(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String line  = in.readLine();
            while (line != null) {
                lines.add(line);
                line = in.readLine();
            }
        }  catch (NullPointerException e) {
            String message = fileName + " cannot be found";
            JOptionPane.showMessageDialog(null, message);
        } catch (IOException e) {
            String message = fileName + " cannot be opened";
            JOptionPane.showMessageDialog(null, message);
        }
        return lines;
    }
}
