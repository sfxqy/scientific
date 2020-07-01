package cn.hsernos.common.utils;

import cn.hsernos.common.exceptions.CheckException;
import org.springframework.context.MessageSource;

import java.lang.reflect.Field;
import java.util.Arrays;


public class CheckUtil {

    private static MessageSource resources;

    public static void setResources(MessageSource resources) {
        CheckUtil.resources = resources;
    }

    public static void check(boolean condition, String msg, Object... args) {
        if (!condition) {
            fail(msg, args);
        }
    }

    public static void mustEmpty(String str, String msg, Object... args) {
        if (str != null && (!str.trim().isEmpty())) {
            fail(msg, args);
        }
    }

    public static void mustNull(Object obj, String msg, Object... args) {
        if (obj != null) {
            fail(msg, args);
        }
    }


    public static void notEmpty(String str, String msg, Object... args) {
        if (str == null || str.trim().isEmpty()) {
            fail(msg, args);
        }
    }

    public static void notNull(Object obj, String msg, Object... args) {
        if (obj == null) {
            fail(msg, args);
        }
    }

    public static void fail(String msg, Object... args) {
        if (msg.indexOf(".") > 0) {
            throw new CheckException(resources.getMessage(msg, args, UserUtil.getLocale()));
        } else {
            throw new CheckException(msg);
        }
    }

    /**
     * 数组中的属性名必为不空，其他随意
     */
    public static void notNull(Object obj, String msg, String arg, Object... args) {
        notNull(obj, msg);
        String[] strings = arg.split("&");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String type = field.getGenericType().toString();
            if (Arrays.asList(strings).contains(name)) {
                if (type.equals("class java.lang.String")) {
                    notEmpty((String) value, msg, args);
                } else {
                    notNull(value, msg, args);
                }

            }
        }
    }


    /**
     * 数组中的属性名必为不空，其他必为空
     */
    public static void notNullOtherOpposite(Object obj, String msg, String arg, Object... args) {
        notNull(obj, msg);
        String[] strings = arg.split("&");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String type = field.getGenericType().toString();
            if (Arrays.asList(strings).contains(name)) {
                if (type.equals("class java.lang.String")) {
                    notEmpty((String) value, msg, args);
                } else {
                    notNull(value, msg, args);
                }

            } else {
                if (type.equals("class java.lang.String")) {
                    mustEmpty((String) value, msg, args);
                } else {
                    mustNull(value, msg, args);
                }
            }
        }
    }

    /**
     * 数组中的属性名必为空，其他必为不空
     */
    public static void mustNullOtherOpposite(Object obj, String msg, String arg, Object... args) {
        notNull(obj, msg);
        String[] strings = arg.split("&");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String type = field.getGenericType().toString();
            if (Arrays.asList(strings).contains(name)) {
                if (type.equals("class java.lang.String")) {
                    mustEmpty((String) value, msg, args);
                } else {
                    mustNull(value, msg);
                }
            } else {
                if (type.equals("class java.lang.String")) {
                    notEmpty((String) value, msg, args);
                } else {
                    notNull(value, msg, args);
                }
            }
        }
    }

    /**
     * 数组中的属性名必为空，其他随意
     */
    public static void mustNull(Object obj, String msg, String arg, Object... args) {
        notNull(obj, msg);
        String[] strings = arg.split("&");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String type = field.getGenericType().toString();
            if (Arrays.asList(strings).contains(name)) {
                mustNull(value, msg, args);
            }
        }
    }

}
