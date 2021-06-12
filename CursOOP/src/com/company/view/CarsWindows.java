package com.company.view;

import com.company.db.DBWorker;
import com.company.db.DBWorkerCars;
import com.company.db.DBWorkerOrg;
import com.company.model.CarList;
import com.company.model.CarTableModel;
import com.company.model.ComboBoxModel;
import com.company.model.OrgList;
import com.company.model.OrgTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CarsWindows extends JFrame {
    private JTable table;
    public static CarTableModel tableModel=new CarTableModel(new CarList());
    private JPanel panelTable=new JPanel();
    private JPanel panelMenuTable=new JPanel();

    private JComboBox<String> cbFirst;
    public static ComboBoxModel cbmodel=new ComboBoxModel();

    private JLabel labelNumber=new JLabel("Введите номер машины:");
    private JLabel labelId=new JLabel("Введите название организации:");

    private JTextField textNumber=new JTextField();
    //private JTextField textId=new JTextField();

    private JButton buttonAdd=new JButton("Добавить запись");
    private JButton buttonDelete=new JButton("Удалить запись");
    private JButton buttonUpdate=new JButton("Изменить запись");

    //пранировщик для формы
    private GridLayout layoutForm = new GridLayout(1, 2, 5, 12);
    //пранировщик для панели
    private GridLayout layoutPanel = new GridLayout(7, 1, 5, 12);
    public CarsWindows(){
        super("Машины");
        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(layoutForm);

        table=new JTable();
        table.setModel(tableModel);
        JScrollPane jScrollPane=new JScrollPane(table);

        panelTable.add(jScrollPane);

        tableModel.connectDB();
        cbFirst=new JComboBox<>();
        cbFirst.setModel(cbmodel);

        this.add(panelTable);
        this.add(panelMenuTable);

        panelMenuTable.setLayout(layoutPanel);

        panelMenuTable.add(labelNumber);
        panelMenuTable.add(textNumber);

        panelMenuTable.add(labelId);
        panelMenuTable.add(cbFirst);

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
            if (textNumber.getText().trim().length() == 0 || cbFirst.getSelectedItem()==null ) {
                JOptionPane.showMessageDialog(null, "Введите значения в поля");
                return false;
            } else return true;

    }

    public class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                if(checkInput()) {
                    if(!DBWorker.checkCar(textNumber.getText())) {
                    DBWorkerCars.writeDB(textNumber.getText(), cbFirst.getSelectedItem().toString());
                    //tableModel.addPub(num,Integer.parseInt(textOrg.getText()), textDriver.getText(), textCar.getText(),Integer.parseInt(textPay.getText()), Date.valueOf(textDate.getText()));

                    tableModel.connectDB();
                    tableModel.fireTableDataChanged();
                    }else JOptionPane.showMessageDialog(null, "Такая машина уже есть");
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
                DBWorkerCars.delete(num);
                System.out.println(num);
                JOptionPane.showMessageDialog(null, "Запись успешно удалёна");
                tableModel.connectDB();
                tableModel.fireTableDataChanged();

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
                    DBWorkerCars.updateDB(num,textNumber.getText(),cbFirst.getSelectedItem().toString());
                    JOptionPane.showMessageDialog(null, "Запись успешно изменена");
                    tableModel.connectDB();
                    tableModel.fireTableDataChanged();
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
