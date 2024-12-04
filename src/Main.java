import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        // Run GUI construction codes in Event-D
        // ispatching thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("TTT");
                // Set the content-pane of the JFrame to an instance of main JPanel
                frame.setContentPane(new GameMain());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true);            // show it
            }
        });
    }
}