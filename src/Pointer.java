import java.awt.*;
import java.awt.event.MouseEvent;

public class Pointer implements Wireframe{
    private String name;
    private int textX = 20*DrawPanel.scale;
    private int textY = 20*DrawPanel.scale;
    private int pointX = 40*DrawPanel.scale;
    private int pointY = 40*DrawPanel.scale;
    private int threshold = 8*DrawPanel.scale;     //TODO won't scale as the scale changes
    private Color textColor = Color.BLACK;
    private Font font;
    private int brushWidth = 1*DrawPanel.scale;

    private boolean selectedText = true; //was the last true edge check for text or the pointer?

    public Pointer(String name){
        this.name = name;
        setSize(14*DrawPanel.scale);
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
        g.setStroke(new BasicStroke(brushWidth));
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
        g.fillOval(pointX - 2*DrawPanel.scale, pointY - 2*DrawPanel.scale, 4*DrawPanel.scale, 4*DrawPanel.scale);
    }

    @Override
    public void draw(Graphics2D g) {
        draw(g, false);
    }

    @Override
    public void drawSelectOverlay(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(textX - 5, textY - 5, 10, 10);
        g.setStroke(new BasicStroke(brushWidth));

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
        g.fillOval(pointX - 2*DrawPanel.scale, pointY - 2*DrawPanel.scale, 4*DrawPanel.scale, 4*DrawPanel.scale);
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

    public void setBrushWidth(int brushWidth){
        this.brushWidth = brushWidth;
    }

    @Override
    public Wireframe upscale(int scale) {
        Pointer returnPointer = new Pointer(name);
        returnPointer.setLocations(textX*scale, textY*scale, pointX*scale, pointY*scale);
        returnPointer.setBrushWidth(brushWidth * scale);
        returnPointer.setSize(font.getSize()*scale);
        return returnPointer;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
