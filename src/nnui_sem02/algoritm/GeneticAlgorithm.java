package algoritm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class GeneticAlgorithm {

    private ArrayList<Singleton> singletons;
    private final Queue<Singleton> frontOfSingletons;
    private final int countSingletons;
    private Singleton bestSingleton;
    private double minPath = Integer.MAX_VALUE;
    private final Random random;
    private int countElit;

    public GeneticAlgorithm(int countSingletons, HashMap<String, Double> paths, String[] nameOfCities, Random random) {
        this.countSingletons = countSingletons;

        frontOfSingletons = new LinkedList<>();
        singletons = new ArrayList<>();
        this.random = random;

        Singleton.setHashMap(paths);
        Singleton.setNameOfCity(nameOfCities);
        Singleton.setRandom(this.random);
    }

    public void iterate(int countGeneration) {
        for (int i = 0; i < countSingletons; i++) {
            singletons.add(new Singleton());
        }
        countElit = (int) (countSingletons * 0.05);

        if ((countElit - countSingletons) % 2 != 0) {
            countElit--;
        }

        for (int i = 0; i < countGeneration; i++) {
            selection();
            crossOver();
            mutation(0.05);
            elitism();

            Singleton j = singletons.stream().min(Comparator.comparing(Singleton::getFitness)).get();
            if (j.getFitness() < minPath) {
                bestSingleton = new Singleton(j.getPath());
                minPath = j.getFitness();
            }

            if (i % (countGeneration / 10) == 0) {
                System.out.println("Min for iteration number: " + i + " je " + bestSingleton.toString());
            }

        }
        System.out.println("Fin min: " + bestSingleton.toString());
    }

    private void selection() {
        for (int i = 0; i < countSingletons - countElit; i++) {
            int index1 = randomIndexOfSingleton();
            int index2 = randomIndexOfSingleton();
            if (singletons.get(index1).getFitness() < singletons.get(index2).getFitness()) {
                frontOfSingletons.add(new Singleton(singletons.get(index1).getPath()));
            } else {
                frontOfSingletons.add(new Singleton(singletons.get(index2).getPath()));
            }
        }
    }

    private void elitism() {
        ArrayList<Singleton> listOfFinalSingletons = new ArrayList<>();
        for (int i = 0; i < countElit; i++) {
            Singleton tmp = singletons.stream().min(Comparator.comparing(Singleton::getFitness)).get();
            listOfFinalSingletons.add(tmp);
            singletons.remove(tmp);
        }
        singletons = listOfFinalSingletons;
    }

    private void crossOver() {
        for (int j = 0; j < (countSingletons - countElit / 2); j++) {

            int fromNumber = genRandom(0, Singleton.getLengthPath() - 5);
            int toNumber = genRandom(fromNumber, Singleton.getLengthPath());

            Singleton first = frontOfSingletons.poll();
            Singleton second = frontOfSingletons.poll();

            for (int i = fromNumber; i < toNumber; i++) {
                if(first != null && second != null) {
                    String tmp = first.getCity(i);
                    first.setCity(i, second.getCity(i));
                    second.setCity(i, tmp);
                }
            }
            first.correction();
            second.correction();
            singletons.add(first);
            singletons.add(second);
        }
    }

    private void mutation(double probability) {
        for (int j = 0; j < singletons.size(); j++) {

            int index = randomIndexOfSingleton();
            if (genRandom() <= probability) {
                int countMutation = 10;
                for (int i = 0; i < countMutation; i++) {
                    int a = genRandom(0, Singleton.getLengthPath());
                    int b = genRandom(0, Singleton.getLengthPath());

                    String pom = singletons.get(index).getCity(a);
                    singletons.get(index).setCity(a, singletons.get(index).getCity(b));
                    singletons.get(index).setCity(b, pom);
                }
                singletons.get(index).setFitness();
            }
        }
    }

    public int genRandom(int min, int max) {
        return random.nextInt((max - min)) + min;
    }

    public double genRandom() {
        return random.nextDouble() * 1;
    }

    private int randomIndexOfSingleton() {
        return genRandom(0, countSingletons);
    }

}
