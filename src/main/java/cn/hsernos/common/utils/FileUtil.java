package cn.hsernos.common.utils;

import cn.hsernos.common.exceptions.CheckException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {


    /**
     * 获取时间戳字符串
     */
    public static String getTimer() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }


    /**
     * 获取随机数字符串
     */
    public static String getRandom(int a) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder("");
        for (int i = 0; i < a; i++) {
            int b = random.nextInt(10);
            buffer.append(b).append("");
        }
        return buffer.toString();
    }

    /**
     * 获取后缀名
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getFileFormatName(String fileName) {
        int num = fileName.lastIndexOf(".");
        if (num > 0 && num != (fileName.length() - 1)) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return null;
    }

    /**
     * 是否图片
     *
     * @param fileName 文件名
     * @return true or false
     */
    public static Boolean isImg(String fileName) {
        String[] imgs = {".jpg", ".png", ".jpeg", ".gif", ".bmp"};
        String fileFormat = getFileFormatName(fileName);
        if (fileFormat != null) {
            for (String str : imgs) {
                if (str.toLowerCase().equals(fileFormat.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 保存图片文件
     *
     * @param file         文件
     * @param relativePath 相对路径
     * @return 路径
     */
    public static String saveImgFile(HttpSession session, MultipartFile file, String relativePath) throws IOException {
        if (file == null || file.getSize() == 0) {
            return null;
        }
        if (isImg(file.getOriginalFilename())) {
            return saveFile(session, file, relativePath);
        } else {
            throw new CheckException("不支持该文件类型");
        }
    }

    /**
     * 保存文件
     *
     * @param file         文件
     * @param relativePath 相对路径
     * @return 路径
     */
    public static String saveFile(HttpSession session, MultipartFile file, String relativePath) throws IOException {
        if (file == null && file.getSize() == 0) {
            return null;
        }
        String path = session.getServletContext().getRealPath(relativePath);
        String filename = getTimer() + getRandom(2) + getFileFormatName(file.getOriginalFilename());
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        String realPath = path + File.separator + filename;
        file.transferTo(new File(realPath));
        return relativePath + filename;
    }

    /**
     * 保存文件组
     *
     * @param files        文件组
     * @param relativePath 相对路径
     * @return 路径
     */
    public static String saveFiles(HttpSession session, MultipartFile[] files, String relativePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (MultipartFile file : files) {
            String str = saveFile(session, file, relativePath);
            if (str != null) {
                sb.append("&&");
                sb.append(str);
            }
        }
        if (sb.length() > 0) {
            return sb.substring(2);
        } else {
            return null;
        }
    }


    /**
     * 删除文件集合
     */
    public static void deleteFiles(HttpSession session, String[] paths) {
        if (paths == null) {
            return;
        }
        for (String path : paths) {
            deleteFile(session, path);
        }

    }

    /**
     * 删除文件
     */
    public static void deleteFile(HttpSession session, String path) {
        if (path == null) {
            return;
        }
        String realURL = session.getServletContext().getRealPath(path);
        File file = new File(realURL);
        if (file.exists()) {
            file.delete();
        }

    }


    public static String Change(String log_time) {
        return log_time.replace('/', '-');
    }

    public static void delFolder(String folderPath) {
        try {
            File file = new File(folderPath);
            if (file.exists()) {
                //删除完里面所有内容
                delAllFile(folderPath);
                String filePath = folderPath;
                filePath = filePath.toString();
                File myFilePath = new File(filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                //再删除空文件夹
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simple.format(date);
    }
}


