import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnePizzaMain {

    private static final Map<String, Integer> likeIngredients = new HashMap<>();
    private static final Map<String, Integer> disLikeIngredients = new HashMap<>();
    private static final Map<String, Integer> finalIngredients = new HashMap<>();

    private static void fillMaps() {
        Path path = Paths.get("a_an_example.in.txt");
        List<String> list = fileToStringList(path);

        for (int i = 1; i <= Integer.parseInt(list.get(0)) * 2; i += 2) {
            String[] likeParts = list.get(i).split(" ");
            for (int j = 1; j <= Integer.parseInt(likeParts[0]); j++) {
                if (likeIngredients.containsKey(likeParts[j]))
                    likeIngredients.put(likeParts[j], likeIngredients.get(likeParts[j]) + 1);
                else likeIngredients.put(likeParts[j], 1);
            }
            String[] disLikeParts = list.get(i + 1).split(" ");
            for (int j = 1; j <= Integer.parseInt(disLikeParts[0]); j++) {
                if (disLikeIngredients.containsKey(disLikeParts[j]))
                    disLikeIngredients.put(disLikeParts[j], disLikeIngredients.get(disLikeParts[j]) + 1);
                else disLikeIngredients.put(disLikeParts[j], 1);
            }
        }
    }

    private static void countFinalIngredients() {
        likeIngredients.forEach((key, value) -> {
            if (disLikeIngredients.containsKey(key)) {
                if (likeIngredients.get(key) - disLikeIngredients.get(key) > 0)
                    finalIngredients.put(key, likeIngredients.get(key) - disLikeIngredients.get(key));
            } else finalIngredients.put(key, value);
        });
    }

    private static void output() {
        Path path = Paths.get("output.txt");
        String result = "";

        int count = finalIngredients.keySet().size();
        result = count + " ";

        for (String s : finalIngredients.keySet()) {
            result += s + " ";
        }

        result = result.substring(0, result.length() - 1);

        if (Files.exists(path)) {
            try {
                Files.delete(path);
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(path, result.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> fileToStringList(Path path) {
        List<String> list;
        if (Files.exists(path)) {
            try {
                list = Files.lines(path).toList();
            } catch (IOException e) {
                return null;
            }
        } else {
            System.out.println("Create and fill file first.");
            return null;
        }
        return list;
    }

    public static void main(String[] args) {
        fillMaps();
        System.out.println("Liked igredients: ");
        likeIngredients.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();
        System.out.println("Disliked igredients: ");
        disLikeIngredients.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println();
        countFinalIngredients();
        finalIngredients.forEach((key, value) -> System.out.println(key + ":" + value));
        output();
    }
}
