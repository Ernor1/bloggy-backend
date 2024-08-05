package rw.global.qt.bloggy.utils;


import rw.global.qt.bloggy.enums.EVisibility;
import rw.global.qt.bloggy.models.User;

public class ServiceUtils {
    // method to check if a user is valid or deleted
    public static boolean isUserDeleted(User user) {
        return user.getVisibility().equals(EVisibility.VOIDED);
    }
}
