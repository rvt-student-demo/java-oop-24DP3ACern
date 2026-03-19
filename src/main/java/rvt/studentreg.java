package rvt;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class studentreg {

    static class Student {
        String name;
        String surname;
        String email;
        String personalCode;
        String date;

        public Student(String name, String surname, String email, String personalCode, String date) {
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.personalCode = personalCode;
            this.date = date;
        }

        public String toCSV() {
            return name + "," + surname + "," + email + "," + personalCode + "," + date;
        }

        public static Student fromCSV(String line) {
            String[] p = line.split(",");
            return new Student(p[0], p[1], p[2], p[3], p[4]);
        }
    }

    static class Validator {

        static boolean isValidName(String name) {
            return name.matches("^[A-Za-z]{3,}$");
        }

        static boolean isValidEmail(String email) {
            return email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
        }

        static boolean isValidCode(String code) {
            return code.matches("^\\d{11}$");
        }
    }

    static class FileHandler {
        static final String FILE = "students.csv";

        static void save(Student s) throws IOException {
            FileWriter fw = new FileWriter(FILE, true);
            fw.write(s.toCSV() + "\n");
            fw.close();
        }

        static List<Student> readAll() throws IOException {
            List<Student> list = new ArrayList<>();
            File file = new File(FILE);

            if (!file.exists()) return list;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                list.add(Student.fromCSV(line));
            }

            br.close();
            return list;
        }

        static void overwrite(List<Student> list) throws IOException {
            FileWriter fw = new FileWriter(FILE);
            for (Student s : list) {
                fw.write(s.toCSV() + "\n");
            }
            fw.close();
        }
    }

    static class Service {

        static void register(Student s) throws Exception {
            List<Student> list = FileHandler.readAll();

            for (Student st : list) {
                if (st.email.equals(s.email))
                    throw new Exception("E-pasts jau eksistē!");
                if (st.personalCode.equals(s.personalCode))
                    throw new Exception("Personas kods jau eksistē!");
            }

            FileHandler.save(s);
        }

        static void show() throws IOException {
            List<Student> list = FileHandler.readAll();

            System.out.println("+----------------+----------------+----------------------+----------------------+----------------------+");
            System.out.println("| Name | Surname | Email | Personal Code | Date |");
            System.out.println("+----------------+----------------+----------------------+----------------------+----------------------+");

            for (Student s : list) {
                System.out.printf("| %-14s | %-14s | %-20s | %-20s | %-20s |\n",
                        s.name, s.surname, s.email, s.personalCode, s.date);
            }

            System.out.println("+----------------+----------------+----------------------+----------------------+----------------------+");
        }

        static void remove(String code) throws IOException {
            List<Student> list = FileHandler.readAll();
            list.removeIf(s -> s.personalCode.equals(code));
            FileHandler.overwrite(list);
        }

        static void edit(String code, Scanner sc) throws IOException {
            List<Student> list = FileHandler.readAll();

            for (Student s : list) {
                if (s.personalCode.equals(code)) {

                    System.out.print("New email: ");
                    String newEmail = sc.nextLine();

                    if (!Validator.isValidEmail(newEmail)) {
                        System.out.println("Nepareizs e-pasts!");
                        return;
                    }

                    s.email = newEmail;
                    System.out.println(" Atjaunināts!");
                }
            }

            FileHandler.overwrite(list);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nCommands: register | show | remove | edit | exit");
            String cmd = sc.nextLine();

            try {
                switch (cmd) {

                    case "register":
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        if (!Validator.isValidName(name)) throw new Exception("Nepareizs vārds!");

                        System.out.print("Surname: ");
                        String surname = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        if (!Validator.isValidEmail(email)) throw new Exception("Nepareizs e-pasts!");

                        System.out.print("Personal Code: ");
                        String code = sc.nextLine();
                        if (!Validator.isValidCode(code)) throw new Exception("Nepareizs personas kods!");

                        String date = LocalDateTime.now().toString();

                        Service.register(new Student(name, surname, email, code, date));
                        System.out.println("shReģistrēts!");
                        break;

                    case "show":
                        Service.show();
                        break;

                    case "remove":
                        System.out.print("Code: ");
                        Service.remove(sc.nextLine());
                        System.out.println("✔ Dzēsts!");
                        break;

                    case "edit":
                        System.out.print("Code: ");
                        Service.edit(sc.nextLine(), sc);
                        break;

                    case "exit":
                        return;

                    default:
                        System.out.println("Nezināma komanda!");
                }

            } catch (Exception e) {
                System.out.println("Kļūda: " + e.getMessage());
            }
        }
    }
}