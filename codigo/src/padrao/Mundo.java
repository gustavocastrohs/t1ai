/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
    
    private int tamanhoVisaoDoColetor;

    /**
     *
     * @param x tamanho do mundo
     * @param y tamanho do mundo
     * @param quantidadeDeLixoEsperada quantidade de lixo que será gerada
     * randomicamente
     * @param quantidadeDeRecargas quantidade de recargas
     * @param quantidadeDeLixeirasEsperado
     * @param capacidadeDaLixeira
     * @param tamanhoVisaoDoColetor tamanho da visão dos coletores
     */
    public Mundo(int x, int y, int quantidadeDeLixoEsperada, int quantidadeDeRecargas, int quantidadeDeLixeirasEsperado, int capacidadeDaLixeira, int tamanhoVisaoDoColetor) throws Exception{
    int tamanhoTotalDoMundo = x*y;
        tamanhoDoX = x;
        tamanhoDoY = y;
        this.tamanhoVisaoDoColetor = tamanhoVisaoDoColetor;
        areaDoMundo = new Area[x][y];
        locaisDosLixos = new ArrayList<>();
        locaisDasRecargas = new ArrayList<>();
        locaisLixeiras = new ArrayList<>();
        itemsASeremExecutados = new ArrayList<>();
        inicializaMundo();
        int minimoEspaco = 1-(quantidadeDeLixeirasEsperado/4) - quantidadeDeRecargas -quantidadeDeLixoEsperada;
        System.out.println(tamanhoTotalDoMundo + " == " + minimoEspaco);
        
        if (tamanhoTotalDoMundo - minimoEspaco >0)        {
        
        
        for (int i = 1; i <= quantidadeDeLixoEsperada; i++) {
            addLixo(i);
        }
        
        for (int i = 1; i <= quantidadeDeRecargas; i++) {
            addRecarga();
        }
        for (int i = 0; i < quantidadeDeLixeirasEsperado/4; i++) {
            
            
                addLixeira(TipoDeLixo.metal, capacidadeDaLixeira);
            
            
                addLixeira(TipoDeLixo.vidro, capacidadeDaLixeira);
            
            
                addLixeira(TipoDeLixo.papel, capacidadeDaLixeira);
            
            
                addLixeira(TipoDeLixo.plastico, capacidadeDaLixeira);
            
        }
        }
        else{
            
            throw  new Exception("o mundo é muito pequeno para o que voce deseja colocar nele");
        }
        /**
         * Area[][] criaUmaVisao = criaUmaVisao(3,4,2);
         *
         * ser revisado ainda implementação não está ok
         */

    }

    private void inicializaMundo() {

        for (int x = 0; x < tamanhoDoY; x++) {
            for (int y = 0; y < tamanhoDoX; y++) {
                areaDoMundo[x][y] = new Area(x, y);
            }
        }
    }

    private void addLixo(int numeroDoLixo) {
        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(tamanhoDoX - 1);
        int aleatorioY = gerador.nextInt(tamanhoDoY - 1);
        Area a = areaDoMundo[aleatorioX][aleatorioY];
        if (a.getItem() == null) {
            a.setItem(criaLixo(numeroDoLixo));
            locaisDosLixos.add(a);
        } else {
            addLixo(numeroDoLixo);
        }

    }

    private void addRecarga() {

        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(tamanhoDoX - 1);
        int aleatorioY = gerador.nextInt(tamanhoDoY - 1);
        Area a = areaDoMundo[aleatorioX][aleatorioY];
        if (a.getItem() == null) {
            a.setItem(criaRecarga(aleatorioX, aleatorioY));
            locaisDasRecargas.add(a);
        } else {
            addRecarga();
        }

    }

    private void addLixeira(TipoDeLixo tipo, int capacidadeDaLixeira) {

        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(tamanhoDoX - 1);
        int aleatorioY = gerador.nextInt(tamanhoDoY - 1);
        Area a = areaDoMundo[aleatorioX][aleatorioY];
        if (a.getItem() == null) {
            a.setItem(criaLixeira(aleatorioX, aleatorioY, capacidadeDaLixeira, tipo));
            locaisLixeiras.add(a);
        } else {
            addLixeira(tipo, capacidadeDaLixeira);
        }

    }

    public void printaMundo() {

        for (int x = 0; x < tamanhoDoX; x++) {
            System.out.println("");
            
                for (int y = 0; y < tamanhoDoY; y++) {
                System.out.print(areaDoMundo[x][y]);
            }
        }
        System.out.println("\n");

    }

    private Lixo criaLixo(int numeroDoLixo) {

        Random gerador = new Random();

        int aleatorio = gerador.nextInt(4);
        String prefixo = "Lixo";
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

    private Recarga criaRecarga(int x, int y) {
        String prefixo = "Rec";
        return new Recarga(prefixo + "-" + x + "/" + y);

    }

    private Lixeira criaLixeira(int x, int y, int capacidade, TipoDeLixo tipo) {
        String prefixo = tipo.toString();
        return new Lixeira(capacidade, tipo, prefixo + "-" + x + "/" + y);
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

        int auxX = 0;

        for (int x = criaQuandradoX1; x <= criaQuandradoX2; x++) {
            int auxY = 0;
            for (int y = criaQuandradoY1; y <= criaQuandradoY2; y++) {
                Area a = areaDoMundo[x][y];
                visao[auxX][auxY] = a;
                auxY++;
            }
            auxX++;
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
            return tamanhoDoX - 1;
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
            return tamanhoDoY - 1;
        }

        return posicao + tamanhoDaVisao;
    }

    
    public Object executaOMunda(JTable tabelaVisaoMundo, JTable tabelaVisaoColetor) {
        Object object = null;
        if (!itemsASeremExecutados.isEmpty()){
            object = itemsASeremExecutados.remove(0);
        
     //   do {
            
            if (object instanceof Coletor) {
                Coletor c = (Coletor) object;
                mudaUmaAreaColetor(c.getxAtual(), c.getyAtual(), c);
                Area[][] criaUmaVisao = criaUmaVisao(c.getxAtual(), c.getyAtual());
                int xAntigo = c.getxAtual();
                int yAntigo = c.getyAtual();
                
                atualizaVisaoModel(tabelaVisaoMundo);
                //printaMundo();
                c.printaVisao(criaUmaVisao,1);
                
                //printaMundo();
                c.percepcao(criaUmaVisao);
                mudaUmaAreaColetor(xAntigo, yAntigo, null);
                mudaUmaAreaColetor(c.getxAtual(), c.getyAtual(), c);
                atualizaVisaoModel(tabelaVisaoMundo);
                printaMundo();
                if (c.getEnergiaAtual() == 0) {
                    object = null;
                }

            }
//            if (object instanceof Recarga) {
//
//            }
//            if (object instanceof Lixeira) {
//
//            }
            if (object != null) {
                
                itemsASeremExecutados.add(object);
                return object;
            }
      
       // } while (itemsASeremExecutados.size() > 0);
      //  return false;
        }
        return null;
    }
    
        public void atualizaVisaoModel(JTable tabela) {
      //  printaMundo();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        
        try{
        for (int x = 0; x < tamanhoDoX; x++) {
        //    System.out.println("");
            for (int y = 0; y < tamanhoDoY; y++) {

                model.setValueAt((areaDoMundo[x][y]), x, y);
                
            }
        }
        }catch (NullPointerException e){
        System.out.println(e.getMessage());
        
        }
        //tabela.repaint();
        //tabela.revalidate();

    }

    /**
     *
     * @param x posicao
     * @param y posicao
     * @param novoObjeto nov
     */
    public Area mudaUmaAreaItem(int x, int y, Object novoObjeto) {
        Area a = areaDoMundo[x][y];
        Area oldArea = new Area(a);
        a.setItem(novoObjeto);
        return oldArea;

    }

    /**
     *
     * @param x posição
     * @param y posição
     * @param novoObjeto objeto onde vai ser adicionado o item
     * @return resultado da tentativa de mudanção de um coletor de local
     */
    public boolean mudaUmaAreaColetor(int x, int y, Object novoObjeto) {

        Area a = areaDoMundo[x][y];
        Object item = a.getItem();

        if (item instanceof Lixeira) {
            return false;
        }
        if (item instanceof Recarga) {
            return false;
        }
        if (a.getColetor() == null) {
            a.setColetor((Coletor) novoObjeto);
            return true;
        }
        if (a.getColetor() != null && novoObjeto == null) {
            a.setColetor(null);
            return true;
        }

        return false;

    }

    /**
     *
     * @param capacidadeLixeiraMaxima
     * @param capacidadeMinima
     * @param energiaMinima energia minima para o coletor chegar a uma lixeira
     * @param energiaMaxima energia maxima do coletor
     * @return 
     */

    public Coletor criaUmColetor(int capacidadeLixeiraMaxima, int energiaMinima, int energiaMaxima,JTable tabelaVisaoColetorTotal,JTable tabelaVisaoColetorParcial) {
        Coletor coletor = null;
        Area area = null;
        do {
            Random gerador = new Random();
            int x = gerador.nextInt(tamanhoDoX - 1);
            int y = gerador.nextInt(tamanhoDoY - 1);

            coletor = new Coletor(locaisLixeiras, locaisDasRecargas, capacidadeLixeiraMaxima, energiaMinima, energiaMaxima, x, y, tamanhoVisaoDoColetor,tabelaVisaoColetorTotal,tabelaVisaoColetorParcial);
            //   coletor.setEnergiaAtual(energiaMaxima);
            area = areaDoMundo[x][y];
            if (area.getColetor() == null && area.getItem() == null) {

                area.setColetor(coletor);
            }
        } while (area.getColetor() == null);
        itemsASeremExecutados.add(coletor);

        return coletor;
    }
    
    

    public ArrayList<Area> getLocaisLixeiras() {
        return locaisLixeiras;
    }

    public void setLocaisLixeiras(ArrayList<Area> locaisLixeiras) {
        this.locaisLixeiras = locaisLixeiras;
    }

    public ArrayList<Area> getLocaisDosLixos() {
        return locaisDosLixos;
    }

    public void setLocaisDosLixos(ArrayList<Area> locaisDosLixos) {
        this.locaisDosLixos = locaisDosLixos;
    }

    public ArrayList<Area> getLocaisDasRecargas() {
        return locaisDasRecargas;
    }

    public void setLocaisDasRecargas(ArrayList<Area> locaisDasRecargas) {
        this.locaisDasRecargas = locaisDasRecargas;
    }

    public ArrayList<Object> getItemsASeremExecutados() {
        return itemsASeremExecutados;
    }

    public void setItemsASeremExecutados(ArrayList<Object> itemsASeremExecutados) {
        this.itemsASeremExecutados = itemsASeremExecutados;
    }

    public int getTamanhoDoX() {
        return tamanhoDoX;
    }

    public void setTamanhoDoX(int tamanhoDoX) {
        this.tamanhoDoX = tamanhoDoX;
    }

    public int getTamanhoDoY() {
        return tamanhoDoY;
    }

    public void setTamanhoDoY(int tamanhoDoY) {
        this.tamanhoDoY = tamanhoDoY;
    }

    public Area[][] getAreaDoMundo() {
        return areaDoMundo;
    }

    public void setAreaDoMundo(Area[][] areaDoMundo) {
        this.areaDoMundo = areaDoMundo;
    }

    public int getTamanhoVisaoDoColetor() {
        return tamanhoVisaoDoColetor;
    }

    public void setTamanhoVisaoDoColetor(int tamanhoVisaoDoColetor) {
        this.tamanhoVisaoDoColetor = tamanhoVisaoDoColetor;
    }

}
