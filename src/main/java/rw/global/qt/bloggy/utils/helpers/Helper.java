package rw.global.qt.bloggy.utils.helpers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rw.global.qt.bloggy.enums.EGender;
import rw.global.qt.bloggy.exceptions.BadRequestException;
import rw.global.qt.bloggy.models.User;
import rw.global.qt.bloggy.services.IUserService;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j

public class Helper {
    private static IUserService userService;
    public Helper(IUserService userService){
        Helper.userService = userService;
    }

    public User getLoggedInUser(HttpServletRequest request){
        return  null;
    }

    public static String getActor(){
        return "Done at : " + LocalDateTime.now() +  " By Email : " +  userService.getLoggedInUser().getEmail() + ", Username : " + userService.getLoggedInUser().getUsername();
    }
    public static void logAction(String message){
        log.info(message);
        log.info(getActor());
    }
    public static EGender getGender(String gender){
        System.out.println("Gender : " + gender);
        switch (gender.toUpperCase()){
            case "MALE", "M":
                return EGender.MALE;
            case "FEMALE", "F":
                return EGender.FEMALE;
            default:
                throw new BadRequestException("The provided gender is invalid");
        }
    }





    public static String generateRandomPasswordString(int length) {
        String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUM = "0123456789";
        String ALPHANUM = ALPHA + NUM;
        Random rng = new Random();
        StringBuilder sb = new StringBuilder();
        while (length > 0) {
            length--;
            sb.append(ALPHANUM.charAt(rng.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
    



}
