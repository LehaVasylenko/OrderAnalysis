package classes;

import classes.excelExecutor.PhoneBase;
import classes.order.Datum;
import classes.order.OrderList;
import classes.order.OrderLog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static foldedCreator.Folder.createFolderIfNotExists;


public class ExcelWriter {
    private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
    private String backUpPath;
    private String reportPath;
    private ArrayList<PhoneBase> excelReader() {
        String filePath = "phoneBase/base.xlsx";
        ArrayList<PhoneBase> dataBase = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String name = row.getCell(0).getStringCellValue();
                String phone = row.getCell(1).getStringCellValue();

                PhoneBase obj = new PhoneBase(name, phone.replaceAll("\\+",""));
                dataBase.add(obj);
            }

        } catch (Exception e) {
            logger.error("Error reading phone base: " + e.getMessage());
        }
        return dataBase;
    }

    private String getDateNow() {
        DateTimeFormatter formatterOnlyDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");// HH-mm
        return LocalDateTime.now().format(formatterOnlyDate);
    }

    private String getDesktopPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (osName.contains("win")) {
            return userHome + "\\OneDrive - Proximaresearch International LLC\\Робочий стіл";
        } else if (osName.contains("mac")) {
            return userHome + "/Desktop";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return userHome + "/Desktop";
        } else {
            return "";
        }
    }

    private void folderPreparation() {
        final String BASE_FOLDER = "report";
        final String BACK_UP_FOLDER = "backUp";

        try {
            String base = getDesktopPath() + File.separator + BASE_FOLDER;
            createFolderIfNotExists(base);

            this.backUpPath = base + File.separator + BACK_UP_FOLDER;
            createFolderIfNotExists(this.backUpPath);
            createFolderIfNotExists(base + File.separator + getDateNow());

            this.reportPath = base + File.separator + getDateNow() + File.separator + LocalDateTime.now().getHour();
            createFolderIfNotExists(this.reportPath);

        } catch (Exception e) {
            logger.error("Bad deals with a folder: " + e.getMessage());
        }

    }

    public ExcelWriter(List<List<OrderList>> orderList) {
        //prepare result folders
        folderPreparation();

        for (List<OrderList> ordersByCorp: orderList) {
            writeToExcel(ordersByCorp);
        }

    }

    private void writeToExcel(List<OrderList> orders) {
        //get phone base
        ArrayList<PhoneBase> phones = excelReader();

        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a new sheet
            Sheet sheet = workbook.createSheet(orders.get(0).getIdCorp());
            // Create a header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"IdMorion", "IdExt", "Name", "Head", "Phone", "OrderId", "StoreOrderId", "Time", "Agent",
                    "Shipping", "Status", "Reason", "DrugId", "DrugName", "OrderPrice", "OrderQuantity", "basePrice",
                    "baseQuant", "Shop drugId", "Shop drugName", "ShopPrice", "ShopQuantity", "Time received"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            // Fill the sheet with data for the current user
            int rowIndex = 1;
            for (OrderList order : orders) {
                OrderLog[] log = order.getOrder();
                for (int i = 0; i < log.length; i++) {
                    int k = 0;
                    Row row = sheet.createRow(rowIndex++);

                    row.createCell(k++).setCellValue(log[i].getShopInfo().getId());
                    row.createCell(k++).setCellValue(log[i].getShopInfo().getId_ex());
                    row.createCell(k++).setCellValue(log[i].getShopInfo().getName() + ", " + log[i].getShopInfo().getAddr_city_ua() + ", " + log[i].getShopInfo().getAddr_street_ua());
                    row.createCell(k++).setCellValue(log[i].getShopInfo().getCorp_ua());

                    boolean equal = false;
                    for (PhoneBase ph : phones) {
                        if (log[i].getPhone().equals(ph.getPhone())) {
                            row.createCell(k++).setCellValue(ph.getName());
                            equal = true;
                            break;
                        }
                    }
                    if (!equal) {
                        row.createCell(k++).setCellValue(log[i].getPhone());
                    }

                    row.createCell(k++).setCellValue(log[i].getId_order());
                    row.createCell(k++).setCellValue(log[i].getExt_id_order());
                    row.createCell(k++).setCellValue(log[i].convertTimestamp());
                    row.createCell(k++).setCellValue(log[i].getAgent());

                    try {
                        if (log[i].getShipping().equalsIgnoreCase("optima")) {
                            Cell cell = row.createCell(k); // Create the cell
                            fillCellWithColor(cell, IndexedColors.YELLOW.getIndex()); // Fill the cell with color
                            cell.setCellValue(log[i].getShipping()); // Set the cell value
                        } else {
                            row.createCell(k).setCellValue(log[i].getShipping());
                        }
                    } catch (Exception e) {
                        row.createCell(k).setCellValue(e.getMessage());
                    }
                    k++;

                    row.createCell(k++).setCellValue(log[i].getState());
                    row.createCell(k++).setCellValue(log[i].getReason());

                    for (Datum items: log[i].getData()) {
                        try {
                            row.createCell(k).setCellValue(items.getId());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue(0);
                            logger.error("ID in " + rowIndex + " didn't received: " + e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getDrugInfo());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue("Unknown prep");
                            logger.error("Error receiving Drug name in " + rowIndex + ": " + e.getMessage());
                        }

                        try {
                            row.createCell(k).setCellValue(items.getPrice());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue("No price");
                            logger.error("Error receiving Drug price in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;

                        try {
                            if (items.getQuant() == 0) {
                                Cell cell = row.createCell(k); // Create the cell
                                fillCellWithColor(cell, IndexedColors.RED1.getIndex()); // Fill the cell with color
                                cell.setCellValue(items.getQuant()); // Set the cell value
                            } else {
                                row.createCell(k).setCellValue(items.getQuant());
                            }
                        } catch (Exception e) {
                            row.createCell(k).setCellValue(e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getApi().getPrice());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue(0);
                            logger.error("Base Price in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getApi().getQuant());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue(0);
                            logger.error("Base quantity in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getSftp().getId());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue("null");
                            logger.error("No price list info for ID in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getSftp().getName());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue("No data available");
                            logger.error("No price list info for Drug Name in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getSftp().getPrice());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue("null");
                            logger.error("No price list info for Price in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getSftp().getQuant());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue("null");
                            logger.error("No price list info for Quantity in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;

                        try {
                            row.createCell(k).setCellValue(items.getSftp().getLink().getId());
                        } catch (Exception e) {
                            row.createCell(k).setCellValue("null");
                            logger.error("No price list info for Time Received in " + rowIndex + ": " + e.getMessage());
                        }
                        k++;
                    }
                }
                rowIndex++;
            }
            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream(this.reportPath + File.separator + orders.get(0).getIdCorp() + ".xlsx");
                    FileOutputStream fileBackUp = new FileOutputStream(this.backUpPath + File.separator + getDateNow() + "_" + LocalDateTime.now().getHour() + "_" + orders.get(0).getIdCorp() + ".xlsx")) {
                workbook.write(fileOut);
                workbook.write(fileBackUp);
                logger.info("Excel file for " + orders.get(0).getIdCorp() + " created successfully.");
            } catch (Exception e) {
                logger.error("Error creating excel file " + e.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error creating excel file " + e.getMessage());
        }
    }

    private static void fillCellWithColor(Cell cell, short colorIndex) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(colorIndex);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
    }



}
