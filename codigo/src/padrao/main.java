package padrao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 09201801
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Mundo m = new Mundo(10, 10,4,4);
        m.printaMundo();
        Area[][] criaUmaVisao = m.criaUmaVisao(3, 4, 2);
        m.printaVisao(criaUmaVisao,2);
    }
    
}