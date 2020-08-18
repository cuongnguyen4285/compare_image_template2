package utils;

import java.io.File;

public class FileUtil {

    public static void delete(String filePath){
        File file = new File(filePath);

        if(file.exists()){
            file.delete();
            System.out.println("File deleted successfully");
        } else {
            System.out.println("File path doesn't exist");
        }
    }
}
