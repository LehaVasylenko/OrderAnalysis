package sftpreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

import static foldedCreator.Folder.createFolderIfNotExists;

public class TarGZ extends BaseSFTP{
    private static final Logger logger = LoggerFactory.getLogger(TarGZ.class);

    private void extractTarGz(String compressedFile, String decompressedFile) {
        byte[] buffer = new byte[1024];
        String fileName = "";
        try {
            FileInputStream fileIn = new FileInputStream(compressedFile);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
            FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);
            int bytes_read;
            while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bytes_read);
            }
            gZIPInputStream.close();
            fileOutputStream.close();

            File file = new File(compressedFile);
            fileName = file.getName();
            logger.info("The file " + fileName + " was decompressed successfully!");
        } catch (IOException ex) {
            logger.error("Error decompressing " + fileName + " " + ex.getMessage());
        }
    }

    public TarGZ (String archiveName)  {
        String archiveDirectory = Paths.get(PROJECT_ROOT, PRICES_DIR).toString();
        String sourceDirectory = Paths.get(PROJECT_ROOT, SOURCE_DIR).toString();

        try {
            createFolderIfNotExists(sourceDirectory);
        } catch (IOException e) {
            logger.error("Can't create a folder " + sourceDirectory);
        }
        extractTarGz(archiveDirectory + File.separator + archiveName + ".gz",
                    sourceDirectory + File.separator + archiveName);
        }
    }
