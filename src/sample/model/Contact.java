package sample.model;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * get contact id
     * @return
     */
    public int getContactID() { return contactID; }

    /**
     * get contact name
     * @return
     */

    public String getContactName() { return contactName; }

    /**
     * get email
     * @return
     */

    public String getEmail() {return email; }

    /**
     * create new contact
     * @param contactID
     * @param contactName
     * @param email
     */

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
