package app.gui;

import app.dao.ClientDAO;
import app.model.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDashboard extends JFrame {

    private JPanel mainPanel;
    private JLabel messageLabel;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField cnpField;
    private JTextField surnameField;
    private JTextField nameField;
    private JCheckBox isBusiness;
    private JPasswordField passwordField;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel emailLabel;
    private JLabel cnpLabel;
    private JLabel phoneLabel;
    private JLabel passwordLabel;
    private JLabel isBusinessLabel;
    private JButton registerButton;
    private JLabel registerLabel;

    public RegisterDashboard() {

        setContentPane(mainPanel);
        setTitle("Register - Travel App");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String cnp = cnpField.getText();
                String phoneNum = phoneField.getText();
                String password = new String(passwordField.getPassword());

                if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || cnp.isEmpty() ||
                    phoneNum.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(RegisterDashboard.this,
                            "Do not leave any empty fields.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                if(!email.contains("@gmail.com") && !email.contains("@yahoo.com") && !email.contains("@outlook.com")){
                    JOptionPane.showMessageDialog(RegisterDashboard.this,
                            "Introduced email is not supported", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(cnp.length() < 13){
                    JOptionPane.showMessageDialog(RegisterDashboard.this,
                            "Invalid CNP", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(phoneNum.length() < 10 || phoneNum.length() > 15){
                    JOptionPane.showMessageDialog(RegisterDashboard.this,
                            "Invalid phone number", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //TODO: restrictii suplimentare la introducerea parolei(lungime, char special, cifre)
                
                Client newClient = new Client(name, surname, email, cnp, phoneNum,
                        isBusiness.isSelected() ? "BUSINESS" : "CLIENT");

                ClientDAO dao = new ClientDAO();
                boolean isRegistered = dao.registerClient(newClient, password);
                if(isRegistered){
                    dispose();
                    new LoginDashboard();
                }else{
                    JOptionPane.showMessageDialog(RegisterDashboard.this,
                            "Account may already exist or data introduced is wrong.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
        });


        setVisible(true);
    }
}
