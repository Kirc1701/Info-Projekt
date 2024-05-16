package src.drawables.objects;

import src.Drawable;
import src.Main;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import java.awt.*;

import static src.visuals.GameScreen.spaceBetweenLinesPixels;
import static src.visuals.GameScreen.titelbalkenSizePixels;


public abstract class Object implements Drawable {
    protected final int strength;
    protected final int maxHealth;
    protected int health;
    protected CoordsInt position;
    protected final String type;
    protected double spawntime;

    public Object(int pStrength, int pHealth, CoordsInt position, String type) {
        strength = pStrength;
        health = pHealth;
        maxHealth = health;
        this.position = position;
        this.type = type;
        spawntime = (double) System.currentTimeMillis() / 1000;
        Main.registerDrawable(this);
    }

    public int getHealth() {
        return health;
    }

    public CoordsInt getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public double getSpawntime() {
        return spawntime;
    }

    public void setHealth(int health) {
        this.health = health;
        if(this.health <= 0){
            die();
        }
    }

    public void setPosition(CoordsInt position) {
        this.position = position;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setSpawntime(int spawntime) {
        this.spawntime = spawntime;
    }

    public void die(){
        Main.unregisterDrawable(this);
    }

    @Override
    public void draw(Graphics g) {
        Drawable.super.draw(g);
        if (this.getDrawnPosition().equals(new CoordsDouble(-1, -1))) return;
        CoordsDouble pixelPosition = this.getDrawnPosition().scale(spaceBetweenLinesPixels);
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.getOpacity()));
        g2.drawImage(this.
                getImage(), (int) pixelPosition.x(), (int) pixelPosition.y() + titelbalkenSizePixels, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        renderLifeBar((Graphics2D) g);
    }

    private void renderLifeBar(Graphics2D graphics2D) {
        if(health < maxHealth) {
            double lifeInPercent = (double) health / maxHealth;
            int width = (int) (lifeInPercent * (spaceBetweenLinesPixels - 4));
            graphics2D.setStroke(new BasicStroke(3));
            CoordsDouble objektPosition = getDrawnPosition().scale(spaceBetweenLinesPixels);
            graphics2D.setColor(Color.red);
            graphics2D.drawLine((int) (objektPosition.x()+ 2), (int) (objektPosition.y() + 2 + titelbalkenSizePixels), (int) (objektPosition.x() + width), (int) (objektPosition.y() + 2 + titelbalkenSizePixels));
        }
    }

    abstract protected Image getImage();

    abstract protected CoordsDouble getDrawnPosition();
}

