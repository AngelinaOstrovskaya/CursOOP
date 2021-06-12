package com.company.view;

import com.company.db.DBWorker;
import com.company.model.ComboBoxModel;
import com.company.model.RecordList;
import com.company.model.RecordTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    private JTable table;
    public static RecordTableModel tableModel=new RecordTableModel(new RecordList());

    private JPanel panel1=new JPanel();


    private JPanel panelTable=new JPanel();
    private JPanel panelMenuTable=new JPanel();
    private JPanel panelMenu=new JPanel();

    private JLabel labelOrg=new JLabel("Введите название организации:");
    private JLabel labelCar=new JLabel("Введите номер машины:");
    private JLabel labelPay=new JLabel("Введите стоимость заправки:");
    private JLabel labelDate=new JLabel("Введите дату заправки [yyy-mm-dd]:");

    private JComboBox<String> cbFirst;
    public static ComboBoxModel cbmodel=new ComboBoxModel();

    private JTextField textCar=new JTextField();
    private JTextField textPay=new JTextField();
    private JTextField textDate=new JTextField();

    private JButton buttonAdd=new JButton("Добавить запись");
    private JButton buttonDelete=new JButton("Удалить запись");
    private JButton buttonUpdate=new JButton("Изменить запись");
    private JButton buttonFilter=new JButton("Фильтр");

    private JButton buttonOrg=new JButton("Таблица с организациями");
    private JButton buttonCar=new JButton("Таблица с машинами");
    private JButton buttonExel=new JButton("Отчёт Excel");


    //пранировщик для формы
    private GridLayout layoutForm = new GridLayout(1, 2, 5, 12);
    //пранировщик для панели
    private GridLayout layoutPanel = new GridLayout(12, 1, 5, 12);
   private GridLayout layoutPanelЕ = new GridLayout(2, 1, 5, 12);
    private GridLayout layoutP= new GridLayout(1, 3, 5, 12);
    private BoxLayout layoutT=new BoxLayout(getContentPane() ,BoxLayout.X_AXIS);
    private BoxLayout layout=new BoxLayout(getContentPane() ,BoxLayout.Y_AXIS);

    public MainWindow()  {
        super("Записи");
        this.setSize(500,500);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

       // this.setLayout(layoutForm);
        this.setLayout(layout);
        //panelTable.setLayout(new FlowLayout(FlowLayout.CENTER));
       // panelTable.setLayout();

        table=new JTable();
        table.setModel(tableModel);
        JScrollPane jScrollPane=new JScrollPane(table);
        panelTable.add(jScrollPane);

        this.add(panelMenu);
        this.add(panel1);
        panel1.setLayout(layoutForm);
        panel1.add(panelTable);
        panel1.add(panelMenuTable);
         // panelTable.add(panelMenu);
        //setLayout(layoutP);
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.X_AXIS));
        panelMenu.add(buttonOrg);
        panelMenu.add(buttonCar);
        panelMenu.add(buttonExel);
        tableModel.connectDB();

        //this.add(panelTable);
       // this.add(panelMenuTable);
        cbFirst=new JComboBox<>();
        cbFirst.setModel(cbmodel);



        panelMenuTable.setLayout(layoutPanel);

        panelMenuTable.add(labelOrg);
        panelMenuTable.add(cbFirst);

        panelMenuTable.add(labelCar);
        panelMenuTable.add(textCar);

        panelMenuTable.add(labelPay);
        panelMenuTable.add(textPay);

        panelMenuTable.add(labelDate);
        panelMenuTable.add(textDate);

        panelMenuTable.add(buttonAdd);
        panelMenuTable.add(buttonUpdate);
        panelMenuTable.add(buttonDelete);
        panelMenuTable.add(buttonFilter);

        if(!AuthorizationWindow.user){
            buttonUpdate.setVisible(false);
            buttonDelete.setVisible(false);
            buttonFilter.setVisible(false);
            buttonExel.setVisible(false);
        }else{
            buttonUpdate.setVisible(true);
            buttonDelete.setVisible(true);
            buttonFilter.setVisible(true);
            buttonExel.setVisible(true);
        }

        buttonFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilterWindow v=new FilterWindow();
            }
        });

        AddListener addListener = new AddListener();
        buttonAdd.addActionListener(addListener);

        DeleteListener deleteListener=new DeleteListener();
        buttonDelete.addActionListener(deleteListener);

        UpdateListener updateListener=new UpdateListener();
        buttonUpdate.addActionListener(updateListener);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    DBWorker.closeDB();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        buttonOrg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               OrganizationWindow v=new OrganizationWindow();
            }
        });

        buttonCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarsWindows v=new CarsWindows();
            }
        });
        buttonExel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ExcelWindow v=new ExcelWindow();
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    private boolean checkInput() {
        try {
            if (cbFirst.getSelectedItem()==null || textCar.getText().trim().length() == 0 || textPay.getText().trim().length() == 0 ||
                    textDate.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Введите значения в поля");
                return false;
            } else if ( Double.parseDouble(textPay.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "Введите целое положитлеьное число в поле стоимости заправки");
                return false;
            } else {
                Date.valueOf(textDate.getText());
                return true;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Введите целое положитлеьное число в поле стоимости заправки");
            return false;
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Неверный формат введенной даты");
            return false;
        }

    }
    public class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int num=0;
                try {
                    if(checkInput()) {

                            if(DBWorker.checkCar(textCar.getText())) {
                                num = DBWorker.writeDB(cbFirst.getSelectedItem().toString(), textCar.getText(), Double.parseDouble(textPay.getText()), Date.valueOf(textDate.getText()));
                                System.out.println(num);
                                tableModel.connectDB();
                                tableModel.fireTableDataChanged();
                            }else JOptionPane.showMessageDialog(null, "Такой машины нет");

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
                int num= (int) GetIndex();
                DBWorker.delete(num);
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
                    if(DBWorker.checkOrg(cbFirst.getSelectedItem().toString())) {
                        if(DBWorker.checkCar(textCar.getText())) {

                            int num = (int) GetIndex();
                            DBWorker.updateDB(num, cbFirst.getSelectedItem().toString(), textCar.getText(), Integer.parseInt(textPay.getText()), Date.valueOf(textDate.getText()));
                            JOptionPane.showMessageDialog(null, "Запись успешно изменена");
                            tableModel.connectDB();
                            tableModel.fireTableDataChanged();

                        }else JOptionPane.showMessageDialog(null, "Такой машины нет");
                    }else JOptionPane.showMessageDialog(null, "Такой организации нет");
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
