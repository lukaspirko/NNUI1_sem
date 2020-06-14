package fileLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class FileLoader {

    private HashMap<String, Double> paths;
    private String[][] distanceMatrix;
    private String[] nameOfCities;
    private String filePath;

    public FileLoader(String filePath) {
        paths = new HashMap<>();
        distanceMatrix = new String[36][36];
        nameOfCities = new String[35];
        this.filePath = filePath;
    }

    public HashMap<String, Double> loadFromCsv() {
        try {
            FileReader reader = new FileReader(this.getClass().getResource(filePath).getFile());
            BufferedReader bufReader = new BufferedReader(reader);

            String row;
            String[] rowItems;

            int rd = 0;
            String delimiter = ";";
            while ((row = bufReader.readLine()) != null) {
                rowItems = row.split(delimiter);

                System.arraycopy(rowItems, 0, distanceMatrix[rd], 0, rowItems.length);
                rd++;
            }
        } catch (IOException e) {
            System.err.println("Load file FAILED! " + e);
        }

        // create hash map
        for (int i = 1; i < 36; i++) {
            for (int j = 1 + i; j < 36; j++) {
                paths.put(getKey(i, j), Double.parseDouble(distanceMatrix[i][j]));
            }
        }
        System.arraycopy(distanceMatrix[0], 1, nameOfCities, 0, distanceMatrix.length - 1);
        return paths;
    }

    private String getKey(int row, int column) {
        return distanceMatrix[row][0] + distanceMatrix[0][column];
    }

    public String[] getNameOfCities() {
        return nameOfCities;
    }

}
