package sample.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private ZonedDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdatedBy;
    private String division;
    private int divisionID;

    /**
     * get customer id
     * @return
     */

    public int getCustomerID() {
        return customerID;
    }

    /**
     * get customer name
     * @return
     */


    public String getCustomerName() {
        return customerName;
    }

    /**
     * get customer address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * get postal code
     * @return
     */
    public String getPostalCode() { return postalCode;}

    /**
     * get phone number
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * get creation date
     * @return
     */

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    /**
     * get created by
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * get last update
     * @return
     */
    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * get last updated by
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * get division id
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * set division id
     * @param division
     */

    public void setDivision(String division){this.division = division;}
    public String getDivision(){return division;}

    /**
     * get create date converted to utc
     * @return
     */
    public ZonedDateTime getCreateDateUTC() {return createDate.withZoneSameInstant(ZoneId.of("UTC"));}

    /**
     * get last update converted to UTC
     * @return
     */
    public ZonedDateTime getLastUpdateUTC() {return lastUpdate.withZoneSameInstant(ZoneId.of("UTC"));}

    /**
     * create new customer
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     */

    public Customer(int customerID, String customerName, String address, String postalCode,
                    String phone, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate,
                    String lastUpdatedBy, int divisionID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }
}
//
//SELECT `customers`.`Customer_ID`,
//        `customers`.`Customer_Name`,
//        `customers`.`Address`,
//        `customers`.`Postal_Code`,
//        `customers`.`Phone`,
//        `customers`.`Create_Date`,
//        `customers`.`Created_By`,
//        `customers`.`Last_Update`,
//        `customers`.`Last_Updated_By`,
//        `customers`.`Division_ID`
//        FROM `client_schedule`.`customers`;
