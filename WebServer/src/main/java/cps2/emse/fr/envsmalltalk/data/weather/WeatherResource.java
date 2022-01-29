package cps2.emse.fr.envsmalltalk.data.weather;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WeatherResource {
    @Getter
    private int code, day, month, year, hour;

    /**
     * get the resource of type meteo:Location for this Location
     * 
     * @param model the model to create Resources
     * @return the Resource
     */
    public Resource getResourceForLocation(Model model) {
        return model.createResource(
                WeatherWebsiteScraper.METEOCIEL_INDEX + "L" + getCode() + "_" + getDay() + "_" + getMonth() + "_" + getYear()
                        + "T"
                        + getHour(),
                model.createResource(WeatherWebsiteScraper.METEOCIEL_INDEX + "Location"));
    }
}
