import javax.swing.*;
import GameMain.GameMainTT;

public class Main {
    public static void main(String[] args) {
//        // Run GUI construction codes in Event-D
//        // ispatching thread for thread safety
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JFrame frame = new JFrame("TTT");
//                // Set the content-pane of the JFrame to an instance of main JPanel
//                frame.setContentPane(new GameMain());
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.pack();
//                frame.setLocationRelativeTo(null); // center the application window
//                frame.setVisible(true);            // show it
//            }
//        });

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Tic-Tac-Toe");
                // Set content-pane JFrame ke instance dari GameMainTT atau panel lain yang diimplementasikan
                frame.setContentPane(new GameMainTT()); // Pastikan GameMainTT adalah JPanel
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // Pusatkan jendela aplikasi
                frame.setVisible(true); // Tampilkan frame
            }
        });

    }


}