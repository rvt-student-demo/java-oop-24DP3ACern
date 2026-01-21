package rvt;
import java.util.ArrayList;


    
    class Person {
        private String name;
        private String adress;

        public Person(String name, String adress){
            this.name = name;
            this.adress = adress;
        }
        @Override
        public String toString() {
            return name + "\n" + adress;
        }
    }
    class Student extends Person {
        private int credit;

        public Student(String name, String adress) {
            super(name, adress);
            this.credit = 0;
        }
        public void study() {
            credit++;
        }
        public int credit() {
            return credit;
        }
        @Override
        public String toString(){
            return super.toString() + "\n Study credits " + credit;
        }
    }

public class personNsubclass {

    static class Teacher extends Person{
        private int salary;
        
        public Teacher(String name, String adress, int salary) {
            super(name, adress);
            this.salary = salary;
        }
        @Override
        public String toString(){
            return super.toString() + "\n Teachers salary" + salary + "eur/month ";
        }
    }
    
    public static void printPerson(ArrayList<Person> persons) {
        for (Person person : persons) {
            System.out.println(person);
        }
    }
    public static void main(String[] args){
        // 1
        Person ada = new Person("Ada Lovelace", "  24 Maddox St. London W1S 2QN");
        Person esko = new Person("Esko Ukkonen", "  Mannerheimintie 15 00100 Helsinki");
        System.out.println(ada);
        System.out.println(esko);
        // 2+3
        Student ollie = new Student("\nOllie", "6381 Hollywood Blvd. Los Angeles 90028");
        System.out.println(ollie);
        System.out.println("Study credits " + ollie.credit());
        ollie.study();
        System.out.println("Study credits "+ ollie.credit());
        // 4
        Teacher teacherada = new Teacher("\nAda Lovelace", "24 Maddox St. London W1S 2QN", 1200);
        Teacher teacheresko = new Teacher("Esko Ukkonen", "Mannerheimintie 15 00100 Helsinki", 5400);
        System.out.println(teacherada);
        System.out.println(teacheresko);

        Student studentollie = new Student("Ollie", "6381 Hollywood Blvd. Los Angeles 90028");

        int i = 0;
        while (i < 25) {
            studentollie.study();
            i = i + 1;
        }
        System.out.println(studentollie);
        // 5
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(new Teacher("\nAda Lovelace", "24 Maddox St. London W1S 2QN", 1200));
        persons.add(new Student("Ollie", "6381 Hollywood Blvd. Los Angeles 90028"));

        printPerson(persons);
    }
}
