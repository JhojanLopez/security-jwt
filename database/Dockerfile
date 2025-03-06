FROM postgres:15

# Copiar el archivo .backup al contenedor
COPY security_db.backup /docker-entrypoint-initdb.d/security_db.backup

# Variables de entorno para la creación de la base de datos
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=security_db

# Copiar el script de restauración al contenedor
COPY restore.sh /docker-entrypoint-initdb.d/restore.sh
EXPOSE 5432