# P3 Backend Chatop

Ce projet est le backend pour un portail de mise en relation entre les futurs locataires et les propriétaires pour la location saisonnière, initialement sur la côte basque et éventuellement sur toute la France.

## Table des matières

- [Fonctionnalités](#fonctionnalités)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Démarrage du Serveur](#démarrage-du-serveur)
- [Utilisation](#utilisation)
- [Test avec Postman](#test-avec-postman)
- [Documentation de l'API](#documentation-de-lapi)
- [Contribution](#contribution)

## Fonctionnalités

- Enregistrement & connexion des utilisateurs (authentification avec JWT)
- Listing, Création et mise à jour des annonces de location. 
- Consultation du détail d'une annonce
- Intégration avec une application front-end Angular
- Documentation des API avec Swagger

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants :

- [Java 17 ou supérieur](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- [Git](https://git-scm.com/)
- [Postman](https://www.postman.com/) (pour tester l'API)

## quik start dev

1. Clonez le repository GitHub sur votre machine locale :

   ```bash
   git clone https://github.com/aflouat/p3-backend-chatop.git
   cd p3-backend-chatop

2. exécuter la commande maven pour téléchrger les dépencdances. :
        sous windows : 
    ```bash
   ./mvnw clean install
3. démarrer le backend avec votre meilleur IDE, lancer un build et démarrer
 le projet spring boot
4. cloner le frontend angular a partir de github:
   ```bash 
   https://github.com/aflouat/P3-frontend-chatop
   
5. démarrer le frontend angular avec :
 ```bash 
   cd P3-frontend-chatop
   ./ng serve

```
6. accéder au swagger pour tester les endpoints :
```bash
http://localhost:3001/swagger-ui/index.html
