package app.gui;

import app.dao.ClientDAO;
import app.model.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDashboard extends JFrame{
    private JPanel panel1;
    private JPanel mainPanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel logLabel;
    private JLabel registerLabel;

    public LoginDashboard() {
        setContentPane(mainPanel);
        setTitle("Login - Travel App");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterDashboard();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginDashboard.this,
                            "Introduce email and password!\nDo not leave any empty fields.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ClientDAO dao = new ClientDAO();
                Client foundClient = dao.login(email, password);

                if(foundClient != null){
                    dispose();
                    new ClientDashboard(foundClient);
                }else{
                    JOptionPane.showMessageDialog(LoginDashboard.this,
                            "Wrong email or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}
