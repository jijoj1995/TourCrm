package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "core_user" )
public class CoreUserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    @Column(name = "core_user_id")
    private Integer coreUserId;
    private String userName;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String emailAddress;

    public Integer getCoreUserId() {
        return coreUserId;
    }

    public void setCoreUserId(Integer coreUserId) {
        this.coreUserId = coreUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
