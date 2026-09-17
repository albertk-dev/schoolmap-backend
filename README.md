<div align="center">

# 🗺️ SchoolMap Backend

**API REST géospatiale & Système d'Information Géographique (SIG) pour la cartographie des établissements scolaires.**

[![Java](https://img.shields.io/badge/Java-17-orange.svg?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-PostGIS-blue.svg?logo=postgresql&logoColor=white)](https://postgis.net/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg?logo=docker&logoColor=white)](https://www.docker.com/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-Swagger%20UI-85EA2D.svg?logo=swagger&logoColor=black)](https://swagger.io/)

[Architecture](#-architecture-technique) • [Stack](#-stack-technologique) • [Installation & Lancement](#-démarrage-rapide) • [Documentation API](#-documentation-api--swagger) • [Frontend Associé](#-frontend-associé)

</div>

---

## 📌 Présentation

**SchoolMap Backend** est le moteur d'API REST géospatiale propulsant l'application de cartographie scolaire. Il permet d'indexer, géolocaliser, requêter et administrer les données des infrastructures scolaires à travers des requêtes spatiales précises (coordonnées GPS, polygones de zonage, périmètres de proximité).

Il est conçu selon une architecture en couches stricte (*Controller-Service-Repository-Mapper-DTO*), intégrant une persistance spatiale avec **PostGIS** et un conteneur de base de données prêt à l'emploi via **Docker Compose**.

---

## ⚡ Stack Technologique

* **Langage & Framework** : Java 17, Spring Boot 3.4.2 (Spring MVC, Spring Data JPA).
* **Base de données spatiale** : PostgreSQL avec extension **PostGIS** (`PostgisPG95Dialect` / `org.hibernate.spatial`).
* **Sécurité & Authentification** : JWT (JSON Web Tokens) avec filtres de sécurité personnalisés.
* **Documentation & Spécification** : SpringDoc OpenAPI 3 (Swagger UI).
* **Productivité & Mapping** : Lombok, DTO Mappers, Global Exception Handling (`@ControllerAdvice`).
* **Infrastructure locale** : Docker Compose (base de données PostGIS préconfigurée).

---

## 🧩 Architecture en Couches

```text
com.albertk.schoolmap/
├── configuration/     # Configurations Spring (Security, CORS, OpenAPI Swagger)
├── controller/        # Points d'entrée REST exposant les endpoints /api
├── dto/               # Objets de transfert de données (Data Transfer Objects)
├── mapper/            # Mappeurs découplant entités JPA et DTOs
├── model/             # Entités JPA et types spatiaux PostGIS
├── repository/        # Interfaces Spring Data JPA avec requêtes spatiales
├── service/           # Logique métier et validation des règles de gestion
├── security/          # Gestion des tokens JWT et contextes de sécurité
├── exception/         # Gestionnaires d'exceptions personnalisés et responses standardisées
└── types/             # Énumérations et types spécifiques au domaine
```

---

## 🚀 Démarrage Rapide

### 1. Prérequis
* Java 17+ installé (`java -version`)
* Docker & Docker Compose
* Maven (ou utilisation du wrapper `./mvnw` inclus)

### 2. Démarrer la base de données PostGIS

Un fichier `docker-compose.yml` est fourni à la racine :

```bash
docker-compose up -d
```

### 3. Lancer l'application Spring Boot

```bash
# Sur Linux / macOS :
./mvnw spring-boot:run

# Sur Windows :
mvnw.cmd spring-boot:run
```

Le serveur démarre par défaut sur le port **`8094`** avec le préfixe de contexte **`/api`** :
👉 `http://localhost:8094/api`

---

## 📖 Documentation API & Swagger UI

Une fois le serveur démarré, la documentation interactive Swagger UI est accessible sur :
* **Swagger UI** : `http://localhost:8094/api/swagger-ui/index.html`
* **Spécification OpenAPI JSON** : `http://localhost:8094/api/v3/api-docs`

---

## 🔗 Frontend Associé

Ce backend alimente l'application cartographique interactive disponible sur le dépôt :
👉 **[schoolmap-frontend](https://github.com/albertk-dev/schoolmap-frontend)** (Leaflet, Cartographie dynamique).

---

## 👤 Auteur & Contact

* **Albert Kameni** (*"Le débogueur"*) — Full-Stack Software Engineer & Telecom Specialist
* **GitHub** : [@albertk-dev](https://github.com/albertk-dev)
* **LinkedIn** : [linkedin.com/in/albertk-linked](https://www.linkedin.com/in/albertk-linked)
* **Email** : [albertk.explorer@gmail.com](mailto:albertk.explorer@gmail.com)
