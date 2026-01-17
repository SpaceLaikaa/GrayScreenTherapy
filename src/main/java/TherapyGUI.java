import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TherapyGUI {
    private JFrame frame;
    private JLabel timeLabel;
    private JLabel statusLabel;

    public TherapyGUI() {
        frame = new JFrame("Gray Screen Therapy");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);

        try { //Logo
            URL iconURL = getClass().getResource("/Logo.png");
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                frame.setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            System.err.println("No Logo: " + e.getMessage());
            e.printStackTrace();
        }

        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("TOTAL WASTED TIME", SwingConstants.CENTER);
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 14));

        timeLabel = new JLabel("00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 48));
        timeLabel.setForeground(Color.WHITE);

        statusLabel = new JLabel("Searching for the Summoners Rift", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setFont(new Font("Verdana", Font.PLAIN, 16));

        frame.add(titleLabel);
        frame.add(timeLabel);
        frame.add(statusLabel);

        frame.setVisible(true);
    }

    public void update(double totalSeconds, String status) {
        long mins = (long) totalSeconds / 60;
        long secs = (long) totalSeconds % 60;

        timeLabel.setText(String.format("%02d:%02d", mins, secs));
        statusLabel.setText(status);

        if (status.contains("Waiting") || status.contains("not found")) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(Color.GREEN);
        }
    }
}