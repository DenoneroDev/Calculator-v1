package deno.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class ImageButton extends JButton {

    Image image;
    ImageObserver imageObserver;


    public ImageButton(ImageIcon icon) {

        super();
        image = icon.getImage();
        imageObserver = icon.getImageObserver();

    }

    public void paint(Graphics g) {

        super.paint( g );
        g.drawImage(image,  15, 10 , 50 , 50 , imageObserver);

    }
}
