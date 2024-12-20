/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231197 - Imtiyaz Shafhal Afif
 * 2 - 5026231172 - Mochamad Zhulmi Danovanz H
 * 3 - 5026231136 - Maulana Muhammad Ad-Dzikri
 */
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedButton extends JButton {
    private float opacity = 0f; // Initial opacity

    public AnimatedButton(String text, int x, int y, int width, int height, Font font) {
        super(text);
        setFont(font);
        setBounds(x, y, width, height);
        setBackground(new Color(0, 102, 204, 0)); // Fully transparent initially
        setForeground(Color.WHITE);
        setFocusPainted(false);

        startFadeInAnimation();
    }

    /**
     * Start the fade-in animation for the button
     */
    private void startFadeInAnimation() {
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                setOpaque(true);
                setBackground(new Color(0, 102, 204, (int) (opacity * 255))); // Gradually increase opacity
                if (opacity >= 1f) {
                    ((Timer) e.getSource()).stop(); // Stop animation when fully visible
                }
                repaint();
            }
        });
        timer.start();
    }
}
