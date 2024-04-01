package org.classes;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Excel extends Command {

    public Excel(String value) {
        super(value);
    }

    public String CommandImplementation(ArrayList<String> args) throws IOException {

        try {
            if (args.size() > 0) {
                throw new InvalidArgs("Invalid arguments");
            }

            Map<String, Set<String>> abilityToPeopleMap = new HashMap<>();

            FileInputStream fis = new FileInputStream(new File("abilities.xlsx"));
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                String person = row.getCell(0).getStringCellValue();
                String[] abilities = row.getCell(1).getStringCellValue().split(",");

                for (String ability : abilities) {
                    abilityToPeopleMap.computeIfAbsent(ability.trim(), k -> new HashSet<>()).add(person);
                }
            }
            workbook.close();
            fis.close();
            for (Map.Entry<String, Set<String>> entry : abilityToPeopleMap.entrySet())
            {
                System.out.println("Ability: " + entry.getKey());
                System.out.println("People: " + entry.getValue());
                System.out.println();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return "OK";
    }
}
