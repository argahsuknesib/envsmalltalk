package cps2.emse.fr.envsmalltalk.data.weather;

import cps2.emse.fr.envsmalltalk.utils.ScraperUtils;
import lombok.Getter;

public class WeatherLocation {
    public static final int METEOCIEL_ID_SAINT_ETIENNE = 7475;

    @Getter
    private int code;
    private boolean hasDate;
    @Getter
    private int day;
    @Getter
    private int month;
    @Getter
    private int year;

    /**
     * get today's weather for a city
     * 
     * @param code the code of the city
     */
    public WeatherLocation(int code) {
        setCode(code);
        setToday();
    }

    /**
     * get the weather for a city of a certain day
     * 
     * @param code  the code of the city
     * @param day   between 1 and 31
     * @param month between 1 and 12
     * @param year  greater than 1975
     */
    public WeatherLocation(int code, int day, int month, int year) {
        setCode(code);
        setDate(day, month, year);
    }

    public WeatherLocation setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * set the date to today
     */
    public WeatherLocation setToday() {
        this.hasDate = false;
        return this;
    }

    /**
     * set the date
     * 
     * @param day   between 1 and 31
     * @param month between 1 and 12
     * @param year  greater than 1975
     */
    public WeatherLocation setDate(int day, int month, int year) {
        this.day = ScraperUtils.interval(day, 1, 31, "day");
        this.month = ScraperUtils.interval(month, 1, 12, "month");
        this.year = ScraperUtils.intervalMin(year, 1975, "year");
        this.hasDate = true;
        return this;
    }

    /**
     * @return the get query to append to {@link WeatherWebsiteScraper#METEOCIEL_PAGE}
     */
    public String toGetQuery() {
        StringBuilder bld = new StringBuilder();
        bld.append("?code2=").append(getCode());
        if (hasDate()) {
            bld.append("&jour2=").append(getDay());
            bld.append("&mois2=").append(getMonth() - 1);
            bld.append("&annee2=").append(getYear());
        }
        return bld.toString();
    }

    /**
     * @return false for today, true for other days
     */
    public boolean hasDate() {
        return hasDate;
    }

}
