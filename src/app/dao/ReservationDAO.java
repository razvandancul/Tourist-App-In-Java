package app.dao;

import app.model.Reservation;
import app.util.DataBaseConnection;

import java.sql.*;

public class ReservationDAO {

    public int addReservation(Reservation reservation){
        String sql = "INSERT INTO rezervari " +
                "(data_rezervare, status, nr_persoane, id_client, id_pachet) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            assert conn != null;
            stmt.setDate(1, Date.valueOf(reservation.getReservationDate()));
            stmt.setString(2, reservation.getStatus().toString());
            stmt.setInt(3, reservation.getNumOfPeople());
            stmt.setInt(4, reservation.getClient().getId());
            stmt.setInt(5, reservation.getTouristicPackage().getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
