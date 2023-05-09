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
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

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

    public LocalDateTime getStart() {
        return start;
    }

    public ZonedDateTime getStartEST(){
        ZonedDateTime zdtUTC = start.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtEST = zdtUTC.withZoneSameInstant(ZoneId.of("US/Eastern"));
        return zdtEST;
    }

    public ZonedDateTime getStartUTC(){
        ZonedDateTime zdtUTC = start.atZone(ZoneId.of("UTC"));
        return zdtUTC;
    }

    public ZonedDateTime getStartSystem(){
        ZonedDateTime zdtUTC = start.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtSystem = zdtUTC.withZoneSameInstant(ZoneId.systemDefault());
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

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
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

    public LocalDateTime getEnd() {
        return end;
    }

    public ZonedDateTime getEndEST(){
        ZonedDateTime zdtUTC = end.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtEST = zdtUTC.withZoneSameInstant(ZoneId.of("US/Eastern"));
        return zdtEST;
    }

    public ZonedDateTime getEndUTC(){
        ZonedDateTime zdtUTC = end.atZone(ZoneId.of("UTC"));
        return zdtUTC;
    }

    public ZonedDateTime getEndSystem(){
        ZonedDateTime zdtUTC = end.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtSystem = zdtUTC.withZoneSameInstant(ZoneId.systemDefault());
        return zdtSystem;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setStart(ZonedDateTime start){
        ZonedDateTime startUTC = start.withZoneSameInstant(ZoneId.of("UTC"));
        this.start = startUTC.toLocalDateTime();
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setEnd(ZonedDateTime end){
        ZonedDateTime startUTC = end.withZoneSameInstant(ZoneId.of("UTC"));
        this.end = startUTC.toLocalDateTime();
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

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public Appointment(int appointmentID, String title, String description, String location,
                       String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate,
                       String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID){
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