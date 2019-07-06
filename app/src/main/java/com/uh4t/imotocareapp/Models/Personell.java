package com.uh4t.imotocareapp.Models;


import org.json.JSONException;
import org.json.JSONObject;

public class Personell {
    private String PersonId;
    private String GarageId;
    private String Fname;
    private String Lname;
    private String Contact;
    private String Type;
    private String ImageUri;
    private String GarageName;
    private String Address;
    private double Longitude;
    private double Lattitude;

    public Personell(String personId, String garageId, String fname, String lname, String contact,
                     String type, String imageUri, String garageName, String address, double longitude, double lattitude) {
        PersonId = personId;
        GarageId = garageId;
        Fname = fname;
        Lname = lname;
        Contact = contact;
        Type = type;
        ImageUri = imageUri;
        GarageName = garageName;
        Address = address;
        Longitude = longitude;
        Lattitude = lattitude;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void setPersonId(String personId) {
        PersonId = personId;
    }

    public String getGarageId() {
        return GarageId;
    }

    public void setGarageId(String garageId) {
        GarageId = garageId;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getGarageName() {
        return GarageName;
    }

    public void setGarageName(String garageName) {
        GarageName = garageName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLattitude() {
        return Lattitude;
    }

    public void setLattitude(double lattitude) {
        Lattitude = lattitude;
    }


    public Personell(JSONObject object) {
        try {
            this.PersonId = object.getString("PersonId");
            this.GarageId = object.getString("GarageId");
            this.Fname = object.getString("Fname");
            this.Lname = object.getString("Lname");
            this.Contact = object.getString("Contact");
            this.Type = object.getString("Type");
            this.ImageUri = object.getString("ImageUri");
            this.GarageName = object.getString("GarageName");
            this.Address = object.getString("Address");
            this.Longitude = object.getDouble("Longitude");
            this.Lattitude = object.getDouble("Lattitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
