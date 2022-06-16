package team2.capSystem;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.qos.logback.core.spi.LifeCycle;
import team2.capSystem.model.*;
import team2.capSystem.repo.*;

@SpringBootApplication
public class CapSystemApplication {

	@Autowired 
	AdminRepository adminRepository;

	@Autowired 
	StudentRepository studentRepository;
	
	@Autowired 
	LecturerRepository lecturerRepository;
	
	@Autowired 
	CourseRepository courseRepository;

	@Autowired
	StudentCourseRepository scRepository;

	@Autowired
	CourseDetailRepository cdRepository;

	public static void main(String[] args) {
		SpringApplication.run(CapSystemApplication.class, args);		
	}

	@Bean 
	InitializingBean loadData(){
		return () -> {
			if(!adminRepository.existsBy()){
				adminRepository.save(new Admin("admin", "password", "admin1", "admin@gmail.com"));
			}

			if(!studentRepository.existsBy()){
				studentRepository.save(new Student("Student1", "password", "Student", "student@gmail.com"));
				studentRepository.save(new Student("alyssa", "password", "Alyssa", "alyssa@gmail.com"));
				studentRepository.save(new Student("emmanuel", "password", "Emmanuel", "emmanuel@gmail.com"));
				studentRepository.save(new Student("gavin", "password", "Gavin", "gavin@gmail.com"));
				studentRepository.save(new Student("youcheng", "password", "YouCheng", "YC@gmail.com"));
				studentRepository.save(new Student("hein", "password", "Hein", "hein@gmail.com"));
				studentRepository.save(new Student("yoonmie", "password", "YoonMie", "YM@gmail.com"));
				studentRepository.save(new Student("anand", "password", "Anand", "anand@gmail.com"));
			}

			if(!lecturerRepository.existsBy()){
				lecturerRepository.save(new Lecturer("esther", "password", "Esther", "Esther@gmail.com"));
				lecturerRepository.save(new Lecturer("tin", "password", "Tin", "Tin@gmail.com"));
				lecturerRepository.save(new Lecturer("yeunkwan", "password", "Yeun Kwan", "YK@gmail.com"));
				lecturerRepository.save(new Lecturer("suria", "password", "Suria", "Suria@gmail.com"));
				lecturerRepository.save(new Lecturer("cherwah", "password", "Cher Wah", "CW@gmail.com"));
				lecturerRepository.save(new Lecturer("liufan", "password", "LiuFan", "LF@gmail.com"));
				lecturerRepository.save(new Lecturer("tsukiji", "password", "Tsukiji", "tsutsu@gmail.com"));
			}

			if (!courseRepository.existsBy()){
				courseRepository.save(new Course("Java Programming", "Basic java and springboot application"));
				courseRepository.save(new Course("ML", "Machine Learning"));
				courseRepository.save(new Course("FOPCS", "Basic programming"));
				courseRepository.save(new Course("NiHonGo", "Minasan no nihongo!"));
				courseRepository.save(new Course("Mobile Applications", "Basic app creation through android studio"));
				courseRepository.save(new Course("Pokemon 101", "How to be a pokemon master"));
				courseRepository.save(new Course("Farming 101", "How to start your own farm"));
				courseRepository.save(new Course("Cooking with Pork", "From meat to dish, learn all the tricks chefs use!"));
			}
		};
	}

	@Bean 
	InitializingBean loadCourseDetailsData(){
		return () -> {
			if(!cdRepository.existsBy()){
				Course course = courseRepository.findCourseByName("Cooking with Pork");
				LocalDate start = LocalDate.of(2022, 06, 15);
				LocalDate end = LocalDate.of(2023, 06, 15);
				Lecturer liufan = lecturerRepository.findByUsername("liufan");
				Lecturer tin = lecturerRepository.findByUsername("tin");
				CourseDetail porky = new CourseDetail(start, end, course);
				cdRepository.saveAndFlush(porky);

				tin.getCourses().add(porky);
				liufan.getCourses().add(porky);
				lecturerRepository.saveAndFlush(tin);
				lecturerRepository.saveAndFlush(liufan);
				
				Course course2 = courseRepository.findCourseByName("NiHonGo");
				LocalDate start2 = LocalDate.of(2022, 06, 04);
				LocalDate end2 = LocalDate.of(2023, 06, 25);
				Lecturer tsukiji = lecturerRepository.findByUsername("tsukiji");


				CourseDetail japCourse = new CourseDetail(start2, end2, course2);
				cdRepository.saveAndFlush(japCourse);

				tin.getCourses().add(japCourse);
				tsukiji.getCourses().add(japCourse);
				lecturerRepository.saveAndFlush(tin);
				lecturerRepository.saveAndFlush(tsukiji);


				
				
			}
		};
	}

	@Bean 
	InitializingBean loadStudentCourseData(){
		return () -> {
			if(!scRepository.existsBy()){
				Student s1 = studentRepository.findByUsername("emmanuel");
				LocalDate date = LocalDate.of(2022, 06, 14);
				List<CourseDetail> cdlist = cdRepository.findByStartDateAfter(date);
				CourseDetail cd = cdlist.get(0);		
				scRepository.save(new StudentCourse(s1, cd, 0.0));

				Student s2 = studentRepository.findByUsername("youcheng");
				scRepository.save(new StudentCourse(s2, cd, 0.0));

			}
		};
	}

}
