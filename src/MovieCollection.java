import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private final Scanner scanner;
  private int sortType;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
    QuickSort.sort(movies);
    sortType = 0;
  }

  public void menu() {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option) {
    switch (option) {
      case "t" -> searchTitles();
      case "c" -> searchCast();
      case "k" -> searchKeywords();
      case "g" -> searchGenre();
      case "r" -> listHighestRated();
      case "h" -> listHighestRevenue();
      default -> System.out.println("Invalid choice!");
    }
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

  private void searchCast()  {
    String targetActor = getCastMember();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<>();

    // search through ALL movies in collection
    for (Movie movie : movies) {
      if (movie.cast().contains(targetActor)) {
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

  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.title());
    System.out.println("Tagline: " + movie.tagline());
    System.out.println("Runtime: " + movie.runtime() + " minutes");
    System.out.println("Year: " + movie.year());
    System.out.println("Directed by: " + movie.director());
    System.out.println("Cast: " + movie.cast());
    System.out.println("Overview: " + movie.overview());
    System.out.println("User rating: " + movie.userRating());
    System.out.println("Box office revenue: " + movie.revenue());
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

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void importMovieList(String fileName)  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();
      
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
}