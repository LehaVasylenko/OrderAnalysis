package sftpreader;

import sftpreader.shopPrice.ShopPrice;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static foldedCreator.Folder.createFolderIfNotExists;

public class SFTP extends BaseSFTP{
    private static final Logger logger = LoggerFactory.getLogger(SFTP.class);
    private static final String KEY_IMG_UPLOAD = "key/img-upload";
    private static final String RUNNINGMASTER_GEOAPTD_SRC = "/home/runningmaster/geoaptd/src/";
    private static final String PASSPHRASE = "";
    private static final String HOST = "api-geoapteka.morion.local";
    private static final String USERNAME = "img-upload";
    private static final String EXTENSION = ".gz";

    private List<ShopPrice> files;
    private List<String> downloadedFiles = new ArrayList<>();

    public SFTP(List<ShopPrice> files, int hours) throws IOException {
        boolean notFound = false;

        String sourceDirectory = Paths.get(PROJECT_ROOT, PRICES_DIR) + "/";
        createFolderIfNotExists(sourceDirectory);

        try {
            JSch jsch = new JSch();
            // Load private key
            jsch.addIdentity(KEY_IMG_UPLOAD, PASSPHRASE);

            // Create session
            Session session = jsch.getSession(USERNAME, HOST, 22);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // Connect to the remote server
            session.connect();

            // Create SFTP channel
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            for (ShopPrice file: files) {
                // Retrieve file attributes
                String remoteFilePath = RUNNINGMASTER_GEOAPTD_SRC + file.getShopId() + EXTENSION;
                sourceDirectory = Paths.get(PROJECT_ROOT, PRICES_DIR) + "/" + file.getShopId() + EXTENSION;
                SftpATTRS fileAttributes;
                long lastModifiedTime = 0;

                try {
                    fileAttributes = sftpChannel.lstat(remoteFilePath);
                    lastModifiedTime = fileAttributes.getMTime() * 1000L; // Convert to milliseconds
                } catch (SftpException e) {
                    // Handle the exception (e.g., file not found)
                    logger.error("File " + file + " not found on the server.");
                    notFound = true;
                }
                if (!notFound) {
                    // Check if the file was modified within the last 3 hours
                    long hoursAgo = System.currentTimeMillis() - ((long) hours * 60 * 60 * 1000);
                    if (lastModifiedTime >= hoursAgo) {
                        // Download file
                        sftpChannel.get(remoteFilePath, sourceDirectory);
                        file.setPriceTime(lastModifiedTime);
                        this.downloadedFiles.add(file.getShopId());
                        logger.info("File " + file.getShopId() + " downloaded successfully!");
                    } else {
                        logger.error("File " + file.getShopId() + " not downloaded. Last modified time more than " + hours + " hours ago.");
                    }
                }
            }
            // Disconnect
            sftpChannel.disconnect();
            session.disconnect();

        } catch (JSchException | SftpException e) {
            logger.error("Error SFTP connection: " + e.getMessage());
        }
        this.files = files;
    }

    public List<ShopPrice> getFiles() {
        return files;
    }
}