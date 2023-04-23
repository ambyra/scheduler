package sample.model;

import java.time.LocalDateTime;

public class Country {
    private int countryID;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public int getCountryID() {
        return countryID;
    }

    public String getCountry() {
        return country;
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

    public Country(int countryID, String country, LocalDateTime createDate,
                   String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy){
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
