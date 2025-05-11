package tiket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame {

    public login() {
        setTitle("TIKET.IN");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 32, 96));
        add(topPanel, BorderLayout.NORTH);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(33, 32, 76));
        JLabel headerLabel = new JLabel("Pesan Mudah, Tonton Bahagia - TIKET.IN");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 35));
        headerPanel.add(headerLabel);
        topPanel.add(headerPanel, BorderLayout.CENTER);

        // Poster panel
        JPanel posterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        posterPanel.setBackground(new Color(33, 32, 96));
        topPanel.add(posterPanel, BorderLayout.SOUTH);

        String[] posterPaths = {
            "C:\\Users\\EVAN\\Desktop\\Station\\Login Page\\logo revisi.jpg",
        };

        for (String posterPath : posterPaths) {
            ImageIcon originalIcon = new ImageIcon(posterPath);
            Image image = originalIcon.getImage();
            int maxWidth = 250;
            int maxHeight = 400;
            double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
            int newWidth = Math.min(maxWidth, (int) (maxHeight * aspectRatio));
            int newHeight = Math.min(maxHeight, (int) (maxWidth / aspectRatio));
            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JButton posterButton = new JButton(scaledIcon);
            posterButton.setPreferredSize(new Dimension(newWidth, newHeight));
            posterButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Poster button action
                }
            });
            posterPanel.add(posterButton);
        }

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 204, 102));
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;

        // Welcome label
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        JLabel welcomeLabel = new JLabel("Selamat datang di aplikasi kami! Temukan kemudahan dalam menonton film.");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(welcomeLabel, c);

        // Subtitle label
        c.gridx = 0;
        c.gridy = 1;
        JLabel subtitleLabel = new JLabel("Klik Lanjutkan untuk mulai memesan sekarang!");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(subtitleLabel, c);

        // Next button
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        JButton nextButton = new JButton("Lanjutkan");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setBackground(new Color(33, 32, 96));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Membuka frame booking dan menutup frame login
                new BioskopBookingApp().setVisible(true);
                dispose();
            }
        });
        formPanel.add(nextButton, c);

        // Adding a decorative image at the bottom
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        
        ImageIcon decorationIcon = new ImageIcon("");
        Image decorationImage = decorationIcon.getImage();
        Image scaledDecorationImage = decorationImage.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledDecorationIcon = new ImageIcon(scaledDecorationImage);
        JLabel decorationLabel = new JLabel(scaledDecorationIcon);
        decorationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(decorationLabel, c);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }
}
