# P3 Backend Chatop
Ce projet représente le backend pour une plateforme de mise en relation entre futurs locataires et propriétaires pour des locations saisonnières, initialement sur la côte basque, avec une expansion prévue à toute la France.

## Table des matières
### Fonctionnalités
Prérequis
Démarrage rapide
Installation de l'environnement de développement
Documentation de l'API
Test avec Swagger
Contribution
Fonctionnalités
Enregistrement et connexion des utilisateurs (authentification via JWT)
Création, mise à jour et listing des annonces de location
Consultation du détail d'une annonce
Documentation de l'API avec Swagger
Intégration avec un frontend Angular
## Prérequis
Avant de commencer, assurez-vous d'avoir installé les éléments suivants :

Java 17 ou supérieur
Maven
MySQL
Git
Postman (pour tester l'API)
## Démarrage rapide
1. Cloner le projet
   Clonez le repository GitHub sur votre machine locale :

``` bash
git clone https://github.com/aflouat/p3-backend-chatop.git
cd p3-backend-chatop
``` 
2. Installer les dépendances Maven
   Exécutez la commande Maven pour télécharger les dépendances :

Sous Windows :
``` bash
./mvnw clean install
``` 
3. Définir les variables d'environnement
   Pour configurer la base de données et les secrets JWT, définissez les variables d'environnement.

Sous PowerShell (Windows) :

``` bash
$env:DB_URL = "jdbc:mysql://localhost:3306/rental_db"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "rootROOT@1"
$env:JWT_SECRET = "REdBd2NtVmpTN0tyczl1YmE3WFY0NC9adXNLUHZDTHIzN2dDMFkyUkdYODFxVFp4NzA2SU5lbFI2ODdQc3YyKw=="
Sous Linux/MacOS (ou en utilisant bash sous Windows) :

``` 

4. Créer la base de données
   Créez la base de données MySQL et les tables nécessaires en exécutant les commandes SQL suivantes :

``` sql
CREATE DATABASE IF NOT EXISTS `rental_db`;
USE `rental_db`;

CREATE TABLE `users` (
`id` int NOT NULL AUTO_INCREMENT,
`email` varchar(255) DEFAULT NULL,
`name` varchar(255) DEFAULT NULL,
`password` varchar(255) DEFAULT NULL,
`created_at` datetime(6) DEFAULT NULL,
`updated_at` datetime(6) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `rentals` (
`id` int NOT NULL AUTO_INCREMENT,
`created_at` datetime(6) DEFAULT NULL,
`description` varchar(1000) DEFAULT NULL,
`name` varchar(255) DEFAULT NULL,
`owner_id` int NOT NULL,
`picture` varchar(255) DEFAULT NULL,
`price` float NOT NULL,
`surface` float NOT NULL,
`updated_at` datetime(6) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `messages` (
`id` int NOT NULL AUTO_INCREMENT,
`created_at` datetime(6) DEFAULT NULL,
`message` varchar(255) DEFAULT NULL,
`rental_id` int NOT NULL,
`updated_at` datetime(6) DEFAULT NULL,
`user_id` int NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
``` 
5. Démarrer le backend
   Lancez votre projet Spring Boot avec Maven :

``` bash
./mvn spring-boot:run
``` 
Le backend sera accessible sur http://localhost:3001.

Installation de l'environnement de développement
1. Cloner le frontend Angular
   Clonez le repository GitHub du frontend Angular :

``` bash
git clone https://github.com/aflouat/P3-frontend-chatop
cd P3-frontend-chatop
```
2. Installer les dépendances du frontend
   Installez les dépendances du projet Angular :

``` bash
npm install
3. Démarrer le frontend Angular
   Lancez le frontend Angular :
```
``` bash
ng serve
Le frontend sera accessible sur http://localhost:4200.
```
Test avec Swagger
Pour tester les endpoints protégés via Swagger, accédez à la documentation Swagger :

``` bash

http://localhost:3001/swagger-ui/index.html
```
## Contribution
Les contributions sont les bienvenues. Veuillez soumettre un pull request pour toute amélioration ou nouvelle fonctionnalité.