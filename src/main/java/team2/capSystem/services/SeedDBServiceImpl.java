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
            adminService.createAdmin("admin", "password", "Admin1", "admin@gmail.com");
        }

        if(!studentService.tableExist()){
            studentService.createStudent("alyssa", "password", "Alyssa", "alyssa@gmail.com");
            studentService.createStudent("emmanuel", "password", "Emmanuel", "emmanuel@gmail.com");
            studentService.createStudent("gavin", "password", "Gavin", "gavin@gmail.com");
            studentService.createStudent("youcheng", "password", "YouCheng", "YC@gmail.com");
            studentService.createStudent("hein", "password", "Hein", "hein@gmail.com");
            studentService.createStudent("yoonmie", "password", "YoonMie", "YM@gmail.com");
            studentService.createStudent("anand", "password", "Anand", "anand@gmail.com");

            studentService.createStudent("Student1", "password", "Student", "student@gmail.com");
            studentService.createStudent("alice", "password", "Alice", "Alice@gmail.com");
            studentService.createStudent("brad", "password", "Brad", "Brad@gmail.com");
            studentService.createStudent("cat", "password", "Catherine", "Catherine@gmail.com");
            studentService.createStudent("dill", "password", "Dillion", "Dillion@gmail.com");
            studentService.createStudent("eliza", "password", "Eliza", "Eliza@gmail.com");
            studentService.createStudent("frank", "password", "Frank", "Frank@gmail.com");
            studentService.createStudent("gordon", "password", "Gordon", "Gordon@gmail.com");
            studentService.createStudent("henry", "password", "Henry", "Henry@gmail.com");
            studentService.createStudent("isla", "password", "Isla", "Isla@gmail.com");
            studentService.createStudent("joan", "password", "Joan", "Joan@gmail.com");
            studentService.createStudent("kokane", "password", "Kokane", "Kokane@gmail.com");
            studentService.createStudent("leon", "password", "Leon", "Leon@gmail.com");
            studentService.createStudent("momo", "password", "Momo", "Momo@gmail.com");
            studentService.createStudent("nora", "password", "Nora", "Nora@gmail.com");
            studentService.createStudent("oliver", "password", "Oliver", "Oliver@gmail.com");
            studentService.createStudent("peanut", "password", "Peanut", "Peanut@gmail.com");

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
            CourseDetail cook3 = courseService.createCourseDetail(cookingCourse, LocalDate.of(2020, 06, 15), LocalDate.of(2021, 06, 15));
            CourseDetail cook4 = courseService.createCourseDetail(cookingCourse, LocalDate.of(2023, 06, 15), LocalDate.of(2024, 06, 15));

            CourseDetail jap1 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail jap2 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 7, 30));
            CourseDetail jap3 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 8, 01), LocalDate.of(2022, 11, 30));
            CourseDetail jap4 = courseService.createCourseDetail(japCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 3, 30));
            CourseDetail jap5 = courseService.createCourseDetail(japCourse, LocalDate.of(2021, 4, 01), LocalDate.of(2021, 7, 30));
            CourseDetail jap6 = courseService.createCourseDetail(japCourse, LocalDate.of(2021, 8, 01), LocalDate.of(2021, 11, 30));
            CourseDetail jap7 = courseService.createCourseDetail(japCourse, LocalDate.of(2023, 1, 01), LocalDate.of(2023, 3, 30));


            CourseDetail java1 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 02, 28));
            CourseDetail java2 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 04, 30));
            CourseDetail java3 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 05, 01), LocalDate.of(2022, 06, 30));
            CourseDetail java4 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 8, 30));
            CourseDetail java5 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 10, 30));
            CourseDetail java6 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 11, 01), LocalDate.of(2023, 12, 30));


            CourseDetail fopcs1 = courseService.createCourseDetail(fopcs, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 06, 30));
            CourseDetail fopcs2 = courseService.createCourseDetail(fopcs, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 12, 30));
            CourseDetail fopcs3 = courseService.createCourseDetail(fopcs, LocalDate.of(2023, 01, 01), LocalDate.of(2023, 06, 30));
            CourseDetail fopcs4 = courseService.createCourseDetail(fopcs, LocalDate.of(2023, 07, 01), LocalDate.of(2023, 12, 30));
            CourseDetail fopcs5 = courseService.createCourseDetail(fopcs, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 06, 30));
            CourseDetail fopcs6 = courseService.createCourseDetail(fopcs, LocalDate.of(2021, 07, 01), LocalDate.of(2021, 12, 30));

            CourseDetail mobile1 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15));
            CourseDetail mobile2 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 3, 15));
            CourseDetail mobile3 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 06, 01), LocalDate.of(2022, 6, 15));
            CourseDetail mobile4 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 9, 15));
            CourseDetail mobile5 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 12, 01), LocalDate.of(2022, 12, 15));


            CourseDetail pokemon1 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail pokemon2 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2022, 6, 01), LocalDate.of(2022, 9, 30));
            CourseDetail pokemon3 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2023, 1, 01), LocalDate.of(2023, 3, 30));
            CourseDetail pokemon4 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2023, 6, 01), LocalDate.of(2023, 9, 30));
            CourseDetail pokemon5 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 3, 30));
            CourseDetail pokemon6 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2021, 6, 01), LocalDate.of(2021, 9, 30));

            CourseDetail farming1 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
            CourseDetail farming2 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 12, 30));
            CourseDetail farming3 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2023, 1, 01), LocalDate.of(2023, 12, 30));
            CourseDetail farming4 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2020, 1, 01), LocalDate.of(2020, 12, 30));


            CourseDetail ml1 = courseService.createCourseDetail(mlCourse, LocalDate.of(2021, 9, 01), LocalDate.of(2022, 3, 30));
            CourseDetail ml2 = courseService.createCourseDetail(mlCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 8, 30));
            CourseDetail ml3 = courseService.createCourseDetail(mlCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2023, 3, 30));
            CourseDetail ml4 = courseService.createCourseDetail(mlCourse, LocalDate.of(2021, 4, 01), LocalDate.of(2021, 8, 30));
            CourseDetail ml5 = courseService.createCourseDetail(mlCourse, LocalDate.of(2023, 4, 01), LocalDate.of(2023, 8, 30));
            CourseDetail ml6 = courseService.createCourseDetail(mlCourse, LocalDate.of(2023, 9, 01), LocalDate.of(2024, 3, 30));

            
            //Association Lecturers with courses 
            courseService.addLecturer(cook1, liufan);
            courseService.addLecturer(cook2, liufan);
            courseService.addLecturer(cook3, liufan);
            courseService.addLecturer(cook4, liufan);
            courseService.addLecturer(cook1, suria);
            courseService.addLecturer(cook2, suria);
            courseService.addLecturer(cook3, suria);
            courseService.addLecturer(cook4, suria);

            courseService.addLecturer(jap1, tsukiji);
            courseService.addLecturer(jap2, tsukiji);
            courseService.addLecturer(jap3, tsukiji);
            courseService.addLecturer(jap4, tsukiji);
            courseService.addLecturer(jap5, tsukiji);
            courseService.addLecturer(jap6, tsukiji);
            courseService.addLecturer(jap7, tsukiji);
            courseService.addLecturer(jap1, esther);
            courseService.addLecturer(jap2, esther);
            courseService.addLecturer(jap3, esther);
            courseService.addLecturer(jap4, esther);
            courseService.addLecturer(jap5, esther);
            courseService.addLecturer(jap6, esther);
            courseService.addLecturer(jap7, esther);


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
            courseService.addLecturer(fopcs3, liufan);
            courseService.addLecturer(fopcs4, liufan);
            courseService.addLecturer(fopcs3, tin);
            courseService.addLecturer(fopcs4, tin);
            courseService.addLecturer(fopcs3, cherwah);
            courseService.addLecturer(fopcs4, cherwah);
            courseService.addLecturer(fopcs5, liufan);
            courseService.addLecturer(fopcs6, liufan);
            courseService.addLecturer(fopcs5, tin);
            courseService.addLecturer(fopcs6, tin);
            courseService.addLecturer(fopcs5, cherwah);
            courseService.addLecturer(fopcs6, cherwah);

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
            courseService.addLecturer(pokemon3, tsukiji);
            courseService.addLecturer(pokemon3, yeunkwan);
            courseService.addLecturer(pokemon4, tsukiji);
            courseService.addLecturer(pokemon4, yeunkwan);
            courseService.addLecturer(pokemon5, tsukiji);
            courseService.addLecturer(pokemon5, yeunkwan);
            courseService.addLecturer(pokemon6, tsukiji);
            courseService.addLecturer(pokemon6, yeunkwan);

            courseService.addLecturer(farming1, suria);
            courseService.addLecturer(farming2, suria);
            courseService.addLecturer(farming3, esther);
            courseService.addLecturer(farming4, suria);


            courseService.addLecturer(ml1, cherwah);
            courseService.addLecturer(ml1, yeunkwan);
            courseService.addLecturer(ml2, cherwah);
            courseService.addLecturer(ml2, yeunkwan);
            courseService.addLecturer(ml3, cherwah);
            courseService.addLecturer(ml3, yeunkwan);
            courseService.addLecturer(ml4, cherwah);
            courseService.addLecturer(ml4, yeunkwan);
            courseService.addLecturer(ml5, cherwah);
            courseService.addLecturer(ml5, yeunkwan);
            courseService.addLecturer(ml6, cherwah);
            courseService.addLecturer(ml6, yeunkwan);

        }
    }

    public void loadStudentCourseData(){
        if(!studentService.scTableExist()){
            //Students 
            Student student = studentService.findStudentByUsername("Student1");
            Student emmanuel = studentService.findStudentByUsername("emmanuel");
            Student alyssa = studentService.findStudentByUsername("alyssa");
            Student gavin = studentService.findStudentByUsername("gavin");
            Student yc = studentService.findStudentByUsername("youcheng");
            Student hein = studentService.findStudentByUsername("hein");
            Student yoonmie = studentService.findStudentByUsername("yoonmie");
            Student anand = studentService.findStudentByUsername("anand");

            Student alice = studentService.findStudentByUsername("alice");
            Student brad = studentService.findStudentByUsername("brad");
            Student cat = studentService.findStudentByUsername("cat");
            Student dill = studentService.findStudentByUsername("dill");
            Student eliza = studentService.findStudentByUsername("eliza");
            Student frank = studentService.findStudentByUsername("frank");
            Student gordon = studentService.findStudentByUsername("gordon");
            Student henry = studentService.findStudentByUsername("henry");
            Student isla = studentService.findStudentByUsername("isla");
            Student joan = studentService.findStudentByUsername("joan");
            Student kokane = studentService.findStudentByUsername("kokane");
            Student leon = studentService.findStudentByUsername("leon");
            Student momo = studentService.findStudentByUsername("momo");
            Student nora = studentService.findStudentByUsername("nora");
            Student oliver = studentService.findStudentByUsername("oliver");
            Student peanut = studentService.findStudentByUsername("peanut");



            //Courses
            Course cookingCourse = courseService.getCourseByName("Cooking with Pork");
            Course japCourse = courseService.getCourseByName("NiHonGo");
            Course javaCourse = courseService.getCourseByName("Java Programming");
            Course fopcs = courseService.getCourseByName("FOPCS");
            Course mobileCourse = courseService.getCourseByName("Mobile Applications");
            Course pokemonCourse = courseService.getCourseByName("Pokemon 101");
            Course farmingCourse = courseService.getCourseByName("Farming 101");
            Course mlCourse = courseService.getCourseByName("ML");				
            
            //Cooking Course Details
            CourseDetail cook1 = courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2021, 06, 15), LocalDate.of(2022, 06, 15));
            CourseDetail cook2 = courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2022, 06, 15), LocalDate.of(2023, 06, 15));
            CourseDetail cook3 = courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2020, 06, 15), LocalDate.of(2021, 06, 15));
            CourseDetail cook4 = courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2023, 06, 15), LocalDate.of(2024, 06, 15));

            //Creating StudentCourses
            courseService.addStudentToCourseDetail(cook1, yc, 5);
            courseService.addStudentToCourseDetail(cook1, yoonmie, 4);
            courseService.addStudentToCourseDetail(cook1, gavin, 4.5);
            courseService.addStudentToCourseDetail(cook2, alyssa, -1);
            courseService.addStudentToCourseDetail(cook2, hein, -1);
            courseService.addStudentToCourseDetail(cook2, brad, -1);
            courseService.addStudentToCourseDetail(cook3, anand, 5);
            courseService.addStudentToCourseDetail(cook3, cat, 3);
            courseService.addStudentToCourseDetail(cook3, alice, 2);


            // Jap courses
            CourseDetail jap1 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail jap2 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 7, 30));
            CourseDetail jap3 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2022, 8, 01), LocalDate.of(2022, 11, 30));
            CourseDetail jap4 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 3, 30));
            CourseDetail jap5 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2021, 4, 01), LocalDate.of(2021, 7, 30));
            CourseDetail jap6 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2021, 8, 01), LocalDate.of(2021, 11, 30));
            CourseDetail jap7 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2023, 1, 01), LocalDate.of(2023, 3, 30));

            courseService.addStudentToCourseDetail(jap1, emmanuel, 4);
            courseService.addStudentToCourseDetail(jap1, alyssa, 4);
            courseService.addStudentToCourseDetail(jap1, hein, 4);            
            courseService.addStudentToCourseDetail(jap2, gavin, -1);
            courseService.addStudentToCourseDetail(jap2, anand, -1);
            courseService.addStudentToCourseDetail(jap2, cat, -1);
            courseService.addStudentToCourseDetail(jap3, alice, -1);
            courseService.addStudentToCourseDetail(jap3, brad, -1);

            courseService.addStudentToCourseDetail(jap4, dill, 4);
            courseService.addStudentToCourseDetail(jap4, eliza, 4);
            courseService.addStudentToCourseDetail(jap4, henry, 5);            
            courseService.addStudentToCourseDetail(jap4, gordon, 2);
            courseService.addStudentToCourseDetail(jap5, isla, 3);
            courseService.addStudentToCourseDetail(jap5, kokane, 5);
            courseService.addStudentToCourseDetail(jap5, leon, 3);
            courseService.addStudentToCourseDetail(jap5, momo, 5);
            courseService.addStudentToCourseDetail(jap6, nora, 2);
            courseService.addStudentToCourseDetail(jap6, peanut, 3);
            courseService.addStudentToCourseDetail(jap6, yoonmie, 5);


            //java courses
            CourseDetail java1 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 02, 28));
            CourseDetail java2 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 04, 30));
            CourseDetail java3 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 05, 01), LocalDate.of(2022, 06, 30));
            CourseDetail java4 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 8, 30));
            CourseDetail java5 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 10, 30));
            CourseDetail java6 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 11, 01), LocalDate.of(2023, 12, 30));

            courseService.addStudentToCourseDetail(java1, cat, 5);
            courseService.addStudentToCourseDetail(java1, dill, 3);
            courseService.addStudentToCourseDetail(java1, eliza, 2);
            courseService.addStudentToCourseDetail(java2, frank, 1);
            courseService.addStudentToCourseDetail(java2, henry, 5);            

            courseService.addStudentToCourseDetail(java3, alyssa, -1);
            courseService.addStudentToCourseDetail(java3, momo, -1);
            courseService.addStudentToCourseDetail(java3, isla, -1);

            courseService.addStudentToCourseDetail(java4, anand, -1);
            courseService.addStudentToCourseDetail(java4, yc, -1);
            courseService.addStudentToCourseDetail(java4, yoonmie, -1);
            courseService.addStudentToCourseDetail(java4, gavin, -1);            

            courseService.addStudentToCourseDetail(java5, alice, -1);
            courseService.addStudentToCourseDetail(java5, brad, -1);
            courseService.addStudentToCourseDetail(java6, nora, -1);
            courseService.addStudentToCourseDetail(java6, peanut, -1);
            courseService.addStudentToCourseDetail(java6, leon, -1);            

            CourseDetail fopcs1 = courseService.findExactCourseDetail(fopcs, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 06, 30));
            CourseDetail fopcs2 = courseService.findExactCourseDetail(fopcs, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 12, 30));
            CourseDetail fopcs5 = courseService.findExactCourseDetail(fopcs, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 06, 30));
            CourseDetail fopcs6 = courseService.findExactCourseDetail(fopcs, LocalDate.of(2021, 07, 01), LocalDate.of(2021, 12, 30));

            courseService.addStudentToCourseDetail(fopcs1, cat, 5);
            courseService.addStudentToCourseDetail(fopcs1, henry, 5);
            courseService.addStudentToCourseDetail(fopcs1, leon, 3);

            courseService.addStudentToCourseDetail(fopcs2, gordon, -1);
            courseService.addStudentToCourseDetail(fopcs2, momo, -1); 
            courseService.addStudentToCourseDetail(fopcs2, isla, -1);
            courseService.addStudentToCourseDetail(fopcs2, kokane, -1);       
            courseService.addStudentToCourseDetail(fopcs2, joan, -1);    

            courseService.addStudentToCourseDetail(fopcs5, alice, 2);
            courseService.addStudentToCourseDetail(fopcs5, brad, 3); 
            courseService.addStudentToCourseDetail(fopcs5, oliver, 4);
            courseService.addStudentToCourseDetail(fopcs5, nora, 5);       
            courseService.addStudentToCourseDetail(fopcs5, peanut, 1);  

            courseService.addStudentToCourseDetail(fopcs6, yc, 5);
            courseService.addStudentToCourseDetail(fopcs6, anand, 5); 
            courseService.addStudentToCourseDetail(fopcs6, emmanuel, 5);
            courseService.addStudentToCourseDetail(fopcs6, yoonmie, 5);       
            courseService.addStudentToCourseDetail(fopcs6, hein, 5);   


            CourseDetail mobile1 = courseService.findExactCourseDetail(mobileCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15));
            CourseDetail mobile2 = courseService.findExactCourseDetail(mobileCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 3, 15));
            CourseDetail mobile3 = courseService.findExactCourseDetail(mobileCourse, LocalDate.of(2022, 06, 01), LocalDate.of(2022, 6, 15));
            CourseDetail mobile4 = courseService.findExactCourseDetail(mobileCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 9, 15));
            CourseDetail mobile5 = courseService.findExactCourseDetail(mobileCourse, LocalDate.of(2022, 12, 01), LocalDate.of(2022, 12, 15));

            courseService.addStudentToCourseDetail(mobile1, alice, 3.5);
            courseService.addStudentToCourseDetail(mobile1, brad, 4);
            courseService.addStudentToCourseDetail(mobile1, cat, 3.5);
            courseService.addStudentToCourseDetail(mobile2, dill, 1);
            courseService.addStudentToCourseDetail(mobile2, eliza, 2.5);
            courseService.addStudentToCourseDetail(mobile2, frank, 3);
            courseService.addStudentToCourseDetail(mobile3, henry,5);
            courseService.addStudentToCourseDetail(mobile3, yc, 5);
            courseService.addStudentToCourseDetail(mobile3, gordon, 3);
            courseService.addStudentToCourseDetail(mobile4, joan,-1);
            courseService.addStudentToCourseDetail(mobile4, momo, -1);
            courseService.addStudentToCourseDetail(mobile4, isla, -1);
            courseService.addStudentToCourseDetail(mobile5, kokane, -1);
            courseService.addStudentToCourseDetail(mobile5, nora, -1);
            courseService.addStudentToCourseDetail(mobile5, gavin, -1);


            CourseDetail pokemon1 = courseService.findExactCourseDetail(pokemonCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
            CourseDetail pokemon2 = courseService.findExactCourseDetail(pokemonCourse, LocalDate.of(2022, 6, 01), LocalDate.of(2022, 9, 30));
            CourseDetail pokemon5 = courseService.findExactCourseDetail(pokemonCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 3, 30));
            CourseDetail pokemon6 = courseService.findExactCourseDetail(pokemonCourse, LocalDate.of(2021, 6, 01), LocalDate.of(2021, 9, 30));

            courseService.addStudentToCourseDetail(pokemon1, emmanuel, 3);
            courseService.addStudentToCourseDetail(pokemon1, alyssa, 4);
            courseService.addStudentToCourseDetail(pokemon1, alice, 3.5);
            courseService.addStudentToCourseDetail(pokemon1, brad, 4);

            courseService.addStudentToCourseDetail(pokemon2, hein, -1);
            courseService.addStudentToCourseDetail(pokemon2, cat, -1);
            courseService.addStudentToCourseDetail(pokemon2, dill, -1);

            courseService.addStudentToCourseDetail(pokemon5, eliza, 2.5);
            courseService.addStudentToCourseDetail(pokemon5, frank, 3);
            courseService.addStudentToCourseDetail(pokemon5, henry,5);
            courseService.addStudentToCourseDetail(pokemon6, yc, 5);
            courseService.addStudentToCourseDetail(pokemon6, gordon, 3);
            courseService.addStudentToCourseDetail(pokemon6, joan,1);


            CourseDetail farming1 = courseService.findExactCourseDetail(farmingCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
            CourseDetail farming2 = courseService.findExactCourseDetail(farmingCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 12, 30));
            CourseDetail farming3 = courseService.findExactCourseDetail(farmingCourse, LocalDate.of(2023, 1, 01), LocalDate.of(2023, 12, 30));
            CourseDetail farming4 = courseService.findExactCourseDetail(farmingCourse, LocalDate.of(2020, 1, 01), LocalDate.of(2020, 12, 30));

            courseService.addStudentToCourseDetail(farming1, anand, 4);
            courseService.addStudentToCourseDetail(farming1, yc, 5);
            courseService.addStudentToCourseDetail(farming1, oliver, 2);


            courseService.addStudentToCourseDetail(farming2, alyssa, -1);
            courseService.addStudentToCourseDetail(farming2, henry, -1);
            courseService.addStudentToCourseDetail(farming2, momo, -1);

            courseService.addStudentToCourseDetail(farming3, hein, -1);
            courseService.addStudentToCourseDetail(farming3, kokane, -1);
            courseService.addStudentToCourseDetail(farming3, joan, -1);

            courseService.addStudentToCourseDetail(farming4, peanut, 2);
            courseService.addStudentToCourseDetail(farming4, yc, 5);
            courseService.addStudentToCourseDetail(farming4, eliza, 3);


            CourseDetail ml1 = courseService.findExactCourseDetail(mlCourse, LocalDate.of(2021, 9, 01), LocalDate.of(2022, 3, 30));
            CourseDetail ml2 = courseService.findExactCourseDetail(mlCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 8, 30));
            CourseDetail ml3 = courseService.findExactCourseDetail(mlCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2023, 3, 30));
            CourseDetail ml4 = courseService.findExactCourseDetail(mlCourse, LocalDate.of(2021, 4, 01), LocalDate.of(2021, 8, 30));
            CourseDetail ml5 = courseService.findExactCourseDetail(mlCourse, LocalDate.of(2023, 4, 01), LocalDate.of(2023, 8, 30));
            CourseDetail ml6 = courseService.findExactCourseDetail(mlCourse, LocalDate.of(2023, 9, 01), LocalDate.of(2024, 3, 30));

            courseService.addStudentToCourseDetail(ml1, student, 5);
            courseService.addStudentToCourseDetail(ml1, yc, 5);

            courseService.addStudentToCourseDetail(ml2, anand, -1);
            courseService.addStudentToCourseDetail(ml2, henry, -1);
            courseService.addStudentToCourseDetail(ml2, momo, -1);

            courseService.addStudentToCourseDetail(ml3, alice, -1);
            courseService.addStudentToCourseDetail(ml3, brad, -1);
            courseService.addStudentToCourseDetail(ml3, nora, -1);
            courseService.addStudentToCourseDetail(ml3, peanut, -1);
            courseService.addStudentToCourseDetail(ml3, leon, -1); 

            courseService.addStudentToCourseDetail(ml4, isla, 2);
            courseService.addStudentToCourseDetail(ml4, kokane, 3.5);
            courseService.addStudentToCourseDetail(ml4, nora, 5);
            
        }
    }

}
