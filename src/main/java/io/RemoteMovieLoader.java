package io;

import model.Movie;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class RemoteMovieLoader implements MovieLoader {
    private static final String url = "https://datasets.imdbws.com/title.basics.tsv.gz";

    @Override
    public List<Movie> loadAll() {
        try{
            return loadFrom(new URL(url).openConnection());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> loadFrom(URLConnection urlConnection) throws IOException {
        try(InputStream is = unzip(urlConnection.getInputStream())){
            return loadFrom(is);
        }
    }

    private List<Movie> loadFrom(InputStream is) throws IOException {
        return loadFrom(new BufferedReader(new InputStreamReader(is)));
    }

    private List<Movie> loadFrom(BufferedReader reader) throws IOException {
        List<Movie> movies = new ArrayList<>();
        reader.readLine();
        while (true){
            String line = reader.readLine();
            if (line == null) break;
            movies.add(toMovie(line));
        }
        return movies;
    }

    private static Movie toMovie(String line) {
        String[] parts = line.split("\t");
        return new Movie(parts[2], toInt(parts[7]));
    }

    private static int toInt(String s) {
        if(s.equals("\\N")) return -1;
        return Integer.parseInt(s);
    }

    private InputStream unzip(InputStream is) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(is));
    }

}

