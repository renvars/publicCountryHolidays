package io.codelex.myswaggyapp.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Holiday {
    private LocalDate date;
    private String localName;
    private String name;
    private String countryCode;
    private boolean fixed;
    private boolean global;
    private List<String> counties;
    private int launchYear;
    private List<String> types;

    public Holiday(String date, String localName, String name, String countryCode, boolean fixed, boolean global, List<String> counties, int launchYear, List<String> types) {
        this.date = LocalDate.parse(date);
        this.localName = localName;
        this.name = name;
        this.countryCode = countryCode;
        this.fixed = fixed;
        this.global = global;
        this.counties = counties;
        this.launchYear = launchYear;
        this.types = types;
    }

    public Holiday() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public List<String> getCounties() {
        return counties;
    }

    public void setCounties(List<String> counties) {
        this.counties = counties;
    }

    public int getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(int launchYear) {
        this.launchYear = launchYear;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return fixed == holiday.fixed && global == holiday.global && launchYear == holiday.launchYear && Objects.equals(date, holiday.date) && Objects.equals(localName, holiday.localName) && Objects.equals(name, holiday.name) && Objects.equals(countryCode, holiday.countryCode) && Objects.equals(counties, holiday.counties) && Objects.equals(types, holiday.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, localName, name, countryCode, fixed, global, counties, launchYear, types);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "date=" + date +
                ", localName='" + localName + '\'' +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", fixed=" + fixed +
                ", global=" + global +
                ", counties='" + counties + '\'' +
                ", launchYear=" + launchYear +
                ", types=" + types +
                '}';
    }
}
