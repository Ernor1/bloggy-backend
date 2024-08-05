package rw.global.qt.bloggy.services.serviceImpls;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rw.global.qt.bloggy.dtos.requests.CreateAdminDTO;
import rw.global.qt.bloggy.dtos.requests.CreateUserDTO;
import rw.global.qt.bloggy.enums.Roles;
import rw.global.qt.bloggy.exceptions.BadRequestException;
import rw.global.qt.bloggy.exceptions.NotFoundException;
import rw.global.qt.bloggy.exceptions.UnAuthorizedException;
import rw.global.qt.bloggy.models.Person;
import rw.global.qt.bloggy.models.Role;
import rw.global.qt.bloggy.models.User;
import rw.global.qt.bloggy.repositories.IRoleRepository;
import rw.global.qt.bloggy.repositories.IUserRepository;
import rw.global.qt.bloggy.security.user.UserSecurityDetails;
import rw.global.qt.bloggy.services.IUserService;
import rw.global.qt.bloggy.utils.ExceptionUtils;
import rw.global.qt.bloggy.utils.Hash;
import rw.global.qt.bloggy.utils.Utility;


import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final MailServiceImpl mailService;



    public  boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        } else {
            return true;
        }
    }

    public User getLoggedInUser() {
        UserSecurityDetails userSecurityDetails;
        // Retrieve the currently authenticated user from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("here");
            System.out.println(authentication.getPrincipal() );
            System.out.println(authentication.getAuthorities().size());
            userSecurityDetails = (UserSecurityDetails) authentication.getPrincipal();
            return this.userRepository.findUserByEmail(userSecurityDetails.getUsername()).orElseThrow(() -> new UnAuthorizedException("You are not authorized! please login"));
        } else {
            throw new UnAuthorizedException("You are not authorized! please login");
        }
    }

    @Override
    public User getUserById(UUID uuid) {
        System.out.println(uuid);
        Optional<User> user =  userRepository.findById(uuid);
        if(user.isEmpty()) throw  new NotFoundException("The profile with the provided id is not found");
        return user.get();
    }

    public boolean isNotUnique(Person user) {
        return this.userRepository.findUserByEmail(user.getEmail()).isPresent();
    }

    public boolean validateUserEntry(Person user) {
        if (isNotUnique(user)) {
            String errorMessage = "The user with the email: " + user.getEmail() +
                    " already exists";
            throw new BadRequestException(errorMessage);
        } else {
            return true;
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public User findById(UUID id) {
        try {
            return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public User saveUser(CreateUserDTO userDto, Roles role) {
        try {
            if(userRepository.findUserByEmail(userDto.getEmail()).isPresent()){
                throw new BadRequestException("Email already in use");
            }
            User user1= new User(userDto.getFirstName(),userDto.getLastName(),userDto.getEmail(),userDto.getUsername(), Utility.generatedCode());
            user1.setPassword(Hash.hashPassword(userDto.getPassword()));

            Set<Role> roles= new HashSet<>();
            roles.add(roleRepository.findByRoleName(role.toString()));
            user1.setRoles(roles);
           User user= userRepository.save(user1);
           if(user !=null){
               mailService.sendAccountVerificationEmail(user1);

           }
            return user;

        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;

        }
    }

    @Override
    public User createAdmin(CreateAdminDTO userDto) {
        try{
            if(userRepository.findUserByEmail(userDto.getEmail()).isPresent()){
                throw new BadRequestException("Email already in use");
            }
            if(userDto.getAdminKey().equals("KeyAdminKeyAdmin")){
                User user1= new User(userDto.getFirstName(),userDto.getLastName(),userDto.getEmail(),userDto.getUsername(),Utility.generatedCode());
                user1.setPassword(Hash.hashPassword(userDto.getPassword()));
                Set<Role> roles= new HashSet<>();
                roles.add(roleRepository.findByRoleName(Roles.ADMIN.toString()));
                user1.setRoles(roles);
                User user= userRepository.save(user1);
                if(user !=null){
                    mailService.sendAccountVerificationEmail(user1);

                }
                return user;

            }else{
                throw new BadRequestException("Invalid admin key");
            }

        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

}
