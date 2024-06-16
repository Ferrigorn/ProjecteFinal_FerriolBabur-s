create database projectefinal;
use projectefinal;

create table Usuaris(
idUsuari bigint unsigned auto_increment primary key,
nom varchar(55),
cognoms varchar(100),
contrasenya varchar(100),
email varchar(100) unique,
rol varchar(55)
);

create table Llibres(
idLlibre bigint unsigned auto_increment primary key,
titol varchar(255),
autor varchar(255),
editorial varchar(100),
anyEdicio int,
genere varchar(100),
ubicacio varchar(100),
idioma varchar(100),
estat varchar(55),
coleccio varchar(100),
imatge longblob
);
alter table Llibres auto_increment = 3327;
commit;
create table LlibresLlegitsUsuari (
idLlibresLlegitsUsuari int auto_increment primary key,
idUsuari int not null,
idLlibre int not null,
foreign key (idUsuari) references Usuaris(idUsuari) On delete cascade,
foreign key (idLlibre) references Llibres(idLlibre) on delete cascade
);

CREATE TABLE Comentaris (
idComentari BIGINT AUTO_INCREMENT PRIMARY KEY,
idUsuari BIGINT UNSIGNED,
idLlibre BIGINT UNSIGNED,
comentari LONGTEXT,
FOREIGN KEY (idUsuari) REFERENCES Usuaris(idUsuari) ON DELETE CASCADE,
FOREIGN KEY (idLlibre) REFERENCES Llibres(idLlibre) ON DELETE CASCADE
) ENGINE = InnoDB;

create table Valoracions(
idValoracio bigint auto_increment primary key,
idUsuari bigint unsigned,
idLlibre bigint unsigned,
valoracio int,
foreign key (idUsuari) references Usuaris(idUsuari) ON DELETE CASCADE,
foreign key (idLlibre) references Llibres(idLlibre) ON DELETE CASCADE
)engine = InnoDB;

