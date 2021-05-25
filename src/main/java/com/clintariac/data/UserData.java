package com.clintariac.data;


public class UserData {

    public final String firstName;
    public final String lastName;
    public final String id;
    public final String email;
    public final String phone;

    public UserData(String firstName, String lastName, String id, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.phone = phone;
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "UserData [email=" + email + ", firstName=" + firstName + ", id=" + id
                + ", lastName="
                + lastName + ", phone=" + phone + "]";
    }
}
