package service;


import db.BaseConnection;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.h2.engine.Session;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

public class InventoryReader extends BaseConnection {
    private static Logger log=Logger.getLogger(InventoryReader.class.getName());

    public static boolean uploadStock(File file){
        boolean isSuccessful = false;
        Connection connection=null;
       // Statement stmt = null;
        //PreparedStatement statement=null;
        Statement connStatement=null;
        boolean isValidRow=false;
        String truncateStockTableQuery = "DELETE FROM `stocks`;";
        String resetIdValueQuery = "ALTER TABLE stocks ALTER COLUMN stock_id RESTART WITH 1";
        String insertQuery = "INSERT INTO `stocks` (name, category,stockLeft,originalPrice, suppliers, sellingPrice) " +
                "VALUES ";
        try {
            connection=getDBConnection();
            connection.setAutoCommit(false);
            connStatement=connection.createStatement();
            connStatement.execute("SAVEPOINT Half_Done; ");


             connStatement.execute(truncateStockTableQuery);
             connStatement.execute(resetIdValueQuery);

          //  statement=connection.prepareStatement(insertQuery);

            Workbook workbook = new XSSFWorkbook(file);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter=new DataFormatter();
            String stockCategory="";
            String stockSupplier="";
            String stockName="";
            Integer stockOriginalPrice=0;
            Integer stockSellingPrice=0;
            Integer stockItemLeftPrice=0;

            for (Row row: datatypeSheet) {

                for(Cell cell: row) {
                    if (cell.getRowIndex() == 0) {
                        //first row usually contains headings. no need to save it
                        break;
                    }
                    if(cell.getColumnIndex()==0 && dataFormatter.formatCellValue(cell).equals("")){
                        log.warning("empty or invalid row found");
                        isValidRow=false;
                        break;
                    }
                    else{
                        isValidRow=true;
                    }
                    switch (cell.getColumnIndex()){

                        case 0://stock name
                            stockName=dataFormatter.formatCellValue(cell);
                            break;

                        case 1://suppliers
                            stockSupplier=dataFormatter.formatCellValue(cell);
                            break;

                        case 2://category
                            stockCategory=dataFormatter.formatCellValue(cell);
                            break;

                        case 3://originalPrice
                            try {
                                stockOriginalPrice = (dataFormatter.formatCellValue(cell) != null &&dataFormatter.formatCellValue(cell)!="") ?
                                        Integer.parseInt(dataFormatter.formatCellValue(cell)) : 0;
                            }
                            catch (NumberFormatException e){
                                log.warning("Number not in correct Format");
                                stockOriginalPrice=0;
                            }
                            break;

                        case 4://selling price
                            try {
                                stockSellingPrice = (dataFormatter.formatCellValue(cell) != null &&dataFormatter.formatCellValue(cell)!="") ?
                                        Integer.parseInt(dataFormatter.formatCellValue(cell)) : 0;
                            }
                            catch (NumberFormatException e){
                                log.warning("Number not in correct Format");
                                stockSellingPrice=0;
                            }
                            break;

                        case 5://stockLeft
                            try {
                                stockItemLeftPrice = (dataFormatter.formatCellValue(cell) != null &&dataFormatter.formatCellValue(cell)!="") ?
                                        Integer.parseInt(dataFormatter.formatCellValue(cell)) : 0;
                            }
                            catch (NumberFormatException e){
                                log.warning("Number not in correct Format");
                                stockItemLeftPrice=0;
                            }
                            break;
                        default:
                            log.info("invalid cell index found at"+row.getRowNum());
                    }
                }
                if(isValidRow) {
                    insertQuery+= "('"+stockName+"', '"+stockCategory+"', "+stockItemLeftPrice+", "+stockOriginalPrice+", '"+stockSupplier+"', "+stockSellingPrice+"),";
//                    statement.setString(1, stockName);
//                    statement.setString(2, stockCategory);
//                    statement.setInt(3, stockItemLeftPrice);
//                    statement.setInt(4, stockOriginalPrice);
//                    statement.setString(5, stockSupplier);
//                    statement.setInt(6, stockSellingPrice);
//                    statement.addBatch();
                }
            }
          insertQuery=  insertQuery.substring(0,insertQuery.length()-1);
            log.info(insertQuery);
            connStatement.execute(insertQuery);
            connection.commit();
            isSuccessful = true;

        }
        catch (Exception e){
            log.info("Exception occurred while uploading stock file");
            e.printStackTrace();
            log.info("reverting back to previous savepoint");
            try {
                connStatement.execute("ROLLBACK TO SAVEPOINT Half_Done");
              }
            catch (SQLException e1){
                 log.info("error while restoring savePoint"+e1.getMessage());
            }
        }
        finally {
            sqlCleanup(connection,connStatement,null);
        }
        return isSuccessful;
    }

}
