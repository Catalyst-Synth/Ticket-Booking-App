package tiket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BioskopBookingApp extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> paymentMethodComboBox;
    private JComboBox<String> filmComboBox;
    private JComboBox<String> classComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> rowComboBox;
    private JComboBox<Integer> seatComboBox;
    private JTextField priceField;
    private JTextField ticketCountField;
    private JTextField totalPriceField;
    private DefaultTableModel tableModel;
    private Connection connection;
    private JTable bookingTable;
    private JScrollPane tableScrollPane;
    private boolean isTableVisible = false;

    public BioskopBookingApp() {
        setTitle("TIKET.IN App");
        setSize(1200, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeDatabaseConnection();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 32, 96));
        add(topPanel, BorderLayout.NORTH);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(0, 32, 96));
        JLabel headerLabel = new JLabel("TIKET.IN CINEMA");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerPanel.add(headerLabel);
        topPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel posterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        posterPanel.setBackground(new Color(0, 32, 96));
        topPanel.add(posterPanel, BorderLayout.SOUTH);

        String[] posterPaths = {
            "C:\\Users\\EVAN\\Desktop\\Station\\Poster\\vina.jpg",
            "C:\\Users\\EVAN\\Desktop\\Station\\Poster\\Avengers.jpg",
            "C:\\Users\\EVAN\\Desktop\\Station\\Poster\\Oppenheimer.jpg",
            "C:\\Users\\EVAN\\Desktop\\Station\\Poster\\Nun.jpg",
            "C:\\Users\\EVAN\\Desktop\\Station\\Poster\\Frozen.jpg"
        };

        String[] filmTitles = {
            "Vina: Sebelum 7 Hari",
            "Avengers: Endgame",
            "Oppenheimer",
            "The Nun II",
            "Frozen II"
        };

        for (int i = 0; i < posterPaths.length; i++) {
            JButton posterButton = new JButton(new ImageIcon(posterPaths[i]));
            String filmTitle = filmTitles[i];
            posterButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    filmComboBox.setSelectedItem(filmTitle);
                }
            });
            posterPanel.add(posterButton);
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 204, 102));
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 50, 10, 10);

        // Baris 1
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("Nama:"), c);

        c.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, c);

        // Baris 2
        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(new JLabel("Email:"), c);

        c.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, c);

        // Baris 3
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(new JLabel("Metode Pembayaran:"), c);

        c.gridx = 1;
        paymentMethodComboBox = new JComboBox<>(new String[]{"BCA", "BNI", "BRI", "Dana", "QRIS", "PayLater"});
        formPanel.add(paymentMethodComboBox, c);

        // Baris 4
        c.gridx = 0;
        c.gridy = 3;
        formPanel.add(new JLabel("Film:"), c);

        c.gridx = 1;
        filmComboBox = new JComboBox<>(filmTitles);
        formPanel.add(filmComboBox, c);

        // Baris 5
        c.gridx = 0;
        c.gridy = 4;
        formPanel.add(new JLabel("Kelas:"), c);

        c.gridx = 1;
        classComboBox = new JComboBox<>(new String[]{"Reguler", "VIP"});
        formPanel.add(classComboBox, c);

        // Baris 6 (Tanggal)
        c.gridx = 0;
        c.gridy = 5;
        formPanel.add(new JLabel("Tanggal:"), c);

        c.gridx = 1;
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datePanel.setBackground(new Color(255, 204, 102));
        dayComboBox = new JComboBox<>(generateDays());
        monthComboBox = new JComboBox<>(generateMonths());
        yearComboBox = new JComboBox<>(generateYears());
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);
        formPanel.add(datePanel, c);

        // Baris 7
        c.gridx = 0;
        c.gridy = 6;
        formPanel.add(new JLabel("Jam Tayang:"), c);

        c.gridx = 1;
        timeComboBox = new JComboBox<>(new String[]{"09:30 WIB", "12:45 WIB", "15:00 WIB", "18:30 WIB", "21:00 WIB", "23:30 WIB"});
        formPanel.add(timeComboBox, c);

        // Baris 8
        c.gridx = 0;
        c.gridy = 7;
        formPanel.add(new JLabel("Kursi (Baris):"), c);

        c.gridx = 1;
        rowComboBox = new JComboBox<>(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"});
        formPanel.add(rowComboBox, c);

        // Baris 9
        c.gridx = 0;
        c.gridy = 8;
        formPanel.add(new JLabel("Kursi (Nomor):"), c);

        c.gridx = 1;
        seatComboBox = new JComboBox<>(generateSeatNumbers());
        formPanel.add(seatComboBox, c);

        // Baris 10
        c.gridx = 0;
        c.gridy = 9;
        formPanel.add(new JLabel("Harga (Rp):"), c);

        c.gridx = 1;
        priceField = new JTextField(10);
        priceField.setEditable(false);
        formPanel.add(priceField, c);

        c.gridx = 2;
        JButton checkPriceButton = new JButton("Lihat Harga");
        checkPriceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkPrice();
            }
        });
        formPanel.add(checkPriceButton, c);

        // Baris 11
        c.gridx = 0;
        c.gridy = 10;
        formPanel.add(new JLabel("Jumlah Tiket:"), c);

        c.gridx = 1;
        ticketCountField = new JTextField(10);
        formPanel.add(ticketCountField, c);

        // Baris 12
        c.gridx = 0;
        c.gridy = 11;
        formPanel.add(new JLabel("Total Harga (Rp):"), c);

        c.gridx = 1;
        totalPriceField = new JTextField(10);
        totalPriceField.setEditable(false);
        formPanel.add(totalPriceField, c);

        // Baris 13
        c.gridx = 1;
        c.gridy = 12;
        JButton saveButton = new JButton("Pesan Tiket");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBooking();
            }
        });
        formPanel.add(saveButton, c);

        c.gridx = 2;
        c.gridy = 0;
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        formPanel.add(clearButton, c);

        c.gridx = 20;
        c.gridy = 12;
        JButton toggleTableButton = new JButton("History");
        toggleTableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleTableVisibility();
            }
        });
        formPanel.add(toggleTableButton, c);
        
        c.gridx = 0;
        c.gridy = 12;
        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengembalikan tampilan ke frame 1
                dispose(); // Menutup frame saat ini
                // Misalnya jika frame 1 adalah tampilan awal atau layar utama, Anda bisa membuat instance baru dari frame tersebut
                new login().setVisible(true);
            }
        });
        formPanel.add(backButton, c);
        
     // Tambahkan tombol "Lihat Tiket"
        c.gridx = 2; 
        c.gridy = 12; 
        
        JButton lihatTiketButton = new JButton("Lihat Tiket");
        lihatTiketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<String, String> data = new HashMap<>();
                data.put("filmTitle", (String) filmComboBox.getSelectedItem());
                data.put("date", dayComboBox.getSelectedItem() + "-" + monthComboBox.getSelectedItem() + "-" + yearComboBox.getSelectedItem());
                data.put("time", (String) timeComboBox.getSelectedItem());
                data.put("email", emailField.getText());
                data.put("ticketCount", ticketCountField.getText());
                data.put("price", priceField.getText());
                data.put("totalPrice", totalPriceField.getText());
                SlipLauncher.launchSlip(data);
            }
        });
        formPanel.add(lihatTiketButton, c);

        
        add(formPanel);
        setVisible(true);
        
        // Tabel untuk menampilkan data pemesanan
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nama");
        tableModel.addColumn("Email");
        tableModel.addColumn("Metode Pembayaran");
        tableModel.addColumn("Film");
        tableModel.addColumn("Kelas");
        tableModel.addColumn("Tanggal");
        tableModel.addColumn("Jam Tayang");
        tableModel.addColumn("Kursi (Baris)");
        tableModel.addColumn("Kursi (Nomor)");
        tableModel.addColumn("Harga (Rp)");
        tableModel.addColumn("Jumlah Tiket");
        tableModel.addColumn("Total Harga (Rp)");

        bookingTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(bookingTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 70));
        tableScrollPane.setVisible(isTableVisible);
        add(tableScrollPane, BorderLayout.SOUTH);
    }

    private void toggleTableVisibility() {
        isTableVisible = !isTableVisible;
        tableScrollPane.setVisible(isTableVisible);
        revalidate();
        repaint();
    }

    private void initializeDatabaseConnection() {
        try {
        	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projek", "root", "Dusk0");
            System.out.println("Koneksi database berhasil.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database. Pastikan database sudah berjalan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkPrice() {
        String selectedClass = (String) classComboBox.getSelectedItem();
        String price = "";

        if (selectedClass.equals("Reguler")) {
            price = "40000";
        } else if (selectedClass.equals("VIP")) {
            price = "80000";
        }

        priceField.setText(price);

        // Update total harga berdasarkan harga dan jumlah tiket
        try {
            int ticketCount = Integer.parseInt(ticketCountField.getText());
            int totalPrice = Integer.parseInt(price) * ticketCount;
            totalPriceField.setText(String.valueOf(totalPrice));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah tiket harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveBooking() {
        String name = nameField.getText();
        String email = emailField.getText();
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        String film = (String) filmComboBox.getSelectedItem();
        String filmClass = (String) classComboBox.getSelectedItem();
        String date = yearComboBox.getSelectedItem() + "-" + monthComboBox.getSelectedItem() + "-" + dayComboBox.getSelectedItem();
        String time = ((String) timeComboBox.getSelectedItem()).split(" ")[0]; // Menghapus WIB
        String seat = rowComboBox.getSelectedItem() + String.valueOf(seatComboBox.getSelectedItem());
        int price = Integer.parseInt(priceField.getText());
        int ticketCount = Integer.parseInt(ticketCountField.getText());
        int totalPrice = price * ticketCount;

        totalPriceField.setText(String.valueOf(totalPrice));

        if (paymentMethod == null || paymentMethod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Metode pembayaran harus dipilih.");
            return;
        }

        try {
            connection.setAutoCommit(false);

            String insertTiketQuery = "INSERT INTO tiket (nama, email, metode_pembayaran, film, kelas, tanggal, jam_tayang, kursi, harga, jumlah_tiket, total_harga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement tiketStatement = connection.prepareStatement(insertTiketQuery);
            tiketStatement.setString(1, name);
            tiketStatement.setString(2, email);
            tiketStatement.setString(3, paymentMethod);
            tiketStatement.setString(4, film);
            tiketStatement.setString(5, filmClass);
            tiketStatement.setString(6, date);
            tiketStatement.setString(7, time); // Menghapus WIB
            tiketStatement.setString(8, seat);
            tiketStatement.setInt(9, price);
            tiketStatement.setInt(10, ticketCount);
            tiketStatement.setInt(11, totalPrice);
            tiketStatement.executeUpdate();

            connection.commit();

            tableModel.addRow(new Object[]{name, email, paymentMethod, film, filmClass, date, time, seat, price, ticketCount, totalPrice});
            clearForm();
            JOptionPane.showMessageDialog(this, "Pemesanan tiket berhasil disimpan!");

            Map<String, String> data = new HashMap<>();
            data.put("filmTitle", film);
            data.put("date", date);
            data.put("time", time);
            data.put("email", email);
            data.put("ticketCount", String.valueOf(ticketCount));
            data.put("seat", seat);

            SlipLauncher.launchSlip(data);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan pemesanan tiket.");
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }


    private void updateTable() {
        try {
            String query = "SELECT * FROM tiket";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            tableModel.setRowCount(0);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String paymentMethod = resultSet.getString("paymentMethod");
                String film = resultSet.getString("film");
                String selectedClass = resultSet.getString("class");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String row = resultSet.getString("row");
                int seat = resultSet.getInt("seat");
                String price = resultSet.getString("price");
                int ticketCount = resultSet.getInt("ticketCount");
                String totalPrice = resultSet.getString("totalPrice");

                tableModel.addRow(new Object[]{name, email, paymentMethod, film, selectedClass, date, time, row, seat, price, ticketCount, totalPrice});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Map<String, String> getDataFromDatabase() {
        Map<String, String> data = new HashMap<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Dusk0");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tiket WHERE email = '" + emailField.getText() + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                data.put("filmTitle", rs.getString("filmTitle"));
                data.put("date", rs.getString("date"));
                data.put("time", rs.getString("time"));
                data.put("email", rs.getString("email"));
                data.put("ticketCount", rs.getString("ticketCount"));
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        paymentMethodComboBox.setSelectedIndex(0);
        filmComboBox.setSelectedIndex(0);
        classComboBox.setSelectedIndex(0);
        dayComboBox.setSelectedIndex(0);
        monthComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedIndex(0);
        timeComboBox.setSelectedIndex(0);
        rowComboBox.setSelectedIndex(0);
        seatComboBox.setSelectedIndex(0);
        priceField.setText("");
        ticketCountField.setText("");
        totalPriceField.setText("");
    }

    private String[] generateDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        return days;
    }

    private String[] generateMonths() {
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[i] = String.valueOf(i + 1);
        }
        return months;
    }

    private String[] generateYears() {
        String[] years = new String[10];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear + i);
        }
        return years;
    }

    private Integer[] generateSeatNumbers() {
        Integer[] seats = new Integer[10];
        for (int i = 0; i < 10; i++) {
            seats[i] = i + 1;
        }
        return seats;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BioskopBookingApp().setVisible(true);
            }
        });
    }
}