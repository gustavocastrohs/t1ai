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
public class Recarga {

    /**
     * se vago = true se não = false
     */
    private boolean vaga1;
    private boolean vaga2;
    private String nome;

    public Recarga(String nome) {
        this.nome = nome;
        vaga1 = true;
        vaga2 = true;
    }

    public boolean disponivelParaRecarga() {
        return vaga1 || vaga2;
    }

    public int conectaParaRecarrega() {
        if (vaga1) {
            vaga1 = false;
            return 1;

        }
        if (vaga2) {
            vaga2 = false;
            return 2;
        }
        return -1;
    }

    public void liberaPosicao(int posicao) {
        if (posicao == 1) {
            vaga1 = true;
        }
        if (posicao == 2) {
            vaga2 = false;
        }
    }

    @Override
    public String toString() {
        return nome;
    }

    public int energia() {

        return 1;
    }

}
