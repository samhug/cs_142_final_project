import java.awt.Graphics;
import java.awt.Point;

public class GameObject {
    
    protected Point position;
    private Sprite sprite;

    public Signal onUpdateSignal;
    
    public GameObject() {
        onUpdateSignal = new Signal();
        position = new Point(0,0);
    }
    
    public void render(Graphics g) {
        g.drawImage(sprite.getImage(), position.x, position.y, null);
    }
    
    protected Sprite getSprite() {
        return sprite;
    }
    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
