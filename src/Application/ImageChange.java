package Application;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageChange {
    private Image image;
    private WritableImage BlackandWhiteImg;

    public ImageChange(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    /**
     * Converts images pixels to either black(-1) or white
     */
    public void BlackandWhiteConversion(Double hue) {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage wImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        Color blackColor = new Color(0, 0, 0, 1);
        Color whiteColor = new Color(1, 1, 1, 1);
        for (int c = 0; c < image.getWidth(); c++)
            for (int r = 0; r < image.getHeight(); r++) {
                Color color = pixelReader.getColor(c, r);
                if ((color.getHue() > hue + 20) || (color.getHue() < hue - 20)) {
                    pixelWriter.setColor(c, r, blackColor);
                } else {
                    pixelWriter.setColor(c, r, whiteColor);
                }
            }
        setBlackandWhiteImg(wImage);
    }

    public WritableImage getBlackandWhiteImg() {
        return BlackandWhiteImg;
    }

    public void setBlackandWhiteImg(WritableImage BlackandWhiteImg) {
        this.BlackandWhiteImg = BlackandWhiteImg;
    }
}
