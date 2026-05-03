package app.gui;

import app.dao.DestinationDAO;
import app.dao.HotelDAO;
import app.dao.PackageDAO;
import app.model.Destination;
import app.model.Guides;
import app.model.Hotel;
import app.model.TouristicPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

public class CreateNewOfferDashboard extends JFrame{
    private JPanel panel1;
    private JPanel mainPanel;
    private JComboBox destinationBox;
    private JButton addDestinationButton;
    private JComboBox hotelBox;
    private JButton addHotelButton;
    private JTextField nameField;
    private JTextField typeField;
    private JTextField startField;
    private JTextField endField;
    private JTextField priceField;
    private JTextField numOfPeopleField;
    private JButton submitButton;

    public CreateNewOfferDashboard(){
        setContentPane(mainPanel);
        setTitle("Travel App - Post a new offer on app");
        setPreferredSize(new Dimension(600, 500));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        DestinationDAO daoD = new DestinationDAO();
        HotelDAO daoH = new HotelDAO();

        var destinationList = daoD.getAllDestinations();

        destinationList.forEach((k, v) -> destinationBox.addItem((Destination)v));

        addDestinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel popupPanel = new JPanel(new GridLayout(0, 1));

                JTextField cityField = new JTextField();
                JTextField countryField = new JTextField();
                JTextField climateField = new JTextField();

                popupPanel.add(new JLabel("City name:"));
                popupPanel.add(cityField);
                popupPanel.add(new JLabel("Country:"));
                popupPanel.add(countryField);
                popupPanel.add(new JLabel("Climate:"));
                popupPanel.add(climateField);

                int result = JOptionPane.showConfirmDialog(null, popupPanel,
                        "Add new destination", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String city = cityField.getText().trim();
                    String country = countryField.getText().trim();
                    String climate = climateField.getText().trim();

                    if (!city.isEmpty() && !country.isEmpty()) {
                        Destination newDest = new Destination(country, city, climate);

                        int newId = daoD.addDestination(newDest);

                        if (newId > 0) {
                            newDest.setId(newId);

                            destinationBox.addItem(newDest);

                            destinationBox.setSelectedItem(newDest);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid data! Check fields.");
                    }
                }
            }
        });

        addHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Destination selectedDest = (Destination) destinationBox.getSelectedItem();

                if (selectedDest == null) {
                    JOptionPane.showMessageDialog(null, "Please create or select a destination first!");
                    return;
                }

                JPanel popupPanel = new JPanel(new GridLayout(0, 1));
                JTextField nameField = new JTextField();
                JTextField starsField = new JTextField();
                JTextField addressField = new JTextField();

                popupPanel.add(new JLabel("Hotel name:"));
                popupPanel.add(nameField);
                popupPanel.add(new JLabel("Stars:"));
                popupPanel.add(starsField);
                popupPanel.add(new JLabel("Address:"));
                popupPanel.add(addressField);

                int result = JOptionPane.showConfirmDialog(null, popupPanel,
                        "Add new hotel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText().trim();
                    String address = addressField.getText().trim();
                    int stars = 0;

                    try {
                        stars = Integer.parseInt(starsField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Stars must be a number!");
                        return;
                    }

                    if (!name.isEmpty() && !address.isEmpty() && stars > 0 && stars <= 5) {
                        Hotel newHotel = new Hotel(name, stars, address, selectedDest);

                        int newId = daoH.addHotel(newHotel);

                        if (newId > 0) {
                            newHotel.setId(newId);
                            hotelBox.addItem(newHotel);
                            hotelBox.setSelectedItem(newHotel);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid data! Check fields.");
                    }
                }
            }
        });

        destinationBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hotelBox.removeAllItems();
                Destination selected = (Destination) destinationBox.getSelectedItem();

                if (selected != null) {
                    var hotels = daoH.getAllHotelsOfDestination(selected);

                    hotels.forEach((k, v) -> hotelBox.addItem(v));
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String type = typeField.getText().trim();

                LocalDate startDate = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                try{
                    startDate = LocalDate.parse(startField.getText().trim(), formatter);
                }catch (DateTimeParseException dtp){
                    JOptionPane.showMessageDialog(null, "Date format is invalid(use dd-mm-yyyy");
                    return;
                }

                LocalDate endDate = null;
                try{
                    endDate = LocalDate.parse(endField.getText().trim(), formatter);
                }catch (DateTimeParseException dtp){
                    JOptionPane.showMessageDialog(null, "Date format is invalid(use dd-mm-yyyy");
                    return;
                }

                BigDecimal basePrice = null;
                try{
                    basePrice = new BigDecimal(priceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please put a number for price!");
                    return;
                }

                int ppl = 0;
                try{
                    ppl = Integer.parseInt(numOfPeopleField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please put a number for maximum number of people!");
                    return;
                }

                if(name.isEmpty() || type.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please do not leave empty fields!");
                    return;
                }

                if(ppl <= 0){
                    JOptionPane.showMessageDialog(null, "Invalid number of people!");
                    return;
                }

                if(startDate.isAfter(endDate)){
                    JOptionPane.showMessageDialog(null, "Invalid dates!");
                    return;
                }

                Hotel selectedHotel = (Hotel) hotelBox.getSelectedItem();
                if (selectedHotel == null) {
                    JOptionPane.showMessageDialog(null, "Please select a hotel!");
                    return;
                }

                //TODO: neaparat adaugare ghizi pentru pachetele noi
                Set<Guides> guides = new HashSet<>();

                TouristicPackage pack = new TouristicPackage(
                       name, type, startDate, endDate, basePrice, ppl, selectedHotel, guides
                );

                PackageDAO dao = new PackageDAO();
                int existingId = dao.insertNewPackage(pack);
                if(existingId == -1){
                    JOptionPane.showMessageDialog(null, "Package already exists!");
                }else if (existingId > 0){
                    JOptionPane.showMessageDialog(null, "Package successfully added!");
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Database error!");
                }
            }
        });
    }
}
