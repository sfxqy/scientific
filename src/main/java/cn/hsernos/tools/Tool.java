package cn.hsernos.tools;

import java.util.List;

public class Tool {


    public static <T> T getListFirst(List<T> list) {
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
