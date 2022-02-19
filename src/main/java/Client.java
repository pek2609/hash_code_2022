import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Client {

    private Set<String> likeIngredients;
    private Set<String> dislikeIngredients;
    private int likeSize;
    private int dislikeSize;

    public Client(String[] likes, String[] dislikes) {
        likeIngredients = Arrays.stream(likes).skip(1).collect(Collectors.toSet());
        likeSize = Integer.parseInt(likes[0]);
        dislikeIngredients = Arrays.stream(dislikes).skip(1).collect(Collectors.toSet());
        dislikeSize = Integer.parseInt(dislikes[0]);
    }

    public int getLikeSize() {
        return likeSize;
    }

    public void setLikeSize(int likeSize) {
        this.likeSize = likeSize;
    }

    public int getDislikeSize() {
        return dislikeSize;
    }

    public void setDislikeSize(int dislikeSize) {
        this.dislikeSize = dislikeSize;
    }

    public Set<String> getLikeIngredients() {
        return likeIngredients;
    }

    public void setLikeIngredients(Set<String> likeIngredients) {
        this.likeIngredients = likeIngredients;
    }

    public Set<String> getDislikeIngredients() {
        return dislikeIngredients;
    }

    public void setDislikeIngredients(Set<String> dislikeIngredients) {
        this.dislikeIngredients = dislikeIngredients;
    }

    @Override
    public String toString() {
        return "Client{" +
                "likeIngredients=" + likeIngredients +
                ", dislikeIngredients=" + dislikeIngredients +
                '}';
    }

    public boolean isLike(Set<String> ingredients) {
        return ingredients.containsAll(likeIngredients) && (!ingredients.containsAll(dislikeIngredients) || dislikeSize == 0);
    }
}
