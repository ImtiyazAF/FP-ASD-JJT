/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231197 - Imtiyaz Shafhal Afif
 * 2 - 5026231172 - Mochamad Zhulmi Danovanz H
 * 3 - 5026231136 - Maulana Muhammad Ad-Dzikri
 */
package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import javax.sound.sampled.*;

public class GameMainTT extends JPanel {
    public static final int ROWS = 3;
    public static final int COLS = 3;

    private JButton[][] cells = new JButton[ROWS][COLS];
    private SeedTT currentPlayer = SeedTT.CROSSTT;
    private StateTT currentState = StateTT.PLAYINGTT;
    private BoardTT board;
    private BoardTT.AIPlayer aiPlayer;

    public GameMainTT() {
        setLayout(new GridLayout(ROWS, COLS));
        initBoard();
    }

    private void initBoard() {
        board = new BoardTT();
        aiPlayer = board.new AIPlayerMinimax(board); // Change to Minimax AI for better logic

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new JButton(" ");
                cells[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
                final int r = row, c = col;
                cells[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cells[r][c].getText().equals(" ") && currentState == StateTT.PLAYINGTT) {
                            cells[r][c].setText(currentPlayer.getIcon());
                            playSound("eat.wav");
                            board.stepGame(currentPlayer, r, c);
                            updateGameState(r, c);
                            switchPlayer();
                            if (currentState == StateTT.PLAYINGTT) {
                                aiMove(); // Let AI play after player
                            }
                        }
                    }
                });
                add(cells[row][col]);
            }
        }
    }

    private void setPlayerIcon(JButton button) {
        if (currentPlayer == SeedTT.CROSSTT) {
            button.setIcon(new ImageIcon(getClass().getResource("/image/cross.gif")));
        } else {
            button.setIcon(new ImageIcon(getClass().getResource("/image/not.gif")));
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == SeedTT.CROSSTT) ? SeedTT.NOUGHTTT : SeedTT.CROSSTT;
    }

    private void updateGameState(int row, int col) {
        if (board.checkWin(row, col, currentPlayer)) {
            currentState = (currentPlayer == SeedTT.CROSSTT) ? StateTT.CROSS_WONTT : StateTT.NOUGHT_WONTT;
            playSound("explode.wav");
            JOptionPane.showMessageDialog(this, currentPlayer.getIcon() + " won!");
            resetGame();
        } else if (board.isDraw()) {
            currentState = StateTT.DRAWTT;
            playSound("die.wav");
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        }
    }

    private void resetGame() {
        board.newGame();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].setText(" ");
            }
        }
        currentPlayer = SeedTT.CROSSTT;
        currentState = StateTT.PLAYINGTT;
    }

    private void aiMove() {
        if (currentState == StateTT.PLAYINGTT) {
            int[] aiMove = aiPlayer.move();
            int aiRow = aiMove[0];
            int aiCol = aiMove[1];
            cells[aiRow][aiCol].setText(SeedTT.NOUGHTTT.getIcon());
            board.stepGame(SeedTT.NOUGHTTT, aiRow, aiCol);
            updateGameState(aiRow, aiCol);
            switchPlayer();
        }
    }

    private void playSound(String soundFile) {
        try {
            File sound = new File(getClass().getResource("/sound/" + soundFile).toURI());
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
