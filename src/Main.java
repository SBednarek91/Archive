import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Article {
    private String title;
    private int year;
    private String category;
    private int numberOfCopies;
    private String content;
    private boolean isBorrowed;
    private String borrowerName;

    // Konstruktor
    public Article(String title, int year, String category, int numberOfCopies, String content) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.numberOfCopies = numberOfCopies;
        this.content = content;
        this.isBorrowed = false;
        this.borrowerName = "";
    }

    // Gettery i settery
    public String getTitle() {        return title;    }

    public void setTitle(String title) {        this.title = title;    }

    public int getYear() {        return year;    }

    public void setYear(int year) {        this.year = year;    }

    public String getCategory() {        return category;    }

    public void setCategory(String category) {        this.category = category;    }

    public int getNumberOfCopies() {        return numberOfCopies;    }

    public void setNumberOfCopies(int numberOfCopies) {        this.numberOfCopies = numberOfCopies;    }

    public String getContent() {        return content;    }

    public void setContent(String content) {        this.content = content;    }

    public boolean isBorrowed() {        return isBorrowed;    }

    public void setBorrowed(boolean borrowed) {        isBorrowed = borrowed;    }

    public String getBorrowerName() {        return borrowerName;    }

    public void setBorrowerName(String borrowerName) {        this.borrowerName = borrowerName;    }

    // Metoda do wypożyczania dokumentu
    public boolean borrow(String borrowerName) {
        if (isBorrowed || numberOfCopies <= 0) {
            return false; // Nie można wypożyczyć, jeśli już wypożyczono lub nie ma dostępnych kopii
        } else {
            this.isBorrowed = true;
            this.borrowerName = borrowerName;
            numberOfCopies--;
            return true;
        }
    }

    // Metoda do zwracania dokumentu
    public boolean returnArticle() {
        if (!isBorrowed) {
            return false; // Nie można zwrócić, jeśli dokument nie jest wypożyczony
        } else {
            this.isBorrowed = false;
            this.borrowerName = "";
            numberOfCopies++;
            return true;
        }
    }

    //Określenie sposobu wyświetlania
    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", category='" + category + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                ", content='" + content + '\'' +
                ", isBorrowed=" + isBorrowed +
                ", borrowerName='" + borrowerName + '\'' +
                '}';
    }
}

class Library {
    private List<Article> articles;

    // Konstruktor
    public Library() {
        this.articles = new ArrayList<>();
        // Dodajmy dwa przykładowe artykuły do biblioteki
        articles.add(new Article("Java", 2022, "Programming", 10, "Treść artykułu o Javie"));
        articles.add(new Article("Python", 2021, "Data Science", 8, "Treść artykułu o naukach danych w Pythonie"));
    }

    // Metoda do dodawania nowego artykułu do biblioteki
    public void addArticle(Article article) {
        articles.add(article);
    }

    // Metoda do usuwania artykułu z biblioteki
    public void removeArticle(Article article) {
        articles.remove(article);
    }

    public Article findArticleByTitle(String title) {
        for (Article article : articles) {
            if (article.getTitle().equals(title)) {
                return article; // Zwracamy artykuł, jeśli jego tytuł pasuje
            }
        }
        return null; // Zwracamy null, jeśli nie znaleziono artykułu o podanym tytule
    }

    public boolean borrowArticle(String title, String borrower) {
        Article articleToBorrow = findArticleByTitle(title);
        if (articleToBorrow != null) {
            return articleToBorrow.borrow(borrower);
        } else {
            return false; // Artykuł o podanym tytule nie został znaleziony
        }
    }

    public void returnArticle(String title) {
        Article articleToReturn = findArticleByTitle(title);
        if (articleToReturn != null) {
            articleToReturn.returnArticle();
        } else {
            System.out.println("Artykuł o podanym tytule nie został znaleziony.");
        }
    }
    // Metoda do wyświetlania wszystkich artykułów w bibliotece
    public void displayLibrary() {
        System.out.println("Biblioteka artykułów:");
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    public List<Article> getArticles() {
        return articles;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        boolean exit = false;
        boolean loggedIn = false;
        String loggedInUser = null;

        // Tablica użytkowników z ich loginami i hasłami
        String[][] users = {
                {"admin", "admin123"},
                {"user1", "password1"},
                {"user2", "password2"}
        };

        while (true) {
            System.out.println("Witaj w archiwum");
            System.out.println("Wybierz opcję:");
            System.out.println("1. Zaloguj");
            System.out.println("2. Kontynuuj bez logowania");
            System.out.println("3. Zakoncz");
            int loginchoice = sc.nextInt();
            sc.nextLine(); //Wczytanie znaku nowej linii

            switch (loginchoice) {
                case 1:
                    loggedInUser = login(sc, users);
                    break;
                case 2:
                    libraryMenu(library, sc);
                    break;
                case 3:
                    System.out.println("Wyjście z programu");
                    return; // Zakończ program
                default:
                    System.out.println("Niepoprawny wybór.");
                    break;
            }

            // Jeśli użytkownik jest zalogowany, wyświetl menu biblioteki
            if (loggedInUser != null) {
                libraryMenuLogin(library, sc, loggedInUser);
            }
        }
    }

    public static String login(Scanner sc, String[][] users) {
        String loggedInUser = null;

        do {
            System.out.println("Logowanie");
            System.out.println("Podaj login:");
            String userlogin = sc.nextLine();
            System.out.println("Podaj hasło:");
            String userpassword = sc.nextLine();

            for (String[] user : users) {
                if (user[0].equals(userlogin) && user[1].equals(userpassword)) {
                    loggedInUser = userlogin;
                    System.out.println("Zalogowano pomyślnie, witaj: " + loggedInUser);
                    return loggedInUser; // Zwracamy nazwę zalogowanego użytkownika
                }
            }
            System.out.println("Błąd logowania, spróbuj ponownie");
        } while (loggedInUser == null);

        return loggedInUser;
    }

    // Metoda do wyświetlania menu biblioteki
    public static void libraryMenu(Library library, Scanner scanner) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println("Menu archiwum:");
            System.out.println("1. Wyświetl wszystkie artykuły");
            System.out.println("2. Powrót do menu głównego.");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    library.displayLibrary();
                    break;
                case 2:
                    backToMainMenu = true; // Powrót do menu głównego
                    break;
                default:
                    System.out.println("Nieprawidłowa opcja. Spróbuj ponownie.");
            }
        }
    }
    public static void libraryMenuLogin(Library library, Scanner scanner, String loggedInUser) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println("Menu archiwum:");
            System.out.println("1. Wyświetl wszystkie artykuły");
            System.out.println("2. Dodaj artykuł do listy");
            System.out.println("3. Usuń artykuł z listy");
            System.out.println("4. Wypożycz artykuł");
            System.out.println("5. Zwróć artykuł");
            System.out.println("6. Wyloguj");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    library.displayLibrary();
                    break;
                case 2: //Dodawanie artykułu
                    System.out.println("Podaj tytuł artykułu:");
                    String title = scanner.nextLine();
                    System.out.println("Podaj rok wydania artykułu:");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Podaj kategorię artykułu:");
                    String category = scanner.nextLine();
                    System.out.println("Podaj ilość kopii artykułu:");
                    int numberOfCopies = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Podaj treść artykułu:");
                    String content = scanner.nextLine();

                    Article newArticle = new Article(title, year, category, numberOfCopies, content);
                    library.addArticle(newArticle);
                    System.out.println("Dodano nowy artykuł do biblioteki.");
                    break;
                case 3:
                    // Usuwanie artykułu
                    System.out.println("Podaj tytuł artykułu do usunięcia:");
                    String articleTitle = scanner.nextLine();
                    boolean found = false;
                    for (Article article : library.getArticles()) {
                        if (article.getTitle().equalsIgnoreCase(articleTitle)) {
                            library.removeArticle(article);
                            found = true;
                            System.out.println("Artykuł usunięto z biblioteki.");
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Nie znaleziono artykułu o podanym tytule.");
                    }
                    break;
                case 4: //Wypożyczanie artykułu
                    System.out.println("Wypożycz artykuł");
                    System.out.println("Podaj tytuł artykułu do wypożyczenia:");
                    String borrowArticleTitle = scanner.nextLine();
                    boolean isBorrowed = library.borrowArticle(borrowArticleTitle, loggedInUser);
                    if (isBorrowed) {
                        System.out.println("Artykuł został wypożyczony.");
                    } else {
                        System.out.println("Brak dostępnych kopii artykułu lub artykuł jest już wypożyczony.");
                    }
                    break;
                case 5: //Zwracanie artykułu
                    System.out.println("Zwróć artykuł");
                    System.out.println("Podaj tytuł artykułu do zwrotu:");
                    String returnArticleTitle = scanner.nextLine();
                    library.returnArticle(returnArticleTitle);
                    System.out.println("Artykuł został zwrócony.");
                    break;
                case 6:
                    System.out.println("Wylogowano");
                    backToMainMenu = true; // Wyjście z pętli menu biblioteki
                    break;
                default:
                    System.out.println("Nieprawidłowa opcja. Spróbuj ponownie.");
            }
        }
    }
}