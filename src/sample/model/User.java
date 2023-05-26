package sample.model;

import java.time.ZonedDateTime;

public class User {
    private int userID;
    private String userName;
    private String password;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * get user id
     * @return
     */

    public int getUserID() {return userID;}

    /**
     * get user name
     * @return
     */
    public String getUserName() {return userName;}

    /**
     * get password
     * @return
     */
    public String getPassword() {return password; }

    /**
     * get create date
     * @return
     */
    public ZonedDateTime getCreateDate() {return createDate;}

    /**
     * get created by
     * @return
     */
    public String getCreatedBy() {return createdBy;}

    /**
     * get last update
     * @return
     */
    public ZonedDateTime getLastUpdate() {return lastUpdate; }

    /**
     * get last updated by
     * @return
     */
    public String getLastUpdatedBy() {return lastUpdatedBy; }

    /**
     * create new user
     * @param userID
     * @param userName
     * @param password
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */

    public User(int userID, String userName, String password, ZonedDateTime createDate,
                String createdBy, ZonedDateTime lastUpdate, String lastUpdatedBy){
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
