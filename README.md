# Guia End-to-End: Projeto Java + Gradle + Docker (CLI e WildFly)

Este documento consolida o fluxo completo para rodar o projeto em modo CLI (linha de comando) e em modo Web (WildFly), explica o papel do Docker, a persistência do H2, como trocar de "aula" (main), e como replicar a solução em outros projetos.


## Visão Geral
- **Projeto**: Java (Gradle) com múltiplas "aulas" (mains) no módulo `app/`.
- **Modos de execução**:
  - **CLI**: app Java simples que roda e termina (sem servidor).
  - **Web (WildFly)**: WAR mínimo com endpoint REST (Hello), para evoluir e expor suas aulas via HTTP.
- **Banco**: H2 (file) persistido via volume Docker.
- **Docker**: usado para buildar e executar sem precisar instalar JDK local. Compose orquestra serviços (`app` e `wildfly`).

Diretórios/arquivos importantes:
- `app/build.gradle` — define `application.mainClass` (a aula ativa), plugins `application`, `shadow`, `war`.
- `app/src/main/java/` — código-fonte (CLI e web/REST).
- `Dockerfile` — multi-stage para gerar fat JAR do CLI.
- `Dockerfile.wildfly` — multi-stage para gerar WAR e fazer deploy no WildFly.
- `docker-compose.yml` — serviços `app` (CLI) e `wildfly` (servidor web).
- `ferramentas/` — dados do H2 persistidos no host (montado no container do CLI).
- `CLI.md` — detalhes do modo CLI.
- `WILDFLY.md` — detalhes do modo WildFly.


## Papel do Docker neste projeto
- **Isolamento de ambiente**: imagens garantem JDK/Gradle/WildFly corretos.
- **Build reprodutível**: multi-stage gera artefatos (JAR/WAR) dentro de containers.
- **Orquestração**: `docker compose` simplifica executar e observar logs.
- **Persistência**: volume mapeia `./ferramentas` (host) → `/app/ferramentas` (container) para H2.


## Modo 1: Executar como CLI (todas as aulas)
Esse é o modo indicado para rodar qualquer aula rapidamente, trocando a `mainClass`.

1) Escolher a aula no `app/build.gradle`:
```groovy
application {
    // Aula padrão
    mainClass = 'testeexportarcomgradlew.App'
    // Para outra aula, ajuste:
    // mainClass = 'prova1.App'
}
```
2) Build e execução (sem precisar JDK local):
- `docker compose build`  (recria a imagem do app CLI)
- `docker compose up`     (roda e imprime logs; encerra ao final)

3) Persistência do H2
- URL usada pelo app: `jdbc:h2:file:./ferramentas/banco;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE`
- Arquivos ficam no host em `./ferramentas/`.

4) Dicas
- Mudou a `mainClass`? Faça `docker compose build --no-cache`.
- O app é CLI: não expõe porta e termina com exit code 0 quando conclui.


## Modo 2: Executar via WildFly (Web)
Serve para expor endpoints REST e evoluir suas aulas para web. Já existe um Hello REST.

1) Subir o WildFly com WAR mínimo:
- `docker compose build wildfly`
- `docker compose up -d wildfly`
- Logs: `docker compose logs -f wildfly` (aguarde "Deployed app.war")
- Teste: http://localhost:8080/app/api/hello  → deve retornar `ok`

2) Como expor uma aula via REST
- Crie um recurso JAX-RS em `app/src/main/java/.../api/` que chame sua lógica atual:
```java
@Path("/aula1")
public class Aula1Resource {
    @GET @Path("/run") @Produces(MediaType.TEXT_PLAIN)
    public String run() {
        // instancie seus Services/DAOs e execute a aula
        return "aula1 ok";
    }
}
```
- Rebuild e suba: `docker compose build wildfly && docker compose up -d wildfly`
- Teste: `http://localhost:8080/app/api/aula1/run`

3) Portas
- HTTP: `8080`  (mapeada como `8080:8080` no compose)
- Management: `9990` (console: http://localhost:9990)
- Se 8080 ocupado, troque o mapeamento no `docker-compose.yml`.


## H2 e persistência
- CLI: volume `./ferramentas:/app/ferramentas` garante persistência mesmo após `down`.
- Web: inicialmente os endpoints não usam banco; ao migrar uma aula para REST, você pode:
  - (Fase 1) Continuar usando sua `ConexaoBanco` com caminho relativo (embedded H2 no WAR).
  - (Fase 2, recomendado) Configurar datasource no WildFly e obter via JNDI no código.


## Troubleshooting
- "JAVA_HOME" ao rodar `./gradlew` no host: rode sempre via Docker/Compose ou use container Gradle.
- Porta 8080 ocupada: ajuste no compose, por ex.: `"8081:8080"`.
- WAR/JAR sem dependências: para CLI usamos Shadow (fat JAR). Para web, usamos `compileOnly` de Jakarta EE (WildFly provê as APIs).
- Alterou a `mainClass` e o app não refletiu? `docker compose build --no-cache`.
- Ver logs do WildFly: `docker compose logs -f wildfly`.


## Replicar em outro projeto (Checklist)
1) **Gradle (CLI)**
- `plugins { id 'application'; id 'com.github.johnrengelman.shadow' version '8.1.1' }`
- `application { mainClass = 'seu.pacote.App' }`
- Task `build` depende de `shadowJar`.

2) **Gradle (Web)**
- `plugins { id 'war' }`
- `compileOnly 'jakarta.platform:jakarta.jakartaee-api:10.0.0'`
- Classes JAX-RS: `RestApplication` (`@ApplicationPath("/api")`) e `HelloResource`/"suas aulas".

3) **Dockerfiles**
- CLI (`Dockerfile`): multi-stage com Gradle Wrapper → copia `*-all.jar` → `ENTRYPOINT ["java","-jar","app.jar"]`.
- WildFly (`Dockerfile.wildfly`): multi-stage → `app:war` → copia WAR como `app.war` para `standalone/deployments/`.

4) **Compose**
- Serviço `app` (CLI) com volume `./ferramentas:/app/ferramentas`.
- Serviço `wildfly` com portas `8080` e `9990`.

5) **Comandos padrão**
- CLI: `docker compose build && docker compose up`
- WildFly: `docker compose build wildfly && docker compose up -d wildfly`


## Referências internas
- CLI detalhado: `CLI.md`
- WildFly detalhado: `WILDFLY.md`
