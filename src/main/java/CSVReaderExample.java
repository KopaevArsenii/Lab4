import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderExample {

    public static void main(String[] args) {
        String csvFilePath = "/Users/arsenijkopaev/Documents/University/Java/Lab4/src/main/resources/people.csv"; // Path to CSV file
        List<Person> people = readCSV(csvFilePath);
        if (people.isEmpty()) {
            System.out.println("No people found in the CSV file.");
        } else {
            for (Person person : people) {
                System.out.println(person);
            }
        }
    }

    private static List<Person> readCSV(String csvFilePath) {
        List<Person> people = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath))
                .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length != 6) continue; // Skip invalid rows

                // Map the columns correctly based on your CSV format:
                int id = Integer.parseInt(nextLine[0]); // ID (from column 1)
                String name = nextLine[1]; // Name (from column 2)
                String gender = nextLine[2]; // Gender (from column 3)
                String birthDate = nextLine[3]; // BirthDate (from column 4)
                String departmentName = nextLine[4]; // Division (from column 5)
                String salaryStr = nextLine[5]; // Salary (from column 6)

                // Handle the salary string and convert it to a double
                salaryStr = salaryStr.replace(',', '.'); // Replace commas with periods for correct parsing
                double salary = Double.parseDouble(salaryStr);

                // Create Department object
                Department department = new Department(departmentName);

                // Create Person object and add to the list
                Person person = new Person(id, name, gender, department, salary, birthDate);
                people.add(person);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return people;
    }
}
