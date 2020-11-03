package com.epstein.model;

import com.epstein.entity.Timesheet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CsvWriter {

    private String name;
    //private CSVPrinter printer;

    ICsvBeanWriter printer;



    public CsvWriter(String name, HttpServletResponse response) throws IOException {
        this.name = name;

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", name);
        response.setHeader(headerKey, headerValue);


        printer = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

    }


    public void create(List<Timesheet> timesheets) throws IOException {
        String[] header = {"Project", "Faza", "Miesiąc", "Rok", "Suma godzin",
                "1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31"};

        String[] nameMapping = {"project", "stage", "month", "year", "sum",
                "day1","day2","day3","day4","day5","day6","day7","day8","day9","day10",
                "day11","day12","day13","day14","day15","day16","day17","day18","day19","day20",
                "day21","day22","day23","day24","day25","day26","day27","day28","day29","day30","day31"};

        printer.writeHeader(header);

        for (Timesheet timesheet : timesheets) {
            printer.write(timesheet, nameMapping);
        }
        printer.close();

    }

    public void create(Map<Timesheet, Integer> map) throws IOException {
        String[] header = {"Nazwa Projektu", "Faza", "Miesiąc", "Rok", "Suma godzin"};

        printer.writeHeader(header);

        for (Map.Entry<Timesheet,Integer> entry : map.entrySet() ) {

            printer.writeComment(
                    entry.getKey().getProject().getName() + ","
                            + entry.getKey().getStage() + ","
                            + entry.getKey().getMonth() + ","
                            + entry.getKey().getYear() + ","
                            + entry.getValue() );
        }
        printer.close();

    }



}
