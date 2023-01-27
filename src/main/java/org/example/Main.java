package org.example;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

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
        int skip=0;
        int selectedstudentx=-1;
        String[] enrolledcourses=null;
        do {
            if (skip>=0)
            {
            if (skip==0){selectedstudentx = HomePage(Studentsarray);}
            printStudents(Studentsarray, selectedstudentx);
            enrolledcourses = jsonreader(selectedstudentx);
            printCourses(coursessarray, false, enrolledcourses);
            }
            int OpenPage =MenuList();
            skip=0;
            switch (OpenPage)
            {
                case 1: skip=EnrollInaCourse(selectedstudentx, coursessarray); break;
                case 2: skip=UnenrollInaCourse(selectedstudentx, coursessarray,enrolledcourses); break;
                case 3: ReplaceCourse(selectedstudentx, coursessarray,enrolledcourses);break;
                case 4://Do nothing new iteration

            }
        }while (true);
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
    static void printCourses(String[][] coursessarray,boolean all, String[] idarray){

        ArrayList<Course> courses = new ArrayList<>();
        courses= CreateCoursesList(coursessarray);
        if (all)
        {
            System.out.println( courses.get(1).Header());
            for (Course course : courses)
            {
                System.out.println(course.toString());
            }
        }
        else
        {
            if (idarray==null)
            {
                System.out.println("Enrolled Courses\n"+"The student hasn't enrolled in any course yet.\n"+
                        "------------------------------------------------------------------------------------");
            }
            else
            {
                System.out.println("Enrolled Courses\n"+
                        "------------------------------------------------------------------------------------\n"+
                                    courses.get(1).Header());

                for(int i=0;i<idarray.length;i++)

               {
                   System.out.println( courses.get(Integer.valueOf(idarray[i])-1).toString());
               }

            }


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
    public static String[] jsonreader( int id ) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject allstudents = (JSONObject) parser.parse(new FileReader("src/main/java/org/example/Student course details.json"));
        JSONArray SelectedStudentCourses = (JSONArray) allstudents.get(Integer.toString(id));

        if (SelectedStudentCourses!=null){
            String  x= (allstudents.get(Integer.toString(id))).toString();
            x= x.replace("[","");
            x= x.replace("]","");
            String[] studentcoursesarray = x.split(",");
            return( studentcoursesarray);
        }
       return( null);
    }
    public static int ValidateIDInput(String[][] ObjectArray){
        int[] idarrays=new int[ObjectArray.length];
        for (int i=0;i<ObjectArray.length;i++)
        {
            idarrays[i]=Integer.valueOf(ObjectArray[i][0]);
        }
        String x=""   ;
        int y=-1;
        Scanner sc = new Scanner(System.in);
        if(sc.hasNext())
        {
            x= sc.nextLine();
        }
        try {
            y = Integer.parseInt(x);
            for (int id : idarrays)
            {
                if (id == y){return (y);}
            }
            System.out.println("ID not exist choose an id from the list");
            return (-1);

        } catch (NumberFormatException e) {

            switch (x) {
                case "b":
                    return (-2);
                default:
                    System.out.println("Enter valid input");
                    return (-1);
            }
        }
    }
    public static int HomePage(String[][] Studentsarray){
        String seperator="====================================================================================\n";
        String seperator2="------------------------------------------------------------------------------------\n";
        System.out.println("Welcome to LMS\n"+"created by {TarekAhmed_21.Jan.2023}\n"+seperator+"Home page\n"+seperator);
        System.out.println("Student List:");
        printStudents(Studentsarray,0);
        System.out.println(seperator2+"Please select the required student:");

        int id=-1;
         while(id==-1)
        {
            id = ValidateIDInput(Studentsarray);
        }
        return id;
}
    public static int  MenuList() {
        System.out.println("----------------------------------------------------------------------------------------------------\n" +
                "Please choose from the following:\n" +
                "a - Enroll in a course\n" +
                "d - Unenroll from an existing course\n" +
                "r - Replacing an existing course\n" +
                "b - Back to the main page\n" +
                "please select the required action:");
        Scanner sc = new Scanner(System.in);
        do
        {
            if (sc.hasNextLine())
            {
                switch ( sc.nextLine())
                {
                    case "a": return(1);
                    case "d": return(2);
                    case "r": return(3);
                    case "b": return(4);
                    default: System.out.println("Wrong Entry, please select the required action from the listed menu:");
                }
            }
        }    while (true) ;

    }

    public static int EnrollInaCourse(int StudentId,String[][] CourseArray) throws IOException, ParseException {
        System.out.println("Enrollment page\n"+"====================================================================================================");
        printCourses(CourseArray,true,null);
      do {
          System.out.println("----------------------------------------------------------------------------------------------------\n" +
                  "Please make one of the following:\n" +
                  "**Enter the course id that you want to enroll the student to\n" +
                  "** Or Enter b to go back to the home page\n" +
                  "Please select the required action:");
          int TheNewCourse=-1;
          while(TheNewCourse==-1)
          {
              TheNewCourse = ValidateIDInput(CourseArray);
          }
          if (TheNewCourse==-2){return 0;}


          String[] OldCoursesArray = jsonreader(StudentId);
          if (OldCoursesArray != null && OldCoursesArray.length >= 6) {
              System.out.println("cannot enroll the student in more courses");
          }
          else
          {
              JSONArray NewCoursesArray = new JSONArray();
              JSONParser parser = new JSONParser();
              JSONObject allstudents = (JSONObject) parser.parse(new FileReader("src/main/java/org/example/Student course details.json"));

              if (OldCoursesArray != null) {
                  allstudents.remove(Integer.toString(StudentId));
                  for (int x : Stream.of(OldCoursesArray).mapToInt(Integer::parseInt).toArray()) {
                      NewCoursesArray.add(x);
                  }
              }

              NewCoursesArray.add(TheNewCourse);
              allstudents.put(Integer.toString(StudentId), NewCoursesArray);
              //Write JSON file
              try (FileWriter file = new FileWriter("src/main/java/org/example/Student course details.json")) {
                  file.write(allstudents.toJSONString());
                  file.flush();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

      }while (true);
    }
    public static int UnenrollInaCourse(int StudentId,String[][] CourseArray,String[] OldCoursesArray) throws IOException, ParseException {
        System.out.println("Unenrollment page\n"+"====================================================================================================");
      //  do {
        if (OldCoursesArray==null||OldCoursesArray.length<=1)
        {
            System.out.println("Faild to unenroll: The student as only one or no courses to unenroll from\n");
            return -1;
        }
            System.out.println("----------------------------------------------------------------------------------------------------\n" +
                    "Please make one of the following:\n" +
                    "**Enter the course id that you want to Unenroll the student from\n" +
                    "** Or Enter b to go back to the home page\n" +
                    "Please select the required action:");

            String[][] MOldCoursesArray=new String[OldCoursesArray.length][1];
            for (int i=0;i<OldCoursesArray.length;i++){MOldCoursesArray[i][0] = OldCoursesArray[i];}

            int removedcourse=-1;
            while(removedcourse==-1)
            {
                removedcourse = ValidateIDInput(MOldCoursesArray);
            }
            if (removedcourse==-2){return 0;}

                JSONArray NewCoursesArray = new JSONArray();
                JSONParser parser = new JSONParser();
                JSONObject allstudents = (JSONObject) parser.parse(new FileReader("src/main/java/org/example/Student course details.json"));

                allstudents.remove(Integer.toString(StudentId));
                for (int x : Stream.of(OldCoursesArray).mapToInt(Integer::parseInt).toArray())
                { if (x!=removedcourse){ NewCoursesArray.add(x);}}

                allstudents.put(Integer.toString(StudentId), NewCoursesArray);
                //Write JSON file
                try (FileWriter file = new FileWriter("src/main/java/org/example/Student course details.json")) {
                    //We can write any JSONArray or JSONObject instance to the file
                    file.write(allstudents.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return 1;
    }
    public static void ReplaceCourse(int StudentId,String[][] CourseArray,String[] OldCoursesArray){}















}

