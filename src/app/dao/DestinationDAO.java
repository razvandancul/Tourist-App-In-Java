package app.dao;

import app.model.Destination;

import java.util.Map;
import app.util.DataBaseConnection;
import java.sql.*;
import java.util.HashMap;

public class DestinationDAO {

    public int addDestination(Destination newDest) {
        int existingId = getDestinationIdIfExists(newDest.getCity(), newDest.getCountry());

        if (existingId != -1) {
            return existingId;
        }

        String sql = "INSERT INTO destinatii (tara, oras, climat) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection()) {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, newDest.getCountry());
            stmt.setString(2, newDest.getCity());
            stmt.setString(3, newDest.getClimate());

            int affectedRows = stmt.executeUpdate();

            if(affectedRows > 0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt("id_destinatie");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int getDestinationIdIfExists(String city, String country) {
        String sql = "SELECT id_destinatie FROM destinatii WHERE oras = ? AND tara = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city);
            stmt.setString(2, country);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_destinatie");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Map<Integer, Destination> getAllDestinations(){
        Map<Integer, Destination> destinations = new HashMap<>();

        String sql = "SELECT * from destinatii";
        try (Connection conn = DataBaseConnection.getConnection()) {
            assert conn != null;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id_destinatie");
                  Destination dest = new Destination(
                          id,
                          rs.getString("tara"),
                          rs.getString("oras"),
                          rs.getString("climat")
                  );

                destinations.put(id, dest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return destinations;
    }

}
