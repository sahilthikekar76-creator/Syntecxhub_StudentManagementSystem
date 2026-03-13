import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    // ─────────────────────────────────────────────
    // Inner class: Record  (Data Model)
    // ─────────────────────────────────────────────
    static class Record {
        private String name;
        private int    idNumber;
        private int    contactNumber;

        public Record() {}

        public Record(String name, int idNumber, int contactNumber) {
            this.name          = name;
            this.idNumber      = idNumber;
            this.contactNumber = contactNumber;
        }

        public String getName()          { return name; }
        public int    getIdNumber()      { return idNumber; }
        public int    getContactNumber() { return contactNumber; }

        public void setName(String name)                { this.name          = name; }
        public void setIdNumber(int idNumber)            { this.idNumber      = idNumber; }
        public void setContactNumber(int contactNumber)  { this.contactNumber = contactNumber; }

        @Override
        public String toString() {
            return "┌─────────────────────────────────┐\n"
                 + "│  Student ID      : " + idNumber      + "\n"
                 + "│  Name            : " + name          + "\n"
                 + "│  Contact Number  : " + contactNumber + "\n"
                 + "└─────────────────────────────────┘";
        }
    }

    // ─────────────────────────────────────────────
    // Inner class: StudentRecordManagement  (CRUD)
    // ─────────────────────────────────────────────
    static class StudentRecordManagement {
        LinkedList<Record> list = new LinkedList<>();

        // CREATE
        public void add(Record record) {
            if (!existsById(record.getIdNumber())) {
                list.add(record);
                System.out.println("\n✔ Student record added successfully!");
            } else {
                System.out.println("\n✘ A record with ID " + record.getIdNumber() + " already exists.");
            }
        }

        // READ – print & return true if found
        public boolean find(int idNumber) {
            for (Record r : list) {
                if (r.getIdNumber() == idNumber) {
                    System.out.println("\n✔ Record found:\n" + r);
                    return true;
                }
            }
            return false;
        }

        // Helper – return Record object silently
        public Record findRecord(int idNumber) {
            for (Record r : list) {
                if (r.getIdNumber() == idNumber) return r;
            }
            return null;
        }

        // Helper – silent existence check
        private boolean existsById(int idNumber) {
            for (Record r : list) {
                if (r.getIdNumber() == idNumber) return true;
            }
            return false;
        }

        // UPDATE
        public void update(int id, Scanner input) {
            Record rec = findRecord(id);
            if (rec != null) {
                System.out.println("\nCurrent record:\n" + rec);
                System.out.print("\nEnter new Student ID      : ");
                int newId = input.nextInt();
                System.out.print("Enter new Contact Number  : ");
                int newContact = input.nextInt();
                input.nextLine();
                System.out.print("Enter new Name            : ");
                String newName = input.nextLine();
                rec.setIdNumber(newId);
                rec.setContactNumber(newContact);
                rec.setName(newName);
                System.out.println("\n✔ Record updated successfully!\n" + rec);
            } else {
                System.out.println("\n✘ No record found with ID " + id);
            }
        }

        // DELETE
        public void delete(int idNumber) {
            Record toRemove = findRecord(idNumber);
            if (toRemove == null) {
                System.out.println("\n✘ No record found with ID " + idNumber);
            } else {
                list.remove(toRemove);
                System.out.println("\n✔ Record with ID " + idNumber + " deleted successfully.");
            }
        }

        // DISPLAY ALL
        public void display() {
            if (list.isEmpty()) {
                System.out.println("\n  (No student records found)");
                return;
            }
            System.out.println("\n===== ALL STUDENT RECORDS (" + list.size() + ") =====");
            for (Record r : list) {
                System.out.println(r);
            }
        }
    }

    // ─────────────────────────────────────────────
    // main() – Menu Driver
    // ─────────────────────────────────────────────
    public static void main(String[] args) {

        StudentRecordManagement mgr = new StudentRecordManagement();

        // Pre-load one demo record
        mgr.add(new Record("Ankit Sharma", 6862, 91100001));

        Scanner input  = new Scanner(System.in);
        int     option = 0;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   STUDENT MANAGEMENT SYSTEM  (CRUD)  ║");
        System.out.println("╚══════════════════════════════════════╝");

        do {
            printMenu();

            while (!input.hasNextInt()) {
                System.out.print("Please enter a number: ");
                input.next();
            }
            option = input.nextInt();

            switch (option) {

                case 1: // ADD
                    System.out.print("\nEnter Student ID      : ");
                    int newId = input.nextInt();
                    System.out.print("Enter Contact Number  : ");
                    int newContact = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter Student Name    : ");
                    String newName = input.nextLine();
                    mgr.add(new Record(newName, newId, newContact));
                    break;

                case 2: // DELETE
                    System.out.print("\nEnter Student ID to delete: ");
                    mgr.delete(input.nextInt());
                    break;

                case 3: // UPDATE
                    System.out.print("\nEnter Student ID to update: ");
                    mgr.update(input.nextInt(), input);
                    break;

                case 4: // SEARCH
                    System.out.print("\nEnter Student ID to search: ");
                    int searchId = input.nextInt();
                    if (!mgr.find(searchId)) {
                        System.out.println("\n✘ No record found with ID " + searchId);
                    }
                    break;

                case 5: // DISPLAY ALL
                    mgr.display();
                    break;

                case 9: // EXIT
                    System.out.println("\nThank you for using Student Management System. Goodbye!\n");
                    break;

                default:
                    System.out.println("\n✘ Invalid option. Please try again.");
            }

        } while (option != 9);

        input.close();
    }

    // Menu printer
    static void printMenu() {
        System.out.println("\n┌──────────────────────────┐");
        System.out.println("│         MENU             │");
        System.out.println("├──────────────────────────┤");
        System.out.println("│  1 → Add Student         │");
        System.out.println("│  2 → Delete Student      │");
        System.out.println("│  3 → Update Student      │");
        System.out.println("│  4 → Search Student      │");
        System.out.println("│  5 → Display All         │");
        System.out.println("│  9 → Exit                │");
        System.out.println("└──────────────────────────┘");
        System.out.print("Enter your choice: ");
    }
}
