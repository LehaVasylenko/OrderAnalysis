package sftpreader;

import interfaces.ISftpManager;
import sftpreader.shopPrice.PriceItem;
import sftpreader.shopPrice.ShopPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static sftpreader.FileReader.readJsonArrayFromFile;

public class SftpManager implements ISftpManager {
    private static final Logger logger = LoggerFactory.getLogger(SftpManager.class);
    private List<ShopPrice> shopPrice;

    public SftpManager(List<String> shops) {
        this.shopPrice = new ArrayList<>();
        shops.forEach(shop -> {
            this.shopPrice.add(new ShopPrice(shop));
        });

        download();
        extract();
        read();
        delete();
    }

    private void download() {
        try {
            SFTP sftp = new SFTP(this.shopPrice, 24 * 7);
            this.shopPrice = sftp.getFiles();
        } catch (IOException e) {
            logger.error("Error downloading from SFTP: " + e.getCause());
        }
    }

    private void extract() {
        this.shopPrice.forEach(shop -> new TarGZ(shop.getShopId()));
    }

    private void read() {
        for (ShopPrice shop: this.shopPrice) {
            try {
                shop.setPriceList(readJsonArrayFromFile(shop.getShopId()));
                logger.info("File " + shop.getShopId() + " read successfully!");
            } catch (IOException e) {
                logger.error("Error reading " + shop.getShopId());
            }
        }
    }

    private void delete() {
        this.shopPrice.forEach(shop -> new FileEraser(shop.getShopId()));
    }

    @Override
    public List<ShopPrice> getShopPrice() {
        return shopPrice;
    }
    @Override
    public PriceItem getItemByShopAndId(String idShop, String idDrug) {
        PriceItem result = null;
        float quant = 0;
        String time = "";
        for (ShopPrice shopPrice: this.shopPrice) {
            if (shopPrice.getShopId().equalsIgnoreCase(idShop)) {
                try {
                    result = shopPrice.getItemById(idDrug);
                    result.getLink().setId(convertTime(shopPrice.getPriceTime()));
                } catch (Exception e) {
                    result.getLink().setId("No time");
                    logger.error("No time available for " + shopPrice.getShopId());
                }
            }
        }
        return result;
    }
    private String convertTime (long timestamp) {
        Date date = new Date(timestamp);
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM - HH:mm:ss");
        // Format the date
        return sdf.format(date);
    }
}
