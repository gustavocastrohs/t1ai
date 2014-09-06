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
public class Lixeira {
   private boolean cheia;
   private int capacidade;
   private TipoDeLixo tipoDeArmazenagem;
   private String nome;

    
     public Lixeira(int capacidade, TipoDeLixo tipoDeArmazenagem, String nome) {
        this.capacidade = capacidade;
        this.tipoDeArmazenagem = tipoDeArmazenagem;
        this.nome = nome;
        cheia = false;
    }

   
   
    public boolean isCheia() {
        return cheia;
    }

    public int getCapacidade() {
        return capacidade;
    }

    @Override
    public String toString() {
        return nome;
    }

  

}
