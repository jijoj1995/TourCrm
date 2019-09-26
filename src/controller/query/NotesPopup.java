package controller.query;

import dto.CoreLead;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class NotesPopup implements Initializable {
    private CoreLead coreLeadDto;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void initializeCoreLeadObject(CoreLead coreLeadDto){
        this.coreLeadDto=coreLeadDto;
    }

}
