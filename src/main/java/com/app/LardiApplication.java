package com.app;

import com.app.entity.Contact;
import com.app.entity.User;
import com.app.entity.UserRole;
import com.app.service.ContactService;
import com.app.service.UserRoleService;
import com.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class LardiApplication {

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService;
	@Autowired
	private ContactService contactService;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String db;
	@Value("${use.file.database}")
	private boolean useFileDB;
	@Value("${use.file.database.ddl-auto}")
	public String fileDB;
	@Value("${use.file.database.path}")
	public String fileDBPath;
	@Value("${test}")
	public boolean test;


	public static void main(String[] args) {ApplicationContext ctx = SpringApplication.run(LardiApplication.class, args);}

	@PostConstruct
	public void createOnStartup(){
		File f = new File(fileDBPath);
		if (!f.exists()){
			new File(fileDBPath).mkdirs();
		}
		if (test){
			createTestData();
		} else {
			if (useFileDB){
				if (fileDB.contains("create")){
					new File(fileDBPath + "contacts/").mkdir();
					try {
						FileUtils.cleanDirectory(new File(fileDBPath));
					} catch (IOException e) {
						e.printStackTrace();
					}
					new File(fileDBPath + "contacts/").mkdir();
					UserRole role = new UserRole();
					role.setName("ROLE_USER");
					ObjectMapper mapper = new ObjectMapper();
					List<User> list = new ArrayList<>();
					try {
						mapper.writeValue(new File(fileDBPath+"users.json"), list);
						mapper.writeValue(new File(fileDBPath+"userRole.json"), role);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (db.contains("create")){
					UserRole role = new UserRole();
					role.setName("ROLE_USER");
					userRoleService.update(role);
				}
			}
		}
	}

	private void createTestData(){
		try {
			FileUtils.cleanDirectory(new File(fileDBPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		new File(fileDBPath + "contacts/").mkdir();
		UserRole role = new UserRole();
		role.setName("ROLE_USER");
		role.setId(1L);
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setId(1L);
		user.setLogin("test");
		user.setPassword("testtest");
		user.setFirstName("test");
		user.setLastName("test");
		user.setForeName("test");
		user.setRole(role);
		user.setContacts(null);
		List<User> list = new ArrayList<>();
		list.add(user);
		Contact contact = new Contact();
		contact.setFirstName("Firstname");
		contact.setLastName("Lastname");
		contact.setForeName("Forename");
		contact.setMobilePhone("+380991112233");
		contact.setHomePhone("4444444");
		contact.setUser(user);
		contact.setEmail("email@email.com");
		contact.setAddress("test Address");
		contact.setId(1L);
		List<Contact> contacts = new ArrayList<>();
		contacts.add(contact);
		try {
			mapper.writeValue(new File(fileDBPath+"users.json"), list);
			mapper.writeValue(new File(fileDBPath+"userRole.json"), role);
			mapper.writeValue(new File(fileDBPath+"contacts/contact_"+user.getLogin()+".json"), contacts);
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserRole role1 = new UserRole();
		role1.setName("ROLE_USER");
		role1 = userRoleService.update(role1);
		User user1 = new User();
		user1.setLogin("test");
		user1.setPassword("testtest");
		user1.setFirstName("test");
		user1.setLastName("test");
		user1.setForeName("test");
		user1.setRole(role1);
		user1.setContacts(null);
		user1 = userService.update(user1);
		Contact contact1 = new Contact();
		contact1.setFirstName("Firstname");
		contact1.setLastName("Lastname");
		contact1.setForeName("Forename");
		contact1.setMobilePhone("+380991112233");
		contact1.setHomePhone("4444444");
		contact1.setUser(user1);
		contact1.setEmail("email@email.com");
		contact1.setAddress("test Address");
		contactService.update(contact1);
	}
}
