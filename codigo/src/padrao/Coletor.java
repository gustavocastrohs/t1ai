/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package padrao;

import java.util.ArrayList;

/**
 *
 * @author 09201801
 */
public class Coletor {
    private ArrayList<Lixo> lixeira;
    private boolean  statusLixeira;
    private ArrayList<Lixeira> locaisLixeiras;
    private ArrayList<Recarga> locaisPontosDeRecarga;
    private int capacidadeLixeira;
    private int energia;
    private int energiaMinima;

    public Coletor(ArrayList<Lixeira> locaisLixeiras, int capacidadeLixeira, int energia, int energiaMinima) {
        this.locaisLixeiras = locaisLixeiras;
        this.capacidadeLixeira = capacidadeLixeira;
        this.energia = energia;
        this.energiaMinima = energiaMinima;
        
        
    }

    public Coletor() {
    }

    public boolean isStatusLixeira() {
        return statusLixeira;
    }

    public void setStatusLixeira(boolean statusLixeira) {
        this.statusLixeira = statusLixeira;
    }

    public ArrayList<Lixeira> getLocaisLixeiras() {
        return locaisLixeiras;
    }

    public void setLocaisLixeiras(ArrayList<Lixeira> locaisLixeiras) {
        this.locaisLixeiras = locaisLixeiras;
    }

    public ArrayList<Recarga> getLocaisPontosDeRecarga() {
        return locaisPontosDeRecarga;
    }

    public void setLocaisPontosDeRecarga(ArrayList<Recarga> locaisPontosDeRecarga) {
        this.locaisPontosDeRecarga = locaisPontosDeRecarga;
    }

    public int getCapacidadeLixeira() {
        return capacidadeLixeira;
    }

    public void setCapacidadeLixeira(int capacidadeLixeira) {
        this.capacidadeLixeira = capacidadeLixeira;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getEnergiaMinima() {
        return energiaMinima;
    }

    public void setEnergiaMinima(int energiaMinima) {
        this.energiaMinima = energiaMinima;
    }

    public ArrayList<Lixo> getLixeira() {
        return lixeira;
    }

    
    
    
   
    public void percepcao(Area visaoAtual[][]){
    
        if(energia <=energiaMinima)
            carregar();
        if (statusLixeira)
            descarregarLixo();
        
        mover();
        
    
    }
    public void mover(){
        energia--;
    }
    public void carregar(){
        energia++;
    }
    public void descarregarLixo(){
        energia--;
    }
    
    
    
}
