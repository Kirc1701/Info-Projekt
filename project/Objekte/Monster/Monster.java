package project.Objekte.Monster;

import project.Coords;
import project.Karte;
import project.Objekte.Objekt;

import java.awt.*;
import java.util.List;
import project.Graphic;

public abstract class Monster extends Objekt {
    protected int movingSpeed;
    public Monster(int strength, int health, Coords position, int movingSpeed){
        super(strength, health, position, "Monster");
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
        int width = graphic.space;
        int height = graphic.space;
        if(directionX < 0){
            x = oldPosition.getX()+directionX;
            width = width - directionX * graphic.space;
        }else{
            x = oldPosition.getX();
            width = width + directionX * graphic.space;
        }
        if(directionY < 0){
            y = oldPosition.getY()+directionY;
            height = height - directionY * graphic.space;
        }else{
            y = oldPosition.getY();
            height = height + directionY * graphic.space;
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
