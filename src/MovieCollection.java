import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private final HashMap<String, ArrayList<Movie>> linkedActors;
  private final Scanner scanner;
  private int sortType;


  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
    QuickSort.sort(movies);
    sortType = 0;
    linkedActors = linkActors();
  }

  private HashMap<String, ArrayList<Movie>> linkActors() {
    HashMap<String, ArrayList<Movie>> linkedActors = new HashMap<>();

    for (Movie movie : movies) {
      for (String actor : movie.cast().split("\\|")) {
        if (linkedActors.containsKey(actor)) {
          linkedActors.get(actor).add(movie);
        } else {
          linkedActors.put(actor, new ArrayList<>(List.of(movie)));
        }
      }
    }

    return linkedActors;
  }

  public void menu() {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("}~-----------~{ Main Menu }~-----------~{");
      System.out.println("|                                       |");
      System.out.println("| Search (T)itles                       |");
      System.out.println("| Search (K)eywords                     |");
      System.out.println("| Search (C)ast                         |");
      System.out.println("| See all movies of a (G)enre           |");
      System.out.println("| List top 50 (R)ated movies            |");
      System.out.println("| List top 50 (H)igest revenue movies   |");
      System.out.println("| Run the (B)aconater!                  |");
      System.out.println("| Change default (S)ort type            |");
      System.out.println("| (Q)uit                                |");
      System.out.println("}~---------------~{ O }~---------------~{");
      System.out.println("\nSelect an option");
      System.out.print("> ");

      menuOption = scanner.nextLine();

      processMenuOption(menuOption);
    }
  }
  
  private void processMenuOption(String option) {
    System.out.println();
    switch (option.toLowerCase()) {
      case "t" -> searchTitles();
      case "c" -> chooseMovie(linkedActors.get(getCastMember()));
      case "k" -> searchKeywords();
      case "g" -> searchGenre();
      case "r" -> listHighestRated();
      case "h" -> listHighestRevenue();
      case "q" -> {}
      case "b" -> runTheBaconater();
      case "s" -> sortMenu();
      default -> System.out.println("Invalid choice!");
    }
  }

  private void sortMenu() {
    System.out.println("}~-----------~{ Sort Menu }~-----------~{");
    System.out.println("|                                       |");
    System.out.println("| Sort by (T)itle                       |");
    System.out.println("| Sort by (R)untime                     |");
    System.out.println("| Sort by (G)enre                       |");
    System.out.println("| Sort by (U)ser rating                 |");
    System.out.println("| Sort by (Y)ear released               |");
    System.out.println("| Sort by r(E)evenue                    |");
    System.out.println("| (Q)uit to main menu                   |");
    System.out.println("}~---------------~{ O }~---------------~{");
    System.out.println("\nSelect an option");
    System.out.print("> ");

    processSortType(scanner.nextLine());
  }

  private void processSortType(String option) {
    sortType = switch (option.toLowerCase()) {
      default -> sortType;
      case "t" -> 0;
      case "r" -> 1;
      case "g" -> 2;
      case "u" -> 3;
      case "y" -> 4;
      case "e" -> 5;
    };

    QuickSort.sort(movies, sortType);
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();
    
    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();
    
    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<>();
    
    // search through ALL movies in collection
    for (Movie movie : movies) {
      String movieTitle = movie.title();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.contains(searchTerm)) {
        //add the Movie object to the results list
        results.add(movie);
      }
    }

    chooseMovie(results);
  }

  private void searchKeywords()  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<>();

    // search through ALL movies in collection
    for (Movie movie : movies) {
      String movieKeyword = movie.keywords();
      movieKeyword = movieKeyword.toLowerCase();

      if (movieKeyword.contains(searchTerm)) {
        //add the Movie object to the results list
        results.add(movie);
      }
    }

    chooseMovie(results);
  }

  private void searchGenre()  {
    String targetGenre = getGenre();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<>();

    // search through ALL movies in collection
    for (Movie movie : movies) {
      if (movie.genres().contains(targetGenre)) {
        //add the Movie object to the results list
        results.add(movie);
      }
    }

    chooseMovie(results);
  }


  private void listHighestRated()
  {
    QuickSort.sort(movies, 3);
    ArrayList<Movie> top = new ArrayList<>();

    for (int i = 0; i < 50; i++) {
      top.add(movies.get(i));
    }

    chooseMovie(top);

    QuickSort.sort(movies, sortType);
  }

  private void listHighestRevenue()
  {
    QuickSort.sort(movies, 5);
    ArrayList<Movie> top = new ArrayList<>();

    for (int i = 0; i < 50; i++) {
      top.add(movies.get(i));
    }

    chooseMovie(top);

    QuickSort.sort(movies, sortType);
  }

  private String getCastMember() {
    System.out.print("Enter an actor's name: ");
    String searchTerm = scanner.nextLine().toLowerCase();

    ArrayList<String> actors = new ArrayList<>();

    for (Movie movie : movies) {
      for (String actor : movie.cast().split("\\|")) {
        if (actor.toLowerCase().contains(searchTerm) && !actors.contains(actor)) {
          actors.add(actor);
        }
      }
    }

    QuickSort.sortStr(actors);

    for (int i = 0; i < actors.size(); i++) {
      System.out.printf("%s: %s\n", i + 1, actors.get(i));
    }

    System.out.println("Select an actor:");
    System.out.print("> ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    return actors.get(choice - 1);
  }

  private String getGenre() {
    ArrayList<String> genres = new ArrayList<>();

    for (Movie movie : movies) {
      for (String genre : movie.genres().split("\\|")) {
        if (genres.contains(genre)) continue;
        genres.add(genre);
      }
    }

    QuickSort.sortStr(genres);

    for (int i = 0; i < genres.size(); i++) {
      System.out.printf("%s: %s\n", i + 1, genres.get(i));
    }

    System.out.println("Select an actor:");
    System.out.print("> ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    return genres.get(choice - 1);
  }

  private void chooseMovie(ArrayList<Movie> movies) {
    // now, display them all to the user
    for (int i = 0; i < movies.size(); i++)
    {
      System.out.printf("%s: %s\n", i + 1, movies.get(i).title());
    }

    System.out.println("Select a movie to learn more about");
    System.out.print("> ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = movies.get(choice - 1);
    selectedMovie.displayInfo();

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void importMovieList(String fileName)  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      bufferedReader.readLine();
      String line;
      
      movies = new ArrayList<>();
      
      while ((line = bufferedReader.readLine()) != null)
      {
        String[] movieFromCSV = line.split(",");
     
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
        movies.add(nextMovie);  
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());              
    }
  }

  public void runTheBaconater() {
    String actor = getCastMember();

    for (int i = 1; i < 7; i++) {
      if (runTheBaconater(actor, 0, i)) {
        scanner.nextLine();
        return;
      }
    }

    System.out.println("Baconater failed!");
    scanner.nextLine();
  }

  private boolean runTheBaconater(String actor, int depth, int maxDepth) {
    if (depth == maxDepth) return false;

    for (Movie movie : linkedActors.get(actor)) {
      String cast = movie.cast();

      if (cast.contains("Kevin Bacon")) {
        System.out.printf("\nAfter linking %s actors, Kevin Bacon was found!\n", depth + 1);
        System.out.printf("In %s he shared a role with %s%s\n", movie.title(), actor, depth == 0 ? "!\n" : "...");
        return true;
      }

      for (String newActor : cast.split("\\|")) {
        if (newActor.equals(actor)) continue;
        if (!runTheBaconater(newActor, depth + 1, maxDepth)) continue;

        System.out.printf("And %s shared a role with %s in %s%s\n", newActor, actor, movie.title(), depth == 0 ? "!\n" : "...");
        return true;
      }
    }

    return false;
  }
}