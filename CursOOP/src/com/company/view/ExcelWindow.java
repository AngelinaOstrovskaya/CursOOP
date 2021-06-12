package com.company.view;

import com.company.excel.ExcelWorker;
import com.company.model.RecordTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ExcelWindow extends JFrame {
    private JLabel labelName=new JLabel("Введите название организации:");
    private JLabel labelDate1=new JLabel("Выберите дату начала:");
    private JLabel labelDate2=new JLabel("Выберите дату конца:");

    private JTextField textName=new JTextField();
    private JTextField textDate1=new JTextField();
    private JTextField textDate2=new JTextField();

    private JButton buttonFilter=new JButton("Создать отчёт");
    private GridLayout layout = new GridLayout(7, 1, 5, 12);
    public ExcelWindow() throws HeadlessException {
        super("Публикации");
        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(layout);

        this.add(labelName);
        this.add(textName);

        this.add(labelDate1);
        this.add(textDate1);

        this.add(labelDate2);
        this.add(textDate2);

        this.add(buttonFilter);

        ExcelListener filterListener=new ExcelListener();

        buttonFilter.addActionListener(filterListener);



    }

    private boolean checkInput() {
        try {
            if (textName.getText().trim().length() == 0 || textDate1.getText().trim().length() == 0 || textDate2.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Введите значения в поля");
                return false;
            } else {
                Date.valueOf(textDate1.getText());
                Date.valueOf(textDate2.getText());
                return true;
            }
        }catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Неверный формат введенной даты");
            return false;
        }

    }

    public class ExcelListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(checkInput()) {
                Date date1 = Date.valueOf(textDate1.getText());
                Date date2 = Date.valueOf(textDate2.getText());
                if(date1.before(date2)) {
                    ExcelWorker exl = new ExcelWorker(textName.getText(), Date.valueOf(textDate1.getText()), Date.valueOf(textDate2.getText()));
                }else JOptionPane.showMessageDialog(null, "Дата начала не может быть больше даты конца");
            }
        }
    }
}
