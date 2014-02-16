import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class R {

    public static ImageIcon getIcon(String iconName) throws IOException {
        File f = new File("resources/icons/small/"+iconName+".png");
        //System.out.println("Getting resource "+f.getAbsolutePath());
           //Image img = ImageIO.read(getClass().getResource("resources/icons/small/"+iconName+".png"));
           //return new ImageIcon(img);
       // return new ImageIcon("resources/small/"+iconName+".png");

        BufferedImage buttonIcon = ImageIO.read(f);
        return new ImageIcon(buttonIcon);
    }

    public static String getText(String textName){
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/text/"+textName+".txt"));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();

        } catch(Exception e){

        }

        return "";
    }


}
