package team2.capSystem.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team2.capSystem.model.*;

@Service
public class SeedDBServiceImpl implements SeedDBService{
    @Autowired 
	AdminService adminService;

	@Autowired 
	StudentService studentService;
	
	@Autowired 
	LecturerService lecturerService;

	@Autowired
	CourseService courseService;



    public void databaseInit(){
        loadInitialData();
        loadCourseDetailData();
        loadStudentCourseData();
    }

    public void loadInitialData(){
        if(!adminService.tableExist()){
            adminService.createAdmin("admin", "password", "admin1", "admin@gmail.com");
        }

        if(!studentService.tableExist()){
            studentService.createStudent("Student1", "password", "Student", "student@gmail.com");
            studentService.createStudent("alyssa", "password", "Alyssa", "alyssa@gmail.com");
            studentService.createStudent("emmanuel", "password", "Emmanuel", "emmanuel@gmail.com");
            studentService.createStudent("gavin", "password", "Gavin", "gavin@gmail.com");
            studentService.createStudent("youcheng", "password", "YouCheng", "YC@gmail.com");
            studentService.createStudent("hein", "password", "Hein", "hein@gmail.com");
            studentService.createStudent("yoonmie", "password", "YoonMie", "YM@gmail.com");
            studentService.createStudent("anand", "password", "Anand", "anand@gmail.com");
        }

        if(!lecturerService.tableExist()){
            lecturerService.createLecturer("esther", "password", "Esther", "Esther@gmail.com");
            lecturerService.createLecturer("tin", "password", "Tin", "Tin@gmail.com");
            lecturerService.createLecturer("yeunkwan", "password", "Yeun Kwan", "YK@gmail.com");
            lecturerService.createLecturer("suria", "password", "Suria", "Suria@gmail.com");
            lecturerService.createLecturer("cherwah", "password", "Cher Wah", "CW@gmail.com");
            lecturerService.createLecturer("liufan", "password", "LiuFan", "LF@gmail.com");
            lecturerService.createLecturer("tsukiji", "password", "Tsukiji", "tsutsu@gmail.com");
        }

        if (!courseService.tableExist()){
            courseService.createCourse("Java Programming", "Basic java and springboot application");
            courseService.createCourse("ML", "Machine Learning");
            courseService.createCourse("FOPCS", "Basic programming");
            courseService.createCourse("NiHonGo", "Minasan no nihongo!");
            courseService.createCourse("Mobile Applications", "Basic app creation through android studio");
            courseService.createCourse("Pokemon 101", "How to be a pokemon master");
            courseService.createCourse("Farming 101", "How to start your own farm");
            courseService.createCourse("Cooking with Pork", "From meat to dish, learn all the tricks chefs use!");
        }
    }

    public void loadCourseDetailData(){
        if(!courseService.courseDetailTableExist()){
            //Lecturers 
            Lecturer liufan = lecturerService.findByUsername("liufan");
            Lecturer tin = lecturerService.findByUsername("tin");
            Lecturer tsukiji = lecturerService.findByUsername("tsukiji");
            Lecturer esther = lecturerService.findByUsername("esther");
            Lecturer cherwah = lecturerService.findByUsername("cherwah");
            Lecturer suria = lecturerService.findByUsername("suria");
            Lecturer yeunkwan = lecturerService.findByUsername("yeunkwan");



            Course cookingCourse = courseService.getCourseByName("Cooking with Pork");
            Course japCourse = courseService.getCourseByName("NiHonGo");
            Course javaCourse = courseService.getCourseByName("Java Programming");
            Course fopcs = courseService.getCourseByName("FOPCS");
            Course mobileCourse = courseService.getCourseByName("Mobile Applications");
            Course pokemonCourse = courseService.getCourseByName("Pokemon 101");
            Course farmingCourse = courseService.getCourseByName("Farming 101");
            Course mlCourse = courseService.getCourseByName("ML");


            //Seeding of CourseDetails
            CourseDetail cook1 = courseService.createCourseDetail(cookingCourse, LocalDate.of(2021, 06, 15), LocalDate.of(2022, 06, 15));
            CourseDetail cook2 = courseService.createCourseDetail(cookingCourse, LocalDate.of(2022, 06, 15), LocalDate.of(2023, 06, 15));

            CourseDetail jap1 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail jap2 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 7, 30));
            CourseDetail jap3 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 8, 01), LocalDate.of(2022, 11, 30));


            CourseDetail java1 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 02, 28));
            CourseDetail java2 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 04, 30));
            CourseDetail java3 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 05, 01), LocalDate.of(2022, 06, 30));
            CourseDetail java4 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 8, 30));
            CourseDetail java5 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 10, 30));
            CourseDetail java6 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 11, 01), LocalDate.of(2023, 12, 30));


            CourseDetail fopcs1 = courseService.createCourseDetail(fopcs, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 06, 30));
            CourseDetail fopcs2 = courseService.createCourseDetail(fopcs, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 12, 30));


            CourseDetail mobile1 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15));
            CourseDetail mobile2 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 3, 15));
            CourseDetail mobile3 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 06, 01), LocalDate.of(2022, 6, 15));
            CourseDetail mobile4 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 9, 15));
            CourseDetail mobile5 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 12, 01), LocalDate.of(2022, 12, 15));



            CourseDetail pokemon1 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail pokemon2 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2022, 6, 01), LocalDate.of(2022, 9, 30));

            CourseDetail farming1 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
            CourseDetail farming2 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 12, 30));
            CourseDetail farming3 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2023, 1, 01), LocalDate.of(2023, 12, 30));


            CourseDetail ml1 = courseService.createCourseDetail(mlCourse, LocalDate.of(2021, 9, 01), LocalDate.of(2022, 3, 30));
            CourseDetail ml2 = courseService.createCourseDetail(mlCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 8, 30));
            CourseDetail ml3 = courseService.createCourseDetail(mlCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2023, 3, 30));

            
            //Association Lecturers with courses 
            courseService.addLecturer(cook1, liufan);
            courseService.addLecturer(cook2, liufan);
            courseService.addLecturer(cook1, suria);
            courseService.addLecturer(cook2, suria);


            courseService.addLecturer(jap1, tsukiji);
            courseService.addLecturer(jap2, tsukiji);
            courseService.addLecturer(jap3, tsukiji);

            courseService.addLecturer(java1, tin);
            courseService.addLecturer(java1, suria);
            courseService.addLecturer(java2, tin);
            courseService.addLecturer(java2, suria);
            courseService.addLecturer(java3, tin);
            courseService.addLecturer(java3, suria);
            courseService.addLecturer(java4, tin);
            courseService.addLecturer(java4, suria);
            courseService.addLecturer(java5, tin);
            courseService.addLecturer(java5, suria);
            courseService.addLecturer(java6, tin);
            courseService.addLecturer(java6, suria);

            courseService.addLecturer(fopcs1, liufan);
            courseService.addLecturer(fopcs2, liufan);
            courseService.addLecturer(fopcs1, tin);
            courseService.addLecturer(fopcs2, tin);
            courseService.addLecturer(fopcs1, cherwah);
            courseService.addLecturer(fopcs2, cherwah);

            courseService.addLecturer(mobile1, cherwah);
            courseService.addLecturer(mobile1, esther);
            courseService.addLecturer(mobile2, cherwah);
            courseService.addLecturer(mobile2, esther);
            courseService.addLecturer(mobile3, cherwah);
            courseService.addLecturer(mobile3, esther);
            courseService.addLecturer(mobile4, cherwah);
            courseService.addLecturer(mobile4, esther);
            courseService.addLecturer(mobile5, cherwah);
            courseService.addLecturer(mobile5, esther);

            courseService.addLecturer(pokemon1, tsukiji);
            courseService.addLecturer(pokemon1, yeunkwan);
            courseService.addLecturer(pokemon2, tsukiji);
            courseService.addLecturer(pokemon2, yeunkwan);

            courseService.addLecturer(farming1, suria);
            courseService.addLecturer(farming2, suria);
            courseService.addLecturer(farming3, esther);

            courseService.addLecturer(ml1, cherwah);
            courseService.addLecturer(ml1, yeunkwan);
            courseService.addLecturer(ml2, cherwah);
            courseService.addLecturer(ml2, yeunkwan);
            courseService.addLecturer(ml3, cherwah);
            courseService.addLecturer(ml3, yeunkwan);

        }
    }

    public void loadStudentCourseData(){
        if(!studentService.scTableExist()){
            //Students 
            Student emmanuel = studentService.findStudentByUsername("emmanuel");
            Student alyssa = studentService.findStudentByUsername("alyssa");
            Student gavin = studentService.findStudentByUsername("gavin");
            Student yc = studentService.findStudentByUsername("youcheng");
            Student hein = studentService.findStudentByUsername("hein");
            Student yoonmie = studentService.findStudentByUsername("yoonmie");
            Student anand = studentService.findStudentByUsername("anand");

            //Courses
            Course cookingCourse = courseService.getCourseByName("Cooking with Pork");
            Course japCourse = courseService.getCourseByName("NiHonGo");
            Course javaCourse = courseService.getCourseByName("Java Programming");
            Course fopcs = courseService.getCourseByName("FOPCS");
            Course mobileCourse = courseService.getCourseByName("Mobile Applications");
            Course pokemonCourse = courseService.getCourseByName("Pokemon 101");
            Course farmingCourse = courseService.getCourseByName("Farming 101");
            Course mlCourse = courseService.getCourseByName("ML");				
            
            //Course Details 
            CourseDetail jap1 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail cook1 = courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2021, 06, 15), LocalDate.of(2022, 06, 15));
            CourseDetail pokemon1 = courseService.findExactCourseDetail(pokemonCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail farming1 = courseService.findExactCourseDetail(farmingCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
            CourseDetail java3 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 05, 01), LocalDate.of(2022, 06, 30));
            CourseDetail java4 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 8, 30));

            //Creating StudentCourses
            courseService.addStudentToCourseDetail(jap1, emmanuel, 4);
            courseService.addStudentToCourseDetail(jap1, alyssa, 4);
            courseService.addStudentToCourseDetail(jap1, hein, 4);

            courseService.addStudentToCourseDetail(cook1, yc, 5);
            courseService.addStudentToCourseDetail(cook1, yoonmie, 4);
            courseService.addStudentToCourseDetail(cook1, gavin, 4.5);

            courseService.addStudentToCourseDetail(pokemon1, emmanuel, 3);
            courseService.addStudentToCourseDetail(pokemon1, hein, 5);
            courseService.addStudentToCourseDetail(pokemon1, alyssa, 4);


            courseService.addStudentToCourseDetail(farming1, anand, 4);
            courseService.addStudentToCourseDetail(farming1, alyssa, 3);
            courseService.addStudentToCourseDetail(farming1, hein, 2.5);
            courseService.addStudentToCourseDetail(farming1, yc, 5);

            courseService.addStudentToCourseDetail(java3, alyssa, -1);


            courseService.addStudentToCourseDetail(java4, anand, -1);
            courseService.addStudentToCourseDetail(java4, yc, -1);
            courseService.addStudentToCourseDetail(java4, yoonmie, -1);
            courseService.addStudentToCourseDetail(java4, gavin, -1);            




        }
    }

}
