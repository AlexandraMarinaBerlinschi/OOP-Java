package service;

import model.Profesor;
import model.Student;
import model.Person;

import java.util.ArrayList;
import java.util.List;

public class StorageService {

    private List<Student> students = new ArrayList<>();
    private List<Profesor> profesors = new ArrayList<>();

    public boolean addStudent(Student student) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(student.getName())) {
                return false;
            }
        }
        students.add(student);
        return true;
    }

    public boolean addProfesor(Profesor profesor) {
        for (Profesor p : profesors) {
            if (p.getName().equalsIgnoreCase(profesor.getName())) {
                return false;
            }
        }
        profesors.add(profesor);
        return true;
    }

    public Person findPerson(String Name) {
        for (Student student : students) {
            if (student.getName().equals(Name)) {
                return student;
            }
        }
        for (Profesor profesor : profesors) {
            if (profesor.getName().equals(Name)) {
                return profesor;
            }
        }
        return null;
    }

    public boolean updatePerson(Person person, String newPhoneNumber, String newEmail) {
        if (person != null) {
            person.setPhone_number(newPhoneNumber);
            person.setEmail_Address(newEmail);
            return true;
        }
        return false;
    }

    public boolean deletePerson(Person person) {
        if (person != null) {
            if (person instanceof Student) {
                students.remove(person);
                return true;
            } else if (person instanceof Profesor) {
                profesors.remove(person);
                return true;
            }
        }
        return false;
    }
}
