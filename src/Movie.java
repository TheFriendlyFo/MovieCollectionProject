/**
 * Class that represents a single Movie object
 */
public record Movie(String title, String cast, String director, String tagline, String keywords, String overview,
                    int runtime, String genres, double userRating, int year, int revenue) implements Comparable {

  public String toString() {
    return "Title: " + title + ", Tagline: " + tagline;
  }

  @Override
  public int compareTo(Comparable compare) {
    Movie movie = (Movie) compare;
    return title.compareTo(movie.title);
  }
}
