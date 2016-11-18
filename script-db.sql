CREATE DATABASE Countdown;

CREATE USER 'username'@'%' IDENTIFIED WITH mysql_native_password AS 'password';
GRANT ALL PRIVILEGES ON `Countdown`.* TO 'username'@'%';
FLUSH PRIVILEGES;

CREATE TABLE `Countdown`.`compteurs` ( `userid` VARCHAR(25) NOT NULL , `id` INT NOT NULL , `name` VARCHAR(20) NOT NULL , `deadline` VARCHAR(20) NOT NULL ) ENGINE = InnoDB
