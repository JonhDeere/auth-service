# Usa una imagen oficial de OpenJDK 17 como base
FROM eclipse-temurin:17-jdk-jammy as build

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar todo el proyecto al contenedor
COPY . .

# Empaquetar el proyecto usando Maven (modo limpio)
RUN ./mvnw clean package -DskipTests

# Segunda etapa: usar solo el JAR final para una imagen más ligera
FROM eclipse-temurin:17-jdk-jammy

# Directorio de trabajo para la app final
WORKDIR /app

# Copiar el jar desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto que usará el microservicio
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
