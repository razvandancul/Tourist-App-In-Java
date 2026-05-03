package app.dao;

import app.model.Destination;
import app.model.Hotel;
import app.util.DataBaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class HotelDAO {

    public int addHotel(Hotel selectedHotel){
        int existingId = getHotelIdIfExists(selectedHotel.getName(),
                selectedHotel.getStars(), selectedHotel.getAdress(), selectedHotel.getDestination().getId());

        if (existingId != -1) {
            return existingId;
        }

        String sql = "INSERT INTO hoteluri (nume, stele, adresa, id_destinatie) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection()) {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, selectedHotel.getName());
            stmt.setInt(2, selectedHotel.getStars());
            stmt.setString(3, selectedHotel.getAdress());
            stmt.setInt(4, selectedHotel.getDestination().getId());

            int affectedRows = stmt.executeUpdate();

            if(affectedRows > 0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt("id_hotel");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int getHotelIdIfExists(String name, int stars, String address, int destId) {
        String sql = "SELECT id_hotel FROM hoteluri WHERE nume = ? AND stele = ? AND adresa = ? AND id_destinatie = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, stars);
            stmt.setString(3, address);
            stmt.setInt(4, destId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_hotel");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Map<Integer, Hotel> getAllHotelsOfDestination(Destination destination){
        Map<Integer, Hotel> hotels = new HashMap<>();

        String sql = "SELECT * from hoteluri WHERE id_destinatie = ?";
        try (Connection conn = DataBaseConnection.getConnection()) {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, destination.getId());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id_hotel");
                Hotel hotel = new Hotel(
                        id,
                        rs.getString("nume"),
                        rs.getInt("stele"),
                        rs.getString("adresa"),
                        destination
                );

                hotels.put(id, hotel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotels;
    }
}
