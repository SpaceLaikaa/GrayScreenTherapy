import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TherapyGUI {
    private JFrame frame;
    private JLabel timeLabel;
    private JLabel statusLabel;
    private JButton toggleButton;
    private JLabel lifetimeLabel;
    private JTabbedPane tabbedPane;

    private boolean therapyEnabled = true;

    private String selectedPlatformUrl = "https://www.youtube.com/shorts";
    private java.util.List<JButton> platformButtons = new java.util.ArrayList<>();

    public void moveToCorner() {
        frame.setLocation(0, 0); // Sol üst köşe
    }

    public TherapyGUI() {
        frame = new JFrame("Gray Screen Therapy");
        frame.setSize(350, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();

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
        JPanel mainPanel = new JPanel(new GridLayout(5, 1));
        mainPanel.setBackground(Color.BLACK);
        frame.getContentPane().setBackground(Color.BLACK);

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

        JPanel platformPanel = new JPanel(new GridLayout(1,3));
        platformPanel.setBackground(Color.BLACK);

        JButton ytBtn = createPlatformButton("YouTube", Color.RED, "https://www.youtube.com/shorts");
        JButton igBtn = createPlatformButton("Instagram", new Color(225, 48, 108), "https://www.instagram.com/reels/");
        JButton ttBtn = createPlatformButton("TikTok", Color.CYAN, "https://www.tiktok.com/");

        ytBtn.setForeground(Color.RED);

        platformPanel.add(ytBtn);
        platformPanel.add(igBtn);
        platformPanel.add(ttBtn);

        mainPanel.add(titleLabel);
        mainPanel.add(timeLabel);
        mainPanel.add(statusLabel);
        mainPanel.add(toggleButton);
        mainPanel.add(platformPanel);

        frame.setVisible(true);
        tabbedPane.addTab("Therapy", mainPanel);
        tabbedPane.addTab("All-Time Stats", createStatsPanel()); // Aşağıda yazacağımız metot

        frame.add(tabbedPane);
    }

    private JButton createPlatformButton(String name, Color color, String url) {

        JButton btn = new JButton(name);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(20, 20, 20));
        btn.setForeground(Color.LIGHT_GRAY);
        btn.setFont(new Font("Verdana", Font.BOLD, 10));
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        platformButtons.add(btn);

        btn.addActionListener(e -> {
            selectedPlatformUrl = url;

            for (JButton b : platformButtons) {
                b.setForeground(Color.LIGHT_GRAY);
            }
            btn.setForeground(color);

            System.out.println("Switched to: " + name);
        });
        return btn;
    }

    public String getSelectedPlatformUrl() {
        return selectedPlatformUrl;
    }

    public boolean isTherapyEnabled() {
        return therapyEnabled;
    }

    public void update(double totalSeconds, double lifetimeSeconds, String status, boolean isDead) {
        long mins = (long) totalSeconds / 60;
        long secs = (long) totalSeconds % 60;

        timeLabel.setText(String.format("%02d:%02d", mins, secs));
        statusLabel.setText(status);
        long hours = (long) lifetimeSeconds / 3600;
        long lMins = ((long) lifetimeSeconds % 3600) / 60;
        long lSecs = (long) lifetimeSeconds % 60;
        lifetimeLabel.setText(String.format("%dh %dm %ds", hours, lMins, lSecs));

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
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(Color.BLACK);

        JLabel statsTitle = new JLabel("LIFETIME WASTED TIME", SwingConstants.CENTER);
        statsTitle.setForeground(Color.GRAY);
        statsTitle.setFont(new Font("Verdana", Font.BOLD, 14));

        lifetimeLabel = new JLabel("0h 0m 0s", SwingConstants.CENTER);
        lifetimeLabel.setFont(new Font("Monospaced", Font.BOLD, 32));
        lifetimeLabel.setForeground(Color.CYAN);

        JLabel subText = new JLabel("Total therapy history", SwingConstants.CENTER);
        subText.setForeground(Color.DARK_GRAY);
        subText.setFont(new Font("Verdana", Font.ITALIC, 12));

        panel.add(statsTitle);
        panel.add(lifetimeLabel);
        panel.add(subText);

        return panel;
    }
}