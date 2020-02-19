package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerFeeder;
import be.pxl.student.util.BudgetPlannerImporter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class BudgetPlanner {
    public static void main(String[] args) {
        BudgetPlannerImporter importer = new BudgetPlannerImporter("account_payments.csv");
        System.out.println(importer.readFile());


    }

}
