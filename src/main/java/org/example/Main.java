package org.example;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String PathName="src/main/java/org/example/student-data.txt";
        String[][] Studentsarray = Readstudents(PathName);
        PathName="src/main/java/org/example/coursedata.xml";
        String[][] coursessarray = Readcourses(PathName);

        int selectedstudentx=HomePage(Studentsarray,coursessarray);
        printStudents(Studentsarray,selectedstudentx);

        Object[] enrolledcourses =Jsonreader(selectedstudentx);

    }//end main





    //text to string array & then create csv
    static  String[][] Readstudents(String PathName) throws IOException {

        String StudentData = "";
        try {
            File SelectedFile=new File(PathName);
            Scanner scanner = new Scanner(SelectedFile);
            while (scanner.hasNextLine())
            {
                StudentData = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        StudentData = StudentData.replace("#", ",");
        StudentData = StudentData.replace("$", "\n"+"$");
        String[] SplitedRows=StudentData.split("\n");
        SplitedRows[0] ="id, " +SplitedRows[0];
        String[][] Studentsarray = new String[SplitedRows.length-1][7];
        //Studentsarray[0] = (SplitedRows[0].split(","));

        for(int i =1 ;i<SplitedRows.length;i++)
        {
            SplitedRows[i] = SplitedRows[i].replace("$", String.valueOf(i)+",");//add id
            String[] OneEntry =SplitedRows[i].split(",");
            Studentsarray[i-1][0]= OneEntry[0];
            Studentsarray[i-1][1]= OneEntry[1];
            Studentsarray[i-1][2]= OneEntry[2];
            Studentsarray[i-1][3]= OneEntry[3];

            if(OneEntry.length==7){
                Studentsarray[i-1][4]= OneEntry[4];
                Studentsarray[i-1][5]= OneEntry[5];
                Studentsarray[i-1][6]= OneEntry[6];
            }
            else {
                Studentsarray[i-1][4]= OneEntry[4]+","+OneEntry[5];
                Studentsarray[i-1][5]= OneEntry[6];
                Studentsarray[i-1][6]= OneEntry[7];
            }
            //System.out.println(SplitedRows[i]);
        }

        WriteCSV(Studentsarray,"Students");
        return Studentsarray;
    }
    //xml to string array & then create csv
    static  String[][] Readcourses(String PathName) throws IOException {
        String CourseData="";
        try {
            File SelectedFile=new File(PathName);
            Scanner scanner = new Scanner(SelectedFile);
            while (scanner.hasNextLine())
            {
                CourseData = CourseData+scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }



        CourseData = CourseData.replace("<id>", "");
        CourseData = CourseData.replace("<CourseName>", "");
        CourseData = CourseData.replace("<Instructor>", "");
        CourseData = CourseData.replace("<CourseDuration>", "");
        CourseData = CourseData.replace("<CourseTime>", "");
        CourseData = CourseData.replace("<Location>", "");
        CourseData = CourseData.replace("<root>", "");
        CourseData = CourseData.replace("  <row>", "");
        CourseData = CourseData.replace("</root>", "");
        CourseData = CourseData.replace("  </row>", "");
        CourseData = CourseData.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");

        CourseData = CourseData.replace("</id>", ",");
        CourseData = CourseData.replace("</CourseName>", ",");
        CourseData = CourseData.replace("</Instructor>", ",");
        CourseData = CourseData.replace("</CourseDuration>", ",");
        CourseData = CourseData.replace("</CourseTime>", ",");
        CourseData = CourseData.replace("</Location>", "\n");
        CourseData = CourseData.replace("    ", "");

        String[] SplitedRows=CourseData.split("\n");
        String[][] coursessarray = new String[SplitedRows.length][6];

        for(int k =0 ;k<SplitedRows.length;k++)
        {
            String[] OneEntry =SplitedRows[k].split(",");

            coursessarray[k][0]= OneEntry[0];
            coursessarray[k][1]= OneEntry[1];
            coursessarray[k][2]= OneEntry[2]+", "+OneEntry[3];
            coursessarray[k][3]= OneEntry[4];
            coursessarray[k][4]= OneEntry[5];
            coursessarray[k][5]= OneEntry[6];

            //System.out.println(SplitedRows[k]);
        }
        WriteCSV(coursessarray,"Courses");
        return coursessarray;
    }
    //string array to csv
    static void WriteCSV(String[][] Data, String FileName) throws IOException {

        File csvFile = new File(FileName);
        FileWriter fileWriter = new FileWriter(csvFile);

        //write header line here if you need.

        for (String[] data : Data) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                line.append("\"");
                line.append(data[i].replaceAll("\"","\"\""));
                line.append("\"");
                if (i != data.length - 1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();

    }
    static void printStudents(String[][] Studentsarray,int id){
        ArrayList<Student> students = new ArrayList<>();
        students= CreateStudentsList(Studentsarray);
       if (id ==0)
       {
           System.out.println(students.get(1).Header());
           for (Student student : students) {
               System.out.println(student.toString());
           }
       }
        else
        {
            System.out.println( students.get(id-1).simpletoString());
        }
    }
    static void printCourses(String[][] coursessarray,int id){

        ArrayList<Course> courses = new ArrayList<>();
        courses= CreateCoursesList(coursessarray);
        if (id ==0)
        {
            System.out.println( courses.get(1).Header());
        for (Course course : courses) {
            System.out.println(course.toString());
        }
        }
        else
        {
            System.out.println( courses.get(id-1).toString());
        }
    }
    //string array to student list
    public static ArrayList<Student> CreateStudentsList( String[][] studentsarray) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i=0;i<studentsarray.length;i++) {
            Student student = new Student();
            student.setId(studentsarray[i][0]);
            student.setName(studentsarray[i][1]);
            student.setGrade(studentsarray[i][2]);
            student.setEmail(studentsarray[i][3]);
            student.setAddress(studentsarray[i][4]);
            student.setRegion(studentsarray[i][5]);
            student.setCountry(studentsarray[i][6]);
            students.add(student);
        }
        return students;
    }
    //string array to course list
    public static ArrayList<Course> CreateCoursesList( String[][]coursesarray) {
        ArrayList<Course> courses = new ArrayList<>();
        for (int i=0;i<coursesarray.length;i++) {
            Course course = new Course();
            course.setId(coursesarray[i][0]);
            course.setCourseName(coursesarray[i][1]);
            course.setInstructor(coursesarray[i][2]);
            course.setCourseduration(coursesarray[i][3]);
            course.setCoursetime(coursesarray[i][4]);
            course.setLocation(coursesarray[i][5]);
            courses.add(course);
        }
        return courses;
    }

    public static Object[] Jsonreader( int id ) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("src/main/java/org/example/Student course details.json"));
        for (Object o : a) {
            JSONObject person = (JSONObject) o;
            JSONArray studentcourses = (JSONArray) person.get(Integer.toString(id));
        if (studentcourses==null){
            break;
            }

            for (Object c : studentcourses) {
                //System.out.println(c + "");
            }
            return( studentcourses.toArray());

        }

return (null);

    }

    public static int getstudentinput(){
        Scanner myObj = new Scanner(System.in);
        // validation required

        return (myObj.nextInt());

    }
    public static int HomePage(String[][] Studentsarray, String[][] coursessarray){
        String seperator="====================================================================================\n";
        System.out.println("Welcome to LMS\n"+"created by {TarekAhmed_21.Jan.2023}\n"+seperator+"Home page\n"+seperator);
        System.out.println("Student List:");
        printStudents(Studentsarray,0);
        System.out.println(seperator+"Please select the required student:");
        return (getstudentinput());
    }

}