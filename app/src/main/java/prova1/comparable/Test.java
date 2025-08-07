package prova1.comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Aluno a1 = new Aluno("Joao", 20);
        Aluno a2 = new Aluno("Maria", 19);
        Aluno a3 = new Aluno("Jose", 21);

        List<Aluno> alunos = new ArrayList<Aluno>();
        alunos.add(a1);
        alunos.add(a2);
        alunos.add(a3);

        Collections.sort(alunos, new Comparator<Aluno>() {
            @Override
            public int compare(Aluno o1, Aluno o2) {
                return o1.getIdade().compareTo(o2.getIdade());
            }
        });

        for (Aluno aluno : alunos) {
            System.out.println(aluno.getNome() + " " + aluno.getIdade());
        }

    }

}
