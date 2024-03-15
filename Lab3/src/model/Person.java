package model;

public class Person {
    private static String Name;
    private String Phone_Number;
    private String Email_Address;

    public Person(String Name, String Phone_Number, String Email_Address) {
        this.Email_Address = Email_Address;
        this.Name = Name;
        this.Phone_Number = Phone_Number;
    }

    public static String getName() {
        return Name;
    }

    public void setName( String Name) {
        this.Name = Name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_number (String Phone_number) {
        this.Phone_Number = Phone_number;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String Email_Address) {
        this.Email_Address = Email_Address;
    }
}

