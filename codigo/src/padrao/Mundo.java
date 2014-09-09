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
    private ArrayList<Object> itemsASeremExecutados;
    private int tamanhoDoX;
    private int tamanhoDoY;
    private Area areaDoMundo[][];
    private int quantidadeDeLixo;
    private int quantidadeDeRecargas;
    private int quantidadeDeLixeiras;

    public Mundo(int x, int y, int quantidadeDeLixoEsperada, int quantidadeDeRecargas) {
        tamanhoDoX = x;
        tamanhoDoY = y;
        areaDoMundo = new Area[y][x];
        locaisDosLixos = new ArrayList<>();
        locaisDasRecargas = new ArrayList<>();
        locaisLixeiras = new ArrayList<>();
        inicializaMundo();
        quantidadeDeLixo = 0;
        quantidadeDeLixeiras = 0;
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
         *
         * ser revisado ainda implementação não está ok
         */

       
        
    }

    private void inicializaMundo() {
        for (int y = 0; y < tamanhoDoX; y++) {
            for (int x = 0; x < tamanhoDoY; x++) {
                areaDoMundo[y][x] = new Area(y, x);
            }
        }
    }

    private void addLixo() {
        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(tamanhoDoX - 1);
        int aleatorioY = gerador.nextInt(tamanhoDoY - 1);
        Area a = areaDoMundo[aleatorioY][aleatorioX];
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
        int aleatorioX = gerador.nextInt(tamanhoDoX - 1);
        int aleatorioY = gerador.nextInt(tamanhoDoY - 1);
        Area a = areaDoMundo[aleatorioY][aleatorioX];
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
        int aleatorioX = gerador.nextInt(tamanhoDoX - 1);
        int aleatorioY = gerador.nextInt(tamanhoDoY - 1);
        Area a = areaDoMundo[aleatorioY][aleatorioX];
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

                System.out.print(areaDoMundo[j][i]);
            }
        }
        System.out.println("\n");
        
    }

    public void printaVisao(Area[][] criaUmaVisao, int tamanhoDaVisao) {
    
        System.out.println("printando a visao");
        int inicializaVisao = tamanhoDaVisao * 2 + 1;
        for (int j = 0; j < inicializaVisao; j++) {
            System.out.println("");
            for (int i = 0; i < inicializaVisao; i++) {

                System.out.print(criaUmaVisao[j][i]);
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

    private Lixeira criaLixeira(int numeroDaLixeira, int capacidade, TipoDeLixo tipo) {
        String prefixo = tipo.toString();
        return new Lixeira(capacidade, tipo, prefixo + numeroDaLixeira);
    }

    public Area[][] criaUmaVisao(int posicaoColetorX, int posicaoColetorY, int tamanhoDaVisao) {

        int inicializaVisao = tamanhoDaVisao * 2 + 1;
        Area[][] visao = new Area[inicializaVisao][inicializaVisao];

        int criaQuandradoX1 = criaQuandradoX1(posicaoColetorX, tamanhoDaVisao);
        int criaQuandradoX2 = criaQuandradoX2(posicaoColetorX, tamanhoDaVisao);
        int criaQuandradoY1 = criaQuandradoY1(posicaoColetorY, tamanhoDaVisao);
        int criaQuandradoY2 = criaQuandradoY2(posicaoColetorY, tamanhoDaVisao);
      
        System.out.println("x"+posicaoColetorX + " y:"+posicaoColetorY);
      
        System.out.println("x1"+criaQuandradoX1 + " x2:"+criaQuandradoX2);
        System.out.println("y1"+criaQuandradoY1 + " y2:"+criaQuandradoY2);
        int auxI = 0;
        for (int i = criaQuandradoX1; i <= criaQuandradoX2 ; i++) {
            int auxJ = 0;

            for (int j = criaQuandradoY1; j <=criaQuandradoY2 ; j++) {

                Area a = areaDoMundo[i][j];
                visao[auxI][auxJ] = a;
                auxJ++;
            }
            auxI++;
        }
        return visao;

    }

    private int criaQuandradoX1(int posicao, int tamanhoDaVisao) {

        if (posicao - tamanhoDaVisao <= 0) {
            return 0;
        }

        return posicao - tamanhoDaVisao;
    }

    private int criaQuandradoX2(int posicao, int tamanhoDaVisao) {

        if (posicao + tamanhoDaVisao >= tamanhoDoX) {
            return tamanhoDoX;
        }

        return posicao + tamanhoDaVisao;
    }

    private int criaQuandradoY1(int posicao, int tamanhoDaVisao) {

        if (posicao - tamanhoDaVisao <= 0) {
            return 0;
        }

        return posicao - tamanhoDaVisao;
    }

    private int criaQuandradoY2(int posicao, int tamanhoDaVisao) {

        if (posicao + tamanhoDaVisao >= tamanhoDoY) {
            return tamanhoDoY;
        }

        return posicao + tamanhoDaVisao;
    }
    
    private void executaOMunda(){
        Object object = itemsASeremExecutados.get(0);
        if (object instanceof Coletor){
        
        }
        if (object instanceof Recarga){
        
        }
        if (object instanceof Lixeira){
        
        }
    }
    
   public void mudaUmaAreaItem(int x, int y,Object novoObjeto){
   Area a = areaDoMundo[y][x];
      
      a.setItem(novoObjeto);
        
   }
   public void mudaUmaAreaColetor(int x, int y,Object novoObjeto){
   Area a = areaDoMundo[y][x];
   a.setColetor((Coletor) novoObjeto);
   }
}
