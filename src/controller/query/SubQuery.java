package controller.query;

import com.jfoenix.controls.JFXTextField;
import dto.CoreLead;
import dto.CoreLeadCommunication;
import dto.CoreLeadCommunicationEntity;
import dto.CoreLeadEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubQuery implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane queryTabs;
    @FXML
    private JFXTextField hotelDestination,hotelCheckInDate,hotelCheckOutDate,hotelCurrencyCode,hotelCategory,hotelNumberOfNights,hotelNumberOfAdult,
            hotelNumberOfChild,hotelNumberOfInfant,hotelTotalPax,hotelRoomTariff,hotelExtraBed,hotelTotalPrice,hotelPlan,hotelStatus,
            holidaysFrom,holidaysTo,holidaysDepartureDate,holidaysReturnDate,holidaysCurrencyCode,holidaysHotelCategory,holidaysNumberOfNights,holidaysNumberOfAdult,
            holidaysNumberOfChild,holidaysTotalInfant,holidaysAdultFare,holidaysChildFare,holidaysInfantFare,holidaysTotalPrice,holidaysTravelType,holidaysStatus,
            railDepartureCity,railArrivalCity,railDateOfDeparture,railTrainNumber,railNumberOfAdult,railNumberOfChild,railNumberOfInfant,railTotalPax,railAdultFare,
            railChildFare,railTotalFare,railClassOfTravel,railStatus;
    private CoreLead coreLeadDto;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDefaultLayout();
    }

    private void initializeDefaultLayout() {
        mainPane.setPrefWidth(Main.WIDTH - Main.SIDE_BAR_WIDTH);
        mainPane.setPrefHeight(Main.HEIGHT - 30);
        double paneWidth = (Main.WIDTH - Main.SIDE_BAR_WIDTH) / 4 - 20;
        queryTabs.setTabMinWidth(paneWidth);
        queryTabs.setTabMaxWidth(paneWidth);
    }

    @FXML
    private void showMainQueryPage() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/query/mainQuery.fxml"));
        mainPane.getChildren().setAll(root);
    }

    @FXML
    private void saveSubQueryData(){
      //  add save method and go back to mainquery Page
        if(coreLeadDto ==null){
            coreLeadDto =new CoreLead();
            coreLeadDto.setCoreLeadCommunication(new CoreLeadCommunication());
        }
    }

    public void initializeCoreLeadObject(CoreLead coreLead){
        this.coreLeadDto = coreLead;
    }


}
