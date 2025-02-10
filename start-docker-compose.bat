@echo off
setlocal enabledelayedexpansion

:: Définir l'encodage pour éviter les caractères bizarres
chcp 65001 > nul

:: Chemin du fichier docker-compose.yml
set COMPOSE_FILE=docker-compose.yml

:: Vérifier si des conteneurs sont déjà en cours d'exécution
echo Verification des conteneurs existants...
docker-compose -f %COMPOSE_FILE% ps -q > nul
if errorlevel 1 (
    echo Aucun conteneur en cours d'execution.
) else (
    echo Conteneurs en cours d'execution detectes. Arret des conteneurs...
    docker-compose -f %COMPOSE_FILE% down
    if errorlevel 1 (
        echo Erreur lors de l'arret des conteneurs.
        exit /b 1
    )
    echo Conteneurs arretes avec succes.
)

:: Lancer Docker Compose
echo Lancement des conteneurs...
docker-compose -f %COMPOSE_FILE% up -d
if errorlevel 1 (
    echo Erreur lors du demarrage des conteneurs.
    exit /b 1
)

:: Attendre que les conteneurs soient operationnels
echo Attente de l'initialisation des services...
timeout /t 10 > nul

:: Informations de connexion
echo =====================================
echo Services demarres avec succes !
echo -------------------------------------
echo [PostgreSQL]
echo Nom du conteneur : schoolmap_postgres-container
echo URL : 192.168.105.10:5432
echo Nom de la base : geo_db
echo Mot de passe : root
echo -------------------------------------
echo [PgAdmin]
echo Nom du conteneur : schoolmap_pgadmin-container
echo URL : http://localhost:5054
echo Email : root@domain.com
echo Mot de passe : root
echo -------------------------------------
echo [GeoServer]
echo Nom du conteneur : schoolmap_geoserver-container
echo URL : http://localhost:8084
echo =====================================

:: Maintenir la console ouverte
pause
