package recap;

public class Country {
    String countryName;
    String continent;
    int population;
    Double lifeExpectancy;

    public Country(String countryName, String continent, int population, Double lifeExpectancy) {
        this.countryName = countryName;
        this.continent = continent;
        this.population = population;
        this.lifeExpectancy = lifeExpectancy;
    }

    @Override
    public String toString() {
        return countryName + " (" + continent + ")";
    }

    public void printCountryName(){
        System.out.println(countryName);
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }
}
