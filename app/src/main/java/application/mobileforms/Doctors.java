package application.mobileforms;

/**
 * Created by Inas on 10-Jul-18.
 */

public class Doctors {
    private String name;
    private String description;
    private String image;
    private String Address;
    private float rate;
    private String Available_time;

//    public Doctors(String name, String description, String image, String address, String rate, String available_time) {
//        this.name = name;
//        this.description = description;
//        this.image = image;
//        this.Address = address;
//        this.rate = rate;
//        this.Available_time = available_time;
//    }


    public Doctors(String name, String description, String image, String address, float rate) {
        this.name = name;
        this.description = description;
        this.image = image;
        Address = address;
        this.rate = rate;
    }

    public Doctors (){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getAvailable_time() {
        return Available_time;
    }

    public void setAvailable_time(String available_time) {
        Available_time = available_time;
    }
}