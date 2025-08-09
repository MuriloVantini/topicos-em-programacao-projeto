# WildFly: Start Rápido e Guia de Reuso

Este guia explica como iniciar o WildFly neste projeto (Hello REST já implementado) e como replicar essa solução em outro projeto Java/Gradle no futuro.


## 1) Iniciar o WildFly neste projeto
Pré-requisitos: Docker + Docker Compose instalados.

- Construir a imagem (build do WAR acontece dentro do Docker):
  - `docker compose build wildfly`
- Subir o servidor WildFly:
  - `docker compose up -d wildfly`
- Ver logs (até ver "Deployed app.war"):
  - `docker compose logs -f wildfly`
- Testar endpoint (Hello):
  - http://localhost:8080/app/api/hello
  - Esperado: `ok`
- Parar:
  - `docker compose down`

Onde está o código:
- Application JAX-RS: `app/src/main/java/testeexportarcomgradlew/api/RestApplication.java` (anotação `@ApplicationPath("/api")`)
- Resource: `app/src/main/java/testeexportarcomgradlew/api/HelloResource.java` (GET `/hello`)
- Context path do WAR: `/app` (definido pelo nome `app.war`)
- Dockerfile do WildFly: `Dockerfile.wildfly`
- Compose: serviço `wildfly` em `docker-compose.yml`

Observações:
- Portas padrão mapeadas: `8080:8080` (HTTP), `9990:9990` (Management).
- Se 8080 estiver ocupada, altere em `docker-compose.yml` o mapeamento (ex.: `"8081:8080"`).
- Caso sua rede bloqueie `quay.io`, substitua a imagem base por outra compatível em `Dockerfile.wildfly`.


## 2) Reusar em outro projeto Java/Gradle
Abaixo um checklist para transformar outro projeto em um WAR simples com endpoint REST e deploy automático no WildFly via Docker.

1) Gradle (módulo que será empacotado como WAR)
   - Ative o plugin `war` e adicione Jakarta EE como `compileOnly`:
   ```groovy
   plugins {
       id 'java'
       id 'war'
   }

   repositories { mavenCentral() }

   dependencies {
       compileOnly 'jakarta.platform:jakarta.jakartaee-api:10.0.0' // provê APIs (JAX-RS etc.)
       // suas outras dependências de runtime
   }

   java {
       toolchain { languageVersion = JavaLanguageVersion.of(21) }
   }
   ```

2) Crie o esqueleto JAX-RS
   - Application (define o base path):
   ```java
   // src/main/java/com/seuprojeto/api/RestApplication.java
   package com.seuprojeto.api;

   import jakarta.ws.rs.ApplicationPath;
   import jakarta.ws.rs.core.Application;

   @ApplicationPath("/api")
   public class RestApplication extends Application { }
   ```
   - Resource (Hello):
   ```java
   // src/main/java/com/seuprojeto/api/HelloResource.java
   package com.seuprojeto.api;

   import jakarta.ws.rs.GET;
   import jakarta.ws.rs.Path;
   import jakarta.ws.rs.Produces;
   import jakarta.ws.rs.core.MediaType;

   @Path("/hello")
   public class HelloResource {
       @GET
       @Produces(MediaType.TEXT_PLAIN)
       public String hello() { return "ok"; }
   }
   ```

3) Dockerfile do WildFly (multi-stage)
   - Adapte o caminho/nomes conforme seu projeto. Exemplo:
   ```Dockerfile
   # --- Stage 1: Build WAR ---
   FROM eclipse-temurin:21-jdk-jammy as builder
   WORKDIR /build
   COPY gradle/ gradle/
   COPY gradlew settings.gradle ./
   RUN chmod +x gradlew
   COPY app/build.gradle ./app/
   RUN ./gradlew app:dependencies --no-daemon
   COPY app/src ./app/src
   RUN ./gradlew app:war -x test --no-daemon

   # --- Stage 2: WildFly runtime ---
   FROM quay.io/wildfly/wildfly:latest
   COPY --from=builder /build/app/build/libs/*.war /opt/jboss/wildfly/standalone/deployments/app.war
   ```

4) docker-compose.yml (trecho do serviço)
   ```yaml
   services:
     wildfly:
       build:
         context: .
         dockerfile: Dockerfile.wildfly
       ports:
         - "8080:8080"   # HTTP
         - "9990:9990"   # Management
   ```

5) Comandos para executar
   - `docker compose build wildfly`
   - `docker compose up -d wildfly`
   - Teste: `http://localhost:8080/app/api/hello`
   - Logs: `docker compose logs -f wildfly`
   - Parar: `docker compose down`

Dicas de reuso:
- O contexto `/app` é definido pelo nome do arquivo WAR copiado (`app.war`). Se quiser outro contexto, renomeie o WAR (ex.: `meuapp.war` → `/meuapp`).
- Para projetos multi-módulos, aponte o Dockerfile para o submódulo correto (ex.: `web/` em vez de `app/`).
- Para usar banco via JNDI/Datasource do WildFly, adicione a configuração no servidor e faça lookup do `DataSource` no código (passo posterior recomendado).
- Se preferir build local sem instalar Java, também pode usar um container Gradle: `docker run --rm -v "$PWD":/workspace -w /workspace gradle:8.8.0-jdk21 gradle app:war`.
