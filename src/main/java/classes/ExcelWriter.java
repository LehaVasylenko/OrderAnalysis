package classes;

import classes.excelExecutor.PhoneBase;
import classes.order.Datum;
import classes.order.OrderList;
import classes.order.OrderLog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sftpreader.shopPrice.PriceItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
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
                    Row row = sheet.createRow(rowIndex++);

                    row.createCell(0).setCellValue(log[i].getShopInfo().getId());
                    row.createCell(1).setCellValue(log[i].getShopInfo().getId_ex());
                    row.createCell(2).setCellValue(log[i].getShopInfo().getName() + ", " + log[i].getShopInfo().getAddr_city_ua() + ", " + log[i].getShopInfo().getAddr_street_ua());
                    row.createCell(3).setCellValue(log[i].getShopInfo().getCorp_ua());

                    boolean equal = false;
                    for (PhoneBase ph : phones) {
                        if (log[i].getPhone().equals(ph.getPhone())) {
                            Cell cell = row.createCell(4); // Create the cell
                            fillCellWithColor(cell, IndexedColors.LIME.getIndex()); // Fill the cell with color
                            cell.setCellValue(ph.getName()); // Set the cell value
                            equal = true;
                            break;
                        }
                    }
                    if (!equal) {
                        row.createCell(4).setCellValue(log[i].getPhone());
                    }

                    row.createCell(5).setCellValue(log[i].getId_order());
                    row.createCell(6).setCellValue(log[i].getExt_id_order());
                    row.createCell(7).setCellValue(log[i].convertTimestamp());
                    row.createCell(8).setCellValue(log[i].getAgent());

                    try {
                        if (log[i].getShipping().equalsIgnoreCase("optima")) {
                            Cell cell = row.createCell(9); // Create the cell
                            fillCellWithColor(cell, IndexedColors.YELLOW.getIndex()); // Fill the cell with color
                            cell.setCellValue(log[i].getShipping()); // Set the cell value
                        } else {
                            row.createCell(9).setCellValue(log[i].getShipping());
                        }
                    } catch (Exception e) {
                        row.createCell(9).setCellValue(e.getMessage());
                    }

                    row.createCell(10).setCellValue(log[i].getState());
                    row.createCell(11).setCellValue(log[i].getReason());

                    try {
                        int shownItem = 0;
                        for (Datum items : log[i].getData()) {
                            shownItem++;
                            try {
                                row.createCell(12).setCellValue(items.getId());
                            } catch (Exception e) {
                                row.createCell(12).setCellValue(0);
                                logger.error("ID in " + rowIndex + " didn't received: " + e.getMessage());
                            }

                            try {
                                row.createCell(13).setCellValue(items.getDrugInfo());
                            } catch (Exception e) {
                                row.createCell(13).setCellValue("Unknown prep");
                                logger.error("Error receiving Drug name in " + rowIndex + ": " + e.getMessage());
                            }

                            try {
                                row.createCell(14).setCellValue(items.getPrice());
                            } catch (Exception e) {
                                row.createCell(14).setCellValue("No price");
                                logger.error("Error receiving Drug price in " + rowIndex + ": " + e.getMessage());
                            }

                            try {
                                if (items.getQuant() == 0) {
                                    Cell cell = row.createCell(15); // Create the cell
                                    fillCellWithColor(cell, IndexedColors.RED1.getIndex()); // Fill the cell with color
                                    cell.setCellValue(items.getQuant()); // Set the cell value
                                } else {
                                    row.createCell(15).setCellValue(items.getQuant());
                                }
                            } catch (Exception e) {
                                row.createCell(15).setCellValue(e.getMessage());
                            }

                            try {
                                row.createCell(16).setCellValue(items.getApi().getPrice());
                            } catch (Exception e) {
                                row.createCell(16).setCellValue(0);
                                logger.error("Base Price in " + rowIndex + ": " + e.getMessage());
                            }

                            try {
                                row.createCell(17).setCellValue(items.getApi().getQuant());
                            } catch (Exception e) {
                                row.createCell(17).setCellValue(0);
                                logger.error("Base quantity in " + rowIndex + ": " + e.getMessage());
                            }

                            int k = 0;
                            for (PriceItem priceItem: items.getSftp()) {
                                if (k > 1) {
                                    System.out.println("azaza!!!");
                                }
                                k*=5;
                                try {
                                    row.createCell(18 + k).setCellValue(priceItem.getId());
                                } catch (Exception e) {
                                    row.createCell(18 + k).setCellValue("null");
                                    logger.error("No price list info for ID in " + rowIndex + ": " + e.getMessage());
                                }

                                try {
                                    row.createCell(19 + k).setCellValue(priceItem.getName());
                                } catch (Exception e) {
                                    row.createCell(19 + k).setCellValue("No data available");
                                    logger.error("No price list info for Drug Name in " + rowIndex + ": " + e.getMessage());
                                }

                                try {
                                    row.createCell(20 + k).setCellValue(priceItem.getPrice());
                                } catch (Exception e) {
                                    row.createCell(20 + k).setCellValue("null");
                                    logger.error("No price list info for Price in " + rowIndex + ": " + e.getMessage());
                                }

                                try {
                                    row.createCell(21 + k).setCellValue(priceItem.getQuant());
                                } catch (Exception e) {
                                    row.createCell(21 + k).setCellValue("null");
                                    logger.error("No price list info for Quantity in " + rowIndex + ": " + e.getMessage());
                                }

                                try {
                                    row.createCell(22 + k).setCellValue(priceItem.getLink().getId());
                                } catch (Exception e) {
                                    row.createCell(22 + k).setCellValue("null");
                                    logger.error("No price list info for Time Received in " + rowIndex + ": " + e.getMessage());
                                }

                                k++;
                            }

                            if (shownItem < log[i].getData().size() - 1) {
                                row = sheet.createRow(rowIndex++);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(log[i].getId_order() + ": Datum is null: " + e.getMessage());
                    }
                }
                rowIndex++;
            }
            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream(this.reportPath + File.separator + System.currentTimeMillis() + " " + orders.get(0).getIdCorp() + ".xlsx");
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
