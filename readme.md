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
``
   3. définir vos variables d'environnement pour fournir la config de sécurité propre à votre installation. si vous êtes 
   sur windows, utilisez power shell 
```bash
   $env:DB_URL = "jdbc:mysql://localhost:3306/rental_db"
   $env:DB_USERNAME = "root"
   $env:DB_PASSWORD = "rootROOT@1"
   $env:JWT_SECRET = "REdBd2NtVmpTN0tyczl1YmE3WFY0NC9adXNLUHZDTHIzN2dDMFkyUkdYODFxVFp4NzA2SU5lbFI2ODdQc3YyKw=="
   

4. 
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



Remarques :
Dans les critères d'éval : 
## bloquant
1 - tester les swaggers-ui fonctionne avec authentification  
2 - fix register 
4 - code propre (relivrer les corrections)  +documenter les méthodes si ne nom n'est pas parlant plutôt dans les interfaces+ override pour les méthodes dans les implem 

6- (4) infos sensibles à ne pas voir dans le code : url BD, secret ,login,password (variable global ${} à inclure dans le readme)

##améliorations
principe solide à revoir
un gestionnaire d'erreur API, @ControllerAdvice
dans la doc, envoyer 401, si email non trouvé sur le login+400 si register.
3 - clean console
5- ajouter le sql dans le readme
utiliser spring validation pour vérifier la validité des champs pour les entrés
