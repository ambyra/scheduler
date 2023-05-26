package sample.model;

import java.time.ZonedDateTime;

public class Country {
    private int countryID;
    private String country;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;

    public int getCountryID() {return countryID; }
    public String getCountry() {return country; }
    public ZonedDateTime getCreateDate() {return createDate; }
    public String getCreatedBy() {return createdBy; }
    public ZonedDateTime getLastUpdate() {return lastUpdate; }
    public String getLastUpdatedBy() {return lastUpdatedBy; }

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
