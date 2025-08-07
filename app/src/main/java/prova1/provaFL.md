# Guia de Estudos Java para sua Prova de Programação

## 1. Relacionamentos Bidirecionais (1-N e N-1)

Estude suas classes `Condominio`, `Casa` e `Morador` como excelentes exemplos:

- **Um-para-Muitos (1-N)**: Uma entidade tem referências para muitas outras entidades
  - Exemplo: `Condominio` tem um ArrayList de objetos `Casa`
  - Implementação: `private ArrayList<Casa> casas = new ArrayList<>();`

- **Muitos-para-Um (N-1)**: Muitas entidades têm uma referência para uma entidade
  - Exemplo: Cada `Casa` referencia um `Condominio` 
  - Implementação: `private Condominio condominio;`

- **Mantendo relacionamentos bidirecionais**: Estude os métodos `associarCasa` e `associarMorador` que atualizam ambos os lados do relacionamento

```java
public void associarCasa(Casa casa) {
    casas.add(casa);         // Atualiza um lado (1)
    casa.setCondominio(this); // Atualiza outro lado (N)
}
```

## 2. Comparação de Objetos: `Comparable` vs `equals`/`hashCode`

- **Igualdade de referência (`==`)**:
  - Compara endereços de memória, não o conteúdo do objeto
  - `if(e1 == e2)` é `false` para objetos diferentes mesmo com propriedades idênticas

- **Comportamento padrão de `equals()`**:
  - Sem sobrescrita, comporta-se como `==` (igualdade de referência)
  - Exemplo: `e1.equals(e2)` é `false` para objetos diferentes com mesmas propriedades

- **Interface `Comparable`**:
  - Estude a implementação da classe `Aluno`:
  ```java
  public int compareTo(Aluno o) {
      return idade.compareTo(o.getIdade());
  }
  ```
  - Permite ordenação natural e uso com `Collections.sort(lista)`

## 3. Comparação de Strings e Memória

- **Literais de String e pool**:
  ```java
  String a = "a";
  String b = "a";
  if(a == b) // true - ambos referenciam a mesma string no pool
  ```

- **Objetos String de fontes diferentes**:
  ```java
  String a = "a";
  String b = scanner.nextLine(); // Usuário digita "a"
  if(a == b) // false - locais de memória diferentes
  if(a.equals(b)) // true - mesmo conteúdo
  ```

## 4. Ordenação com `Comparator`

- **Implementação anônima de `Comparator`**:
  ```java
  Collections.sort(alunos, new Comparator<Aluno>() {
      @Override
      public int compare(Aluno o1, Aluno o2) {
          return o1.getIdade().compareTo(o2.getIdade());
      }
  });
  ```

## Estratégias de Revisão

1. **Crie flashcards** para conceitos-chave
2. **Pratique identificando trechos de código** que mostram diferentes tipos de relacionamento
3. **Escreva pequenos programas de exemplo** implementando cada conceito
4. **Revise a diferença** entre `==`, `.equals()` e `compareTo()`
5. **Trace o código** com lápis e papel para entender relacionamentos entre objetos

Boa sorte na sua prova!