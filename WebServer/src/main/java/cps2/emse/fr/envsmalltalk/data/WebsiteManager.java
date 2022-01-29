package cps2.emse.fr.envsmalltalk.data;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import cps2.emse.fr.envsmalltalk.data.platform.PlatformWebsiteScraper;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.stereotype.Component;

import cps2.emse.fr.envsmalltalk.data.weather.WeatherLocation;
import cps2.emse.fr.envsmalltalk.data.weather.WeatherWebsiteScraper;
import cps2.emse.fr.envsmalltalk.data.platform.CSVSensorWebsiteScraper;
import lombok.Getter;

@Component
public class WebsiteManager {
    public static final String DATASET_URL = "http://localhost:3030/database";
    private Model model = ModelFactory.createDefaultModel();

    public WebsiteManager() {
        model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
        model.setNsPrefix("meteo", WeatherWebsiteScraper.METEOCIEL_INDEX);
    }

    @Getter
    private PlatformWebsiteScraper territoire = new PlatformWebsiteScraper(PlatformWebsiteScraper.TERRITOIRE_URL);

    public WeatherWebsiteScraper getMeteoScraper(WeatherLocation location) {
        return new WeatherWebsiteScraper(location);
    }

    public CSVSensorWebsiteScraper getDataTerritoireScraper(URL url) {
        return new CSVSensorWebsiteScraper(url);
    }

    public CSVSensorWebsiteScraper getDataTerritoireScraper(File file) {
        return new CSVSensorWebsiteScraper(file);
    }

    public void acceptConnection(Consumer<RDFConnection> action) {
        executeConnection(conneg -> {
            action.accept(conneg);
            return null;
        });
    }

    public <T> T executeConnection(Function<RDFConnection, T> action) {
        try (RDFConnection conneg = RDFConnectionFactory.connect(DATASET_URL)) {
            return action.apply(conneg);
        }
    }

    public List<String> selectUris(String uriProp, String query) {
        return select(query, solu -> solu.get(uriProp).asResource().getURI());
    }

    public List<String> selectUris(String uriProp, Supplier<Query> query) {
        return select(query, solu -> solu.get(uriProp).asResource().getURI());
    }

    public <T> List<T> select(String query, Function<QuerySolution, T> selection) {
        return select(() -> QueryFactory.create(query), selection);
    }

    public <T> List<T> select(Supplier<Query> query, Function<QuerySolution, T> selection) {
        Query q = query.get();
        return executeConnection(conneg -> {
            List<T> uris = new ArrayList<>();
            try (QueryExecution exec = conneg.query(q)) {
                ResultSet set = exec.execSelect();
                while (set.hasNext()) {
                    uris.add(selection.apply(set.next()));
                }
            }
            return uris;
        });
    }

    public <T> T selectOne(String query, Function<QuerySolution, T> selection) {
        return selectOne(() -> QueryFactory.create(query), selection);
    }

    public <T> T selectOne(Supplier<Query> query, Function<QuerySolution, T> selection) {
        Query q = query.get();
        return executeConnection(conneg -> {
            try (QueryExecution exec = conneg.query(q)) {
                ResultSet set = exec.execSelect();
                if (set.hasNext()) {
                    return selection.apply(set.next());
                }
            }
            throw new NoSuchElementException();
        });
    }

    public <T> T executeModel(Function<Model, T> action) {
        T t;
        synchronized (model) {
            model.removeAll();
            t = action.apply(model);
            System.out.println("executing model...");
            System.out.println(model.size());
            acceptConnection(conneg -> conneg.load(model));
            System.out.println("end executing model.");
        }
        return t;
    }
}
