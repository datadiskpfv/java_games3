package uk.co.datadisk.speedwords;

import uk.co.datadisk.mytimer.TimerPanel;

import java.awt.*;

public class SpeedWordsTimerPanel extends TimerPanel {

    private static final long serialVersionUID = 5272043737499954666L;

    private static final Font FONT = new Font(Font.DIALOG, Font.BOLD, 24);

    private SpeedWords speedWords;

    public SpeedWordsTimerPanel(SpeedWords speedWords, long time ) {
        super(time, FONT);
        this.speedWords = speedWords;
    }
}