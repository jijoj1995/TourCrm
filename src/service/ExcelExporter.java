package service;

import dto.CustomerList;
import dto.Stock;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ExcelExporter {
    private static Logger log=Logger.getLogger(ExcelExporter.class.getName());


    public boolean stockDataExporter(File file, ArrayList<Stock>stockArrayList){
        boolean isSuccessful=false;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            String[] columns = {"PRODUCT", "SUPPLIER", "VARIETY", "PURCHASE", "SELL", "STOCK LEFT"};
            Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

            // Create a Sheet
            Sheet sheet = workbook.createSheet("Employee");

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            // Create cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with stock data
            int rowNum = 1;
            for (Stock stockDto : stockArrayList) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(stockDto.getName());

                row.createCell(1)
                        .setCellValue(stockDto.getSupplier());

                row.createCell(2)
                        .setCellValue(stockDto.getCategory());

                row.createCell(3)
                        .setCellValue(stockDto.getOriginalPrice());
                row.createCell(4)
                        .setCellValue(stockDto.getSellingPrice());

                row.createCell(5)
                        .setCellValue(stockDto.getStockLeft());

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            // FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
            isSuccessful=true;
        }
        catch (Exception e){
            log.info("Exception occured while exporting stock data to file: "+e.getMessage());
        }
        return isSuccessful;
    }


    public boolean exportCustomerData(File file, ArrayList<CustomerList> customerList,boolean isShowOnlyPendingAmount){
        log.info("export customer data process started");
        boolean isSuccessful=false;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            String[] columns = {"NAME", "PHONE NUMBER", "EMAIL", "ADDRESS", "PENDING AMOUNT"};
            String[] pendingAmountSheetHeader = {"NAME","PENDING AMOUNT"};
            Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

            // Create a Sheet
            Sheet sheet = workbook.createSheet("CUSTOMER");

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);


            // Create Other rows and cells with customer data
            int rowNum = 1;

                            //check if complete list required or only pending amount
            if(!isShowOnlyPendingAmount) {
                // Create cells
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerCellStyle);
                }
                for (CustomerList customerListDto : customerList) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0)
                            .setCellValue(customerListDto.getName());

                    row.createCell(1)
                            .setCellValue(customerListDto.getMobileNumber());

                    row.createCell(2)
                            .setCellValue(customerListDto.getAddress());

                    row.createCell(3)
                            .setCellValue(customerListDto.getEmail());
                    row.createCell(4)
                            .setCellValue(customerListDto.getPending());
                }
            }

            else{           //only pending amount required
                for (int i = 0; i < pendingAmountSheetHeader.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(pendingAmountSheetHeader[i]);
                    cell.setCellStyle(headerCellStyle);
                }
                for (CustomerList customerListDto : customerList) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0)
                            .setCellValue(customerListDto.getName());

                    row.createCell(1)
                            .setCellValue(customerListDto.getPending());
                }
            }
                      // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

                             // Write the output to a file
                   // FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
            isSuccessful=true;
        }
        catch (Exception e){
            log.info("Exception occured while exporting customer data to file: "+e.getMessage());
        }
        log.info("export customer data process completed");
        return isSuccessful;
    }
}
