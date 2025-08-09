# Projeto Java (Gradle) dockerizado com H2 persistente

Este repositório contém um app Java (CLI) organizado por aulas/pacotes, empacotado com Gradle (Shadow) e executável via Docker Compose. O banco H2 é persistido em disco por volume.


## Estrutura relevante
- `app/build.gradle` — configurações Gradle; `application.mainClass` decide qual "aula" roda.
- `app/src/main/java/` — código-fonte. Exemplos de entradas:
  - `testeexportarcomgradlew.App` (padrão atual)
  - `prova1.App` (outras aulas)
- `ferramentas/` — diretório do banco H2 no host (mapeado no container).
- `Dockerfile` — build multi-stage com Gradle Wrapper + Shadow Jar.
- `docker-compose.yml` — orquestra container e persiste H2.


## Como escolher e rodar cada aula
Você escolhe qual main rodar ajustando a opção `mainClass` no `app/build.gradle`.

1) Abra `app/build.gradle` e ajuste:
```groovy
application {
    // Exemplo: padrão atual
    mainClass = 'testeexportarcomgradlew.App'
    // Ou para outra aula (exemplo):
    // mainClass = 'prova1.App'
}
```

2) Rode localmente (sem Docker):
- Baixar dependências e gerar JAR:
  - `./gradlew build`
- Executar em modo dev:
  - `./gradlew run`

3) Rodar via Docker Compose:
- Build da imagem:
  - `docker compose build`
- Subir e executar (modo interativo, imprime logs e sai quando o app termina):
  - `docker compose up`

Observações:
- Sempre que mudar `mainClass`, é recomendável reconstruir a imagem:
  - `docker compose build --no-cache` (ou ao menos `docker compose build`)
- O app atual é CLI e encerra com código 0 após rodar; isso é esperado.


## Banco H2 persistente
- URL usada: `jdbc:h2:file:./ferramentas/banco;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE`
- Diretório host mapeado: `./ferramentas` → `/app/ferramentas` (container)
- Os arquivos do banco ficam preservados em `ferramentas/` mesmo após subir/derrubar containers.

Console H2 (opcional, local):
- Baixe o JAR do H2 (já existe um exemplo em `ferramentas/h2-2.3.232.jar`)
- Rode: `java -jar ferramentas/h2-2.3.232.jar`
- Conecte com:
  - JDBC URL: `jdbc:h2:file:./ferramentas/banco`
  - User: `sa` | Password: vazio


## Como esta dockerização foi construída (guia para replicar)

1) Multi-stage Dockerfile (imagem pequena e segura):
- Estágio builder (Temurin 21 JDK) roda Gradle Wrapper e gera um fat JAR com o plugin Shadow.
- Estágio runner (Temurin 21 JRE) copia apenas o `*-all.jar` e executa com `java -jar`.

2) Gradle + Shadow:
- No `app/build.gradle`:
```groovy
plugins {
    id 'application'
    id 'com.github.johnrengelman.shadow' version '8.1.1'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = 'testeexportarcomgradlew.App' // ajuste conforme a aula
}

tasks.named('build') {
    dependsOn tasks.named('shadowJar') // garante gerar o fat JAR
}
```
- O Shadow gera `app-<versão>-all.jar` em `app/build/libs/`.

3) Dockerfile (resumo):
```Dockerfile
FROM eclipse-temurin:21-jdk-jammy as builder
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew settings.gradle ./
RUN chmod +x gradlew
COPY app/build.gradle ./app/
RUN ./gradlew app:dependencies --no-daemon
COPY app/src ./app/src
RUN ./gradlew app:shadowJar -x test --no-daemon

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/app/build/libs/*-all.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

4) docker-compose.yml (resumo):
```yaml
services:
  app:
    build: .
    volumes:
      - ./ferramentas:/app/ferramentas # H2 persiste
    stdin_open: true
    tty: true
```

5) H2 em Docker (caminho relativo + volume):
- Em `ConexaoBanco.getConnection()` use:
```java
String url = "jdbc:h2:file:./ferramentas/banco;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
```
- Garanta que o diretório existe ou crie-o (`new File("./ferramentas").mkdirs()`).

6) Evitando NullPointerException em dados iniciais:
- Crie tabelas no start (DAO.createTable()).
- Faça seed/insert de registros default se `getById()` retornar `null`.


## Fluxo de trabalho (resumo)
- Ajuste a `mainClass` para a aula desejada.
- `docker compose build` (ou `--no-cache` se mudou `mainClass`/dependências).
- `docker compose up` para executar.
- Verifique logs; ao final o container encerra com sucesso (exit code 0).


## Troubleshooting
- "ClassNotFound" ao rodar o JAR: confirme que o Shadow está habilitado e que o Dockerfile copia `*-all.jar`.
- Permissão do `gradlew`: o `Dockerfile` já faz `chmod +x gradlew`.
- Mudou a `mainClass` e o Docker não refletiu? Reconstrua a imagem (`docker compose build --no-cache`).
- Banco não persiste: verifique o volume `./ferramentas:/app/ferramentas` e a URL do H2.
