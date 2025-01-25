//package ru.kmikhails.metrolog.view;
//
//import com.formdev.flatlaf.FlatClientProperties;
//import net.miginfocom.swing.MigLayout;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class LoginFrame extends JFrame {
//
//    private JTextField usernameTextField;
//    private JPasswordField passwordField;
//    private JCheckBox rememberCheckBox;
//    private JButton loginButton;
//
//    public LoginFrame() {
//        init();
//    }
//
//    public void init() {
//        setTitle("Login");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(new Dimension(1200, 700));
//        setLocationRelativeTo(null);
//        setContentPane(initLoginPanel());
//    }
//
//    private JPanel initLoginPanel() {
//        JPanel loginPanel = new JPanel();
//        loginPanel.setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
//        usernameTextField = new JTextField();
//        passwordField = new JPasswordField();
//        rememberCheckBox = new JCheckBox("Запомнить меня");
//        loginButton = new JButton("Login");
//
//        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
//        panel.putClientProperty(FlatClientProperties.STYLE, "" +
//                "arc:20;" +
//                "[light]background:darken(@background,3%);");
//
//
//        loginPanel.add(panel);
//
//        return loginPanel;
//    }
//
//}
