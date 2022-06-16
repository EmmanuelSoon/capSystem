package team2.capSystem;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import team2.capSystem.services.SeedDBService;

@SpringBootApplication
public class CapSystemApplication {


	public static void main(String[] args) {
		SpringApplication.run(CapSystemApplication.class, args);		
	}

	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {
	
		@Autowired
		private SeedDBService seedDBService;

		@Override
		public void run(String...args) throws Exception {
			seedDBService.databaseInit();;
	
		}
	}

	// @Bean 
	// InitializingBean loadData(){
	// 	return () -> {
	// 		if(!adminRepository.existsBy()){
	// 			adminRepository.save(new Admin("admin", "password", "admin1", "admin@gmail.com"));
	// 		}

	// 		if(!studentRepository.existsBy()){
	// 			studentRepository.save(new Student("Student1", "password", "Student", "student@gmail.com"));
	// 			studentRepository.save(new Student("alyssa", "password", "Alyssa", "alyssa@gmail.com"));
	// 			studentRepository.save(new Student("emmanuel", "password", "Emmanuel", "emmanuel@gmail.com"));
	// 			studentRepository.save(new Student("gavin", "password", "Gavin", "gavin@gmail.com"));
	// 			studentRepository.save(new Student("youcheng", "password", "YouCheng", "YC@gmail.com"));
	// 			studentRepository.save(new Student("hein", "password", "Hein", "hein@gmail.com"));
	// 			studentRepository.save(new Student("yoonmie", "password", "YoonMie", "YM@gmail.com"));
	// 			studentRepository.save(new Student("anand", "password", "Anand", "anand@gmail.com"));
	// 		}

	// 		if(!lecturerRepository.existsBy()){
	// 			lecturerRepository.save(new Lecturer("esther", "password", "Esther", "Esther@gmail.com"));
	// 			lecturerRepository.save(new Lecturer("tin", "password", "Tin", "Tin@gmail.com"));
	// 			lecturerRepository.save(new Lecturer("yeunkwan", "password", "Yeun Kwan", "YK@gmail.com"));
	// 			lecturerRepository.save(new Lecturer("suria", "password", "Suria", "Suria@gmail.com"));
	// 			lecturerRepository.save(new Lecturer("cherwah", "password", "Cher Wah", "CW@gmail.com"));
	// 			lecturerRepository.save(new Lecturer("liufan", "password", "LiuFan", "LF@gmail.com"));
	// 			lecturerRepository.save(new Lecturer("tsukiji", "password", "Tsukiji", "tsutsu@gmail.com"));
	// 		}

	// 		if (!courseRepository.existsBy()){
	// 			courseRepository.save(new Course("Java Programming", "Basic java and springboot application"));
	// 			courseRepository.save(new Course("ML", "Machine Learning"));
	// 			courseRepository.save(new Course("FOPCS", "Basic programming"));
	// 			courseRepository.save(new Course("NiHonGo", "Minasan no nihongo!"));
	// 			courseRepository.save(new Course("Mobile Applications", "Basic app creation through android studio"));
	// 			courseRepository.save(new Course("Pokemon 101", "How to be a pokemon master"));
	// 			courseRepository.save(new Course("Farming 101", "How to start your own farm"));
	// 			courseRepository.save(new Course("Cooking with Pork", "From meat to dish, learn all the tricks chefs use!"));
	// 		}
	// 	};
	// }

	// @Bean 
	// InitializingBean loadCourseDetailsData(){
	// 	return () -> {
	// 		if(!cdRepository.existsBy()){
	// 			//Lecturers 
	// 			Lecturer liufan = lecturerRepository.findByUsername("liufan");
	// 			Lecturer tin = lecturerRepository.findByUsername("tin");
	// 			Lecturer tsukiji = lecturerRepository.findByUsername("tsukiji");
	// 			Lecturer esther = lecturerRepository.findByUsername("esther");
	// 			Lecturer cherwah = lecturerRepository.findByUsername("cherwah");
	// 			Lecturer suria = lecturerRepository.findByUsername("suria");
	// 			Lecturer yeunkwan = lecturerRepository.findByUsername("yeunkwan");



	// 			Course cookingCourse = courseRepository.findCourseByName("Cooking with Pork");
	// 			Course japCourse = courseRepository.findCourseByName("NiHonGo");
	// 			Course javaCourse = courseRepository.findCourseByName("Java Programming");
	// 			Course fopcs = courseRepository.findCourseByName("FOPCS");
	// 			Course mobileCourse = courseRepository.findCourseByName("Mobile Applications");
	// 			Course pokemonCourse = courseRepository.findCourseByName("Pokemon 101");
	// 			Course farmingCourse = courseRepository.findCourseByName("Farming 101");
	// 			Course mlCourse = courseRepository.findCourseByName("ML");


	// 			//Seeding of CourseDetails
	// 			CourseDetail cook1 = courseService.createCourseDetail(cookingCourse, LocalDate.of(2021, 06, 15), LocalDate.of(2022, 06, 15));
	// 			CourseDetail cook2 = courseService.createCourseDetail(cookingCourse, LocalDate.of(2022, 06, 15), LocalDate.of(2023, 06, 15));

	// 			CourseDetail jap1 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
	// 			CourseDetail jap2 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 7, 30));
	// 			CourseDetail jap3 = courseService.createCourseDetail(japCourse, LocalDate.of(2022, 8, 01), LocalDate.of(2022, 11, 30));


	// 			CourseDetail java1 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 02, 28));
	// 			CourseDetail java2 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 04, 30));
	// 			CourseDetail java3 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 05, 01), LocalDate.of(2022, 06, 30));
	// 			CourseDetail java4 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 8, 30));
	// 			CourseDetail java5 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 10, 30));
	// 			CourseDetail java6 = courseService.createCourseDetail(javaCourse, LocalDate.of(2022, 11, 01), LocalDate.of(2023, 12, 30));


	// 			CourseDetail fopcs1 = courseService.createCourseDetail(fopcs, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 06, 30));
	// 			CourseDetail fopcs2 = courseService.createCourseDetail(fopcs, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 12, 30));


	// 			CourseDetail mobile1 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 15));
	// 			CourseDetail mobile2 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 03, 01), LocalDate.of(2022, 3, 15));
	// 			CourseDetail mobile3 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 06, 01), LocalDate.of(2022, 6, 15));
	// 			CourseDetail mobile4 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2022, 9, 15));
	// 			CourseDetail mobile5 = courseService.createCourseDetail(mobileCourse, LocalDate.of(2022, 12, 01), LocalDate.of(2022, 12, 15));



	// 			CourseDetail pokemon1 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
	// 			CourseDetail pokemon2 = courseService.createCourseDetail(pokemonCourse, LocalDate.of(2022, 6, 01), LocalDate.of(2022, 9, 30));

	// 			CourseDetail farming1 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
	// 			CourseDetail farming2 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 12, 30));
	// 			CourseDetail farming3 = courseService.createCourseDetail(farmingCourse, LocalDate.of(2023, 1, 01), LocalDate.of(2023, 12, 30));


	// 			CourseDetail ml1 = courseService.createCourseDetail(mlCourse, LocalDate.of(2021, 9, 01), LocalDate.of(2022, 3, 30));
	// 			CourseDetail ml2 = courseService.createCourseDetail(mlCourse, LocalDate.of(2022, 4, 01), LocalDate.of(2022, 8, 30));
	// 			CourseDetail ml3 = courseService.createCourseDetail(mlCourse, LocalDate.of(2022, 9, 01), LocalDate.of(2023, 3, 30));

				
	// 			//Association Lecturers with courses 
	// 			courseService.addLecturer(cook1, liufan);
	// 			courseService.addLecturer(cook2, liufan);
	// 			courseService.addLecturer(cook1, suria);
	// 			courseService.addLecturer(cook2, suria);


	// 			courseService.addLecturer(jap1, tsukiji);
	// 			courseService.addLecturer(jap2, tsukiji);
	// 			courseService.addLecturer(jap3, tsukiji);

	// 			courseService.addLecturer(java1, tin);
	// 			courseService.addLecturer(java1, suria);
	// 			courseService.addLecturer(java2, tin);
	// 			courseService.addLecturer(java2, suria);
	// 			courseService.addLecturer(java3, tin);
	// 			courseService.addLecturer(java3, suria);
	// 			courseService.addLecturer(java4, tin);
	// 			courseService.addLecturer(java4, suria);
	// 			courseService.addLecturer(java5, tin);
	// 			courseService.addLecturer(java5, suria);
	// 			courseService.addLecturer(java6, tin);
	// 			courseService.addLecturer(java6, suria);

	// 			courseService.addLecturer(fopcs1, liufan);
	// 			courseService.addLecturer(fopcs2, liufan);
	// 			courseService.addLecturer(fopcs1, tin);
	// 			courseService.addLecturer(fopcs2, tin);
	// 			courseService.addLecturer(fopcs1, cherwah);
	// 			courseService.addLecturer(fopcs2, cherwah);

	// 			courseService.addLecturer(mobile1, cherwah);
	// 			courseService.addLecturer(mobile1, esther);
	// 			courseService.addLecturer(mobile2, cherwah);
	// 			courseService.addLecturer(mobile2, esther);
	// 			courseService.addLecturer(mobile3, cherwah);
	// 			courseService.addLecturer(mobile3, esther);
	// 			courseService.addLecturer(mobile4, cherwah);
	// 			courseService.addLecturer(mobile4, esther);
	// 			courseService.addLecturer(mobile5, cherwah);
	// 			courseService.addLecturer(mobile5, esther);

	// 			courseService.addLecturer(pokemon1, tsukiji);
	// 			courseService.addLecturer(pokemon1, yeunkwan);
	// 			courseService.addLecturer(pokemon2, tsukiji);
	// 			courseService.addLecturer(pokemon2, yeunkwan);

	// 			courseService.addLecturer(farming1, suria);
	// 			courseService.addLecturer(farming2, suria);
	// 			courseService.addLecturer(farming3, esther);

	// 			courseService.addLecturer(ml1, cherwah);
	// 			courseService.addLecturer(ml1, yeunkwan);
	// 			courseService.addLecturer(ml2, cherwah);
	// 			courseService.addLecturer(ml2, yeunkwan);
	// 			courseService.addLecturer(ml3, cherwah);
	// 			courseService.addLecturer(ml3, yeunkwan);

	// 		}
	// 	};
	// }

	// @Bean 
	// InitializingBean loadStudentCourseData(){
	// 	return () -> {
	// 		if(!scRepository.existsBy()){
	// 			//Students 
	// 			Student emmanuel = studentRepository.findByUsername("emmanuel");
	// 			Student alyssa = studentRepository.findByUsername("alyssa");
	// 			Student gavin = studentRepository.findByUsername("gavin");
	// 			Student yc = studentRepository.findByUsername("youcheng");
	// 			Student hein = studentRepository.findByUsername("hein");
	// 			Student yoonmie = studentRepository.findByUsername("yoonmie");
	// 			Student anand = studentRepository.findByUsername("anand");

	// 			//Courses
	// 			Course cookingCourse = courseRepository.findCourseByName("Cooking with Pork");
	// 			Course japCourse = courseRepository.findCourseByName("NiHonGo");
	// 			Course javaCourse = courseRepository.findCourseByName("Java Programming");
	// 			Course fopcs = courseRepository.findCourseByName("FOPCS");
	// 			Course mobileCourse = courseRepository.findCourseByName("Mobile Applications");
	// 			Course pokemonCourse = courseRepository.findCourseByName("Pokemon 101");
	// 			Course farmingCourse = courseRepository.findCourseByName("Farming 101");
	// 			Course mlCourse = courseRepository.findCourseByName("ML");				
				
	// 			//Course Details 
	// 			CourseDetail jap1 = courseService.findExactCourseDetail(japCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
	// 			CourseDetail cook1 = courseService.findExactCourseDetail(cookingCourse, LocalDate.of(2021, 06, 15), LocalDate.of(2022, 06, 15));
	// 			CourseDetail pokemon1 = courseService.findExactCourseDetail(pokemonCourse, LocalDate.of(2022, 1, 01), LocalDate.of(2022, 3, 30));
	// 			CourseDetail farming1 = courseService.findExactCourseDetail(farmingCourse, LocalDate.of(2021, 1, 01), LocalDate.of(2021, 12, 30));
	// 			CourseDetail java3 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 05, 01), LocalDate.of(2022, 06, 30));
	// 			CourseDetail java4 = courseService.findExactCourseDetail(javaCourse, LocalDate.of(2022, 07, 01), LocalDate.of(2022, 8, 30));

	// 			//Creating StudentCourses
	// 			courseService.addStudentToCourseDetail(jap1, emmanuel, 4);
	// 			courseService.addStudentToCourseDetail(jap1, alyssa, 4);
	// 			courseService.addStudentToCourseDetail(jap1, hein, 4);

	// 			courseService.addStudentToCourseDetail(cook1, yc, 5);
	// 			courseService.addStudentToCourseDetail(cook1, yoonmie, 4);
	// 			courseService.addStudentToCourseDetail(cook1, gavin, 4.5);

	// 			courseService.addStudentToCourseDetail(pokemon1, emmanuel, 3);
	// 			courseService.addStudentToCourseDetail(pokemon1, hein, 5);
	// 			courseService.addStudentToCourseDetail(pokemon1, alyssa, 4);


	// 			courseService.addStudentToCourseDetail(farming1, anand, 4);
	// 			courseService.addStudentToCourseDetail(farming1, alyssa, 3);
	// 			courseService.addStudentToCourseDetail(farming1, hein, 2.5);
	// 			courseService.addStudentToCourseDetail(farming1, yc, 5);


	// 			courseService.addStudentToCourseDetail(java3, anand, -1);
	// 			courseService.addStudentToCourseDetail(java3, yc, -1);

	// 			courseService.addStudentToCourseDetail(java4, yoonmie, -1);
	// 			courseService.addStudentToCourseDetail(java4, gavin, -1);


	// 		}
	// 	};
	// }

}
