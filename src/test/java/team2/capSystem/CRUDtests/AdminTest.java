package team2.capSystem.CRUDtests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import team2.capSystem.model.Admin;
import team2.capSystem.repo.AdminRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AdminTest {
    @Autowired
    private AdminRepository adminRepo;
    
    @Test
    @Order(1)
    @Rollback(false)
    public void createAdminTest() {
    	Admin admin = Admin.builder()
    			.username("admintest")
    			.password("testpassword")
    			.name("admintest")
    			.email("testadmin@gmail.com")
    			.build();
    	adminRepo.save(admin);
    	System.out.println("Admin ID: "+ admin.getStaffId());
    	assertThat(admin.getStaffId()).isGreaterThan(0);
    }
    
    @Test
    @Order(2)
    public void findAdminByNameTest() {
    	Admin admin1 = Admin.builder()
    			.username("adminA")
    			.password("testA")
    			.name("adminA")
    			.email("testA@gmail.com")
    			.build();
    	adminRepo.saveAndFlush(admin1);
    	
    	Admin admin2 = Admin.builder()
    			.username("adminB")
    			.password("testB")
    			.name("adminB")
    			.email("testB@gmail.com")
    			.build();
    	adminRepo.saveAndFlush(admin2);
    	
    	Admin adminFindName = adminRepo.findAdminByName("adminA");
		assertThat(adminFindName.getName()).isEqualTo("adminA");
	
    	
    	Admin adminFindEmail = adminRepo.findAdminByEmail("testB@gmail.com");
		assertThat(adminFindEmail.getEmail()).isEqualTo("testB@gmail.com");
    }
    
  
    @Test
    @Order(3)
    public void listAdminTest() {
    	List<Admin> adminList = (List<Admin>) adminRepo.findAll();
    	assertThat(adminList).size().isGreaterThan(0);
    	
    }
    
    @Test
    @Order(4)
    public void editAdminTest() {
    	Admin admin = Admin.builder()
    			.username("admintest")
    			.password("testpassword")
    			.name("admintest")
    			.email("testadmin@gmail.com")
    			.build();
    	adminRepo.saveAndFlush(admin);
    	Admin adminFind = adminRepo.findAdminByName("admintest");
    	adminFind.setPassword("changedpassword");
    	adminRepo.saveAndFlush(adminFind);
    	Admin editedAdmin = adminRepo.findAdminByName("admintest");
    	assertThat(editedAdmin.getPassword()).isEqualTo("changedpassword");
    	
    }
    
    @Test
    @Order(5)
    @Rollback(false)
    public void deleteAdminTest() {
    	Admin adminFindName = adminRepo.findAdminByName("admintest");
    	adminRepo.deleteById(adminFindName.getStaffId());
    	Admin deletedAdmin = adminRepo.findAdminByName("admintest");
    	assertThat(deletedAdmin).isNull();
    }
}
