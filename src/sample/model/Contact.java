package sample.model;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public int getContactID() { return contactID; }

    public String getContactName() { return contactName; }

    public String getEmail() {return email; }

    public Contact(int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }
}

//SELECT `contacts`.`Contact_ID`,
//        `contacts`.`Contact_Name`,
//        `contacts`.`Email`
//        FROM `client_schedule`.`contacts`;
