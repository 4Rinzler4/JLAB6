import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileRecord {
    public String fileName;
    public String extension;
    public long size;
    public String creationDate;
    public String attribute;

    public FileRecord(String fileName, String extension, long size, String creationDate, String attribute) {
        this.fileName = fileName;
        this.extension = extension;
        this.size = size;
        this.creationDate = creationDate;
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "FileRecord{" +
                "fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                ", creationDate='" + creationDate + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}

class FileManager {
    private static final String FILE_PATH = "database.txt";

    public List<FileRecord> readRecords() {
        List<FileRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                records.add(new FileRecord(parts[0], parts[1], Long.parseLong(parts[2]), parts[3], parts[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void writeRecords(List<FileRecord> records) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (FileRecord record : records) {
                bw.write(record.fileName + "," + record.extension + "," + record.size + "," +
                        record.creationDate + "," + record.attribute + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class lab6_2 {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        List<FileRecord> records = fileManager.readRecords();

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Add Record");
            System.out.println("2. Edit Record");
            System.out.println("3. Delete Record");
            System.out.println("4. Display Records");
            System.out.println("5. Search Records by Creation Date");
            System.out.println("6. Sort Records by Extension");
            System.out.println("0. Exit");
            System.out.println("Enter choice:");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addRecord(records, scanner);
                    break;
                case "2":
                    editRecord(records, scanner);
                    break;
                case "3":
                    deleteRecord(records, scanner);
                    break;
                case "4":
                    displayRecords(records);
                    break;
                case "5":
                    searchRecordsByCreationDate(records, scanner);
                    break;
                case "6":
                    sortRecordsByExtension(records);
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

        fileManager.writeRecords(records);
        scanner.close();
    }

    static void addRecord(List<FileRecord> records, Scanner scanner) {
        System.out.println("Enter File Name:");
        String fileName = scanner.nextLine();

        System.out.println("Enter Extension:");
        String extension = scanner.nextLine();

        System.out.println("Enter Size:");
        long size = Long.parseLong(scanner.nextLine());

        System.out.println("Enter Creation Date (YYYY-MM-DD):");
        String creationDate = scanner.nextLine();

        System.out.println("Enter Attribute:");
        String attribute = scanner.nextLine();

        records.add(new FileRecord(fileName, extension, size, creationDate, attribute));
        System.out.println("Record Added Successfully!");
    }

    static void editRecord(List<FileRecord> records, Scanner scanner) {
        System.out.println("Enter File Name to Edit:");
        String fileName = scanner.nextLine();

        for (FileRecord record : records) {
            if (record.fileName.equals(fileName)) {
                System.out.println("Enter New Size:");
                long newSize = Long.parseLong(scanner.nextLine());
                record.size = newSize;
                System.out.println("Record Edited Successfully!");
                return;
            }
        }
        System.out.println("Record Not Found!");
    }

    static void deleteRecord(List<FileRecord> records, Scanner scanner) {
        System.out.println("Enter File Name to Delete:");
        String fileName = scanner.nextLine();

        records.removeIf(record -> record.fileName.equals(fileName));
        System.out.println("Record Deleted Successfully!");
    }

    static void displayRecords(List<FileRecord> records) {
        for (FileRecord record : records) {
            System.out.println(record);
        }
    }

    static void searchRecordsByCreationDate(List<FileRecord> records, Scanner scanner) {
        System.out.println("Enter Search Creation Date (YYYY-MM-DD):");
        String searchDate = scanner.nextLine();

        for (FileRecord record : records) {
            if (record.creationDate.equals(searchDate)) {
                System.out.println(record);
            }
        }
    }

    static void sortRecordsByExtension(List<FileRecord> records) {
        records.sort((r1, r2) -> r1.extension.compareTo(r2.extension));
        System.out.println("Records Sorted by Extension Successfully!");
        displayRecords(records);
    }
}
