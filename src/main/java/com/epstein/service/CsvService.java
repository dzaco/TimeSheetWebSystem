package com.epstein.service;

import com.epstein.entity.Timesheet;
import com.epstein.model.CsvWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvService {
    private CsvWriter writer;

    public CsvService(String name, HttpServletResponse response) throws IOException {
        writer = new CsvWriter(name,response);
    }

    public void createCSV(List<Timesheet> timesheets) {
        try {
            writer.create(timesheets);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCSV(Map<Timesheet, Integer> map) {
        try {
            writer.create(map);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
