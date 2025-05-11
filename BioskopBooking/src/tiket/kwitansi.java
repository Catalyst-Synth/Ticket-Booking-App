package tiket;

import javax.swing.*;
import java.awt.*;

public class kwitansi extends JFrame {

    public kwitansi(String nama, String email, String metodePembayaran, String film, String kelas, String tanggal, String jam, String kursi, int harga, int jumlahTiket, int totalHarga) {
        setTitle("Kwitansi Pemesanan Tiket");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 2, 10, 10));

        panel.add(new JLabel("Nama:"));
        panel.add(new JLabel(nama));

        panel.add(new JLabel("Email:"));
        panel.add(new JLabel(email));

        panel.add(new JLabel("Metode Pembayaran:"));
        panel.add(new JLabel(metodePembayaran));

        panel.add(new JLabel("Film:"));
        panel.add(new JLabel(film));

        panel.add(new JLabel("Kelas:"));
        panel.add(new JLabel(kelas));

        panel.add(new JLabel("Tanggal:"));
        panel.add(new JLabel(tanggal));

        panel.add(new JLabel("Jam Tayang:"));
        panel.add(new JLabel(jam));

        panel.add(new JLabel("Kursi:"));
        panel.add(new JLabel(kursi));

        panel.add(new JLabel("Harga:"));
        panel.add(new JLabel(String.valueOf(harga)));

        panel.add(new JLabel("Jumlah Tiket:"));
        panel.add(new JLabel(String.valueOf(jumlahTiket)));

        panel.add(new JLabel("Total Harga:"));
        panel.add(new JLabel(String.valueOf(totalHarga)));

        add(panel);
    }
    
    public static void main(String[] args) {
        // Dummy data for testing
        kwitansi kwitansi = new kwitansi("John Doe", "john@example.com", "Credit Card", "Avengers", "VIP", "2024-06-01", "18:00", "A1", 100000, 2, 200000);
        kwitansi.setVisible(true);
    }
}
