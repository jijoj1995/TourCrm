/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private  SimpleStringProperty firstName;
    private  SimpleStringProperty lastName;
    private  SimpleStringProperty email;

    public Product getCombo() {
        return combo.get();
    }

    public ObjectProperty<Product> comboProperty() {
        return this.combo;
    }

    public void setCombo(Product combo) {
        this.combo.set(combo);
    }

    private ObjectProperty <Product> combo;
    public String getOption() {
        return option.get();
    }

    public SimpleStringProperty optionProperty() {
        return option;
    }

    public void setOption(String option) {
        this.option.set(option);
    }

    private  SimpleStringProperty option;

    Person(String fName, String lName, String email,String option) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
        this.option = new SimpleStringProperty(option);

    }
 
    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String fName) {
        firstName.set(fName);
    }
        
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String fName) {
        lastName.set(fName);
    }
    
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String fName) {
        email.set(fName);
    }

}
