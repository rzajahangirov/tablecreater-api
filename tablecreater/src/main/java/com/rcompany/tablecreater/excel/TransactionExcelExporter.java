package com.rcompany.tablecreater.excel;

import com.rcompany.tablecreater.models.Customer;
import com.rcompany.tablecreater.models.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class TransactionExcelExporter {

    public static ByteArrayInputStream export(Customer customer, List<Transaction> transactions) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Transactions");

            int rowIdx = 0;

            // ===== CUSTOMER INFO HEADER =====
            Row customerRow1 = sheet.createRow(rowIdx++);
            customerRow1.createCell(0).setCellValue("Customer Name:");
            customerRow1.createCell(1).setCellValue(customer.getName());

            Row customerRow2 = sheet.createRow(rowIdx++);
            customerRow2.createCell(0).setCellValue("Customer Phone:");
            customerRow2.createCell(1).setCellValue(customer.getPhone());

            rowIdx++; // boş sətir

            // ===== TABLE HEADER =====
            Row header = sheet.createRow(rowIdx++);

            String[] columns = {
                    "Transaction Date",
                    "Created At",
                    "Product",
                    "Receiving Company",
                    "Weight Ton",
                    "Price Per Ton RUB",
                    "Transport",
                    "Vehicle Count",
                    "Price Per Vehicle",
                    "Paid Amount",
                    "Paid Currency",
                    "Exchange Rate",
                    "Total Expense USD",
                    "Remaining Debt USD"
            };

            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            // ===== DATA =====
            for (Transaction t : transactions) {
                Row row = sheet.createRow(rowIdx++);

                int col = 0;

                row.createCell(col++).setCellValue(String.valueOf(t.getTransactionDate()));
                row.createCell(col++).setCellValue(String.valueOf(t.getCreatedAt()));
                row.createCell(col++).setCellValue(t.getProductName());
                row.createCell(col++).setCellValue(t.getReceivingCompany());
                row.createCell(col++).setCellValue(value(t.getWeightTon()));
                row.createCell(col++).setCellValue(value(t.getPricePerTonRub()));
                row.createCell(col++).setCellValue(t.getTransportType() != null ? t.getTransportType().name() : "");
                row.createCell(col++).setCellValue(value(t.getVehicleCount()));
                row.createCell(col++).setCellValue(value(t.getPricePerVehicle()));
                row.createCell(col++).setCellValue(value(t.getPaidAmount()));
                row.createCell(col++).setCellValue(t.getPaidCurrency() != null ? t.getPaidCurrency().name() : "");
                row.createCell(col++).setCellValue(value(t.getHistoricalExchangeRate()));
                row.createCell(col++).setCellValue(value(t.getHistoricalTotalExpenseUsd()));
                row.createCell(col++).setCellValue(value(t.getHistoricalRemainingDebtUsd()));
            }

            // Auto-size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (Exception e) {
            throw new RuntimeException("Excel export error", e);
        }
    }

    private static String value(Object o) {
        return o == null ? "" : o.toString();
    }
}

