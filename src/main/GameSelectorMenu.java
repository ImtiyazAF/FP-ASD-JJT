package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameSelectorMenu extends JFrame {

    CardLayout page;
    JPanel mainmenu;
    Container cp = getContentPane();

    public GameSelectorMenu(){
        page = new CardLayout();
        mainmenu = new JPanel(page);
        JPanel mainMenu = mainMenu();
        JPanel ticGame = ticGame();
        JPanel confGame = confGame();

        mainmenu.add(mainMenu, "mainMenu");
        mainmenu.add(ticGame, "ticGame");
        mainmenu.add(confGame, "confGame");

        cp.setLayout(new BorderLayout());

        cp.add(mainmenu, BorderLayout.CENTER);

        //pack();
        setTitle("Game Selector");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Allow window resizing
        setVisible(true);
    }

    public JPanel mainMenu() {
        JPanel panel = new JPanel(null); // Use null layout for custom positioning
        panel.setBackground(new Color(70, 130, 180)); // Light blue background

        // Load pixel font
        Font pixelFont = loadFont("font/PressStart2P-Regular.ttf", 24);

        // Title Label
        AnimatedLabel titleLabel = new AnimatedLabel("SELECT A GAME", 200, 50, 400, 100, pixelFont);
        titleLabel.setFont(pixelFont.deriveFont(30f));
        panel.add(titleLabel);

        // Tic Tac Toe Button
        AnimatedButton ticTacToeButton = new AnimatedButton("Tic Tac Toe", 300, 200, 200, 50, pixelFont);
        ticTacToeButton.setFont(pixelFont.deriveFont(12f));
        ticTacToeButton.addActionListener(e -> page.show(mainmenu, "ticGame"));
        panel.add(ticTacToeButton);

        // Connect Four Button
        AnimatedButton connectFourButton = new AnimatedButton("Connect Four", 300, 300, 200, 50, pixelFont);
        connectFourButton.setFont(pixelFont.deriveFont(12f));
        connectFourButton.addActionListener(e -> page.show(mainmenu, "confGame"));
        panel.add(connectFourButton);

        return panel;
    }

    JPanel ticGame(){
        JPanel panel = new JPanel(new BorderLayout());
        TicTacToe.GameMainTT tgame = new TicTacToe.GameMainTT();
        panel.add(tgame, BorderLayout.CENTER);

        return panel;
    }

    JPanel confGame(){
        JPanel panel = new JPanel(new BorderLayout());
        ConnectFour.GameMain cgame = new ConnectFour.GameMain();
        panel.add(cgame, BorderLayout.CENTER);

        return panel;
    }

    private Font loadFont(String fontPath, float size) {
        try {
            // Use getResourceAsStream to load the font from the classpath
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(fontPath));
            return font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            System.out.println("Failed to load font: " + e.getMessage());
            return new Font("Arial", Font.BOLD, 24); // Fallback font
        }
    }


    public static void main(String[] args) {

        new GameSelectorMenu();
    }
}
