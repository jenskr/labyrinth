package de.uniwue.gdp.labyrinth.model;

public class Field {
    private int xVal;
    private int yVal;
    private boolean isVisited;
    private boolean isMarked;

    public Field(int xVal, int yVal, boolean isVisited, boolean isMarked) {
        this.xVal = xVal;
        this.yVal = yVal;
        this.isVisited = isVisited;
        this.isMarked = isMarked;
    }

    public Field(int xVal, int yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
        this.isVisited = false;
        this.isMarked = false;
    }

    public int getxVal() {
        return xVal;
    }

    public int getyVal() {
        return yVal;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
