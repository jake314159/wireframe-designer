import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class FileUtil {

    public static boolean saveFrames(ArrayList<Wireframe> frames, String outputFile) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
        for(Wireframe f : frames){
            writer.println(f.getSaveString());
        }
        writer.close();

        return true;
    }

    public static ArrayList<Wireframe> loadFrames(String file) throws IOException {
        ArrayList<Wireframe> array = new ArrayList<Wireframe>();
        String sCurrentLine;

        BufferedReader br = new BufferedReader(new FileReader(file));

            while ((sCurrentLine = br.readLine()) != null) {
                String[] arr = sCurrentLine.split(",");
                //System.out.println("The line '" + sCurrentLine + "' is " + arr.length + " long");
                try{
                    if(arr[0].equals("Wirebox")){
                        array.add(Wirebox.makeWireframe(arr));
                    } else if(arr[0].equals("Label")){
                        array.add(Label.makeWireframe(arr));
                    } else if(arr[0].equals("Pointer")){
                        array.add(Pointer.makeWireframe(arr));
                    }
                }catch(NullPointerException e){
                    e.printStackTrace();
                }
            }

        return array;
    }


    public static void saveAsImage(ArrayList<Wireframe> wireframes, int x, int y, int width, int height, String filename){
        BufferedImage largeImg = new BufferedImage(width+x, height+y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gLarge = largeImg.createGraphics();
        gLarge.setColor(Color.WHITE);
        gLarge.fillRect(0,0,width+x,height+y);
        for(Wireframe f : wireframes){
            f.draw(gLarge, true);  //true==exporting (so don't display any markers for the UI)
        }

        BufferedImage imageToSave = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gSmall = imageToSave.createGraphics();
        gSmall.drawImage(largeImg, -x, -y, null);

        try {
            // retrieve image
            File outputfile = new File(filename);
            ImageIO.write(imageToSave, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //Returns the file extention if it has one or null if not
    public static String getFileExtension(String filename){
        int lastDot = filename.lastIndexOf(".");
        if(lastDot == -1 ) return null;
        return filename.substring(lastDot+1);
    }
}
