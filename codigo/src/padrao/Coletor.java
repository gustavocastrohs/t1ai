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

    private ArrayList<Lixo> lixeiraDoColetor;
    private boolean statusLixeiraCheia;
    private ArrayList<Lixeira> locaisLixeiras;
    private ArrayList<Recarga> locaisPontosDeRecarga;
    private int capacidadeLixeira;
    private int energiaAtual;
    private int energiaMinima;
    private int energiaMaxima;

    public Coletor(ArrayList<Lixeira> locaisLixeiras, ArrayList<Recarga> locaisPontosDeRecarga, int capacidadeLixeira, int energiaAtual, int energiaMinima, int energiaMaxima) {
        this.locaisLixeiras = locaisLixeiras;
        this.locaisPontosDeRecarga = locaisPontosDeRecarga;
        this.capacidadeLixeira = capacidadeLixeira;
        this.energiaAtual = energiaAtual;
        this.energiaMinima = energiaMinima;
        this.energiaMaxima = energiaMaxima;
        this.statusLixeiraCheia = false;
        this.lixeiraDoColetor = new ArrayList<>();
    }



    public Coletor() {
    }

    public void percepcao(Area visaoAtual[][]) {

        if (energiaAtual <= energiaMinima) {

            carregar();
        }
        if (statusLixeiraCheia) {
            descarregarLixo();
        }

        mover(visaoAtual);

    }

    public boolean verificaSeHaLixoNaVisao(Area visaoAtual[][]) {
        for (int i = 0; i < visaoAtual.length; i++) {
            for (int j = 0; j < visaoAtual.length; j++) {
                Area a = visaoAtual[i][j];
                if (a.getItem() instanceof Lixo) {
                    return true;
                }
            }
        }

        return false;
    }

    public void mover(Area visaoAtual[][]) {
        energiaAtual--;
        if (verificaSeHaLixoNaVisao(visaoAtual)) {
            recolherLixo(visaoAtual);
        }
    }

    public void recolherLixo(Area visaoAtual[][]) {
    }

    public void recolhendoLixo() {
    }

    public void carregar() {

        carregando(new Recarga("me altere"));

    }

    public void carregando(Recarga recarga) {
        while (energiaAtual < energiaMaxima) {
            energiaAtual++;
        }

    }

    public void descarregarLixo() {

    }

    public void descarregandoLixo(Lixeira lixeira, Lixo lixo) {
        if (lixeira.getTipoDeArmazenagem() == lixo.getTipoDeLixo()) {
            energiaAtual--;
        }
    }

    @Override
    public String toString() {
        return "*";
    }

}
