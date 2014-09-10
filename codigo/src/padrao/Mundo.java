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
    private int tamanhoVisaoDoColetor;

    public Mundo(int x, int y, int quantidadeDeLixoEsperada, int quantidadeDeRecargas, int tamanhoVisaoDoColetor) {
        tamanhoDoX = x;
        tamanhoDoY = y;
        this.tamanhoVisaoDoColetor = tamanhoVisaoDoColetor;
        areaDoMundo = new Area[y][x];
        locaisDosLixos = new ArrayList<>();
        locaisDasRecargas = new ArrayList<>();
        locaisLixeiras = new ArrayList<>();
        itemsASeremExecutados = new ArrayList<>();
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

    public void printaVisao(Area[][] criaUmaVisao) {

        System.out.println("printando a visao");
        int inicializaVisao = tamanhoVisaoDoColetor * 2 + 1;
        for (int i = 0; i < inicializaVisao; i++) {
            System.out.println("");
            
                for (int j = 0; j < inicializaVisao; j++) {
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

    private Lixeira criaLixeira(int numeroDaLixeira, int capacidade, TipoDeLixo tipo) {
        String prefixo = tipo.toString();
        return new Lixeira(capacidade, tipo, prefixo + numeroDaLixeira);
    }

    public Area[][] criaUmaVisao(int posicaoColetorX, int posicaoColetorY) {

        int inicializaVisao = tamanhoVisaoDoColetor * 2 + 1;
        Area[][] visao = new Area[inicializaVisao][inicializaVisao];

        int criaQuandradoX1 = criaQuandradoX1(posicaoColetorX, tamanhoVisaoDoColetor);
        int criaQuandradoX2 = criaQuandradoX2(posicaoColetorX, tamanhoVisaoDoColetor);
        int criaQuandradoY1 = criaQuandradoY1(posicaoColetorY, tamanhoVisaoDoColetor);
        int criaQuandradoY2 = criaQuandradoY2(posicaoColetorY, tamanhoVisaoDoColetor);

        System.out.println("Posição do coletor");
        System.out.println("x" + posicaoColetorX + " y:" + posicaoColetorY);
        System.out.println("Visão do coletor");
        System.out.println("x1: " + criaQuandradoX1 + " x2:" + criaQuandradoX2);
        System.out.println("y1: " + criaQuandradoY1 + " y2:" + criaQuandradoY2);
        
        int auxI = 0;

        for (int x = criaQuandradoX1; x <= criaQuandradoX2 ; x++) {
            int auxJ = 0;
            for (int y = criaQuandradoY1; y <= criaQuandradoY2 ; y++) {    
                Area a = areaDoMundo[x][y];
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
            return tamanhoDoX-1;
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
            return tamanhoDoY-1;
        }

        return posicao + tamanhoDaVisao;
    }

    public void executaOMunda() {
        Object object = itemsASeremExecutados.remove(0);

        if (object instanceof Coletor) {
            Coletor c = (Coletor) object;
            mudaUmaAreaColetor( c.getxAtual(),c.getyAtual(), c);
            Area[][] criaUmaVisao = criaUmaVisao(c.getxAtual(), c.getyAtual());
            int xAntigo = c.getxAtual();
            int yAntigo = c.getyAtual();

            
            printaVisao(criaUmaVisao);
            printaMundo();
            c.percepcao(criaUmaVisao);
            mudaUmaAreaColetor(xAntigo, yAntigo, null);
            mudaUmaAreaColetor(c.getxAtual(), c.getyAtual(), c);
            printaMundo();

        }
        if (object instanceof Recarga) {

        }
        if (object instanceof Lixeira) {

        }

        itemsASeremExecutados.add(object);
    }

    public void mudaUmaAreaItem(int x, int y, Object novoObjeto) {
        Area a = areaDoMundo[x][y];

        a.setItem(novoObjeto);

    }

    public boolean mudaUmaAreaColetor(int x, int y, Object novoObjeto) {

        Area a = areaDoMundo[x][y];
        if (a.getColetor() == null) {
            a.setColetor((Coletor) novoObjeto);
            return true;
        } else {
            return false;
        }
    }

    public void criaUmColetor(int x, int y, int capacidadeLixeira, int energiaMinima, int energiaMaxima) {
        Coletor coletor = new Coletor(locaisLixeiras, locaisDasRecargas, capacidadeLixeira, energiaMinima, energiaMaxima, x, y);
        Area area = areaDoMundo[x][y];
        area.setColetor(coletor);
        itemsASeremExecutados.add(coletor);

    }
}
