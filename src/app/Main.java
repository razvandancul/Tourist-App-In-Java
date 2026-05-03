package app;

import app.gui.LoginDashboard;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;

//TODO: guideDAO

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.put("Component.accentColor", Color.decode("#e17055"));
            UIManager.put("Button.arc", 20);
            UIManager.put("Component.arc", 15);
            UIManager.put("TextComponent.arc", 15);

            UIManager.put("Component.focusWidth", 1);

            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);

            FlatMacDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("FlatLaf error");
        }

        new LoginDashboard();
    }
}
