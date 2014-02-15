import java.awt.*;
import java.util.Random;

public class Filler {

    private static final String[] fillOptions =
            new String[]{"None","White","Cross","Checkboxes","Button list","Button row","Empty axis","Bar chart",
                    "Line chart strait","Fill"};
    private static Random rand = new Random();

    public static String[] getFillOptions(){
        return fillOptions;
    }

    public static void fill(String option, int x, int y, int width, int height, Graphics2D g){
        if(option.equals("None")){
            return; //do nothing
        }else if(option.substring(0,4).equals("Fill")){
            if(option.length() <10) return; //Not compleate
            g.setColor(Color.decode(option.substring(4,11)));
            g.fillRect(x,y,width,height);
        }else if(option.equals("White")){
            //g.setColor(Color.WHITE);
            //g.fillRect(x,y,width,height);
            fill("Fill#FFFFFF",x,y,width, height, g);
        }else if(option.equals("Cross")){
            g.setColor(Color.GRAY);
            //line size/2 (just makes it easier as normaly want to be using the half)
            int lineSize = 4*DrawPanel.scale;
            g.setStroke(new BasicStroke(lineSize*2));
            g.drawLine(x+lineSize,y+lineSize,x+width-lineSize,y+height-lineSize);
            g.drawLine(x+width-lineSize,y+lineSize,x+lineSize,y+height-lineSize);
        }else if(option.equals("Bar chart")){

            int margin = width/10;

            //Draw background
            g.setColor(Color.WHITE);
            g.fillRect(x,y,width,height);

            //Draw bars
            int barY = (height-margin*2)/6;
            g.setColor(Color.LIGHT_GRAY);
            int numOfBoxes = 5;
            int boxWidth = (width - margin - margin)/numOfBoxes;
            //System.out.println("box width "+boxWidth+" of "+width);
            for(int i=0; i<numOfBoxes; i++){
                g.fillRect(x+margin+(i*boxWidth), y+height-(margin)-(barY*(i+1)), boxWidth, (barY*(i+1)));
            }

            //Draw axis
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(4*DrawPanel.scale));
            g.drawLine((x+margin), y+margin, x+margin, y+height-margin);
            g.drawLine(x+margin, y+height-margin, x+width-margin, y+height-margin);
        }else if(option.equals("Empty axis")){

            int margin = width/10;

            //Draw background
            g.setColor(Color.WHITE);
            g.fillRect(x,y,width,height);


            //Draw axis
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(4*DrawPanel.scale));
            g.drawLine((x+margin), y+margin, x+margin, y+height-margin);
            g.drawLine(x+margin, y+height-margin, x+width-margin, y+height-margin);
        }else if(option.equals("Line chart strait")){

            int margin = width/10;

            //Draw background
            g.setColor(Color.WHITE);
            g.fillRect(x,y,width,height);


            //Draw Line
            int barY = (height-margin*2)/15;
            g.setStroke(new BasicStroke(DrawPanel.scale));
            g.setColor(Color.LIGHT_GRAY);
            int numOfPoints = 15;
            int stepWidth = (width - margin - margin)/numOfPoints;
            for(int i=0; i<numOfPoints; i++){
                g.drawLine(x+(i*stepWidth)+margin, y+height-(i*barY)-margin, x+((i+1)*stepWidth)+margin, y+height-((i+1)*barY)-margin);
            }

            //Draw axis
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(4*DrawPanel.scale));
            g.drawLine((x+margin), y+margin, x+margin, y+height-margin);
            g.drawLine(x+margin, y+height-margin, x+width-margin, y+height-margin);
        }else if(option.equals("Checkboxes")){
            //Draw background
            g.setColor(Color.WHITE);
            g.fillRect(x,y,width,height);
            g.setStroke(new BasicStroke(DrawPanel.scale));

            g.setColor(Color.LIGHT_GRAY);
            int margin = height/10;
            int newY = margin;
            int boxSize = 20*DrawPanel.scale;
            int boxPadding = 10*DrawPanel.scale;
            while(newY+boxSize+boxPadding < height){
                g.drawRect(5*DrawPanel.scale+x, newY+y, boxSize, boxSize);
                newY += boxSize + boxPadding;
            }
        }else if(option.equals("Button list")){
            //Draw background
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
            g.setStroke(new BasicStroke(DrawPanel.scale));

            g.setColor(Color.GRAY);
            int margin = height/10;
            int newY = margin;
            int boxSize = 20*DrawPanel.scale;
            int boxPadding = 10*DrawPanel.scale;
            while(newY+boxSize+boxPadding < height){
                g.drawRect(boxPadding+x, newY+y, width-boxPadding*2, boxSize);
                newY += boxSize + boxPadding;
            }
        } else if(option.equals("Button row")){
            //Draw background
            g.setColor(Color.WHITE);
            g.fillRect(x,y,width,height);
            g.setStroke(new BasicStroke(DrawPanel.scale));

            g.setColor(Color.GRAY);
            int margin = height/10;
            //margin *= DrawPanel.scale;
            //int newY = margin;
            int newX = margin;
            int boxSize = 40*DrawPanel.scale;
            int boxPadding = 10*DrawPanel.scale;
            while(newX+boxSize+boxPadding < width){
                g.drawRect(newX+x, margin+y, boxSize, height-(margin*2));
                newX += boxSize + boxPadding;
            }
        }
    }


}
