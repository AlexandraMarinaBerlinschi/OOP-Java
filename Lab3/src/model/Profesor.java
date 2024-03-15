package model;

public class Profesor extends Person {

    private String course;

    private int year;

    public Profesor(String Name, String Phone_number, String Email_Address, String course, int year)
    {
        super(Name, Phone_number, Email_Address);
        this.course = course;
        this.year = year;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
                "Phone number: " + getPhone_Number() + "\n" +
                "Email: " + getEmail_Address() + "\n" +
                "Course: " + course + "\n" +
                "Year: " + year;
    }
}
