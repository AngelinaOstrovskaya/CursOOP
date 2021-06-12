package com.company.view;

import com.company.model.RecordTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FilterWindow extends JFrame {
    private JLabel labelName=new JLabel("Введите название организации:");
    private JLabel labelDate1=new JLabel("Выберите дату начала:");
    private JLabel labelDate2=new JLabel("Выберите дату конца:");

    private JTextField textName=new JTextField();
    private JTextField textDate1=new JTextField();
    private JTextField textDate2=new JTextField();

    private JButton buttonClear=new JButton("Сбросить фильтр");
    private JButton buttonFilter=new JButton("Применить фильтр");
    private GridLayout layout = new GridLayout(6, 1, 5, 12);
    public FilterWindow() throws HeadlessException {
        super("Публикации");
        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(layout);

        this.add(labelDate1);
        this.add(textDate1);

        this.add(labelDate2);
        this.add(textDate2);

        this.add(buttonFilter);
        this.add(buttonClear);

        FilterListener filterListener=new FilterListener();
        ClearListener clearListener= new ClearListener();

        buttonFilter.addActionListener(filterListener);
        buttonClear.addActionListener(clearListener);


    }
    private boolean checkInput() {
        if (textDate1.getText().trim().length() == 0 || textDate2.getText().trim().length() == 0 ) {
            JOptionPane.showMessageDialog(null, "Введите значения в поля");
            return false;
        } else return true;

    }
    public class FilterListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //String date1 =textDate1.getText();
            try {
                 if(checkInput()) {
                     Date date1 = Date.valueOf(textDate1.getText());
                     Date date2 = Date.valueOf(textDate2.getText());
                     if (date1.before(date2)) {
                         RecordTableModel.filterDB(date1, date2);
                         MainWindow.tableModel.fireTableDataChanged();
                     } else JOptionPane.showMessageDialog(null, "Дата начала не может быть больше даты конца");
                 }


            }catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Неверный формат введенной даты");

            }

        }
    }

    public class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RecordTableModel.connectDB();
            MainWindow.tableModel.fireTableDataChanged();
        }
    }
}
