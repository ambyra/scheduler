package sample.model;

import java.time.ZonedDateTime;

public class Country {
    private int countryID;
    private String country;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * get country id
     * @return
     */
    public int getCountryID() {return countryID; }

    /**
     * get country
     * @return
     */
    public String getCountry() {return country; }

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
     * create new country
     * @param countryID
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */

    public Country(int countryID, String country, ZonedDateTime createDate,
                   String createdBy, ZonedDateTime lastUpdate, String lastUpdatedBy){
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
}

//SELECT `countries`.`Country_ID`,
//        `countries`.`Country`,
//        `countries`.`Create_Date`,
//        `countries`.`Created_By`,
//        `countries`.`Last_Update`,
//        `countries`.`Last_Updated_By`
//        FROM `client_schedule`.`countries`;
