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
    private Coletor coletor;
    private int x;
    private int y;
    private boolean caminhoEscolhido=false;

    public Area(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public Area(Object item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
    }

    public Area(Coletor coletor, int x, int y) {
        this.coletor = coletor;
        this.x = x;
        this.y = y;
    }

    public Area(Area area) {
        this.item = area.getItem();
        this.coletor = area.getColetor();
        this.x = area.getX();
        this.y = area.getY();
    }

    

    @Override
    public String toString() {
        String saida = "";
        if (item != null) {
            //  return item + ", x=" + x + ", y=" + y + '}';
            saida = item.toString();
        }
        //return "Area{" + "lixo=" + lixo + ", recarga=" + recarga + ", x=" + x + ", y=" + y + '}';
        if (coletor != null) {
            saida = saida + " " + coletor.toString();
        }
        if (!saida.equalsIgnoreCase("")) {
            return saida;
        }
        return " - ";
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
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

    public Coletor getColetor() {
        return coletor;
    }

    public void setColetor(Coletor coletor) {
        this.coletor = coletor;
    }

    public boolean isCaminhoEscolhido() {
        return caminhoEscolhido;
    }

    public void setCaminhoEscolhido(boolean caminhoEscolhido) {
        this.caminhoEscolhido = caminhoEscolhido;
    }

}
