# Estágio 1: Construir o JAR
FROM eclipse-temurin:17-jdk-jammy AS build

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /home/gradle/project

# Copiar todos os arquivos do projeto para o contêiner
COPY . .

# Adicione permissão de execução ao script gradlew
RUN chmod +x gradlew

# Executar os testes do Gradle
RUN ./gradlew test --no-daemon

# Executar o comando de build do Gradle
RUN ./gradlew build --no-daemon

# Estágio 2: Construir a imagem final
FROM eclipse-temurin:17-jdk-jammy

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o JAR construído no estágio anterior para o diretório de trabalho
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

# Definir o comando para executar o aplicativo
ENTRYPOINT ["java","-jar","/app/app.jar"]

# Expor a porta que a aplicação vai usar
EXPOSE 8080