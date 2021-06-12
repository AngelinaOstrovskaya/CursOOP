package com.company.excel;

import com.company.db.DBWorker;
import com.company.model.Record;
import com.company.model.RecordList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExcelWorker {
    private  HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private Row row;
    private ArrayList<Record> dataRec=new ArrayList<Record>();
    private double sum;


    public ExcelWorker(String name, Date data1, Date data2){

        // создание самого excel файла в памяти
         workbook = new HSSFWorkbook();
        // создание листа
         sheet = workbook.createSheet("Лист 1");

        int rowNum=0;
        //создание подписей к столбцам
        row=sheet.createRow(rowNum);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Название организации");
        row.createCell(2).setCellValue("Номер машины");
        row.createCell(3).setCellValue("Сумма оплаты");
        row.createCell(4).setCellValue("Дата оплаты");

       // data=fillData(name,data1,data2);
        dataRec=fillData(name,data1,data2);

        // заполняем лист данными
        //for (Record dataModel : dataRec) {
      //     createSheetHeader(sheet, ++rowNum, dataModel);
       // }
        for (Record dataModel : dataRec) {
            createSheetHeader(sheet, ++rowNum, dataModel);
            sum=sum+dataModel.getPayment();
            System.out.println(sum);
            //System.out.println(data.getCount());
            //System.out.println(data.getPecord(i).getPayment());
        }

        //Row row = sheet.createRow(10);
        // идем в 4ю ячейку строки и устанавливаем
        // формулу подсчета зарплат (столбец D)
        //Cell sum = row.createCell(3);
       // sum.setCellValue(String.valueOf(sum));
        row=sheet.createRow(rowNum+1);
        row.createCell(3).setCellValue(sum);


        // записываем созданный в памяти Excel документ в файл
        try (FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Public\\file.xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");

    }

    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Record rec) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(rec.getIdRec());
        row.createCell(1).setCellValue(rec.car.getName());
        row.createCell(2).setCellValue(rec.car.getNumber());
        row.createCell(3).setCellValue(rec.getPayment());
       //Cell date=row.createCell(4);
      //  row.createCell(4).setCellStyle(dataStyle);
      //  String s= String.valueOf(rec.getDate());
        row.createCell(4).setCellValue(String.valueOf(rec.getDate()));

    }

    private static void sumPay(RecordList rec) {

    }

    // заполняем список рандомными данными
    // в реальных приложениях данные будут из БД или интернета
    private static ArrayList<Record> fillData(String name, Date data1, Date data2) {
         RecordList dat=new RecordList();
        ArrayList<Record> r=new ArrayList<Record>();
        try{
              r= DBWorker.excel(name,data1,data2);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return r;

    }


    }
