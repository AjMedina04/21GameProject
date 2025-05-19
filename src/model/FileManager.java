package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles loading and saving the wins/losses record to a file.
 */
public class FileManager {
    private final String recordFilePath = "data/record.txt";

    /**
     * Constructs a new FileManager.
     */
    public FileManager() {
    }

    /**
     * Reads the record file and returns an array with wins and losses.
     * If the file does not exist or contains invalid data, returns {0,0}.
     *
     * @return int array where index 0 is wins and index 1 is losses.
     */
    public int[] loadRecord() {
        int wins = 0, losses = 0;
        File file = new File(recordFilePath);

        if (!file.exists()) {
            // No record yet; start at zero
            return new int[] { wins, losses };
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("wins=")) {
                    wins = Integer.parseInt(line.substring(5).trim());
                } else if (line.startsWith("losses=")) {
                    losses = Integer.parseInt(line.substring(7).trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return new int[] { wins, losses };
    }

    /**
     * Writes the current wins and losses to the record file.
     *
     * @param wins   the number of wins to save.
     * @param losses the number of losses to save.
     */
    public void saveRecord(int wins, int losses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recordFilePath))) {
            writer.write("wins=" + wins);
            writer.newLine();
            writer.write("losses=" + losses);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
