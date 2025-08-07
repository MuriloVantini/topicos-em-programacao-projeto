/*
 * para n precisar baixar maven nem gradlew globalmente. Estou usando gradlew do flutter, que é presente em qualquer projeto flutter, como:
 * copy "C:\Users\Murilo Carazato\Documents\Flutter Projects\assis-ofertas\assis_ofertas\android\gradlew"
 * dai iniciei o projeto java com o gradlew, ainda n testei exportar e importar isso
 * 
 * contexto: condomínio, casa, morador ( 1-n da esquerda para a direita)
 * 
 * Condominio e Casa 1:N
 * Casa e Condominio N:1
 * 
 * Condominio e Morador 1:N
 * Morador e Condominio N:1
 * 
 * Casa e Morador 1:N
 * Morador e Casa N:1
 * 
 * ////
 * vair cair na prova
 * relacionamento (bidimensional) 1-n e n-1
 * 
 * prefirerir {class implements comparable} do que equals e hashcode: nunca compare 2 objetos com equals
 * //isso pergunta se eles estão no mesmo endereço de memória, então sempre vai dar false, mesmo com atributos iguais
 * Empresa e1 = new Empresa(); e1.setNome("empresa 1");
 * Empresa e2 = new Empresa(); e2.setNome("empresa 1");
 * if(e1 == e2) // false
 * ou
 * e1.equals(e2); // false
 * //
 * // quando digitar um novo endereçamento de memória da false
 * Scanner s = new Scanner(System.in);
 * String a = "a";
 * String b = s.nextLine(); //digitar a
 * if(a == b) // false
 * //
 * //valor primitivo (pool de string em memória)
 * String a = "a";
 * String b = "a";
 * if(a == b) // true
 * //
 * isso dá a possibilidade de fazer um collections.sort
 * 
 * comparator
 * Comparator<Aluno> c = new Comparator<Aluno>() {
 * @Override
 * public int compare(Aluno o1, Aluno o2) {
 * return o1.getIdade().compareTo(o2.getIdade());
 * }
 */
package prova1;

import prova1.beans.Casa;
import prova1.beans.Condominio;
import prova1.beans.Morador;

public class App {

    public static void main(String[] args) {
        App app = new App();
        app.iniciar();
    }

    public void iniciar() {
        // O Condomínio é a entidade pai que conterá Casas e Moradores.
        Condominio condominio1 = new Condominio();
        condominio1.setCodigo(1);
        condominio1.setNome("Condominio 1");
        
        // A Casa pertence ao Condomínio.
        Casa casa1 = new Casa();
        casa1.setCodigo(1);
        casa1.setNome("Casa 1");
        // Associa a casa ao condomínio
        // adiciona a casa à lista do Condomínio e também seta o Condomínio no objeto Casa.
        condominio1.associarCasa(casa1);

        // O Morador pertence tanto à Casa quanto ao Condomínio.
        Morador morador1 = new Morador();
        morador1.setCodigo(1);
        morador1.setNome("Morador 1");
        // Associa o Morador à Casa
        // adiciona o morador à lista e seta a Casa no objeto Morador.
        casa1.associarMorador(morador1);
        // Associa o Morador ao Condomínio
        // adiciona o morador à lista de moradores e seta o Condomínio no objeto Morador.
        condominio1.associarMorador(morador1);

        imprimir(condominio1);
    }

    public void imprimir(Condominio condominio) {
        System.out.println(condominio.toString());

        System.out.println("\n Detalhes de cada Casa e seus moradores no Condomínio:");
        for (Casa c : condominio.getCasas()) {
            System.out.println(c.toString());
            for (Morador m : c.getMoradores()) {
                System.out.println(m.toString());
            }
        }

        System.out.println("\n Detalhes dos Moradores no Condomínio:");
        for (Morador m : condominio.getMoradores()) {
            System.out.println(m.toString());
        }

        // Detalhes de cada Morador no Condomínio (relacionamento N:M)
        // for (Morador m : condominio.getMoradores()) {
        //     System.out.println(m.toString());
        //     System.out.println("Número de Casas do Morador: " + m.getCasas().size());
        //     for (Casa c : m.getCasas()) {
        //         System.out.println(c.toString());
        //     }
        // }
    }
}
