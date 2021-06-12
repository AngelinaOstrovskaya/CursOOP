package com.company.view;

import javax.swing.*;
import java.awt.*;

public class AuthorizationWindow extends JFrame {

    JLabel loginLable = new JLabel("Логин:");
    JLabel passvordLable = new JLabel("Пароль:");
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPanel panel=new JPanel();

    JButton loginButton = new JButton("Войти");

    public static boolean user;

    public AuthorizationWindow() throws HeadlessException {

        super("Авторизация");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);



        loginButton.addActionListener(e -> {
            if (passwordField.getText().equals("admin") && loginField.getText().equals("admin")) {
                user = true;
                MainWindow v = new MainWindow();
            }
            else if ((passwordField.getText().equals("operator") && loginField.getText().equals("operator"))){
                user=false;
                MainWindow v = new MainWindow();
            }else  JOptionPane.showMessageDialog(null, "Неверный логин или пароль");


        });

       panel.setLayout(new GridLayout(6, 1, 5, 12));

        panel.add(loginLable);
        panel.add(loginField);
        panel.add(passvordLable);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel, BorderLayout.NORTH);
//        frame.add(loginLable);
//        frame.add(loginField);
//        frame.add(passvordLable);
//        frame.add(passwordField);
//        frame.add(loginButton);
        //frame.add(regisrtationButton);
        pack();
    }
}
