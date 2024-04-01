import classes.DrugsInfoMany;
import sftpreader.SftpManager;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static foldedCreator.Folder.createFolderIfNotExists;

public class test {
    @Test
    public void getDate() {
        String s1 = "car";
        String s2 = "car";
        String s3 = new String("car");

        System.out.println(s1==s2);
        System.out.println(s1==s3);

    }

    private String getDateNow() {
        DateTimeFormatter formatterOnlyDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");// HH-mm
        return LocalDateTime.now().format(formatterOnlyDate);
    }
    private String folderPreparation() {
        final String BASE_FOLDER = "report";
         String dateFolder = "";
         String timeFolder = "";
         final String BACK_UP_FOLDER = "backUp";
        String result = "";
        try {
            String base = getDesktopPath() + File.separator + BASE_FOLDER;
            createFolderIfNotExists(base);
            createFolderIfNotExists(base + File.separator + BACK_UP_FOLDER);
            createFolderIfNotExists(base + File.separator + getDateNow());
            result = base + File.separator + getDateNow() + File.separator + LocalDateTime.now().getHour();
            createFolderIfNotExists(result);

        } catch (Exception e) {
            //logger.error("Bad deals with a folder: " + e.getMessage());
        }
        return result;
    }

    public static String getDesktopPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (osName.contains("win")) {
            return userHome + "\\OneDrive - Proximaresearch International LLC\\Робочий стіл";
        } else if (osName.contains("mac")) {
            return userHome + "/Desktop";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return userHome + "/Desktop";
        } else {
            // Handle other operating systems if needed
            return null;
        }
    }
}
