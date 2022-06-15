package team2.capSystem;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	

	public static void main(String[] args) {
		System.out.println("Hello world!");
		//System.out.println("Hein typing");
		SpringApplication.run(CapSystemApplication.class, args);
		
		//commit and push
	}
// (String username, String password, String name, String email
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

}
