package tutorial;

public class library {

    Book[] catalog = new Book[5];
    int lastIndex = 0;

    void addBook(String name, int year) {
        if (lastIndex < catalog.length) {
            catalog[lastIndex] = new Book(name, year);
            lastIndex++;
        } else {
            System.out.println("The library is full!");
        }

    }

    void popBook() {
        if (lastIndex > 0) {
            lastIndex = lastIndex - 1;
        } else {
            System.out.println("Nothing to pop!");
        }
    }

    void borrowBook(String name) {
        for (int i = 0; i < catalog.length; i++) {

            if (catalog[i] != null) {

                if (catalog[i].name == name) {
                    if (catalog[i].isBorrowed == false) {
                        catalog[i].isBorrowed = true;
                        System.out.println("You have taken out " + catalog[i].name);

                    } else {
                        System.out.println("The book " + catalog[i].name + " has been taken out by other user");
                    }
                }
            }
        }
    }

    void returnBook(String name) {
        for (int i = 0; i < catalog.length; i++) {

            if (catalog[i] != null) {
                if (catalog[i].name == name) {
                    catalog[i].isBorrowed = false;
                    System.out.println("Book returned successfully, thank you!");
                }
            }
        }
    }

    void printCatalog() {
        for (int i = 0; i < catalog.length; i++) {
            if (catalog[i] != null) {
                System.out.println(catalog[i].name + ", " + catalog[i].yearPublication + ", " + catalog[i].isBorrowed);
            }
        }
    }

}
