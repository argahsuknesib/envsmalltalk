package cps2.emse.fr.envsmalltalk.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Function;

public class CSVReader<T> {
    private StreamSupplier stream;
    private Function<String[], T> reading;

    public CSVReader(String file, Function<String[], T> reading) {
        this(new File(file), reading);
    }

    public CSVReader(File file, Function<String[], T> reading) {
        this(() -> new FileInputStream(file), reading);
    }

    public CSVReader(URL url, Function<String[], T> reading) {
        this(() -> url.openStream(), reading);
    }

    public CSVReader(StreamSupplier stream, Function<String[], T> reading) {
        this.stream = stream;
        this.reading = reading;
    }

    /**
     * read the stream
     * 
     * @param action the action to apply
     * @return the number of line read by the reader
     */
    public int readFile(Consumer<T> action) {
        return readFile(-1, action);
    }

    /**
     * read the stream
     * 
     * @param maxLine maximum line count to read, -1 = infinite
     * @param action  the action to apply
     * @return the number of line read by the reader
     */
    public int readFile(int maxLine, Consumer<T> action) {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(stream.get()))) {
            r.readLine(); // header
            String line;
            int i = 0;
            while ((line = r.readLine()) != null) {
                if (line.isEmpty())
                    continue;

                String[] raw;
                if (line.charAt(line.length() - 1) == ',') {
                    raw = (line + ' ').split(",");
                    raw[raw.length - 1] = "";
                } else {
                    raw = line.split(",");
                }

                if (maxLine != -1 && i++ >= maxLine) {
                    break;
                }

                T t = reading.apply(raw);
                action.accept(t);
            }
            return i;
        } catch (Exception e) {
            throw new RuntimeException("Can't read CSV", e);
        }
    }
}