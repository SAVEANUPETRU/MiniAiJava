package input;

import domain.Instance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {
    public static List<Instance<Double, Integer>> loadData(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            return br.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> {
                        String[] parts = line.split(",");
                        List<Double> features = new ArrayList<>();
                        for (int i = 0; i < parts.length - 1; i++) {
                            features.add(Double.parseDouble(parts[i].trim()));
                        }
                        int label = Integer.parseInt(parts[parts.length - 1].trim());
                        return new Instance<>(features, label);
                    })
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
