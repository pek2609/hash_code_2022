import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class OnePizzaMain {

    private static final Map<String, Integer> likeIngredients = new HashMap<>();
    private static final Map<String, Integer> disLikeIngredients = new HashMap<>();
    private static final Set<String> finalIngredients = new HashSet<>();
    private static final List<Client> clients = new ArrayList<>();

    private static final String FILE_INPUT_PATH = "a_an_example.in.txt";
    private static final String FILE_OUTPUT_PATH = "output.txt";

    private static void fillMapsAndClients() throws IOException {
        Path path = Paths.get(FILE_INPUT_PATH);
        List<String> list = fileToStringList(path);
        int clientSize = Integer.parseInt(list.get(0)) * 2;
        for (int i = 1; i <= clientSize - 1; i += 2) {
            String[] likeParts = list.get(i).split(" ");
            String[] disLikeParts = list.get(i + 1).split(" ");
            clients.add(new Client(likeParts, disLikeParts));
            for (int j = 1; j <= Integer.parseInt(likeParts[0]); j++) {
                if (likeIngredients.containsKey(likeParts[j]))
                    likeIngredients.put(likeParts[j], likeIngredients.get(likeParts[j]) + 1);
                else likeIngredients.put(likeParts[j], 1);
            }
            for (int j = 1; j <= Integer.parseInt(disLikeParts[0]); j++) {
                if (disLikeIngredients.containsKey(disLikeParts[j]))
                    disLikeIngredients.put(disLikeParts[j], disLikeIngredients.get(disLikeParts[j]) + 1);
                else disLikeIngredients.put(disLikeParts[j], 1);
            }
        }
    }

    private static void countFinalIngredients() {
        for (Client client : clients) {
            finalIngredients.addAll(client.getLikeIngredients());
            for (String dislikeIngredient : client.getDislikeIngredients()) {
                Map.Entry<Integer, Integer> likeDislikeCount = getLikeDislikeCount(dislikeIngredient);
                if (likeDislikeCount.getValue() >= likeDislikeCount.getKey()) {
                    finalIngredients.remove(dislikeIngredient);
                }
            }
        }
    }

    private static Map.Entry<Integer, Integer> getLikeDislikeCount(String ingredient) {
        int countLikesIngredient = likeIngredients.get(ingredient) == null ? 0 : likeIngredients.get(ingredient);
        int countDislikesIngredient = disLikeIngredients.get(ingredient) == null ? 0 : disLikeIngredients.get(ingredient);
        return new AbstractMap.SimpleEntry<>(countLikesIngredient, countDislikesIngredient);
    }

    private static void output() {
        Path path = Paths.get(FILE_OUTPUT_PATH);
        int ingredientsSize = finalIngredients.size();
        StringBuilder result = new StringBuilder(ingredientsSize + " ");
        for (String ingredient : finalIngredients) {
            result.append(ingredient).append(" ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 1));
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);
            Files.write(path, result.toString().getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> fileToStringList(Path path) throws IOException {
        if (Files.exists(path)) {
            return Files.lines(path).toList();
        }
        throw new FileNotFoundException();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            fillMapsAndClients();
            countFinalIngredients();
            output();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        long end = System.currentTimeMillis();
        System.out.println("Time = " + (end - start));
    }
}
