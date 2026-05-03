package app.gui;

import app.dao.BillDAO;
import app.dao.PackageDAO;
import app.dao.ReservationDAO;
import app.model.Bill;
import app.model.Client;
import app.model.Reservation;
import app.model.TouristicPackage;
import app.util.BillGenerator;
import jdk.jshell.Snippet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationDashboard extends JFrame{
    private JPanel panel1;
    private JPanel mainPanel;
    private JLabel clientPromptLabel;
    private JLabel makeResNow;
    private JLabel reservationDateLabel;
    private JLabel maxPeople;
    private JLabel packageNameLabel;
    private JButton cancelButton;
    private JButton reserveButton;
    private JLabel totalToPayLabel;
    private JSpinner spinner;

    public ReservationDashboard(TouristicPackage selectedPackage, Client connectedClient){
        setContentPane(mainPanel);
        setTitle("Travel App - Make reservation");
        setPreferredSize(new Dimension(500, 300));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        makeResNow.setHorizontalAlignment(SwingConstants.CENTER);
        clientPromptLabel.setText(String.format("%s %s, proceed with your reservation.", connectedClient.getName(), connectedClient.getSurname()));
        reservationDateLabel.setText(LocalDate.now().toString());
        packageNameLabel.setText(selectedPackage.getName());
        totalToPayLabel.setText(String.format("%.2f\u20AC", selectedPackage.getBasePrice()));
        spinner.setModel(new SpinnerNumberModel(1, 1, selectedPackage.getNumOfPeople(), 1));

        final BigDecimal[] totalToPay = { selectedPackage.getBasePrice() };

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    spinner.commitEdit();
                } catch (java.text.ParseException ex) {
                    //Ignore
                }

                Integer numOfPpl = (Integer)spinner.getValue();
                LocalDate resDate = LocalDate.now();
                String status = "CONFIRMATA";

                Reservation newRes = new Reservation(resDate,
                        app.model.Reservation.Status.fromString(status), numOfPpl,
                        connectedClient, selectedPackage);

                ReservationDAO resDAO = new ReservationDAO();
                int resId = resDAO.addReservation(newRes);

                if(resId > 0){
                    newRes.setId(resId);
                    JOptionPane.showMessageDialog(ReservationDashboard.this, "Reservation was successful");

                    int remainingSpots = selectedPackage.getNumOfPeople() - numOfPpl;

                    PackageDAO packageDAO = new PackageDAO();
                    selectedPackage.setNumOfPeople(remainingSpots);
                    packageDAO.updatePackage(selectedPackage);

                    Bill bill = new Bill("", totalToPay[0], LocalDate.now(), newRes);
                    BillDAO billDAO = new BillDAO();
                    billDAO.addBill(bill);
                    BillGenerator.generateBill(bill, connectedClient, selectedPackage);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ReservationDashboard.this, "Reservation failed");
                }
            }
        });

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                totalToPay[0] = new BigDecimal(String.valueOf(newPrice(selectedPackage.getBasePrice())));
            }
        });
    }

    private BigDecimal newPrice(BigDecimal price){
        Integer numOfPpl = (Integer)spinner.getValue();
        BigDecimal totalPay = price.multiply(new BigDecimal(numOfPpl));

        totalToPayLabel.setText(String.format("%.2f\u20AC", totalPay));
        return totalPay;
    }
}
