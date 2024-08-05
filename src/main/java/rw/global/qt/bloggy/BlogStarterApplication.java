package rw.global.qt.bloggy;

import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import rw.global.qt.bloggy.enums.EUserStatus;
import rw.global.qt.bloggy.enums.Roles;
import rw.global.qt.bloggy.exceptions.BadRequestException;
import rw.global.qt.bloggy.models.Category;
import rw.global.qt.bloggy.models.Role;
import rw.global.qt.bloggy.models.Tag;
import rw.global.qt.bloggy.models.User;
import rw.global.qt.bloggy.repositories.ICategoryRepository;
import rw.global.qt.bloggy.repositories.IRoleRepository;
import rw.global.qt.bloggy.repositories.ITagRepository;
import rw.global.qt.bloggy.repositories.IUserRepository;
import rw.global.qt.bloggy.utils.ExceptionUtils;
import rw.global.qt.bloggy.utils.Hash;
import rw.global.qt.bloggy.utils.Utility;

import java.util.HashSet;
import java.util.Set;
@SpringBootApplication
@EnableCaching
@IgnoreForbiddenApisErrors(reason = "json")
@EnableWebSecurity
@EnableGlobalAuthentication()
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@EnableTransactionManagement
@AllArgsConstructor
public class BlogStarterApplication {
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final ITagRepository tagRepository;


    public static void main(String[] args) {
        SpringApplication.run(BlogStarterApplication.class, args);
    }

    @Bean
    public void registerRoles() {
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ADMIN);
        userRoles.add(Roles.AUTHOR);
        userRoles.add(Roles.USER);

        for (Roles role : userRoles) {
            Role sampleRole = new Role(role.toString());
            if (roleRepository.findByRoleName(role.name()) == null) {
                roleRepository.save(sampleRole);
            }
        }
    }

    @Bean
    public void registerDefaultAdmin() {
        try {
            if(userRepository.findUserByEmail("honorerukundo74@gmail.com").isEmpty()){

                User user = new User("Rukundo", "Honore", "honorerukundo74@gmail.com", "Ernor", "12345678");
                user.setPassword(Hash.hashPassword("?Dh344440"));
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByRoleName(Roles.ADMIN.toString()));
                user.setRoles(roles);
                user.setAccountStatus(EUserStatus.ACTIVE);
                userRepository.save(user);

            }

        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
        }
    }

    @Bean
    public void registerDefaultUser() {
        try {
            if(userRepository.findUserByEmail("honoreerukundo@gmail.com").isEmpty()){
                User user = new User("Rukundo", "David", "honoreerukundo@gmail.com", "rukundo", "12345678");
                user.setAccountStatus(EUserStatus.ACTIVE);
                user.setPassword(Hash.hashPassword("?Dh344440"));
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByRoleName(Roles.AUTHOR.toString()));
                user.setRoles(roles);
                userRepository.save(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public void defaultCategories() {
        try {

            Set<Category> categories = new HashSet<>();
            Category category = new Category("Technology", "Technology");
            categories.add(category);
            Category category1 = new Category("Health", "Health");
            categories.add(category1);
            Category category2 = new Category("Business", "Business");
            categories.add(category2);
            Category category3 = new Category("Entertainment", "Entertainment");
            categories.add(category3);
            Category category4 = new Category("Politics", "Politics");
            categories.add(category4);
            Category category5 = new Category("Sports", "Sports");
            categories.add(category5);
            for (Category category6 : categories) {
                if (categoryRepository.findByName(category6.getName()).isEmpty()) {
                    categoryRepository.save(category6);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Bean
    public void defaultTags(){
        try {
            Set<Tag> tags= new HashSet<>();
            tags.add(new Tag("Technology","Technology"));
            tags.add(new Tag("Health","Health"));
            tags.add(new Tag("Business","Business"));
            tags.add(new Tag("Entertainment","Entertainment"));
            tags.add(new Tag("Politics","Politics"));
            tags.add(new Tag("Sports","Sports"));
            for (Tag tag: tags){
                if(tagRepository.findByName(tag.getName()).isEmpty()){
                    tagRepository.save(tag);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}