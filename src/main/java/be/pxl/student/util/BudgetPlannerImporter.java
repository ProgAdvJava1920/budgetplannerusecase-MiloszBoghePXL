package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private Path path;

    public BudgetPlannerImporter(String fileName) {
        this.path=Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/"+fileName);
    }

    public List<Account> readFile() {
        ArrayList<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                lines.add(line.split(","));
            }
        } catch (IOException ex) {
            System.out.println("Oops, something went wrong!");
            System.out.println(ex.getMessage());
        }
        return createAccounts(lines);
    }

    private List<Account> createAccounts(List<String[]> lines) {
        ArrayList<Account> accountsList = new ArrayList<>();
        HashMap<String, List<Payment>> accountsMap = new HashMap<>();
        ArrayList<Payment> payments = new ArrayList<>();

        //formatter aanmaken
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

        //Zorg dat alle foutieve lijnen weg zijn:
        lines = lines.stream().filter(line -> line.length == 7).collect(Collectors.toList());

        //Maak al de accounts aan en zet ze in de lijst
        //Account name,Account IBAN,Counteraccount IBAN,Transaction date,Amount,Currency,Detail
        lines.forEach(line -> {
            String name = line[0];
            String key = line[1];
            Date dateTime = Date.from(LocalDateTime.parse(line[3], formatter).toInstant(ZoneOffset.UTC));
            float amount = Float.parseFloat(line[4]);
            String currency = line[5];
            String detail = line[6];

            if (accountsMap.get(key) == null) {
                Account account = new Account(key, name, payments);
                accountsList.add(account);
                accountsMap.put(key, payments);
            }
            Payment payment = new Payment(dateTime, amount, currency, detail);
            payments.add(payment);
        });
        return accountsList;
    }
}
