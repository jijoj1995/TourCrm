package db;

import dto.ReportData;
import java.io.InputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.log4j.Logger;

public class ReportService extends JFrame{
    private Logger log=Logger.getLogger(ReportService.class.getName());
public  boolean printReport(ArrayList<ReportData> dataList,HashMap<String,Object> parameters,InputStream file) throws URISyntaxException{
    boolean isSuccessful=false;

    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
    try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,beanColDataSource);

        if(jasperPrint != null){
     JRViewer viewer = new JRViewer(jasperPrint);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 700);
        this.setVisible(true);
            isSuccessful=true;
        } }
    catch (JRException e) { e.printStackTrace(); 
       log.warn("Unable to print report "+e.getMessage());
    }

return isSuccessful;
}
}

