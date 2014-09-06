/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

/**
 *
 * @author Gustavo
 */
public class Area {

    private Object item;
    private int x;
    private int y;

    public Area(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public Area(Object item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        if (item != null) {
              //  return item + ", x=" + x + ", y=" + y + '}';
            return item.toString();
        }
        //return "Area{" + "lixo=" + lixo + ", recarga=" + recarga + ", x=" + x + ", y=" + y + '}';
        return " - ";
    }

    public Object getItem() {
        return item;
    }

    public Object setItem(Object item) {
        Object o = this.item;
        this.item = item;
        return o;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
