package recap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Streaming {

    static ArrayList<Country> loadData(String path){
        try {
            ArrayList<Country> countries = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(path));
            String[] params;
            for (String line : lines){
                params = line.split(";");
                countries.add(
                        new Country(
                                params[0],
                                params[1],
                                Integer.parseInt(params[2]),
                                Double.parseDouble(params[3])
                        )
                );
            }
            return countries;
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            return null;
        }
    }
    public static void main(String[] args) {
        ArrayList<Country> countries = loadData("countries.txt");
        System.out.println(countries);
        System.out.println(countries.size()); //pocet zemi

        //vypíše země v evropě
        countries.stream()
                .filter(country -> country.getContinent().equals("Europe"))
                .forEach(System.out::println);
        //map vezm pole a zpracuje ho, pak nad nim mohu pracovat,
        double avgEurope = countries.stream()
                .filter(country -> country.getContinent().equals("Europe"))
                .mapToDouble(Country::getLifeExpectancy)
                .average()
                .orElse(0);
        System.out.println(avgEurope);

        //vypis serazeno dle populace:
        countries.stream()
                .filter(c -> c.getContinent().equals("Asia"))
                .sorted(Comparator.comparingInt(Country::getPopulation))
//                .limit(5)
                .forEach(System.out::println);

        //uloz jen zeme s alespon 15 mil obyvatel
        List<Country> bigCountries = countries.stream()
                .filter(c -> c.getPopulation() > 15000000)
                .toList();
        System.out.println(bigCountries);

        //vypis unikatni kontinenty:
        countries.stream()
                .map(Country::getContinent)
                .distinct()
                .forEach(System.out::println);

        //MAPY:
        //namapuj zeme na kontinenty:
        HashMap<String, ArrayList<Country>> contiMap = new HashMap<>();

        for (Country country : countries){
            //je tam uz ten kontinent ready?
            if (contiMap.containsKey(country.getContinent())){
                contiMap.get(country.getContinent()).add(country);
            } else { //pokud neni, priprav mu sadu zemi
                ArrayList<Country> toAdd = new ArrayList<>();
                toAdd.add(country);
                contiMap.put(country.getContinent(), toAdd);
            }
        }
        //kontolni vypis:
        for (String continent : contiMap.keySet()){
            System.out.println(continent);
            for (Country c : contiMap.get(continent)){
                System.out.println("---" + c.getCountryName());
            }
        }

    }
}
