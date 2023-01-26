package org.example;

public class Student {

    private String id;
    private String name;
    private String Grade;
    private String email;
    private String address;
    private String region;
    private String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String toString()
    {
        return  String.format("%-4s", id)+
                String.format("%-20s", name)+
                String.format("%-6s", Grade)+
                String.format("%-40s", email)+
                String.format("%-40s", address)+
                String.format("%-30s", region)+
                String.format("%-20s", country);

    }
    public String simpletoString()
    {
               return(
               "====================================================================================\n"+
                 "Student Details page\n"+
               "====================================================================================\n"+
                String.format("%-26s","name: "+ name)+
                String.format("%-13s", "Grade: "+Grade)+
                String.format("%-47s", "email: "+ email)+
                "\n ------------------------------------------------------------------------------------\n");
    }
    public String Header()
    {
        return  String.format("%-4s", "id")+
                String.format("%-20s", "name")+
                String.format("%-6s", "Grade")+
                String.format("%-40s", "email")+
                String.format("%-40s", "address")+
                String.format("%-30s", "region")+
                String.format("%-20s", "country");

    }


}
