/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package padrao;

/**
 *
 * @author 09201801
 */
public class AreaCusto {
    private Area area;
    private double custo;

    public AreaCusto(Area area, double custo) {
        this.area = area;
        this.custo = custo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }
    
    
    
}
