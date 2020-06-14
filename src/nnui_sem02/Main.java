import algoritm.GeneticAlgorithm;
import fileLoader.FileLoader;

import java.util.HashMap;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        FileLoader sb = new FileLoader("/csv/Distance.csv");

        HashMap<String, Double> paths = sb.loadFromCsv();
        String[] nameOfCities = sb.getNameOfCities();

        int countSingletons = 100000;
        int countOfIteration = 500;

        GeneticAlgorithm g = new GeneticAlgorithm(countSingletons, paths, nameOfCities, new Random(100000));

        //[St Helens, Scottsdale, Bridport, George Town, Beauty Point, Beaconsfield, Launceston, Evandale, Perth, Longford, Westbury, Deloraine, Sheffield, Latrobe, Port Sorell, Devonport, Ulverstone, Penguin, Burnie, Smithton, Wynyard, Rosebery, Queenstown, New Norfolk, Austins Ferry, Seven Mile Beach, Sandford, Bellerive, Hobart, Margate, Kettering, Cygnet, Huonville, Franklin, Geeveston]
        //1229.23

        g.iterate(countOfIteration);
    }

}
