package app.gui;

import app.model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAccountDashboard extends JFrame {

    private JPanel mainPanel;
    private JButton returnButton;
    private JButton listOfferButton;
    private JLabel fullNameLabel;
    private JLabel emailLabel;
    private JLabel numberLabel;
    private JButton logoutButton;

    public ManageAccountDashboard(Client connectedClient, ClientDashboard cd){
        setContentPane(mainPanel);
        setTitle("Travel App - Manage Account");
        setPreferredSize(new Dimension(400, 300));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        fullNameLabel.setText(connectedClient.getName() + " " + connectedClient.getSurname());
        emailLabel.setText(connectedClient.getEmail());
        numberLabel.setText(connectedClient.getPhoneNum());

        if(!connectedClient.isBusiness()){
            listOfferButton.setVisible(false);
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cd.setVisible(true);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                cd.setVisible(true);
            }
        });

        listOfferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: create offer interface
                new CreateNewOfferDashboard();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginDashboard();
            }
        });
    }
}
