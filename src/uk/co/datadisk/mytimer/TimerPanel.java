package uk.co.datadisk.mytimer;

import javax.swing.*;
import java.awt.*;

public class TimerPanel extends JPanel {

    private static final long serialVersionUID = 8838006751106674425L;

    private int width = 150;
    private int height = 25;
    private String timeString = "00:00:00";
    private long time = 10;

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

        long h = time / 60;
        long m = (time / 60) % 60;
        long s = time % 60;

        timeString = String.format("%02d:%02d:%02d", h, m, s);
        repaint();
    }
}
