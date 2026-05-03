package app.dao;

import app.model.Client;
import app.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {

    public Client login(String email, String password) {
        Client result = null;

        String sql = "SELECT * FROM clienti WHERE email = ? and parola = ?";

        try (Connection conn = DataBaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, email);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    Client client = new Client(rs.getInt("id_client"),
                                            rs.getString("nume"),
                                            rs.getString("prenume"),
                                            rs.getString("email"),
                                            rs.getString("cnp"),
                                        rs.getString("telefon"),
                                            rs.getString("rol")
                    );

                    result = client;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean registerClient(Client client, String password) {
        String sql = "INSERT INTO clienti (nume, prenume, email, cnp, telefon, parola, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getSurname());
            pstmt.setString(3, client.getEmail());
            pstmt.setString(4, client.getCnp());
            pstmt.setString(5, client.getPhoneNum());
            pstmt.setString(6, password);
            pstmt.setString(7, client.getRole());

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
