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
public class Lixeira {

    //private boolean cheia;
    private int capacidade;
    private ArrayList<Lixo> coletorDaLixiera;
    private TipoDeLixo tipoDeArmazenagem;
    private String nome;

    public Lixeira(int capacidade, TipoDeLixo tipoDeArmazenagem, String nome) {
        this.coletorDaLixiera = new ArrayList<>();
        this.capacidade = capacidade;
        this.tipoDeArmazenagem = tipoDeArmazenagem;
        this.nome = nome;
       // cheia = false;
    }

    public boolean isCheia() {
        if (capacidade ==0) {
            return true;
        }
        return false;
    }

    public int getCapacidade() {
        return capacidade;
    }

    @Override
    public String toString() {
        return nome ;
    }

    public TipoDeLixo getTipoDeArmazenagem() {
        return tipoDeArmazenagem;
    }

    public String getNome() {
        return nome;
    }

    public boolean addLixo(Lixo lixo) {

        if (isCheia()) {
            return false;
        } else if (lixo.getTipoDeLixo() == tipoDeArmazenagem) {
            coletorDaLixiera.add(lixo);
            capacidade--;
            return true;
        }
        return false;

    }

    public ArrayList<Lixo> getColetorDaLixiera() {
        return coletorDaLixiera;
    }

    public void setColetorDaLixiera(ArrayList<Lixo> coletorDaLixiera) {
        this.coletorDaLixiera = coletorDaLixiera;
    }
    
    

}
