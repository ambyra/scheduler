package sample.dao;

import sample.model.Contact;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    public static ObservableList<Contact> getContacts(){
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        Connection connection = JDBC.getConnection();
        String query = "select * from client_schedule.contacts";

        try {
            JDBC.makePreparedStatement(query, connection);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email"));
                contacts.add(contact);
            }
        } catch (SQLException sqlException) {return null;}
        return contacts;
    }

    public static Contact getContact(String contactName){
        ObservableList<Contact> contacts= getContacts();
        if (contacts.isEmpty()){return null;}
        for (Contact contact: contacts) {
            if (contact.getContactName().compareToIgnoreCase(contactName)==0) {
                return contact;
            }
        }
        return null;
    }
}