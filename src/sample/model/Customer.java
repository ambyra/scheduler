package sample.model;

import java.time.LocalDateTime;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
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

    public int getDivisionID() {
        return divisionID;
    }

    public Customer(int customerID, String customerName, String address, String postalCode,
                    String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
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
    };
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
