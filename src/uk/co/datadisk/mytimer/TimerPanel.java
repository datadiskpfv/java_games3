package uk.co.datadisk.mytimer;

import uk.co.datadisk.mycommonmethods.FileIO;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;

public class TimerPanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 8838006751106674425L;

    private static final String ALARM_FILE = "/sounds/alarm.wav";

    private int width = 150;
    private int height = 25;
    private String timeString = "00:00:00";
    private long time = 10;
    Thread timerThread;

    public TimerPanel(long time, Font font) {
        setTime(time);
        setFont(font);
        height = font.getSize();
        FontMetrics fm = getFontMetrics(font);
        width = fm.stringWidth(timeString);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString(timeString, 0, height);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = new Dimension(width, height);
        return size;
    }

    public void setTime(long time){
        this.time = time;

        long h = time / 3600;
        long m = (time / 60) % 60;
        long s = time % 60;

        timeString = String.format("%02d:%02d:%02d", h, m, s);
        repaint();
    }

    public void start() {
        stop();
        timerThread = new Thread(this);
        timerThread.start();
    }

    public void stop() {
        if (timerThread != null) {
            timerThread.interrupt();
            timerThread = null;
        }
    }

    public void run() {
        while (time > 0) {
            time--;
            setTime(time);
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                return;
            }
        }
        timesUp();
    }

    protected void timesUp() {
        Clip clip = FileIO.playClip(this, ALARM_FILE);
        String message = "Time is up";
        JOptionPane.showMessageDialog(this, message);
        clip.stop();
    }

    public long getTime() {
        return time;
    }
}
