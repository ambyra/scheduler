package sample.model;

import java.time.LocalDateTime;

public class User {
    private int userID;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public User(int userID, String userName, String password, LocalDateTime createDate,
                String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
//
//SELECT `users`.`User_ID`,
//        `users`.`User_Name`,
//        `users`.`Password`,
//        `users`.`Create_Date`,
//        `users`.`Created_By`,
//        `users`.`Last_Update`,
//        `users`.`Last_Updated_By`
//        FROM `client_schedule`.`users`;
