package tiket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiSQL {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projek", "root", "Dusk0");
            if (connection != null) {
                System.out.println("Koneksi Berhasil ke Database");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
