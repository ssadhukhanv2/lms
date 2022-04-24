package com.ssadhukhanv2.lms.librarymanagementui;

import com.ssadhukhanv2.lms.librarymanagementui.entity.Role;
import com.ssadhukhanv2.lms.librarymanagementui.entity.User;
import com.ssadhukhanv2.lms.librarymanagementui.repository.RoleRepository;
import com.ssadhukhanv2.lms.librarymanagementui.repository.UserRepository;
import com.ssadhukhanv2.lms.librarymanagementui.enumeration.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
@EnableFeignClients
//@EnableDiscoveryClient
public class LibraryManagementUiApplication implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementUiApplication.class, args);
    }

    @Override
    @Transactional
    //auto id generation from database using postgres
    //call a stored procedure postgres
    //https://stackoverflow.com/questions/19582219/hibernate-and-oracle-sequence
    //https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-sequences/
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("super@2021"));
        System.out.println(passwordEncoder.encode("admin@2021"));

//        Role roleAdmin = new Role();
//        roleAdmin.setUserRole(UserRole.ADMIN_USER);
//
//        Role roleBusiness = new Role();
//        roleBusiness.setUserRole(UserRole.BUSINESS_USER);
//
//        roleRepository.saveAll(Set.of(roleAdmin, roleBusiness));
//
//
//        User userAdmin = new User();
//        userAdmin.setUserEmail("admin@gmail.com");
//        userAdmin.setUserName("admin");
//        userAdmin.setUserPassword(passwordEncoder.encode("admin@2021"));
//        userAdmin.setUserAccountEnabled(true);
//        userAdmin.addRole(roleAdmin);
//
//        User superUser = new User();
//        superUser.setUserEmail("super@gmail.com");
//        superUser.setUserName("superuser");
//        superUser.setUserPassword(passwordEncoder.encode("super@2021"));
//        superUser.setUserAccountEnabled(true);
//        superUser.addRole(roleAdmin);
//        superUser.addRole(roleBusiness);
//
//        userRepository.saveAll(Set.of(userAdmin, superUser));

    }
}
