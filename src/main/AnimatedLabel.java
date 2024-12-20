package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedLabel extends JLabel {
    private float opacity = 0f; // Initial opacity

    public AnimatedLabel(String text, int x, int y, int width, int height, Font font) {
        super(text);
        setFont(font);
        setBounds(x, y, width, height);
        setForeground(new Color(255, 255, 255, 0)); // Fully transparent
        setHorizontalAlignment(SwingConstants.CENTER);

        startFadeInAnimation();
    }

    /**
     * Starts the fade-in animation for the label
     */
    private void startFadeInAnimation() {
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                setForeground(new Color(255, 255, 255, (int) (opacity * 255))); // Gradually increase opacity
                if (opacity >= 1f) {
                    ((Timer) e.getSource()).stop(); // Stop animation when fully visible
                }
            }
        });
        timer.start();
    }
}
