package app.gui;

import app.model.Client;
import app.model.Guides;
import app.model.TouristicPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PackageDetailsDashboard extends JFrame{

    private JPanel panel1;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel typeLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel basePriceLabel;
    private JLabel numOfPeopleLabel;
    private JLabel hotelDetails;
    private JLabel destinationDetails;
    private JLabel hotelNameLabel;
    private JLabel starsLabel;
    private JLabel addressLabel;
    private JLabel countryLabel;
    private JLabel cityLabel;
    private JLabel climateLabel;
    private JButton bookButton;
    private JButton goBackButton;
    private JTextArea guidesTextArea;

    public PackageDetailsDashboard(TouristicPackage selectedPackage, Client connectedClient) {
        setContentPane(mainPanel);
        setTitle("Travel App - Offer Details");
        setPreferredSize(new Dimension(800, 400));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        titleLabel.setText(selectedPackage.getName());
        typeLabel.setText(selectedPackage.getType());
        startDateLabel.setText(selectedPackage.getStartDate().toString());
        endDateLabel.setText(selectedPackage.getEndDate().toString());
        basePriceLabel.setText(String.format("%.2f\u20AC", selectedPackage.getBasePrice()));
        numOfPeopleLabel.setText(String.format("%d", selectedPackage.getNumOfPeople()));

        hotelNameLabel.setText(selectedPackage.getHotel().getName());
        starsLabel.setText(setStars(selectedPackage.getHotel().getStars()));
        addressLabel.setText(selectedPackage.getHotel().getAdress());

        countryLabel.setText(selectedPackage.getHotel().getDestination().getCountry());
        cityLabel.setText(selectedPackage.getHotel().getDestination().getCity());
        climateLabel.setText(selectedPackage.getHotel().getDestination().getClimate());

        StringBuilder sb = new StringBuilder();

        if (selectedPackage.getGuides() == null || selectedPackage.getGuides().isEmpty()){
            sb.append("No guides for this package.");
        }else{
            for (Guides g : selectedPackage.getGuides()) {
                sb.append("\u2022 ").append(g.getName())
                        .append(" (Tel: ").append(g.getPhoneNum())
                        .append(", Language: ").append(g.getLanguage()).append(")\n");
            }
        }
        guidesTextArea.setText(sb.toString());
        guidesTextArea.setCaretPosition(0);

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ReservationDashboard(selectedPackage, connectedClient);
            }
        });
    }

    private String setStars(int n){
        String stars = "";
        for(int i = 0; i < n; i++){
            stars = stars.concat("\u2605");
        }
        return stars;
    }
}
