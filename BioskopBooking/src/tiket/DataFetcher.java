package tiket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DataFetcher {

    public Map<String, String> getDataFromDatabase(String email) {
        Map<String, String> data = new HashMap<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projek", "root", "Dusk0");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tiket WHERE email = '" + email + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                data.put("judulFilm", rs.getString("filmTitle"));
                data.put("tanggal", rs.getString("date"));
                data.put("jam", rs.getString("time"));
                data.put("email", rs.getString("email"));
                data.put("jumlahTiket", rs.getString("ticketCount"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
