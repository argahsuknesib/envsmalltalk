package cps2.emse.fr.envsmalltalk.data.platform;

import java.time.Instant;
import java.util.Date;
import java.util.OptionalDouble;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cps2.emse.fr.envsmalltalk.utils.ScraperUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

public record SensorScraper(String name, Date time,
                            OptionalDouble humidity, OptionalDouble luminosity, OptionalDouble snd,
                            OptionalDouble sndf, OptionalDouble sndm, OptionalDouble temperature, UUID id, String location, String type) {

    public static final String SENSOR_INDEX = "http://example.com/Sensor#";
    private static final Pattern IRILOCATION_PATTERN = Pattern.compile("emse/fayol/e([^/]+)/S([^/]+)");
    /**
     * convert raw location to iri location, example
     * emse/fayol/e4/S431H to https://territoire.emse.fr/kg/emse/fayol/4ET/431H
     * 
     * @param raw the csv raw location
     * @return the iri location
     */
    public static String iriLocationFromCsvRaw(String raw) {
        Matcher m = IRILOCATION_PATTERN.matcher(raw);
        if (m.matches()) {
            return "https://territoire.emse.fr/kg/emse/fayol/" + m.group(1) + "ET/" + m.group(2);
        }
        return raw;
    }

    /**
     * create sensordata from raw csv line
     * 
     * name,time,HMDT,LUMI,SND,SNDF,SNDM,TEMP,id,location,type
     * 
     * @param raw csv data
     * @return the sensor data
     */
    public static SensorScraper of(String[] raw) {
        return new SensorScraper(raw[0], Date.from(Instant.ofEpochMilli(Long.parseLong(raw[1]) / 1_000_000L)),
                ScraperUtils.optionalDoubleOf(raw[2]), ScraperUtils.optionalDoubleOf(raw[3]),
                ScraperUtils.optionalDoubleOf(raw[4]), ScraperUtils.optionalDoubleOf(raw[5]),
                ScraperUtils.optionalDoubleOf(raw[6]), ScraperUtils.optionalDoubleOf(raw[7]), UUID.fromString(raw[8]),
                iriLocationFromCsvRaw(raw[9]), raw[10]);
    }

    /**
     * get the resource of type sensor:Sensor for this Sensor
     * 
     * @param model the model to create Resources
     * @return the Resource
     */
    public Resource getResource(Model model) {
        return model.createResource(
                SENSOR_INDEX + "S" + id().toString().replace("-", ""),
                model.createResource(SENSOR_INDEX + "Sensor"));
    }
}
