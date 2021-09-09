package Application;

import javafx.scene.image.Image;

public class UnionFind {
    public int find(int[] imageArray, int data) {
        if (imageArray[data] == data) return data;
        else return find(imageArray, imageArray[data]);
    }

    public void union(int[] imageArray, int a, int b) {
        imageArray[find(imageArray, b)] = find(imageArray, a);//makes the root of b made to reference a
    }

    /**
     * Method that adds Corresponding pixels to Array
     */
    public int[] unionRightDown(int[] imageArray, Image image) {
        for (int i = 0; i < imageArray.length; i++) {
            int right = i + 1;
            int down = i + (int) image.getWidth();
            if (imageArray[i] != -1) {
                if (right < imageArray.length && imageArray[right] != -1) {
                    union(imageArray, i, right);
                }
                if (down < imageArray.length && imageArray[down] != -1) {
                    union(imageArray, i, down);
                }
            }
        }
        return imageArray;
    }
}
