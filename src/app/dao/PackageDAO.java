package app.dao;

import app.model.*;
import app.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageDAO {

    public List<TouristicPackage> getAllPackages(){
        Map<Integer, TouristicPackage> packages = new HashMap<>();

        String sql = "SELECT " +
                "p.id_pachet, p.nume_pachet, p.tip_pachet, p.pret_baza, p.data_inceput, p.data_sfarsit, p.nr_locuri, " +
                "h.id_hotel, h.nume AS nume_hotel, h.stele, h.adresa, " +
                "d.id_destinatie, d.oras, d.tara, d.climat, " +
                "g.id_ghid, g.nume AS nume_ghid, g.telefon AS telefon_ghid, g.limba_vorbita " +
                "FROM pachete_turistice p " +
                "JOIN hoteluri h ON p.id_hotel = h.id_hotel " +
                "JOIN destinatii d ON h.id_destinatie = d.id_destinatie " +
                "LEFT JOIN pachete_ghizi pg ON p.id_pachet = pg.id_pachet " +
                "LEFT JOIN ghizi g ON pg.id_ghid = g.id_ghid " +
                "WHERE p.nr_locuri > 0 " +
                "ORDER BY p.id_pachet";

        try(Connection conn = DataBaseConnection.getConnection()) {
            assert conn != null;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                int packageId = rs.getInt("id_pachet");
                TouristicPackage tPackage = packages.get(packageId);

                if(tPackage == null){
                    Destination destination = new Destination(
                            rs.getInt("id_destinatie"),
                            rs.getString("tara"),
                            rs.getString("oras"),
                            rs.getString("climat")
                    );

                    Hotel hotel = new Hotel(
                            rs.getInt("id_hotel"),
                            rs.getString("nume_hotel"),
                            rs.getInt("stele"),
                            rs.getString("adresa"),
                            destination
                    );

                    tPackage = new TouristicPackage(
                            packageId,
                            rs.getString("nume_pachet"),
                            rs.getString("tip_pachet"),
                            rs.getDate("data_inceput").toLocalDate(),
                            rs.getDate("data_sfarsit").toLocalDate(),
                            rs.getBigDecimal("pret_baza"),
                            rs.getInt("nr_locuri"),
                            hotel
                    );

                    packages.put(packageId, tPackage);
                }

                int guideId = rs.getInt("id_ghid");
                if(guideId > 0){
                    Guides guide = new Guides(
                            guideId,
                            rs.getString("nume_ghid"),
                            rs.getString("telefon_ghid"),
                            rs.getString("limba_vorbita")
                    );

                    tPackage.getGuides().add(guide);
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<>(packages.values());
    }

    public int insertNewPackage(TouristicPackage newPackage){
        int existingId = getPackageIfExists(newPackage);

        if (existingId != -1) {
            return -1;
        }

        String sql = "INSERT INTO pachete_turistice (nume_pachet, tip_pachet, data_inceput, " +
                "data_sfarsit, pret_baza, nr_locuri, id_hotel) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection()) {
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            set(newPackage, stmt);

            int affectedRows = stmt.executeUpdate();

            if(affectedRows > 0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt("id_pachet");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int getPackageIfExists(TouristicPackage newPackage) {
        String sql = "SELECT id_pachet FROM pachete_turistice WHERE " +
                "data_inceput = ? AND data_sfarsit = ? AND pret_baza = ? AND id_hotel = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(newPackage.getStartDate()));
            stmt.setDate(2, Date.valueOf(newPackage.getEndDate()));
            stmt.setBigDecimal(3, newPackage.getBasePrice());
            stmt.setInt(4, newPackage.getHotel().getId());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_pachet");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void deletePackage(int id) {
        String sql = "DELETE FROM pachete_turistice WHERE id_pachet = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePackage(TouristicPackage pack) {
        String sql = "UPDATE pachete_turistice SET nume_pachet = ?, tip_pachet = ?, " +
                "data_inceput = ?, data_sfarsit = ?, pret_baza = ?, nr_locuri = ?, " +
                "id_hotel = ? WHERE id_pachet = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            set(pack, stmt);
            stmt.setInt(8, pack.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void set(TouristicPackage pack, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, pack.getName());
        stmt.setString(2, pack.getType());
        stmt.setDate(3, Date.valueOf(pack.getStartDate()));
        stmt.setDate(4, Date.valueOf(pack.getEndDate()));
        stmt.setBigDecimal(5, pack.getBasePrice());
        stmt.setInt(6, pack.getNumOfPeople());
        stmt.setInt(7, pack.getHotel().getId());
    }
}
