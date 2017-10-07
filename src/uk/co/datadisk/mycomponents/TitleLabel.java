package uk.co.datadisk.mycomponents;

import javax.swing.*;
import java.awt.*;

public class TitleLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    public TitleLabel(String title) {
        Font font = new Font(Font.SERIF, Font.BOLD, 28);
        setFont(font);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
        setText(title);
    }
}
