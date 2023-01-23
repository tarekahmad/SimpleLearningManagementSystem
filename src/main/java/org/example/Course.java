package org.example;

public class Course {
    private String id;
    private String CourseName;
    private String Instructor;
    private String Courseduration;
    private String Coursetime;
    private String Location;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getInstructor() {
        return Instructor;
    }

    public void setInstructor(String instructor) {
        Instructor = instructor;
    }

    public String getCourseduration() {
        return Courseduration;
    }

    public void setCourseduration(String courseduration) {
        Courseduration = courseduration;
    }

    public String getCoursetime() {
        return Coursetime;
    }

    public void setCoursetime(String coursetime) {
        Coursetime = coursetime;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String toString()
    {
        return  String.format("%-4s", id)+
                String.format("%-40s", CourseName)+
                String.format("%-30s", Instructor)+
                String.format("%-15s", Courseduration)+
                String.format("%-15s", Coursetime)+
                String.format("%-15s", Location);
    }



    public String Header()
    {
        return  String.format("%-4s", "id")+
                String.format("%-40s", "CourseName")+
                String.format("%-30s", "Instructor")+
                String.format("%-15s", "Courseduration")+
                String.format("%-15s", "Coursetime")+
                String.format("%-15s", "Location");

    }


}
