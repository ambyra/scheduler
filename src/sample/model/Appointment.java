package sample.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    private LocalDateTime startLocal;
    private LocalDateTime endLocal;

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public LocalDateTime getStartLocal(){return startLocal;}
    public LocalDateTime getEndLocal(){return endLocal;}

    /**
     * get time converted to EST
     * @return
     */


    public ZonedDateTime getStartEST(){
        //ZonedDateTime zdtUTC = start.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtEST = start.withZoneSameInstant(ZoneId.of("US/Eastern"));
        return zdtEST;
    }

    /**
     * get time converted to UTC
     * @return
     */

    public ZonedDateTime getStartUTC(){
        ZonedDateTime zdtUTC = start.withZoneSameInstant(ZoneId.of("UTC"));
        return zdtUTC;
    }

    /**
     * get time converted to local time
     * @return
     */

    public ZonedDateTime getStartSystem(){
        //ZonedDateTime zdtUTC = start.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtSystem = start.withZoneSameInstant(ZoneId.systemDefault());
        return zdtSystem;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    /**
     * get time converted to EST
     * @return
     */

    public ZonedDateTime getEndEST(){
        ZonedDateTime zdtEST = end.withZoneSameInstant(ZoneId.of("US/Eastern"));
        return zdtEST;
    }

    /**
     * get time converted to UTC
     * @return
     */

    public ZonedDateTime getEndUTC(){
        ZonedDateTime zdtUTC = end.withZoneSameInstant(ZoneId.of("UTC"));
        return zdtUTC;
    }

    /**
     * get time at local
     * @return
     */

    public ZonedDateTime getEndSystem(){
        ZonedDateTime zdtSystem = end.withZoneSameInstant(ZoneId.systemDefault());
        return zdtSystem;
    }

    /**
     * set start and local time variables
     * @param start
     */

    public void setStart(ZonedDateTime start) {
        this.start = start;
        this.startLocal = this.start.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * set end and local time variables
     * @param end
     */

    public void setEnd(ZonedDateTime end) {
        this.end = end;
        this.endLocal = this.end.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public ZonedDateTime getCreateDate() {return createDate;}

    /**
     * get create date converted to utc
     * @return
     */
    public ZonedDateTime getCreateDateUTC(){return createDate.withZoneSameInstant(ZoneId.of("UTC"));}

    public String getCreatedBy() {return createdBy;}

    public ZonedDateTime getLastUpdate() {return lastUpdate;}

    /**
     * get last update converted to UTC
     * @return
     */
    public ZonedDateTime getLastUpdateUTC(){return lastUpdate.withZoneSameInstant(ZoneId.of("UTC"));}

    public String getLastUpdatedBy() {return lastUpdatedBy; }
    public int getCustomerID() {return customerID; }
    public int getUserID() {return userID;}
    public int getContactID() {return contactID; }

    public Appointment(int appointmentID, String title, String description, String location,
                       String type, ZonedDateTime start, ZonedDateTime end, ZonedDateTime createDate,
                       String createdBy, ZonedDateTime lastUpdate, String lastUpdatedBy,
                       int customerID, int userID, int contactID){
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

        this.startLocal = this.start.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        this.endLocal = this.end.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}

//Appointment_ID INT(10) (PK)
//Title VARCHAR(50)
//Description VARCHAR(50)
// Location VARCHAR(50)
//Type VARCHAR(50)
//Start DATETIME
//End DATETIME
//Create_Date DATETIME
//Created_By VARCHAR(50)
//Last_Update TIMESTAMP
//Last_Updated_By VARCHAR(50)
//Customer_ID INT(10) (FK)
//User_ID INT(10) (FK)
// Contact_ID INT(10) (FK)