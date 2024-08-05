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
            categories.add(new Category("Technology", "Posts about the latest technology trends and innovations."));
            categories.add(new Category("Health & Wellness", "Articles on physical health, mental wellness, and healthy living."));
            categories.add(new Category("Business & Finance", "Insights on business strategies, financial planning, and economic trends."));
            categories.add(new Category("Entertainment", "Content related to movies, music, games, and other forms of entertainment."));
            categories.add(new Category("Politics", "Discussions on political news, policies, and global affairs."));
            categories.add(new Category("Sports", "Updates and commentary on various sports events and activities."));
            categories.add(new Category("Lifestyle", "Topics related to personal lifestyle, fashion, and daily living tips."));
            categories.add(new Category("Travel", "Guides and experiences related to traveling, destinations, and adventures."));
            categories.add(new Category("Food & Drink", "Recipes, food reviews, and beverage recommendations."));
            categories.add(new Category("Personal Development", "Advice and tips on personal growth, skills development, and self-improvement."));

            for (Category category : categories) {
                if (categoryRepository.findByName(category.getName()).isEmpty()) {
                    categoryRepository.save(category);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public void defaultTags() {
        try {
            Set<Tag> tags = new HashSet<>();
            tags.add(new Tag("Artificial Intelligence", "Posts about advancements and trends in AI technology."));
            tags.add(new Tag("Mental Health", "Articles focused on mental health issues, wellness practices, and support."));
            tags.add(new Tag("Startups", "Insights and stories about new businesses and entrepreneurial ventures."));
            tags.add(new Tag("Movies", "Reviews and discussions about films and cinematic experiences."));
            tags.add(new Tag("Elections", "Coverage of electoral processes, candidates, and political campaigns."));
            tags.add(new Tag("Soccer", "News and updates related to soccer events and teams."));
            tags.add(new Tag("Self-Care", "Tips and advice on self-care routines and practices for personal well-being."));
            tags.add(new Tag("Adventure", "Content related to adventurous activities, exploration, and travel experiences."));
            tags.add(new Tag("Recipes", "Cooking tips, recipes, and food preparation guides."));
            tags.add(new Tag("Productivity", "Strategies and advice on improving efficiency and productivity in personal and professional life."));

            for (Tag tag : tags) {
                if (tagRepository.findByName(tag.getName()).isEmpty()) {
                    tagRepository.save(tag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}