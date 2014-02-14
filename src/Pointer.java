import java.awt.*;
import java.awt.event.MouseEvent;

public class Pointer implements Wireframe{
    private String name;
    private int textX = 20;
    private int textY = 20;
    private int pointX = 40;
    private int pointY = 40;
    private int threshold = 8;
    private Color textColor = Color.BLACK;
    private Font font;

    private boolean selectedText = true; //was the last true edge check for text or the pointer?

    public Pointer(String name){
        this.name = name;
        setSize(14);
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size){
        font = new Font("Arial", Font.PLAIN, size);
    }
    public int getSize(){
        return font.getSize();
    }

    public void setLocations(int textX, int textY, int pointX, int pointY){
        this.textX = textX;
        this.textY = textY;
        this.pointX = pointX;
        this.pointY = pointY;
    }

    @Override
    public String getTypeOfWireframe() {
        return "Pointer";
    }

    @Override
    public Point getTopCorner() {
        return new Point(textX, textY);
    }

    @Override
    public int getWidth() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getHeight() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onBoarder(int x, int y) {
        boolean returnValue1 = (Math.abs(x-textX) < threshold && Math.abs(y-textY) < threshold);
        if(returnValue1){
            selectedText = true;
            return true;
        }
        boolean returnValue2 = (Math.abs(x-pointX) < threshold && Math.abs(y-pointY) < threshold);
        if(returnValue2){
            selectedText = false; //this check was true for the pointer
            return true;
        }
        return false; //nope not touching anything here
    }

    @Override
    public boolean contains(int x, int y) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(Graphics2D g, boolean export) {
        g.setColor(textColor);
        g.setFont(font);
        int stringWidth = 0;
        if(name.indexOf("\n") >0){  //Note ignores leading new lines
            String[] lines = name.split("\n");
            stringWidth = (g.getFontMetrics(font).stringWidth(lines[0]));
            for(int i=0; i<lines.length;i++){
                g.drawString(lines[i], textX, textY+font.getSize()*(i+1));
            }
        }else{
            stringWidth = (g.getFontMetrics(font).stringWidth(name));
            g.drawString(name, textX, textY+font.getSize());
        }
        if(!export){
            g.fillRect(textX-5,textY-5,10,10);
        }
        if(pointX < textX+ (stringWidth/2)){
            //Line going off to left
            g.drawLine(textX-5,textY+(font.getSize()/2),pointX,pointY);
        }else{
            //Line going off to right
            g.drawLine(textX+5+stringWidth,textY+(font.getSize()/2),pointX,pointY);
        }
        g.fillOval(pointX - 2, pointY - 2, 4, 4);
    }

    @Override
    public void draw(Graphics2D g) {
        draw(g, false);
    }

    @Override
    public void drawSelectOverlay(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(textX-5,textY-5,10,10);

        int stringWidth = 0;
        if(name.indexOf("\n") >0){  //Note ignores leading new lines
            String[] lines = name.split("\n");
            stringWidth = (g.getFontMetrics(font).stringWidth(lines[0]));
        }else{
            stringWidth = (g.getFontMetrics(font).stringWidth(name));
        }

        if(pointX < textX+ ((stringWidth)/2)){
            //Line going off to left
            g.drawLine(textX-5,textY+(font.getSize()/2),pointX,pointY);
        }else{
            //Line going off to right
            g.drawLine(textX+5+stringWidth,textY+(font.getSize()/2),pointX,pointY);
        }
        g.fillOval(pointX - 2, pointY - 2, 4, 4);
    }

    @Override
    public void mouseDrag(MouseEvent from, MouseEvent too) {
        if(selectedText){
            textX = too.getX();
            textY = too.getY();
        }else{ //selected pointer
            pointX = too.getX();
            pointY = too.getY();
        }
    }

    @Override
    public int isCorner(int x, int y) {
        return DrawPanel.NOT_CORNER;
    }

    @Override
    public boolean drawName() {
        return true;
    }

    @Override
    public void setDrawName(boolean drawName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getSaveString() {
        return getTypeOfWireframe() +","+
                name.replaceAll("\n","~newline~") +","+     //Make sure to format new lines
                textX +","+
                textY +","+
                pointX +","+
                pointY +","+
                font.getSize();
    }
    public static Pointer makeWireframe(String[] args){
        try{
            Pointer p = new Pointer(args[1].replaceAll("~newline~","\n")); //put the new line characters back in
            int textX = Integer.parseInt(args[2]);
            int textY = Integer.parseInt(args[3]);
            int pointX = Integer.parseInt(args[4]);
            int pointY = Integer.parseInt(args[5]);
            p.setLocations(textX, textY, pointX, pointY);
            int size = Integer.parseInt(args[6]);
            p.setSize(size);
            return p;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getFillType() {
        return "None";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setFillType(String fillType) {
        //Fill makes no sense in this context
    }
}
