package application.mobileforms;

/**
 * Created by Inas on 06-Feb-18.
 */

public class UserAddInformation {

    private String Gender;
    private String birthdate;
    private String Status;
    private String Username;
    private String Email;
    private String Phone;
    private String History;

    public UserAddInformation(String Username, String email, String phone,String Gender,String BirthDate,String Status) {
        this.Username = Username;
        this.Email = email;
        this.Phone = phone;
        this.Gender=Gender;
        this.birthdate=BirthDate;
        this.Status=Status;

    }
    public UserAddInformation()
    {

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getemail() {
        return Email;
    }

    public void setemail(String email) {
        this.Email = email;
    }

    public String getphone() {
        return Phone;
    }
    public String getGender(){
        return Gender;
    }
    public String getStatus(){
        return Status;
    }
    public String getbirthdate(){
        return birthdate;
    }
    public String getHistory(){
        return History;
    }

    public void setphone(String phone) {
        this.Phone = phone;
    }
    public void setGender(String gender) {
        this.Gender = gender;
    }
    public void setbirthdate(String date) {
        this.birthdate = date;
    }
    public void setStatus(String status) {
        this.Status = status;
    }
    public void setHistory(String history) {
        this.History = history;
    }

}
