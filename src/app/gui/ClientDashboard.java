package app.gui;

import app.dao.PackageDAO;
import app.model.Client;
import app.model.TouristicPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ClientDashboard extends JFrame{
    private JPanel panel1;
    private JPanel mainPanel;
    private JTable offerTable;
    private JScrollPane scrollPane;
    private JLabel welcomeLabel;
    private JButton closeButton;
    private JButton accountButton;

    private Client client;
    private List<TouristicPackage> packages;
    private DefaultTableModel table;


    public ClientDashboard(Client connectedClient) {
        client = connectedClient;

        setContentPane(mainPanel);
        setTitle("Travel App - Offer List");
        setPreferredSize(new Dimension(1200, 600));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setupTable();

        loadData();

        offerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = offerTable.getSelectedRow();
                    if (row != -1) {
                        TouristicPackage selectedPackage = packages.get(row);

                        new PackageDetailsDashboard(selectedPackage, connectedClient);
                    }
                }
            }
        });

        welcomeLabel.setText("Welcome, " + connectedClient.getName() + " " + connectedClient.getSurname());
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ManageAccountDashboard(connectedClient, ClientDashboard.this);
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void loadData(){
        PackageDAO dao = new PackageDAO();
        packages = dao.getAllPackages();

        for (TouristicPackage p : packages) {
            Object[] row = {
                    p.getHotel().getDestination().getCity(),
                    p.getHotel().getName(),
                    p.getName(),
                    p.getType(),
                    p.getBasePrice(),
                    p.getStartDate() + " -> " + p.getEndDate()
            };
            table.addRow(row);
        }
    }

    private void setupTable(){
        String[] cols = {"Destination", "Hotel", "Package Name", "Type", "Price(EURO)", "Period"};

        table = new DefaultTableModel(cols, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        offerTable.setModel(table);
    }
}
