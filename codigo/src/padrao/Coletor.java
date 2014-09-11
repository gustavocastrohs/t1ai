/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 *
 * @author 09201801
 */
public class Coletor {

    private ArrayList<Area> lixeiraDoColetor;
    private boolean statusLixeiraCheia;
    private ArrayList<Area> locaisLixeiras;
    private ArrayList<Area> locaisPontosDeRecarga;
    private int capacidadeLixeira;
    private int energiaAtual;
    private int energiaMinima;
    private int energiaMaxima;
    private int xAtual;
    private int yAtual;

    public Coletor(ArrayList<Area> locaisLixeiras, ArrayList<Area> locaisPontosDeRecarga, int capacidadeLixeira, int energiaMinima, int energiaMaxima, int x, int y) {
        this.lixeiraDoColetor = new ArrayList<>();
        this.statusLixeiraCheia = false;
        this.locaisLixeiras = locaisLixeiras;
        this.locaisPontosDeRecarga = locaisPontosDeRecarga;
        this.capacidadeLixeira = capacidadeLixeira;
        this.energiaAtual = energiaMaxima;
        this.energiaMinima = energiaMinima;
        this.energiaMaxima = energiaMaxima;
        this.xAtual = x;
        this.yAtual = y;
    }

    public Coletor() {
    }

    public void percepcao(Area visaoAtual[][]) {

        verificaSeTemEnergiaOBastante();

        if (statusLixeiraCheia) {
            descarregarLixo(visaoAtual);

        } else {
            mover(visaoAtual);
        }

    }

    public boolean verificaSeHaLixoNaVisao(Area visaoAtual[][]) {
        for (int i = 0; i < visaoAtual.length; i++) {
            for (int j = 0; j < visaoAtual.length; j++) {
                Object o = visaoAtual[i][j];
                if (o != null) {
                    Area a = (Area) o;
                    if (a.getItem() instanceof Lixo) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void mover(Area visaoAtual[][]) {
        gastaEnergia();
        System.out.println("minha energia atual é: "+energiaAtual);
        if (verificaSeHaLixoNaVisao(visaoAtual)) {
            recolherLixo(visaoAtual);
        }
        System.out.println("Minha posição:x: " + xAtual + " / y: " + yAtual);
        Area calcularTrajetoria = calcularTrajetoria(visaoAtual, locaisLixeiras.get(0).getX() + 1, locaisLixeiras.get(0).getY() + 1);

        //System.out.println((locaisLixeiras.get(0).getX() + 1) + "/" + (locaisLixeiras.get(0).getY() + 1));
        xAtual = calcularTrajetoria.getX();
        yAtual = calcularTrajetoria.getY();
        //3,8
    }

    public void recolherLixo(Area visaoAtual[][]) {
        gastaEnergia();
    }

    public void recolhendoLixo() {
    }

    public void carregar() {

        carregando(new Recarga("me altere"));

    }

    public void carregando(Recarga recarga) {
     /*   while (energiaAtual < energiaMaxima) {
            energiaAtual++;
        }
*/
        
    }

    public void descarregarLixo(Area[][] visao) {
        verificaSeTemEnergiaOBastante();

    }

    public void descarregandoLixo(Lixeira lixeira, Lixo lixo) {
        if (lixeira.getTipoDeArmazenagem() == lixo.getTipoDeLixo()) {
            gastaEnergia();
        }
    }

    @Override
    public String toString() {
        return "*";
    }

    public Area calcularTrajetoria(Area visaoAtual[][], int xAlvo, int yAlvo) {

        Area caminhoAtual = null;
        double custo = 999;
        if (xAlvo != xAtual || yAlvo != yAtual) {

            for (int y = 0; y < visaoAtual.length; y++) {
                double trajeto = 0;
                for (int x = 0; x < visaoAtual.length; x++) {
                    Object o = visaoAtual[x][y];
                    if (o != null) {
                        Area a = (Area) o;

                        if (a.getX() == xAtual && a.getY() == yAtual) {
                            continue;
                        }

                        if (oCaminhoPodeSerUsado(a)) {
                            //trajeto = trajeto + calculaTrajetoriaPasso1a(xAlvo, yAlvo);
                            trajeto = trajeto + calculaTrajetoriaPasso1b(xAlvo, a.getX(), yAlvo, a.getY());
                            if (trajeto < 0) {
                                System.out.println(trajeto);
                            }
                            if (custo > trajeto && custo >= 0) {
                                custo = trajeto;
                                caminhoAtual = a;

                            }
                        }
                    }
                }
            }
            System.out.println("Destino: " + xAlvo + ":" + yAlvo);
            System.out.println("Menor caminho possivel: " + caminhoAtual.getX() + ":" + caminhoAtual.getY() + " custo " + custo);
        } else {
            System.out.println("ja cheguei onde queria ir");
            return null;
        }

        return caminhoAtual;
    }

    private boolean oCaminhoPodeSerUsado(Area areaTeste) {
        if (areaTeste.getItem() instanceof Recarga || areaTeste.getItem() instanceof Lixeira) {
            return false;
        }
        return true;
    }

    private double calculaTrajetoriaPasso1a(int xAlvo, int yAlvo) {
        int dx = xAlvo - xAtual;
        int dy = yAlvo - yAtual;
        return sqrt((dx * dx) + (dy * dy));
    }

    private double calculaTrajetoriaPasso1b(int xAlvo, int xArea, int yAlvo, int yArea) {
        int dx = xAlvo - xArea;
        int dy = yAlvo - yArea;
        return sqrt((dx * dx) + (dy * dy));
    }

    private void calculaTrajetoriaPasso2() {

    }

    public int getxAtual() {
        return xAtual;
    }

    public void setxAtual(int xAtual) {
        this.xAtual = xAtual;
    }

    public int getyAtual() {
        return yAtual;
    }

    public void setyAtual(int yAtual) {
        this.yAtual = yAtual;
    }

    public void gastaEnergia() {
        energiaAtual--;
        verificaSeTemEnergiaOBastante();
    }

    public void verificaSeTemEnergiaOBastante() {
        if (energiaAtual <= energiaMinima) {

            carregar();

        }

    }

    public int getCapacidadeLixeira() {
        return capacidadeLixeira;
    }

    public int getEnergiaAtual() {
        return energiaAtual;
    }

    public int getEnergiaMinima() {
        return energiaMinima;
    }
    
    
    
}
