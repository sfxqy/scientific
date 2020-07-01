package cn.hsernos.common.beans;

import lombok.Data;

import javax.servlet.http.HttpSession;

/**
 * 放在session中的内容
 */
@Data
public class User {
    private Integer id;
    private String username;
    private Integer power;

    public static Integer getId(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public static String getUsername(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return null;
        }
        return user.getUsername();
    }

    public static Integer getPower(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return null;
        }
        return user.getPower();
    }
}
