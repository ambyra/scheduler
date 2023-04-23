package sample.model;

import java.time.LocalDateTime;

public class Division {
    private int divisionID;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;
}

//SELECT `first_level_divisions`.`Division_ID`,
//        `first_level_divisions`.`Division`,
//        `first_level_divisions`.`Create_Date`,
//        `first_level_divisions`.`Created_By`,
//        `first_level_divisions`.`Last_Update`,
//        `first_level_divisions`.`Last_Updated_By`,
//        `first_level_divisions`.`Country_ID`
//        FROM `client_schedule`.`first_level_divisions`;
