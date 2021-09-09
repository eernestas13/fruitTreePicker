package Application;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;


public class myArray {
    private int[] imageArray;
    private HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<Integer, ArrayList<Integer>>();
    UnionFind unionFind = new UnionFind();

    /**
     * Reads through image pixel by pixel and gives each pixel a corresponding location in the array. All black pixels(fruit)
     * keeps its position in the array as its value and all black is set to -1;
     */
    public void addPixelsToArray(Image image) {
        int[] tempArray = new int[(int) image.getWidth() * (int) image.getHeight()];
        PixelReader pixelReader = image.getPixelReader();
        Color white = new Color(1, 1, 1, 1);
        for (int i = 0; i < tempArray.length; i++) {
            int x = i % (int) image.getWidth();
            int y = i / (int) image.getWidth();
            Color color = pixelReader.getColor(x, y);
            if (color.equals(white)) {
                tempArray[i] = i;
            } else {
                tempArray[i] = -1;
            }
        }
        setImageArray(tempArray);
    }

    /**
     * Moves array values into a hashmap where the key for the value is its root.
     */
    public void ArraytoHashMap() {
        for (int i = 0; i < imageArray.length; i++) {
            if (imageArray[i] != -1) {
                int newInt = unionFind.find(imageArray, i);
                if (!hashMap.containsKey(imageArray[newInt])) { //if key hasn't occurred yet add to the hashtable
                    ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    hashMap.put(imageArray[newInt], arrayList);
                }
                hashMap.get(imageArray[newInt]).add(i); //adds the current pos in the array to the correct arrayList
            }
        }
    }

    /**
     * Removes white pixel groups from Hashmap that dont meet a group size requirement
     */
    public void removeKeysTooSmall(int Minimum) {
        hashMap.remove(-1);
        hashMap.keySet().removeIf(key -> hashMap.get(key).size() < Minimum);
    }

    public HashMap<Integer, ArrayList<Integer>> getHashMap() {
        return hashMap;
    }

    public int[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(int[] imageArray) {
        this.imageArray = imageArray;
    }
}
