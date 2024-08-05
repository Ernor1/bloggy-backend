package rw.global.qt.bloggy;

import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import rw.global.qt.bloggy.enums.Roles;
import rw.global.qt.bloggy.models.Role;
import rw.global.qt.bloggy.repositories.IRoleRepository;

import java.util.HashSet;
import java.util.Set;
@SpringBootApplication
@EnableCaching
@IgnoreForbiddenApisErrors(reason = "json")
@EnableWebSecurity
@EnableGlobalAuthentication()
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@EnableTransactionManagement
public class BlogStarterApplication {
    private final IRoleRepository roleRepository;

    public BlogStarterApplication(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogStarterApplication.class, args);
    }
    @Bean
    public void registerRoles(){
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ADMIN);
        userRoles.add(Roles.AUTHOR);
        userRoles.add(Roles.USER);

        for(Roles role : userRoles ){
            Role sampleRole = new Role(role.toString());
            if(roleRepository.findByRoleName(role.name()) == null){
                roleRepository.save(sampleRole);
            }
        }
    }

}
