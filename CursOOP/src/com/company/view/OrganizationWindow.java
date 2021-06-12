package com.company.view;

import com.company.db.DBWorker;
import com.company.db.DBWorkerOrg;
import com.company.model.OrgList;
import com.company.model.OrgTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class OrganizationWindow extends JFrame {
    private JTable table;
    public static OrgTableModel tableModel=new OrgTableModel(new OrgList());
    private JPanel panelTable=new JPanel();
    private JPanel panelMenuTable=new JPanel();

    private JLabel labelOrg=new JLabel("Введите название организации:");
    private JLabel labelTelephone=new JLabel("Введите номер телефона:");
    private JLabel labelBalance=new JLabel("Введите расчетный счет:");


    private JTextField textName=new JTextField();
    private JTextField textTelephone=new JTextField();
    private JTextField textBalance=new JTextField();


    private JButton buttonAdd=new JButton("Добавить запись");
    private JButton buttonDelete=new JButton("Удалить запись");
    private JButton buttonUpdate=new JButton("Изменить запись");

    //пранировщик для формы
    private GridLayout layoutForm = new GridLayout(1, 2, 5, 12);
    //пранировщик для панели
    private GridLayout layoutPanel = new GridLayout(9, 1, 5, 12);
    public OrganizationWindow(){
        super("Организации");
        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(layoutForm);


        table=new JTable();
        table.setModel(tableModel);
        JScrollPane jScrollPane=new JScrollPane(table);

        panelTable.add(jScrollPane);

        tableModel.connectDB();

        this.add(panelTable);
        this.add(panelMenuTable);


        panelMenuTable.setLayout(layoutPanel);

        panelMenuTable.add(labelOrg);
        panelMenuTable.add(textName);

        panelMenuTable.add(labelTelephone);
        panelMenuTable.add(textTelephone);

        panelMenuTable.add(labelBalance);
        panelMenuTable.add(textBalance);


        panelMenuTable.add(buttonAdd);
        panelMenuTable.add(buttonUpdate);
        panelMenuTable.add(buttonDelete);

        if(!AuthorizationWindow.user){
            buttonUpdate.setVisible(false);
            buttonDelete.setVisible(false);
            buttonAdd.setVisible(false);

        }else{
            buttonUpdate.setVisible(true);
            buttonDelete.setVisible(true);
            buttonAdd.setVisible(true);

        }

        AddListener addListener = new AddListener();
        buttonAdd.addActionListener(addListener);

        DeleteListener deleteListener=new DeleteListener();
        buttonDelete.addActionListener(deleteListener);

        UpdateListener updateListener=new UpdateListener();
        buttonUpdate.addActionListener(updateListener);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private boolean checkInput() {
        try {
            if (textName.getText().trim().length() == 0 || textTelephone.getText().trim().length() == 0 ||
                    textBalance.getText().trim().length() == 0 ) {
                JOptionPane.showMessageDialog(null, "Введите значения в поля");
                return false;
            } else if (Double.parseDouble(textBalance.getText()) < 0 ) {
                JOptionPane.showMessageDialog(null, "Введите целое положитлеьное число в поле расчетного счета");
                return false;
            } else return true;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Введите целое положитлеьное число в поле расчетного счета");
            return false;
        }

    }
    public class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int num=0;
            try {
                if(checkInput()) {
                    if(!DBWorker.checkOrg(textName.getText())) {
                        DBWorkerOrg.writeDB(textName.getText(), textTelephone.getText(), Double.parseDouble(textBalance.getText()));
                        //tableModel.addPub(num,Integer.parseInt(textOrg.getText()), textDriver.getText(), textCar.getText(),Integer.parseInt(textPay.getText()), Date.valueOf(textDate.getText()));

                        tableModel.connectDB();
                        tableModel.fireTableDataChanged();
                        MainWindow.cbmodel.setData();
                    }else JOptionPane.showMessageDialog(null, "Такая запись уже есть");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
    }
    public Object GetIndex(){
        Object o= table.getValueAt(table.getSelectedRow(),0);
        return o;
    }
    public class DeleteListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                String num= (String) GetIndex();
                DBWorkerOrg.delete(num);
                System.out.println(num);
                JOptionPane.showMessageDialog(null, "Запись успешно удалёна");
                tableModel.connectDB();
                tableModel.fireTableDataChanged();
                MainWindow.cbmodel.setData();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Выберите запись");
            }


        }
    }
    public class UpdateListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                if(checkInput()) {
                    String num = (String) GetIndex();
                    DBWorkerOrg.updateDB(textName.getText(), textTelephone.getText(), Double.parseDouble(textBalance.getText()));
                    JOptionPane.showMessageDialog(null, "Запись успешно изменена");
                    tableModel.connectDB();
                    tableModel.fireTableDataChanged();
                    MainWindow.cbmodel.setData();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Выберите запись");
            }


        }
    }

}
