package in.co.iodev.formykerala.Models;

public class DataModel {
    String number_of_people;
    String lattitude;
    String longitude;
    String Locality;
    String District;
    String Battery_percentage;
    String TimeIndex;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    String PhoneNumber;
    String OTP;
    String PIN;
    String Name,Address,Taluk;

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTaluk() {
        return Taluk;
    }

    public void setTaluk(String taluk) {
        Taluk = taluk;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }




    public String getAlternateContactNumber() {
        return AlternateContactNumber;
    }

    public void setAlternateContactNumber(String alternateContactNumber) {
        AlternateContactNumber = alternateContactNumber;
    }

    String AlternateContactNumber;

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    String ContactNumber;

    public String getTimeIndex() {
        return TimeIndex;
    }

    public String getBattery_percentage() {
        return Battery_percentage;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocality (){
        return Locality;
    }

    public String getDistrict() {
        return District;
    }

    public String getNumber_of_people() {
        return number_of_people;
    }

    public void setBattery_percentage(String battery_percentage) {
        Battery_percentage = battery_percentage;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setNumber_of_people(String number_of_people) {
        this.number_of_people = number_of_people;
    }

    public void setTimeIndex(String timeIndex) {
        TimeIndex = timeIndex;
    }
}
