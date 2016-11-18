## Titre
Countdown

## Projet
Ce programme programmé en JSP - JEE - JavaScript
a été développé dans le cadre de mes étude (Master 2)

### Installation

Ce projet a besoin de Java, Tomcat, maven et un mysql server

Vous pouvez installer le projet en faisant

```sh
$ cd Countdown
$ mvn install
$ mvn tomcat7:redeploy
```
Pour mettre en place la partie SQL, faites :

```sh
mysql -h localhost -u root -p < script-db.sql
```
