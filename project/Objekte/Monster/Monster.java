package project.Objekte.Monster;

import project.Coords;
import project.Karte;
import project.Objekte.Objekt;

import java.awt.*;
import java.util.List;
import project.Graphic;

import static project.Graphic.space;

public abstract class Monster extends Objekt {
    protected int movingSpeed;
    public Monster(int strength, int health, Coords position, int movingSpeed, int attackSpeed){
        super(strength, health, position, attackSpeed,  "Monster");
        this.movingSpeed = movingSpeed;
    }

    public Rectangle makeMove(Karte karte, Graphic graphic){
        List<Coords> path = getPath(karte);
        Coords oldPosition = position;
        if(path.isEmpty()){
            System.out.println("Something went seriously wrong.");
        }else {
            System.out.println(path.size());
            position = path.get(1);
        }
        //-1 if monster moves left; 0 if no movement on the x-axis happens; 1 if monster moves right
        int directionX = position.getX() - oldPosition.getX();
        //-1 if monster moves up; 0 if no movement on the y-axis happens; 1 if monster moves down
        int directionY = position.getY() - oldPosition.getY();
        int x;
        int y;
        int width = space;
        int height = space;
        if(directionX < 0){
            x = oldPosition.getX()+directionX;
            width = width - directionX * space;
        }else{
            x = oldPosition.getX();
            width = width + directionX * space;
        }
        if(directionY < 0){
            y = oldPosition.getY()+directionY;
            height = height - directionY * space;
        }else{
            y = oldPosition.getY();
            height = height + directionY * space;
        }
        return new Rectangle(x, y, width, height);
    }

    protected abstract List<Coords> getPath(Karte karte);

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public void setMovingSpeed(int movingSpeed) {
        this.movingSpeed = movingSpeed;
    }
}