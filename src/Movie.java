/**
 * Class that represents a single Movie object
 */
public record Movie(String title, String cast, String director, String tagline, String keywords, String overview,
                    int runtime, String genres, double userRating, int year, int revenue) implements Comparable {

  public String toString() {
    return "Title: " + title + ", Tagline: " + tagline;
  }

  @Override
  public int compareTo(Comparable compare, int sortType) {
    Movie movie = (Movie) compare;
    return switch (sortType) {
      default -> title.compareTo(movie.title);
      case 1 -> Integer.compare(runtime, movie.runtime);
      case 2 -> genres.compareTo(movie.genres);
      case 3 -> -Double.compare(userRating, movie.userRating);
      case 4 -> -Integer.compare(year, movie.year);
      case 5 -> -Integer.compare(revenue, movie.revenue);
    };
  }
}
