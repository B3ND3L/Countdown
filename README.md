# Countdown

## Projet
Ce programme programmé en JSP - JEE - JavaScript
a été développé dans le cadre de mes études (Master 2)

### Installation

Ce projet a besoin de Java, Tomcat, maven et un mysql server

Vous pouvez installer le projet en faisant

```sh
$ git clone https://github.com/B3ND3L/Countdown.git
$ cd Countdown
$ mvn install
$ mvn tomcat7:redeploy
```
Pour mettre en place la partie SQL, faites :

```sh
$ mysql -h localhost -u root -p < script-db.sql
```

Ce script considère que la base de données Countdown existe déjà.

Si vous voulez utiliser une autre base de données vous pouvez configurer ça en modifiant:
  - Le nom de la base de données (ici Countdown)
  - Le nom de l'utilisateur (ici username) 
  - Le mot de passe (ici password)

```java
this.conn = DriverManager.getConnection("jdbc:mysql://localhost/Countdown?user=username&password=password");
```
