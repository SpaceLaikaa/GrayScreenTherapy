import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TherapyGUI {
    private JFrame frame;
    private JLabel timeLabel;
    private JLabel statusLabel;
    private JButton toggleButton;
    private boolean therapyEnabled = true;

    public TherapyGUI() {
        frame = new JFrame("Gray Screen Therapy");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);

        try {
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

        frame.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("TOTAL WASTED TIME", SwingConstants.CENTER);
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 14));

        timeLabel = new JLabel("00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 48));
        timeLabel.setForeground(Color.WHITE);

        statusLabel = new JLabel("Searching for the Summoners Rift", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setFont(new Font("Verdana", Font.PLAIN, 16));

        //Pause button
        toggleButton = new JButton("THERAPY: ACTIVE");
        toggleButton.setFocusPainted(false);
        toggleButton.setBackground(new Color(30, 30, 30));
        toggleButton.setForeground(Color.GREEN);
        toggleButton.setFont(new Font("Verdana", Font.BOLD, 12));
        toggleButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        toggleButton.addActionListener(e -> {
            therapyEnabled = !therapyEnabled;
            if (therapyEnabled) {
                toggleButton.setText("THERAPY: ACTIVE");
                toggleButton.setForeground(Color.GREEN);
            } else {
                toggleButton.setText("THERAPY: PAUSED (Timer Only)");
                toggleButton.setForeground(Color.YELLOW);
            }
        });

        frame.add(titleLabel);
        frame.add(timeLabel);
        frame.add(statusLabel);
        frame.add(toggleButton);

        frame.setVisible(true);
    }

    public boolean isTherapyEnabled() {
        return therapyEnabled;
    }

    public void update(double totalSeconds, String status, boolean isDead) {
        long mins = (long) totalSeconds / 60;
        long secs = (long) totalSeconds % 60;

        timeLabel.setText(String.format("%02d:%02d", mins, secs));
        statusLabel.setText(status);

        if (isDead) {
            //Force to pop up when therapy is active, may change it for UX
            if (therapyEnabled) {
                frame.setAlwaysOnTop(true);
                frame.toFront();
            }
            statusLabel.setForeground(Color.RED);
        }
        else {
            frame.setAlwaysOnTop(false);

            if (!status.contains("Waiting") && !status.contains("not found")) {
                statusLabel.setForeground(Color.GREEN);
            }
        }

        if (status.contains("Waiting") || status.contains("not found")) {
            statusLabel.setForeground(Color.RED);
        }
    }
}