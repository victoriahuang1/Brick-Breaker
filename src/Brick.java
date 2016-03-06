import java.awt.Color;
import java.awt.Graphics;


public class Brick extends GameObj{
    
    public static final int WIDTH = 75;
    public static final int HEIGHT = 25;
    private int hitsLeft;
    private Color[] colors = {Color.RED, Color.MAGENTA, Color.PINK};
    
    public Brick(int pos_x, int pos_y, int h) {
        super(0, 0, pos_x, pos_y, WIDTH, HEIGHT, GameCourt.COURT_LENGTH, GameCourt.COURT_HEIGHT);
        hitsLeft = h;
    }
    
    public void decr(){
        hitsLeft--;
    }
    
    public int getHits(){
        return hitsLeft;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(colors[hitsLeft-1]);
        g.fillRect(pos_x, pos_y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(pos_x, pos_y, width, height);  
    }

}
