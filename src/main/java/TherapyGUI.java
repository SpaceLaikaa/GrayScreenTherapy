import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TherapyGUI {
    private final Color HEXTECH_BG = new Color(10, 15, 20);
    private final Color HEXTECH_GOLD = new Color(200, 155, 60);
    private final Color DARK_ACCENT = new Color(25, 30, 35);
    private final Color BORDER_COLOR = new Color(50, 55, 60);

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
        frame.setLocation(0, 0);
    }

    public TherapyGUI() {
        frame = new JFrame("Gray Screen Therapy");
        frame.setSize(380, 420);
        frame.setMinimumSize(new Dimension(380, 530));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        UIManager.put("TabbedPane.selected", HEXTECH_BG);
        UIManager.put("TabbedPane.contentAreaColor", DARK_ACCENT);
        UIManager.put("TabbedPane.focus", HEXTECH_GOLD);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(DARK_ACCENT);
        tabbedPane.setForeground(Color.WHITE);

        try {
            URL iconURL = getClass().getResource("/Logo.png");
            if (iconURL != null) {
                frame.setIconImage(new ImageIcon(iconURL).getImage());
            }
        } catch (Exception e) {}

        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBackground(HEXTECH_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("TOTAL WASTED TIME", SwingConstants.CENTER);
        titleLabel.setForeground(HEXTECH_GOLD);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        timeLabel = new JLabel("00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Consolas", Font.BOLD, 56));
        timeLabel.setForeground(Color.WHITE);

        statusLabel = new JLabel("<html><center>Searching for Summoner's Rift<br><font size='3' color='#C89B3C'>(Borderless Mode Required)</font></center></html>", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));

        toggleButton = new JButton("THERAPY: ACTIVE");
        toggleButton.setFocusPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(true);
        toggleButton.setBackground(DARK_ACCENT);
        toggleButton.setForeground(Color.GREEN);
        toggleButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        toggleButton.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        toggleButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                toggleButton.setBackground(BORDER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                toggleButton.setBackground(DARK_ACCENT);
            }
        });

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

        JPanel platformPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        platformPanel.setBackground(HEXTECH_BG);

        platformPanel.add(createPlatformButton("YouTube", Color.RED, "https://www.youtube.com/shorts"));
        platformPanel.add(createPlatformButton("Instagram", new Color(225, 48, 108), "https://www.instagram.com/reels/"));
        platformPanel.add(createPlatformButton("TikTok", Color.CYAN, "https://www.tiktok.com/"));

        mainPanel.add(titleLabel);
        mainPanel.add(timeLabel);
        mainPanel.add(statusLabel);
        mainPanel.add(toggleButton);
        mainPanel.add(platformPanel);

        tabbedPane.addTab("Therapy", mainPanel);
        tabbedPane.addTab("All-Time Stats", createStatsPanel());

        tabbedPane.setTabComponentAt(0, createTabLabel("Therapy"));
        tabbedPane.setTabComponentAt(1, createTabLabel("All-Time Stats"));

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JLabel createTabLabel(String title) {
        JLabel lbl = new JLabel(title);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return lbl;
    }

    private JButton createPlatformButton(String name, Color color, String url) {
        String displayName = name;
        if(name.equals("YouTube")) displayName = "▶ YouTube";
        else if(name.equals("Instagram")) displayName = "📷 Instagram";
        else if(name.equals("TikTok")) displayName = "🎵 TikTok";

        JButton btn = new JButton(displayName);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(DARK_ACCENT);
        btn.setForeground(Color.LIGHT_GRAY);
        btn.setFont(new Font("Dialog", Font.BOLD, 10));
        btn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(BORDER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(DARK_ACCENT);
            }
        });

        platformButtons.add(btn);
        if (name.equals("YouTube")) btn.setForeground(Color.RED);

        btn.addActionListener(e -> {
            selectedPlatformUrl = url;
            for (JButton b : platformButtons) b.setForeground(Color.LIGHT_GRAY);
            btn.setForeground(color);
        });
        return btn;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(HEXTECH_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel statsTitle = new JLabel("LIFETIME JOURNEY", SwingConstants.CENTER);
        statsTitle.setForeground(HEXTECH_GOLD);
        statsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lifetimeLabel = new JLabel("0h 0m 0s", SwingConstants.CENTER);
        lifetimeLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        lifetimeLabel.setForeground(Color.CYAN);

        JLabel subText = new JLabel("Total therapy history", SwingConstants.CENTER);
        subText.setForeground(Color.DARK_GRAY);
        subText.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        panel.add(statsTitle);
        panel.add(lifetimeLabel);
        panel.add(subText);

        return panel;
    }

    public void update(double totalSeconds, double lifetimeSeconds, String status, boolean isDead) {
        long mins = (long) totalSeconds / 60;
        long secs = (long) totalSeconds % 60;
        timeLabel.setText(String.format("%02d:%02d", mins, secs));

        long hours = (long) lifetimeSeconds / 3600;
        long lMins = ((long) lifetimeSeconds % 3600) / 60;
        long lSecs = (long) lifetimeSeconds % 60;
        lifetimeLabel.setText(String.format("%dh %dm %ds", hours, lMins, lSecs));

        statusLabel.setText(status);

        if (isDead) {
            if (therapyEnabled) {
                frame.toFront();
            }
            statusLabel.setForeground(Color.RED);
        } else {
            if (!status.contains("Waiting") && !status.contains("not found")) {
                statusLabel.setForeground(Color.GREEN);
            }
        }

        if (status.contains("Waiting") || status.contains("not found")) {
            statusLabel.setForeground(Color.RED);
        }
    }

    public String getSelectedPlatformUrl() {
        return selectedPlatformUrl;
    }

    public boolean isTherapyEnabled() {
        return therapyEnabled;
    }
}