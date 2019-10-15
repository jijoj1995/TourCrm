/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

/**
 *
 * @author Cool IT Help
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    TableView tableview;

    ObservableList<Person> data;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    ObservableList<Product> dummyList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");
        TableColumn actionCol = new TableColumn("Remarks");
        TableColumn <Person,Person> delete = new TableColumn<>("Action");
        TableColumn<Person,Person> comboboxc = new TableColumn<>("Source");
        tableview.getColumns().addAll(firstNameCol, lastNameCol, emailCol, actionCol,delete,comboboxc);
        dummyList=FXCollections.observableArrayList(
        new Product("Everready LED buld 12W","1"),
        new Product("Wepro LED buld 12W","2"),
        new Product("philpse LED buld 12W","3"),
        new Product("syska LED buld 9W","4"),
        new Product("Wepro LED buld 22W","5"),
        new Product("Everready LED buld 9W","6"),
        new Product("Everready LED buld 22W","7"),
        new Product("syska LED buld 22W","8")
        );
        data = FXCollections.observableArrayList(
                new Person("Jacob", "Smith", "jacob.smith@example.com", ""),
                new Person("Isabella", "Johnson", "isabella.johnson@example.com", ""),
                new Person("Ethan", "Williams", "ethan.williams@example.com", ""),
                new Person("Emma", "Jones", "emma.jones@example.com", ""),
                new Person("Michael", "Brown", "michael.brown@example.com", ""),
                new Person("Michael", "Brown", "michael.brown@example.com", ""),
                new Person("Michael", "Brown", "michael.brown@example.com", ""),
                new Person("Michael", "Brown", "michael.brown@example.com", "")
        );

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person,String>("firstName")
        );
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person,String>("lastName")
        );
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person,String>("email")
        );

        actionCol.setCellValueFactory(
                new PropertyValueFactory<Person,String>("remark")
        );
        delete.setCellValueFactory(
                new PropertyValueFactory<Person,Person>("button")
        );

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> new TableCell<Person, Person>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                if (person == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event ->{

                    data.remove(person);
                    System.out.println(person.getFirstName());
                });
            }
        });

        comboboxc.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        comboboxc.setCellFactory(param -> {

           return new TableCell<Person, Person>() {
                  ComboBox<Person> comboBox = new ComboBox((ObservableList) dummyList);


                protected void updateItem(Person person, boolean empty) {
                    super.updateItem(person, empty);
                    if (person == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(comboBox);

                    new ComboBoxAutoComplete<Person>(comboBox);
                    comboBox.setOnAction(event -> {
                        System.out.println(person);
                        System.out.println(comboBox.getSelectionModel().getSelectedItem().getCombo().getId());
                    });
                }
            };
        });
        tableview.setItems(data);
    }


}
