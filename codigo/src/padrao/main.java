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
        //m.printaMundo();
        m.mudaUmaAreaColetor(3, 4, new Coletor());
        
        m.printaMundo();
        m.printaVisao(m.criaUmaVisao(3, 4, 2),2);
         m.mudaUmaAreaColetor(3, 4, null);
         
        m.mudaUmaAreaColetor(4, 4, new Coletor());
        m.printaMundo();
        m.printaVisao(m.criaUmaVisao(4, 4, 2),2);
        
        
    }
    
}
