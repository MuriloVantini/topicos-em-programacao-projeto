# --- Estágio 1: O Construtor (Builder) ---
# Usamos uma imagem completa do JDK com Gradle para compilar o projeto.
FROM eclipse-temurin:21-jdk-jammy as builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de definição do Gradle e baixa as dependências
# Isso aproveita o cache do Docker, acelerando builds futuros
COPY gradle/ gradle/
COPY gradlew settings.gradle ./
RUN chmod +x gradlew
COPY app/build.gradle ./app/
RUN ./gradlew app:dependencies --no-daemon

# Copia o resto do código-fonte e compila o projeto
COPY app/src ./app/src
RUN ./gradlew app:shadowJar -x test --no-daemon

# --- Estágio 2: O Executor (Runner) ---
# Usamos uma imagem muito menor, apenas com o Java Runtime (JRE), para rodar.
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia APENAS o .jar compilado do estágio 'builder' para a imagem final.
# Para projetos Gradle, o JAR fica em app/build/libs/
COPY --from=builder /app/app/build/libs/*-all.jar app.jar

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]