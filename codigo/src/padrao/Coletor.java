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

    private ArrayList<Lixo> lixeiraDoColetor;
    private boolean statusLixeiraCheia;
    private ArrayList<Area> locaisLixeiras;
    private ArrayList<Area> locaisPontosDeRecarga;
    private int capacidadeLixeira;
    private int energiaAtual;
    private int energiaMinima;
    private int energiaMaxima;
    private int xAtual;
    private int yAtual;
    private int lixeirasVisitadas = 0;
    private TipoDeLixo tipoDeLixeiraASerBuscada;
    /**
     * sentidoDoMovimento = true frente
     * sentidoDoMovimento = false retorno
     */
    private boolean  sentidoDoMovimento;

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
        this.sentidoDoMovimento = true;
    }

    public Coletor() {
    }

    public int percepcao(Area visaoAtual[][]) {

        gastaEnergia();
        System.out.println("minha energia atual é: " + energiaAtual);
        System.out.println("Quantidade de itens na lixeira " + lixeiraDoColetor.size());
        if (verificaSeTemEnergiaOBastante()) {

            if (statusLixeiraCheia) {
                descarregarLixo(visaoAtual);
                return 0;
            } else {

                if (verificaSeHaLixoNaVisao(visaoAtual)) {
                    recolherLixo(visaoAtual);
                    return 0;
                }

                mover(visaoAtual);

            }
            return 0;
        } else {
            recarregar(visaoAtual);

        }
        return 0;
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

    public int moverParaUmAlvo(Area visaoAtual[][], Object alvo) {
        AreaCusto calcularTrajetoria = null;
        Area areaTemporaria = null;
        /**
         * 1 - move-se para um lixo 2 - move-se para um alvo
         */

        System.out.println("minha energia atual é: " + energiaAtual);
        if (verificaSeHaLixoNaVisao(visaoAtual)) {
            recolherLixo(visaoAtual);
            return 1;
        }
        System.out.println("Minha posição:x: " + xAtual + " / y: " + yAtual);
        if (alvo instanceof Lixeira) {

            for (int i = 0; i < locaisLixeiras.size(); i++) {
                areaTemporaria = locaisLixeiras.get(i);
                /**
                 * lixeirasVisitadas 1 = metal; 2 = papel; 3 = plastico; 4 =
                 * vidro;
                 */
                Lixeira l = (Lixeira) areaTemporaria.getItem();
                if (l.getTipoDeArmazenagem() == TipoDeLixo.metal && lixeirasVisitadas == 1 && statusLixeiraCheia) {
                    alvo = l;

                }
                if (l.getTipoDeArmazenagem() == TipoDeLixo.papel && lixeirasVisitadas == 2 && statusLixeiraCheia) {
                    alvo = l;

                }
                if (l.getTipoDeArmazenagem() == TipoDeLixo.plastico && lixeirasVisitadas == 3 && statusLixeiraCheia) {
                    alvo = l;

                }
                if (l.getTipoDeArmazenagem() == TipoDeLixo.vidro && lixeirasVisitadas == 4 && statusLixeiraCheia) {
                    alvo = l;

                }

            }

            calcularTrajetoria = calcularTrajetoriaLixeira(visaoAtual);
            if (!calcularTrajetoria.getArea().isCaminhoEscolhido()) {
                xAtual = calcularTrajetoria.getArea().getX();
                yAtual = calcularTrajetoria.getArea().getY();
                if (calcularTrajetoria.getCusto() <= 1 && calcularTrajetoria.getCusto() > 0) {
                    lixeirasVisitadas++;
                }
                if (lixeirasVisitadas > 4) {
                    lixeirasVisitadas = 1;
                    if (lixeiraDoColetor.size() > capacidadeLixeira) {
                        statusLixeiraCheia = false;
                    }
                }
                return 1;
            }
        }
        //System.out.println((locaisLixeiras.get(0).getX() + 1) + "/" + (locaisLixeiras.get(0).getY() + 1));
        if (!calcularTrajetoria.getArea().isCaminhoEscolhido()) {
            xAtual = calcularTrajetoria.getArea().getX();
            yAtual = calcularTrajetoria.getArea().getY();
            return 1;
        }
        return 0;
        //3,8
    }

    public int moverParaUmaLixeira(Area visaoAtual[][]) {
        AreaCusto calcularTrajetoria = null;
        Area areaTemporaria = null;
        /**
         * 1 - move-se para um lixo 2 - move-se para um alvo
         */

        System.out.println("minha energia atual é: " + energiaAtual);
        if (verificaSeHaLixoNaVisao(visaoAtual)) {
            recolherLixo(visaoAtual);
            return 1;
        }
        System.out.println("Minha posição:x: " + xAtual + " / y: " + yAtual);

        /*

         if (l.getTipoDeArmazenagem() == TipoDeLixo.metal && lixeirasVisitadas == 1 && statusLixeiraCheia) {
         alvo = l;

         }
         if (l.getTipoDeArmazenagem() == TipoDeLixo.papel && lixeirasVisitadas == 2 && statusLixeiraCheia) {
         alvo = l;

         }
         if (l.getTipoDeArmazenagem() == TipoDeLixo.plastico && lixeirasVisitadas == 3 && statusLixeiraCheia) {
         alvo = l;

         }
         if (l.getTipoDeArmazenagem() == TipoDeLixo.vidro && lixeirasVisitadas == 4 && statusLixeiraCheia) {
         alvo = l;

         }
         */
        calcularTrajetoria = calcularTrajetoriaLixeira(visaoAtual);
        if (!calcularTrajetoria.getArea().isCaminhoEscolhido()) {
            xAtual = calcularTrajetoria.getArea().getX();
            yAtual = calcularTrajetoria.getArea().getY();
            if (calcularTrajetoria.getCusto() <= 1 && calcularTrajetoria.getCusto() > 0) {
                lixeirasVisitadas++;
            }
            if (lixeirasVisitadas > 4) {
                lixeirasVisitadas = 1;
                if (lixeiraDoColetor.size() > capacidadeLixeira) {
                    statusLixeiraCheia = false;
                }
            }
            return 1;
        }

        //System.out.println((locaisLixeiras.get(0).getX() + 1) + "/" + (locaisLixeiras.get(0).getY() + 1));
        if (!calcularTrajetoria.getArea().isCaminhoEscolhido()) {
            xAtual = calcularTrajetoria.getArea().getX();
            yAtual = calcularTrajetoria.getArea().getY();
            return 1;
        }
        return 0;
        //3,8
    }

    public TipoDeLixo qualOLixoMaisPerto() {

        return null;

    }

    public void mover(Area visaoAtual[][]) {

        /*
        - * - -
        - - - *
        - - - *
        - - * -
        - * - -
        * - - -
        Eu não entendi como fazer a logica completa do mover 
        minha pos sempre no quadrado de 5/5 é a (2,2)
        */
        
        
        int y = 2;
        Object objetoProximo = null;
        Object objetoAtual =null;
        for (int x = 0; x < visaoAtual.length; x++) {
            
                objetoAtual = visaoAtual[x][y];
                if (sentidoDoMovimento) {
                    objetoProximo = visaoAtual[x+1][y];
                }
                else{
                    objetoProximo = visaoAtual[x-1][y];
                }
                    if (objetoProximo != null) {
                        Area a = (Area) objetoProximo;

                            if (a.getY() == yAtual && a.getX() == xAtual) {
                                if (oCaminhoPodeSerUsado(a)) {
                                    
                                }

                            }
                        }
                    else{
                       sentidoDoMovimento = !sentidoDoMovimento ;
                       
                    }
                
            

        }
    }

 

    public int recolherLixo(Area visaoAtual[][]) {
        AreaCusto calcularTrajetoria = calcularTrajetoriaLixo(visaoAtual);

        if (!calcularTrajetoria.getArea().isCaminhoEscolhido()) {
            xAtual = calcularTrajetoria.getArea().getX();
            yAtual = calcularTrajetoria.getArea().getY();

            if (lixeiraDoColetor.size() < capacidadeLixeira) {
                if (calcularTrajetoria.getCusto() <= 1) {
                    lixeiraDoColetor.add((Lixo) calcularTrajetoria.getArea().getItem());
                    calcularTrajetoria.getArea().setItem(null);
                }
                
                
            } else {
                statusLixeiraCheia = true;
            }

            return 1;
        }

        //System.out.println((locaisLixeiras.get(0).getX() + 1) + "/" + (locaisLixeiras.get(0).getY() + 1));
        if (!calcularTrajetoria.getArea().isCaminhoEscolhido()) {
            xAtual = calcularTrajetoria.getArea().getX();
            yAtual = calcularTrajetoria.getArea().getY();
            return 1;
        }
        return 0;
    }

    public void recolhendoLixo() {
    }

    public void carregar(Area visaoAtual[][]) {

        //carregando(new Recarga("me altere"));
        calcularTrajetoriaRecarga(visaoAtual);

    }

    public int procuraMaisPerto(Object itemASerBuscado) {

        return procuraItemMaisPerto(0);

    }

    public int procuraItemMaisPerto(int custo) {

        return 0;

    }

    public void carregando(Recarga recarga) {
        /*   while (energiaAtual < energiaMaxima) {
         energiaAtual++;
         }
         */

    }

    public void descarregandoLixo(Lixeira lixeira, Lixo lixo) {
        if (lixeira.getTipoDeArmazenagem() == lixo.getTipoDeLixo()) {
            // gastaEnergia();
        }
    }

    @Override
    public String toString() {
        return "*";
    }

    public AreaCusto calcularTrajetoriaLixeira(Area visaoAtual[][]) {

        Area caminhoAtual = null;
        double custo = 999;
        for (Area areaDaLixeira : locaisLixeiras) {

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
                            //trajeto = trajeto + calculaTrajetoriaPasso1b(xAlvo, a.getX(), yAlvo, a.getY());

                            if (custo > trajeto && custo >= 0) {
                                custo = trajeto;
                                caminhoAtual = a;

                            }

                            if ((custo <= 1 && custo > 0) && (Math.abs(xAlvo - a.getX()) == 1) && (Math.abs(yAlvo - a.getY()) == 1)) {
                                System.out.println("Destino: " + xAlvo + ":" + yAlvo);
                                System.out.println("Menor caminho possivel: " + caminhoAtual.getX() + ":" + caminhoAtual.getY() + " custo " + custo);
                                System.out.println("Estou circulando meu de destino ");
                                caminhoAtual.setCaminhoEscolhido(true);

                                return new AreaCusto(caminhoAtual, custo);

                            }
                        }
                    }
                }
            }
            System.out.println("Destino: " + xAlvo + ":" + yAlvo);
            System.out.println("Menor caminho possivel: " + caminhoAtual.getX() + ":" + caminhoAtual.getY() + " custo " + custo);

        }
        return new AreaCusto(caminhoAtual, custo);
    }

    public AreaCusto calcularTrajetoriaRecarga(Area visaoAtual[][]) {
        Area caminhoAtual = null;
        double custo = 999;
        for (Area areaDaRecarga : locaisPontosDeRecarga) {
            int xAlvo = areaDaRecarga.getX();
            int yAlvo = areaDaRecarga.getY();

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
                            //trajeto = trajeto + calculaTrajetoriaPasso1b(xAlvo, a.getX(), yAlvo, a.getY());

                            if (custo > trajeto && custo >= 0) {
                                custo = trajeto;
                                caminhoAtual = a;

                            }

                            if ((custo <= 1 && custo > 0) && (Math.abs(xAlvo - a.getX()) == 1) && (Math.abs(yAlvo - a.getY()) == 1)) {
                                System.out.println("Destino: " + xAlvo + ":" + yAlvo);
                                System.out.println("Menor caminho possivel: " + caminhoAtual.getX() + ":" + caminhoAtual.getY() + " custo " + custo);
                                System.out.println("Estou circulando meu de destino ");
                                caminhoAtual.setCaminhoEscolhido(true);

                                return new AreaCusto(caminhoAtual, custo);

                            }
                        }
                    }
                }
            }
            System.out.println("Destino: " + xAlvo + ":" + yAlvo);
            System.out.println("Menor caminho possivel: " + caminhoAtual.getX() + ":" + caminhoAtual.getY() + " custo " + custo);

        }
        return new AreaCusto(caminhoAtual, custo);
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
            for(AreaCusto area :temporario){
                if (area.getCusto()< areaFinal.getCusto()){
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
        if (areaTeste.getItem() instanceof Recarga || areaTeste.getItem() instanceof Lixeira || areaTeste.getColetor() != null) {
            return false;
        }
        return true;
    }

    private double calculaTrajetoriaPasso1a(int xAlvo, int xArea, int yAlvo, int yArea) {
        //Heuristica Euclidiana
        //mais precisa e mais rapida já que podemos andar em diagonal
        int dx = xAlvo - xArea;
        int dy = yAlvo - yArea;
        return sqrt((dx * dx) + (dy * dy));
    }

    private double calculaTrajetoriaPasso1b(int xAlvo, int xArea, int yAlvo, int yArea) {
        //Heuristica Manhattan

        int dx = xAlvo - xArea;
        int dy = yAlvo - yArea;
        return sqrt((dx * dx) + (dy * dy));
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

    public boolean verificaSeTemEnergiaOBastante() {
        if (energiaAtual <= energiaMinima) {
            return false;
        }
        return true;

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

    private void descarregarLixo(Area[][] visaoAtual) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void recarregar(Area[][] visaoAtual) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
