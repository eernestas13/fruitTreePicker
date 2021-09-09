package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class Controller implements Initializable {
    @FXML
    ImageView original, BlackandWhite, viewBoxs , greyScaleImgViewer, redOnlyImgViewer, blueOnlyImgViewer, greenOnlyImgViewer;
    @FXML
    MenuItem imageChoice;
    @FXML
    Slider hueSlider;
    @FXML
    Slider opacitySlider;
    @FXML
    Slider saturationSlider;
    @FXML
    ImageChange imgChange;
    myArray pixArray;
    UnionFind unionFind;


    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Chooses the image to be used for each tab
     */
    public void imgChoose(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image file");
        File file = fileChooser.showOpenDialog(imageChoice.getParentPopup().getOwnerWindow());
        Image image = new Image(file.toURI().toString(), 512, 512, false, false);
        imgChange = new ImageChange(image);
        original.setImage(imgChange.getImage());
        BlackandWhite.setImage(imgChange.getImage());
        viewBoxs.setImage(imgChange.getImage());
        greyScaleImgViewer.setImage(makeGray(image));
        redOnlyImgViewer.setImage(makeRed(image));
        greenOnlyImgViewer.setImage(makeGreen(image));
        blueOnlyImgViewer.setImage(makeBlue(image));

    }

    public static int find(int[] a, int id) {
        return a[id] == id ? id : find(a, a[id]);
    }


    /**
     * 'Change Hue' button that gets the hue value for the image conversion to black and white
     */
    public void changeHue(ActionEvent event) {
        Color color = Color.hsb(hueSlider.getValue(), 1.0, 1.0);
        imgChange.BlackandWhiteConversion(hueSlider.getValue());
        BlackandWhite.setImage(imgChange.getBlackandWhiteImg());
    }


    /**
     * 'Create Boxes button enters the pixels into an array, unions them with down and right,
     * creates the boxes and shows these boxes.
     */
    public void createBoxes(ActionEvent e) {
        UnionFind unionFind = new UnionFind();
        pixArray = new myArray();
        pixArray.addPixelsToArray(imgChange.getBlackandWhiteImg());
        pixArray.setImageArray(unionFind.unionRightDown(pixArray.getImageArray(), BlackandWhite.getImage()));
        pixArray.ArraytoHashMap();
        pixArray.removeKeysTooSmall(50);
        viewBoxs.setImage(imgChange.getImage());
        addBox(pixArray.getHashMap());
    }


    public WritableImage makeGray(Image chosenImage) {
        PixelReader pixelReader = chosenImage.getPixelReader();
        WritableImage wImage = new WritableImage((int) chosenImage.getWidth(), (int) chosenImage.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int c = 0; c < chosenImage.getWidth(); c++)
            for (int r = 0; r < chosenImage.getHeight(); r++) {
                Color color = pixelReader.getColor(c, r);
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                double grey = (red + green + blue) / 3;
                Color greyColor = new Color(grey, grey, grey, 1);
                pixelWriter.setColor(c, r, greyColor);
            }
        return wImage;
    }

    public WritableImage makeRed(Image chosenImage) {
        PixelReader pixelReader = chosenImage.getPixelReader();
        WritableImage wImage = new WritableImage((int) chosenImage.getWidth(), (int) chosenImage.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int c = 0; c < chosenImage.getWidth(); c++)
            for (int r = 0; r < chosenImage.getHeight(); r++) {
                Color color = pixelReader.getColor(c, r);
                double red = color.getRed();
                Color redOnly = new Color(red, 0, 0, 1);
                pixelWriter.setColor(c, r, redOnly);
            }
        return wImage;
    }

    public WritableImage makeGreen(Image chosenImage) {
        PixelReader pixelReader = chosenImage.getPixelReader();
        WritableImage wImage = new WritableImage((int) chosenImage.getWidth(), (int) chosenImage.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int c = 0; c < chosenImage.getWidth(); c++)
            for (int r = 0; r < chosenImage.getHeight(); r++) {
                Color color = pixelReader.getColor(c, r);
                double green = color.getGreen();
                Color greenOnly = new Color(0, green, 0, 1);
                pixelWriter.setColor(c, r, greenOnly);
            }
        return wImage;
    }

    public WritableImage makeBlue(Image chosenImage) {
        PixelReader pixelReader = chosenImage.getPixelReader();
        WritableImage wImage = new WritableImage((int) chosenImage.getWidth(), (int) chosenImage.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int c = 0; c < chosenImage.getWidth(); c++)
            for (int r = 0; r < chosenImage.getHeight(); r++) {
                Color color = pixelReader.getColor(c, r);
                double blue = color.getBlue();
                Color blueOnly = new Color(0, 0, blue, 1);
                pixelWriter.setColor(c, r, blueOnly);
            }
        return wImage;
    }

    /**
     * Method that creates Rectangles that have yet to be added to image
     */
    public void addBox(HashMap<Integer, ArrayList<Integer>> hashMap) {
        double smallX, smallY, bigX, bigY;
        double imageWidth = viewBoxs.getFitWidth();
        for (Integer key : hashMap.keySet()) {
            bigX = (hashMap.get(key).get(0)) % imageWidth;
            bigY = (hashMap.get(key).get(0)) / imageWidth;
            smallX = (hashMap.get(key).get(0)) % imageWidth;
            smallY = (hashMap.get(key).get(0)) / imageWidth;
            for (int i = 0; i < hashMap.get(key).size(); i++) {
                double newX = hashMap.get(key).get(i) % imageWidth;
                double newY = hashMap.get(key).get(i) / imageWidth;
                if (newX < smallX) { smallX = newX; }
                if (newX > bigX) { bigX = newX; }
                if ((smallY != 0) && (newY < smallY)) { smallY = newY; }
                if (newY > bigY) { bigY = newY; }
            }
            createRect(smallX, smallY, bigX, bigY, key);
        }
    }

    /**
     * Method that adds already created Rectangles to image
     */
    public void createRect(double smallX, double smallY, double maxX, double maxY, int key) {
        double width = maxX - smallX;
        double height = maxY - smallY;
        Rectangle rectangle = new Rectangle();
        rectangle.setStroke((Color.BLUE));
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setX(smallX);
        rectangle.setY(smallY);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLUE);
        Tooltip toolTip = new Tooltip("Root Pixel = " + key + " Size in Pixel Units: " + height * width);
        Tooltip.install(rectangle, toolTip);
        ((Pane) viewBoxs.getParent()).getChildren().add(rectangle);

        System.out.println("Fruit of root " + key);
        System.out.println("Approximate Pixels = " + height * width);
    }
}
