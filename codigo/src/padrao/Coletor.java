/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package padrao;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 09201801
 */
public class Coletor {

    private ArrayList<Lixo> lixeiraDoColetor;
    private boolean statusLixeiraCheia;
    private ArrayList<Area> locaisLixeiras;
    private ArrayList<Area> locaisPontosDeRecarga;
    private int capacidadeLixeiraMaxima;
    private int energiaAtual;
    private int energiaMinima;
    private int energiaMaxima;
    private int xAtual;
    private int yAtual;
    private JTable tabelaVisaoColetorTotal;
    private JTable tabelaVisaoColetorParcial;

    private int statusConectaNaRecarrega = 0;

    private boolean sentidoDoMovimento;

    public Coletor(ArrayList<Area> locaisLixeiras, ArrayList<Area> locaisPontosDeRecarga, int capacidadeLixeiraMaxima, int energiaMinima, int energiaMaxima, int x, int y, int tamanhoVisaoDoColetor, JTable tabelaVisaoColetorTotal, JTable tabelaVisaoColetorParcial) {
        this.lixeiraDoColetor = new ArrayList<>();
        this.statusLixeiraCheia = false;
        this.locaisLixeiras = locaisLixeiras;
        this.locaisPontosDeRecarga = locaisPontosDeRecarga;
        this.capacidadeLixeiraMaxima = capacidadeLixeiraMaxima;
        this.energiaAtual = energiaMaxima;
        this.energiaMinima = energiaMinima;
        this.energiaMaxima = energiaMaxima;
        this.xAtual = x;
        this.yAtual = y;
        this.sentidoDoMovimento = true;
        this.tabelaVisaoColetorTotal = tabelaVisaoColetorTotal;
        this.tabelaVisaoColetorParcial = tabelaVisaoColetorParcial;

    }

    public int percepcao(Area visaoParaDecisao[][]) {
        int tamanhoDoPassoDoColetor = 1;
        organizaLixeira();
        Area[][] criaUmaVisaoMovimentacao = criaUmaVisaoMovimentacao(visaoParaDecisao, tamanhoDoPassoDoColetor);
        atualizaVisaoColetorParcial(tabelaVisaoColetorParcial, criaUmaVisaoMovimentacao);
        atualizaVisaoColetorTotal(tabelaVisaoColetorTotal, visaoParaDecisao);
        if (statusConectaNaRecarrega == 0) {
            gastaEnergia();
//            printaMeusStatus();
            if (verificaSeTemEnergiaOBastante()) {

                if (statusLixeiraCheia) {
                    descarregarLixo(criaUmaVisaoMovimentacao, tamanhoDoPassoDoColetor);

                    return 0;
                } else {

                    if (verificaSeHaLixoNaVisao(visaoParaDecisao)) {
                        recolherLixo(criaUmaVisaoMovimentacao);
                        return 0;
                    } else {

                        mover(criaUmaVisaoMovimentacao);
                    }
                }
                return 0;
            } else {
                recarregar(criaUmaVisaoMovimentacao, new Recarga("inicial"), tamanhoDoPassoDoColetor);
                return 0;
            }
            
        } else {

            recarregar(criaUmaVisaoMovimentacao, new Recarga("inicial"), tamanhoDoPassoDoColetor);

        }
        return 0;
    }

    public void organizaLixeira() {
        lixeiraDoColetor.removeAll(Arrays.asList(null, ""));
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

    public int moverParaUmaLixeira(Area visaoAtual[][], ArrayList<TipoDeLixo> tipoDeLixeirasASeremVisitadas, int tamanhoDoPassoDoColetor) {
        AreaCusto calcularTrajetoriaLixeira = null;
        Area areaTemporaria = null;
        Lixeira lixeira = null;
        /**
         * 1 - move-se para um lixo 2 - move-se para um alvo
         */

//        printaMeusStatus();
        calcularTrajetoriaLixeira = calcularTrajetoriaLixeira(visaoAtual, tipoDeLixeirasASeremVisitadas);

      // xAtual = calcularTrajetoria.getArea().getX();
//        yAtual = calcularTrajetoria.getArea().getY();
        if (calcularTrajetoriaLixeira.getCusto() <= 2) {
            lixeira = verificaLixeiraQueEstaProximoDeMim(visaoAtual, tipoDeLixeirasASeremVisitadas, tamanhoDoPassoDoColetor);
            if (lixeira != null) {

                if (lixeira.isCheia() == false) {

                    for (int i = 0; i < lixeiraDoColetor.size(); i++) {
                        Object o = lixeiraDoColetor.get(i);
                        if (o != null) {
                            Lixo lixo = (Lixo) o;
                            if (lixo.getTipoDeLixo() == lixeira.getTipoDeArmazenagem()) {
                                Lixo remove = lixeiraDoColetor.remove(i);

                                lixeira.addLixo(remove);
                                organizaLixeira();
                            }
                        }
                    }
                }
            }
        }

        if (lixeiraDoColetor.size() == 0) {
            statusLixeiraCheia = false;
        }

        xAtual = calcularTrajetoriaLixeira.getArea().getX();
        yAtual = calcularTrajetoriaLixeira.getArea().getY();

//        printaMeusStatus();
        return 0;

    }

    public void mover(Area visaoAtual[][]) {
        Random gerador = new Random();
        int aleatorioX = gerador.nextInt(visaoAtual.length);
        int aleatorioY = gerador.nextInt(visaoAtual.length);
        Area area = visaoAtual[aleatorioX][aleatorioY];
        if (oCaminhoPodeSerUsado(area)) {
            xAtual = area.getX();
            yAtual = area.getY();
        } else {
            mover(visaoAtual);
        }

    }

    public int recolherLixo(Area visaoAtual[][]) {
        AreaCusto calcularTrajetoria = calcularTrajetoriaLixo(visaoAtual);

        //if (!calcularTrajetoria.getArea().isCaminhoEscolhido()) {
        xAtual = calcularTrajetoria.getArea().getX();
        yAtual = calcularTrajetoria.getArea().getY();

        if (lixeiraDoColetor.size() < capacidadeLixeiraMaxima && statusLixeiraCheia == false) {
            if (calcularTrajetoria.getCusto() <= 1) {
                lixeiraDoColetor.add((Lixo) calcularTrajetoria.getArea().getItem());
                calcularTrajetoria.getArea().setItem(null);
            }

        } else {
            statusLixeiraCheia = true;
        }

        return 1;
        //}

        //System.out.println((locaisLixeiras.get(0).getX() + 1) + "/" + (locaisLixeiras.get(0).getY() + 1));
    }

    public int procuraMaisPerto(Object itemASerBuscado) {

        return procuraItemMaisPerto(0);

    }

    public int procuraItemMaisPerto(int custo) {

        return 0;

    }

    public void carregando(Recarga recarga) {

        energiaAtual = energiaAtual + recarga.energia();

    }

    @Override
    public String toString() {
        return "* " + getxAtual() + " / " + getyAtual();
    }

    public AreaCusto calcularTrajetoriaLixeira(Area visaoAtual[][], ArrayList<TipoDeLixo> tipoDeLixeirasASeremVisitadas) {

        AreaCusto caminhoAtual = null;
        double custo = 999;
        for (TipoDeLixo tipo : tipoDeLixeirasASeremVisitadas) {
            for (Area areaDaLixeira : locaisLixeiras) {
                Lixeira lixeira = (Lixeira) areaDaLixeira.getItem();
                if (lixeira.getTipoDeArmazenagem() == tipo) {
                    int xAlvo = areaDaLixeira.getX();
                    int yAlvo = areaDaLixeira.getY();

                    for (int x = 0; x < visaoAtual.length; x++) {
                        double trajeto = 0;
                        for (int y = 0; y < visaoAtual.length; y++) {

                            Object o = visaoAtual[x][y];
                            if (o != null) {
                                Area a = (Area) o;

                                if (a.getX() == xAtual && a.getY() == yAtual) {
                                    continue;
                                }

                                if (oCaminhoPodeSerUsado(a)) {
                                    trajeto = trajeto + calculaTrajetoriaPasso1a(xAlvo, a.getX(), yAlvo, a.getY());

                                    if (custo > trajeto && custo >= 0) {
                                        custo = trajeto;
                                        caminhoAtual = new AreaCusto(a, custo);

                                        double calculoDoX = Math.abs(xAlvo - a.getX());
                                        double calculoDoY = Math.abs(yAlvo - a.getY());
                                        if ((custo < 2 && custo > 0) && (calculoDoX == 1) && (calculoDoY == 1)) {
                                            System.out.println("Destino: " + xAlvo + ":" + yAlvo);
                                            System.out.println("Menor caminho possivel: " + caminhoAtual.getArea().getX() + ":" + caminhoAtual.getArea().getY() + " custo " + custo);
                                            System.out.println("Estou circulando uma recarga ");

                                            return new AreaCusto(caminhoAtual.getArea(), 0);

                                        }
                                    }
                                }
                            }
                        }

                    }
                    System.out.println("Destino: " + xAlvo + ":" + yAlvo);
                    System.out.println("Menor caminho possivel: " + caminhoAtual.getArea().getX() + ":" + caminhoAtual.getArea().getY() + " para o Alvo : " + areaDaLixeira + " custo " + custo);
                }
            }
        }
        return new AreaCusto(caminhoAtual.getArea(), custo);
    }

    public AreaCusto calcularTrajetoriaRecarga(Area visaoAtual[][], Recarga aSerExcluida) {
        AreaCusto caminhoAtual = null;
        double custo = 999;
        for (Area areaDaRecarga : locaisPontosDeRecarga) {
            int xAlvo = areaDaRecarga.getX();
            int yAlvo = areaDaRecarga.getY();

            if (aSerExcluida.toString().equalsIgnoreCase(areaDaRecarga.toString())) {
                continue;
            } else {

                for (int x = 0; x < visaoAtual.length; x++) {
                    double trajeto = 0;
                    for (int y = 0; y < visaoAtual.length; y++) {

                        Object o = visaoAtual[x][y];
                        if (o != null) {
                            Area a = (Area) o;

                            if (a.getX() == xAtual && a.getY() == yAtual) {
                                continue;
                            }

                            if (oCaminhoPodeSerUsado(a)) {

                                trajeto = trajeto + calculaTrajetoriaPasso1a(xAlvo, a.getX(), yAlvo, a.getY());

                                if (custo > trajeto && custo >= 0) {
                                    custo = trajeto;
                                    caminhoAtual = new AreaCusto(a, custo);

                                    double calculoDoX = Math.abs(xAlvo - a.getX());
                                    double calculoDoY = Math.abs(yAlvo - a.getY());
                                    if ((custo < 2 && custo > 0) && (calculoDoX == 1) && (calculoDoY == 1)) {
                                        System.out.println("Destino: " + xAlvo + ":" + yAlvo);
                                        System.out.println("Menor caminho possivel: " + caminhoAtual.getArea().getX() + ":" + caminhoAtual.getArea().getY() + " custo " + custo);
                                        System.out.println("Estou circulando uma recarga ");

                                        return new AreaCusto(caminhoAtual.getArea(), 0);

                                    }
                                }
                            }

                        }
                    }
                }
                System.out.println("Destino: " + xAlvo + ":" + yAlvo);
                System.out.println("Menor caminho possivel: " + caminhoAtual.getArea().getX() + ":" + caminhoAtual.getArea().getY() + " para o Alvo : " + areaDaRecarga + " custo " + custo);

            }

        }

        return new AreaCusto(caminhoAtual.getArea(), custo);
    }

    public Recarga verificaRecargaQueEstaProximoDeMim(Area visaoAtual[][], int tamanhoDoPassoDoColetor) {
        printaVisao(visaoAtual, tamanhoDoPassoDoColetor);
        for (int x = 0; x < visaoAtual.length; x++) {

            for (int y = 0; y < visaoAtual.length; y++) {

                Object o = visaoAtual[x][y];
                if (o != null) {
                    Area a = (Area) o;
                    if (a.getItem() instanceof Recarga) {
                        System.out.println("Achei uma recarga e está pertinho!");
                        return (Recarga) a.getItem();
                    }
                }

            }
        }
        return null;
    }

    public Lixeira verificaLixeiraQueEstaProximoDeMim(Area visaoAtual[][], ArrayList<TipoDeLixo> alvos, int tamanhoDoPassoDoColetor) {
        ArrayList<Lixeira> possiveisAlvos = new ArrayList<>();
        printaVisao(visaoAtual, tamanhoDoPassoDoColetor);

        for (int x = 0; x < visaoAtual.length; x++) {

            for (int y = 0; y < visaoAtual.length; y++) {

                Object o = visaoAtual[x][y];
                if (o != null) {
                    Area a = (Area) o;
                    if (a.getItem() instanceof Lixeira) {
                        Lixeira lixeiraPossivel = (Lixeira) a.getItem();
                        if (verificaSeTipoDeLixeiraPodeSerUsado(alvos, lixeiraPossivel)) {
                            System.out.println("Achei uma Lixeira e está pertinho!");
                            return lixeiraPossivel;
                        }
                    }
                }

            }

        }

        return null;
    }

    public boolean verificaSeTipoDeLixeiraPodeSerUsado(ArrayList<TipoDeLixo> alvos, Lixeira aSerTestada) {

        for (TipoDeLixo tipo : alvos) {
            if (aSerTestada.getTipoDeArmazenagem() == tipo) {
                return true;
            }
        }
        return false;
    }

    public AreaCusto calcularTrajetoriaLixo(Area visaoAtual[][]) {

        Area caminhoAtual = null;
        ArrayList<AreaCusto> temporario = new ArrayList<>();
        double custo = 999;

        // if (xAlvo != xAtual || yAlvo != yAtual) {
        for (int x = 0; x < visaoAtual.length; x++) {
            double trajeto = 0;
            for (int y = 0; y < visaoAtual.length; y++) {

                Object o = visaoAtual[x][y];
                if (o != null) {
                    Area a = (Area) o;
                    if (a.getX() == xAtual && a.getY() == yAtual) {

                        if (a.getItem() instanceof Lixo) {
                            return new AreaCusto(a, 0);
                        }
                    }
                    if (oCaminhoPodeSerUsado(a)) {
                        trajeto = trajeto + calculaTrajetoriaPasso1a(xAtual, a.getX(), yAtual, a.getY());
                        //trajeto = trajeto + calculaTrajetoriaPasso1b(xAlvo, a.getX(), yAlvo, a.getY());

                        if ((custo > trajeto && custo >= 0) || (a.getItem() != null)) {
                            custo = trajeto;
                            System.out.println("Custo calculado: " + custo);
                            System.out.println("Area calculado: " + a.toString());
                            caminhoAtual = a;

                            if (caminhoAtual.getItem() != null) {

                                temporario.add(new AreaCusto(caminhoAtual, custo));
                            }

                        }

                    }
                }
            }
        }

        System.out.println("Menor caminho possivel: " + caminhoAtual.getX() + ":" + caminhoAtual.getY() + " custo " + custo);
        /*  } else {
         System.out.println("ja cheguei onde queria ir");
         return null;
         }
         */
        if (!temporario.isEmpty()) {
            AreaCusto areaFinal = new AreaCusto(new Area(xAtual, yAtual), 9999);
            for (AreaCusto area : temporario) {
                if (area.getCusto() < areaFinal.getCusto()) {
                    areaFinal = area;
                    System.out.println("Ops! achei um lixo proximo de mim!\n Escolherei o com o menor Custo! " + areaFinal.getArea().getX() + ":" + areaFinal.getArea().getY() + " custo " + areaFinal.getCusto());
                }
            }
            return areaFinal;

        }
        return new AreaCusto(caminhoAtual, custo);

    }

    /**
     *
     * @param areaTeste verifica se a area não contem nenhum item bloqueante
     * @return retorna o resultado disso
     */
    private boolean oCaminhoPodeSerUsado(Area areaTeste) {
        if (areaTeste != null) {
            if (areaTeste.getItem() instanceof Recarga || areaTeste.getItem() instanceof Lixeira || areaTeste.getColetor() != null) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private double calculaTrajetoriaPasso1a(int xAlvo, int xArea, int yAlvo, int yArea) {
        //Heuristica Euclidiana
        //mais precisa e mais rapida já que podemos andar em diagonal
        int dx = xAlvo - xArea;
        int dy = yAlvo - yArea;
        double heuristic = sqrt((dx * dx) + (dy * dy));

        return heuristic;
    }

    private double calculaTrajetoriaPasso1b(int xAlvo, int xArea, int yAlvo, int yArea) {
        //Heuristica Euclidiana
        //mais precisa e mais rapida já que podemos andar em diagonal
        double dx = Math.abs(xAlvo - xArea);
        double dy = Math.abs(yAlvo - yArea);

        double heuristic = dx + dy;
        return heuristic;
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
//        verificaSeTemEnergiaOBastante();
    }

    public boolean verificaSeTemEnergiaOBastante() {
        if (energiaAtual <= energiaMinima) {
            return false;
        }
        return true;

    }

    public int getCapacidadeLixeiraMaxima() {
        return capacidadeLixeiraMaxima;
    }

    public void setCapacidadeLixeiraMaxima(int capacidadeLixeiraMaxima) {
        this.capacidadeLixeiraMaxima = capacidadeLixeiraMaxima;
    }

    public int getEnergiaAtual() {
        return energiaAtual;
    }

    public int getEnergiaMinima() {
        return energiaMinima;
    }

    private void descarregarLixo(Area[][] visaoAtual, int tamanhoDoPassoDoColetor) {

        ArrayList<TipoDeLixo> tipoDeLixeirasASeremVisitadas = new ArrayList<>();

        int metal = quantidaDeLixosNaMinhaLixeira(TipoDeLixo.metal);
        if (metal > 0) {
            tipoDeLixeirasASeremVisitadas.add(TipoDeLixo.metal);
        }

        int papel = quantidaDeLixosNaMinhaLixeira(TipoDeLixo.papel);
        if (papel > 0) {
            tipoDeLixeirasASeremVisitadas.add(TipoDeLixo.papel);
        }

        int vidro = quantidaDeLixosNaMinhaLixeira(TipoDeLixo.vidro);
        if (vidro > 0) {
            tipoDeLixeirasASeremVisitadas.add(TipoDeLixo.vidro);
        }

        int plastico = quantidaDeLixosNaMinhaLixeira(TipoDeLixo.plastico);
        if (plastico > 0) {
            tipoDeLixeirasASeremVisitadas.add(TipoDeLixo.plastico);
        }

        moverParaUmaLixeira(visaoAtual, tipoDeLixeirasASeremVisitadas, tamanhoDoPassoDoColetor);
    }

    public int quantidaDeLixosNaMinhaLixeira(TipoDeLixo tipo) {
        int total = 0;
        for (Lixo lixo : lixeiraDoColetor) {
            if (lixo != null) {
                if (lixo.getTipoDeLixo() == tipo) {
                    total++;
                }
            }
        }
        return total;
    }

    private int recarregar(Area[][] visaoAtual, Recarga r1, int tamanhoDoPassoDoColetor) {

        AreaCusto calcularTrajetoriaRecarga = calcularTrajetoriaRecarga(visaoAtual, r1);
        //Recarga recarga = (Recarga) calcularTrajetoriaRecarga.getArea().getItem();
        Recarga recarga = null;
        if (statusConectaNaRecarrega == 0) {
            if (calcularTrajetoriaRecarga.getCusto() <= 2 && statusConectaNaRecarrega == 0) {
                recarga = verificaRecargaQueEstaProximoDeMim(visaoAtual, tamanhoDoPassoDoColetor);
                if (recarga != null) {
                    if (recarga.disponivelParaRecarga()) {
                        //recarga ainda tem vagas
                        statusConectaNaRecarrega = recarga.conectaParaRecarrega();
                        xAtual = calcularTrajetoriaRecarga.getArea().getX();
                        yAtual = calcularTrajetoriaRecarga.getArea().getY();
                        carregando(recarga);
                        return 0;
                    } else {
                        //recarga precisou ser recalculada
                        AreaCusto calcularTrajetoriaRecarga1 = calcularTrajetoriaRecarga(visaoAtual, recarga);
                        xAtual = calcularTrajetoriaRecarga1.getArea().getX();
                        yAtual = calcularTrajetoriaRecarga1.getArea().getY();
                        return 0;
                    }
                }

            }
        } else if (statusConectaNaRecarrega != 0 && (energiaAtual < energiaMaxima)) {
            recarga = verificaRecargaQueEstaProximoDeMim(visaoAtual, tamanhoDoPassoDoColetor);
            carregando(recarga);
            return 0;

        } else if (energiaAtual >= energiaMaxima) {
            recarga = verificaRecargaQueEstaProximoDeMim(visaoAtual, tamanhoDoPassoDoColetor);
            recarga.liberaPosicao(statusConectaNaRecarrega);
            statusConectaNaRecarrega = 0;
            // carregando(recarga);
            return 0;
        }
        xAtual = calcularTrajetoriaRecarga.getArea().getX();
        yAtual = calcularTrajetoriaRecarga.getArea().getY();

//        printaMeusStatus();
        return 0;

    }

    public void setEnergiaAtual(int energiaAtual) {
        this.energiaAtual = energiaAtual;
    }

    public void printaVisao(Area[][] criaUmaVisao, int tamanhoVisaoDoColetor) {

        System.out.println("printando a visao");
        int inicializaVisao = tamanhoVisaoDoColetor * 2 + 1;
        for (int i = 0; i < inicializaVisao; i++) {
            System.out.println("");

            for (int j = 0; j < inicializaVisao; j++) {
                System.out.print(criaUmaVisao[i][j]);
            }
        }
        System.out.println("\n");
//        atualizaVisaoColetorTotal(tabelaVisaoColetorTotal, criaUmaVisao);
        // System.out.println(locaisDosLixos);
    }

//    public void printaMeusStatus() {
//
//        System.out.println("minha energia atual é: " + energiaAtual);
//        System.out.println("Quantidade de itens na lixeira " + lixeiraDoColetor.size());
//
//    }
    public ArrayList<Lixo> getLixeiraDoColetor() {
        return lixeiraDoColetor;
    }

    public void setLixeiraDoColetor(ArrayList<Lixo> lixeiraDoColetor) {
        this.lixeiraDoColetor = lixeiraDoColetor;
    }

    public boolean isStatusLixeiraCheia() {
        return statusLixeiraCheia;
    }

    public void setStatusLixeiraCheia(boolean statusLixeiraCheia) {
        this.statusLixeiraCheia = statusLixeiraCheia;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    public void setEnergiaMaxima(int energiaMaxima) {
        this.energiaMaxima = energiaMaxima;
    }

    public int getStatusConectaNaRecarrega() {
        return statusConectaNaRecarrega;
    }

    public void setStatusConectaNaRecarrega(int statusConectaNaRecarrega) {
        this.statusConectaNaRecarrega = statusConectaNaRecarrega;
    }

    public Area[][] criaUmaVisaoMovimentacao(Area[][] visaoAtual, int tamanhoDoPassoDoColetor) {

        int inicializaVisao = tamanhoDoPassoDoColetor * 2 + 1;
        Area[][] visao = new Area[inicializaVisao][inicializaVisao];

        System.out.println("Posição do coletor");
        System.out.println("x" + xAtual + " y:" + yAtual);
        System.out.println("Visão do coletor");

        int auxX = 0;

        for (int x = 1; x < 4; x++) {
            int auxY = 0;
            for (int y = 1; y < 4; y++) {
                Object o = visaoAtual[x][y];
                if (o != null) {
                    Area a = (Area) o;
                    visao[auxX][auxY] = a;
                }
                auxY++;
            }
            auxX++;
        }
        return visao;

    }

    public void atualizaVisaoColetorTotal(JTable tabela, Area[][] visao) {
        //  printaMundo();
        DefaultTableModel model = new DefaultTableModel(visao.length, visao.length);
        tabela.setModel(model);

         String[] cols = {"0","1","2","3","4"};
        model.setDataVector(visao,cols);

    }
public void atualizaVisaoColetorParcial(JTable tabela, Area[][] visao) {
        //  printaMundo();
        DefaultTableModel model = new DefaultTableModel(visao.length, visao.length);
        tabela.setModel(model);

         String[] cols = {"0","1","2"};
        model.setDataVector(visao,cols);

    }
    private void inicializaVisaoColetor(Area[][] visao) {

        for (int x = 0; x < visao.length; x++) {
            for (int y = 0; y < visao.length; y++) {
                visao[x][y] = new Area(x, y);
            }
        }
    }
}
