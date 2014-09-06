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
public class Lixo {
    private TipoDeLixo tipoDeLixo;
    private String nome;
    private int x;
    private int y;

    public Lixo(TipoDeLixo tipoDeLixo, String nome) {
        this.tipoDeLixo = tipoDeLixo;
        this.nome = nome;
    }

    public TipoDeLixo getTipoDeLixo() {
        return tipoDeLixo;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + " " + tipoDeLixo;
    }
    
}
