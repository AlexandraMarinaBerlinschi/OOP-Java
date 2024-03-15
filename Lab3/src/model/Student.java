package model;
import model.Person;

public class Student extends Person {
    private static String student_number;

    private float average_mark;

    private int clasa;

    public Student(String Name, String Phone_number, String Email_Address, String student_number, float average_mark, int clasa) {
        super(Name, Phone_number, Email_Address);
        this.student_number = student_number;
        this.average_mark = average_mark;
        this.clasa = clasa;
    }


    public static String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public float getAverage_mark() {
        return average_mark;
    }

    public void setAverage_mark(float average_mark) {
        this.average_mark = average_mark;
    }

    public int getClasa() {
        return clasa;
    }

    public void setClasa(int clasa) {
        this.clasa = clasa;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
                "Phone number: " + getPhone_Number() + "\n" +
                "Email: " + getEmail_Address() + "\n" +
                "Student number: " + student_number + "\n" +
                "Average mark: " + average_mark + "\n" +
                "Clasa: " + clasa;
    }

}


