import io.RemoteMovieLoader;
import model.Movie;

import java.util.List;

public class Main {
    static void main() {
        List<Movie> movies = new RemoteMovieLoader().loadAll();
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

