package app.dao;

import app.model.Bill;
import app.util.DataBaseConnection;

import java.sql.*;

public class BillDAO {

    public boolean addBill(Bill bill) {
        String newSeries = generateNextSeries();

        bill.setSeries(newSeries);

        String sql = "INSERT INTO facturi (serie_factura, suma_totala, data_emitere, id_rezervare) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newSeries);
            stmt.setBigDecimal(2, bill.getTotalAmountToPay());
            stmt.setDate(3, Date.valueOf(bill.getPrintDate()));
            stmt.setInt(4, bill.getReservation().getId());

            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateNextSeries() {
        String sql = "SELECT serie_factura FROM facturi ORDER BY id_factura DESC LIMIT 1";
        String prefix = "FV";
        int startNumber = 1001;

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String lastSeries = rs.getString("serie_factura");

                String numberPart = lastSeries.substring(prefix.length());

                int nextNumber = Integer.parseInt(numberPart) + 1;

                return prefix + nextNumber;
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return prefix + startNumber;
    }
}
