/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 09201801
 */
public class Mundo {

    private ArrayList<Area> locaisLixeiras;
    private ArrayList<Area> locaisDosLixos;
    private ArrayList<Area> locaisDasRecargas;
    private int tamanhoDoX;
    private int tamanhoDoY;
    private Area areaDoMundo[][];
    private int quantidadeDeLixo;
    private int quantidadeDeRecargas;
    private int quantidadeDeLixeiras;

    public Mundo(int x, int y, int quantidadeDeLixoEsperada, int quantidadeDeRecargas) {
        tamanhoDoX = x;
        tamanhoDoY = y;
        areaDoMundo = new Area[x][y];
        locaisDosLixos = new ArrayList<>();
        locaisDasRecargas = new ArrayList<>();
        locaisLixeiras = new ArrayList<>();
        inicializaMundo();
        quantidadeDeLixo = 0;
        quantidadeDeLixeiras=0;
        for (int i = 1; i <= quantidadeDeLixoEsperada; i++) {
            addLixo();
        }
        for (int i = 1; i <= quantidadeDeRecargas; i++) {
            addRecarga();
        }
        addLixeira(TipoDeLixo.metal);
        addLixeira(TipoDeLixo.vidro);
        addLixeira(TipoDeLixo.papel);
        addLixeira(TipoDeLixo.plastico);
    /**
     * Area[][] criaUmaVisao = criaUmaVisao(3,4,2);
      
      ser revisado ainda implementação não está ok
      */
        
    }

    private void inicializaMundo() {
        for (int i = 0; i < tamanhoDoX; i++) {
            for (int j = 0; j < tamanhoDoY; j++) {
                areaDoMundo[i][j] = new Area(i, j);
            }
        }
    }

    private void addLixo() {
        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(tamanhoDoX-1);
        int aleatorioY = gerador.nextInt(tamanhoDoY-1);
        Area a = areaDoMundo[aleatorioX][aleatorioY];
        if (a.getItem() == null) {
            a.setItem(criaLixo(quantidadeDeLixo + 1));
            locaisDosLixos.add(a);
        } else {
            addLixo();
        }
        quantidadeDeLixo = quantidadeDeLixo + 1;

    }

    private void addRecarga() {

        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(tamanhoDoX-1);
        int aleatorioY = gerador.nextInt(tamanhoDoY-1);
        Area a = areaDoMundo[aleatorioX][aleatorioY];
        if (a.getItem() == null) {
            a.setItem(criaRecarga(quantidadeDeRecargas + 1));
            locaisDasRecargas.add(a);
        } else {
            addRecarga();
        }
        quantidadeDeRecargas = quantidadeDeRecargas + 1;

    }

        private void addLixeira(TipoDeLixo tipo) {

        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(tamanhoDoX-1);
        int aleatorioY = gerador.nextInt(tamanhoDoY-1);
        Area a = areaDoMundo[aleatorioX][aleatorioY];
        if (a.getItem() == null) {
            a.setItem(criaLixeira(quantidadeDeLixeiras + 1, tamanhoDoX, tipo));
            locaisLixeiras.add(a);
        } else {
            addLixeira(tipo);
        }
        quantidadeDeLixeiras = quantidadeDeLixeiras + 1;

    }
    
    
    public void printaMundo() {

        for (int j = 0; j < tamanhoDoY; j++) {
            System.out.println("");
            for (int i = 0; i < tamanhoDoX; i++) {

                System.out.print(areaDoMundo[i][j]);
            }
        }
        System.out.println("\n");
        // System.out.println(locaisDosLixos);
    }
    public void printaVisao( Area[][] criaUmaVisao) {
        
        for (int j = 0; j < 3; j++) {
            System.out.println("");
            for (int i = 0; i < 3; i++) {

                System.out.print(criaUmaVisao[i][j]);
            }
        }
        System.out.println("\n");
        // System.out.println(locaisDosLixos);
    }
    private Lixo criaLixo(int numeroDoLixo) {
       

        Random gerador = new Random();

        int aleatorio = gerador.nextInt(4);
        String prefixo = "L";
        if (aleatorio == 0 || aleatorio == 4) {
            return new Lixo(TipoDeLixo.vidro, prefixo + numeroDoLixo);
        }
        if (aleatorio == 1) {
            return new Lixo(TipoDeLixo.plastico, prefixo + numeroDoLixo);
        }
        if (aleatorio == 2) {
            return new Lixo(TipoDeLixo.papel, prefixo + numeroDoLixo);
        }
        if (aleatorio == 3) {
            return new Lixo(TipoDeLixo.metal, prefixo + numeroDoLixo);
        }

        return null;
    }

    private Recarga criaRecarga(int numeroDaRecarga) {
        String prefixo = "R";
        return new Recarga(prefixo + numeroDaRecarga);

    }
    private Lixeira criaLixeira(int numeroDaLixeira,int capacidade,TipoDeLixo tipo) {     
        String prefixo = "Lix";
        return new Lixeira(capacidade, tipo, prefixo+numeroDaLixeira);
    }
    
    private Area[][] criaUmaVisao(int x, int y,int tamanhoDaVisao){
        int inicializaVisao = tamanhoDaVisao*2+1;
        Area[][] visao = new Area[inicializaVisao][inicializaVisao] ;
        /**
         * - - - - -
         * - - - - -
         * - - - - -
         * - - - - -
         * - - - - -
         * 
         * 2
         */
       
        int inicialX = x - tamanhoDaVisao;
        if (inicialX>0)
            inicialX = 0;
        int inicialJ = y - tamanhoDaVisao;
         
        for (int i = 0 ;i<tamanhoDaVisao && i <tamanhoDoX;i++){
            for (int j = 0 ;j<tamanhoDaVisao && j <tamanhoDoY;j++){
                Area a  =  areaDoMundo[i][j];
                visao[i][j] = a;
        }
        
        }
        return visao;
    
    
    }
}
