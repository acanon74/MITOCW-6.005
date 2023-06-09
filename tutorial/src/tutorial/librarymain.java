package tutorial;

public class librarymain {

    public static void main(String[] args) {
        library lib = new library();

        lib.addBook("The very hungry caterpillar", 1999);
        lib.addBook("Serious Python", 2020);
        lib.addBook("1984", 1980);

        lib.borrowBook("1984");

        lib.printCatalog();

        lib.returnBook("1984");

        lib.printCatalog();
    }

}
