package algoritm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Singleton {

    private static HashMap<String, Double> priceBetweenCities;
    private static String[] nameOfCity;

    private String[] path;
    private double fitness = 0;
    private static Random random;

    public Singleton(String[] path) {
        this.path = Arrays.copyOf(path, path.length);
        setFitness();
    }

    public Singleton() {
        path = Arrays.copyOf(nameOfCity, nameOfCity.length);
        switchElementsArray(path);
        setFitness();
    }

    public static void switchElementsArray(String[] pole) {
        for (int i = pole.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String pom = pole[index];
            pole[index] = pole[i];
            pole[i] = pom;
        }
    }

    public void setFitness() {
        fitness = 0;
        for (int i = 0; i < path.length - 1; i++) {
            StringBuilder st = new StringBuilder();

            if (path[i].compareTo(path[i + 1]) < 0) {
                st.append(path[i]);
                st.append(path[i + 1]);
            } else {
                st.append(path[i + 1]);
                st.append(path[i]);
            }

            fitness += priceBetweenCities.get(st.toString());
        }
    }


    public void correction() {
        int[] frequencyOfCities = new int[getLengthPath()];
        Arrays.stream(path).forEach((city) -> {
            frequencyOfCities[findIndexOfCity(city)]++;
        });

        for (int i = 0; i < path.length; i++) {
            if (frequencyOfCities[i] == 0) {
                for (int j = 0; j < path.length; j++) {
                    if (frequencyOfCities[j] > 1) {
                        int searchIndex = Arrays.asList(path).indexOf(nameOfCity[j]);

                        path[searchIndex] = nameOfCity[i];
                        frequencyOfCities[i]++;
                        frequencyOfCities[j]--;
                        break;
                    }
                }
            }
        }
        setFitness();
    }

    private int findIndexOfCity(String name) {
        return Arrays.asList(nameOfCity).indexOf(name);
    }

    public static void setHashMap(HashMap<String, Double> paths) {
        Singleton.priceBetweenCities = paths;
    }

    public static void setNameOfCity(String[] nameOfCity) {
        Singleton.nameOfCity = nameOfCity;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return Arrays.toString(path) + "\n" + ((int) (fitness*100))/100;
    }

    public String getCity(int index) {
        return path[index];
    }

    public void setCity(int index, String value) {
        path[index] = value;
    }

    public static int getLengthPath() {
        return nameOfCity.length;
    }

    public String[] getPath() {
        return path;
    }

    public static void setRandom(Random random) {
        Singleton.random = random;
    }



}
