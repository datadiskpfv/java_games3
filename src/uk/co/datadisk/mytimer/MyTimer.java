package uk.co.datadisk.mytimer;

import uk.co.datadisk.mycomponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTimer extends JFrame {

    private static final long serialVersionUID = 6658663244818335767L;

    Font font = new Font(Font.DIALOG, Font.BOLD, 36);

    private TimerPanel timerPanel = new TimerPanel(0, font);

    public MyTimer() {
        initGUI();

        setTitle("My Timer");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initGUI() {
        TitleLabel titleLabel = new TitleLabel("My Timer");
        add(titleLabel, BorderLayout.PAGE_START);

        // Center panel that will display the time
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.MAGENTA);
        add(centerPanel, BorderLayout.CENTER);

        timerPanel.setBackground(Color.MAGENTA);
        centerPanel.add(timerPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.PAGE_END);
        
        // Hours Button
        JButton hoursButton = new JButton("Hours");
        hoursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnHour();
            }
        });
        buttonPanel.add(hoursButton);

        // Minutes Button
        JButton minutesButton = new JButton("Min");
        minutesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAMinute();
            }
        });
        buttonPanel.add(minutesButton);
        
        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        buttonPanel.add(clearButton);
        
        // Start Button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        buttonPanel.add(startButton);

        // Stop Button
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        buttonPanel.add(stopButton);
    }

    private void clear() {
        timerPanel.stop();
        timerPanel.setTime(0);
    }

    private void addAMinute() {
        long time = timerPanel.getTime();
        time += 60;
        timerPanel.setTime(time);
    }

    private void addAnHour() {
        long time = timerPanel.getTime();
        time += 3600;
        timerPanel.setTime(time);
    }

    private void stop() {
        timerPanel.stop();
    }

    private void start() {
        timerPanel.start();
    }

    public static void main(String[] args) {
        try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            System.out.println("Error with UIManager");
        }

        EventQueue.invokeLater(MyTimer::new);
    }
}
