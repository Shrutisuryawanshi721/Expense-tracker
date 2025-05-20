package com.expense.tracker.app.util;

import com.expense.tracker.app.model.Transactions;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    // Read transactions from uploaded CSV file
    public static List<Transactions> readFromMultipartFile(MultipartFile file) {
        List<Transactions> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Expected format: type,category,amount,date
                if (parts.length == 4) {
                    Transactions t = new Transactions();
                    t.setType(parts[0].trim());
                    t.setCategory(parts[1].trim());
                    t.setAmount(Double.parseDouble(parts[2].trim()));
                    t.setDate(LocalDate.parse(parts[3].trim()));
                    list.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Save all transactions to a file
    public static File writeToCsv(List<Transactions> list, String fileName) {
        File file = new File(fileName);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (Transactions t : list) {
                String line = t.getType() + "," + t.getCategory() + "," + t.getAmount() + "," + t.getDate();
                bw.write(line);
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
