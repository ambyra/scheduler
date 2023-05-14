package sample.model;

import java.time.ZonedDateTime;

public class Division {
    private int divisionID;
    private String division;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public Division(int divisionID, String division, ZonedDateTime createDate, String createdBy,
                    ZonedDateTime lastUpdate, String lastUpdatedBy, int countryID){
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }
}

//SELECT `first_level_divisions`.`Division_ID`,
//        `first_level_divisions`.`Division`,
//        `first_level_divisions`.`Create_Date`,
//        `first_level_divisions`.`Created_By`,
//        `first_level_divisions`.`Last_Update`,
//        `first_level_divisions`.`Last_Updated_By`,
//        `first_level_divisions`.`Country_ID`
//        FROM `client_schedule`.`first_level_divisions`;
