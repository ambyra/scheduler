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

    /**
     * get division id
     * @return
     */
    public int getDivisionID() {return divisionID;}

    /**
     * get division name
     * @return
     */
    public String getDivision() {return division; }

    /**
     * get create date
     * @return
     */
    public ZonedDateTime getCreateDate() {return createDate; }

    /**
     * get created by
     * @return
     */
    public String getCreatedBy() {return createdBy; }

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
     * get country id
     * @return
     */
    public int getCountryID() { return countryID; }

    /**
     * create new division
     * @param divisionID
     * @param division
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param countryID
     */

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
