CREATE DATABASE dentisterie;

\c dentisterie

CREATE TABLE dent(
    idDent serial PRIMARY KEY,
    nom VARCHAR(100),
    coutTraitement double precision,
    coutRemplacement double precision,
    typePriorite VARCHAR(50)
);

CREATE TABLE historiqueCout(
    idHistorique serial PRIMARY KEY,
    idDent int,
    daty date,
    coutTraitement double precision,
    coutRemplacement double precision,
    FOREIGN KEY (idDent) REFERENCES dent (idDent)
);

-- Molaire supérieure gauche
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Troisième molaire', 100.0, 150.0, 'santé'),
    ('Deuxième molaire', 90.0, 140.0, 'santé'),
    ('Première molaire', 85.0, 130.0, 'santé');

-- Prémolaire supérieure gauche
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Deuxième prémolaire', 80.0, 120.0, 'beauté'),
    ('Première prémolaire', 75.0, 110.0, 'beauté');

-- Canine supérieure gauche
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Canine', 70.0, 100.0, 'beauté');

-- Incisive supérieure gauche
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Latérale gauche', 65.0, 95.0, 'beauté'),
    ('Centrale gauche', 60.0, 90.0, 'beauté');

-- Incisive centrale supérieure
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Centrale centrale', 55.0, 85.0, 'beauté');

-- Incisive supérieure droite
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Centrale droite', 50.0, 80.0, 'beauté'),
    ('Latérale droite', 45.0, 75.0, 'beauté');

-- Canine supérieure droite
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Canine droite', 40.0, 70.0, 'beauté');

-- Prémolaire supérieure droite
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Première prémolaire droite', 35.0, 65.0, 'beauté'),
    ('Deuxième prémolaire droite', 30.0, 60.0, 'beauté');

-- Molaire supérieure droite
INSERT INTO dent (nom, coutTraitement, coutRemplacement, typePriorite) VALUES 
    ('Première molaire droite', 25.0, 55.0, 'santé'),
    ('Deuxième molaire droite', 20.0, 50.0, 'santé'),
    ('Troisième molaire droite', 15.0, 45.0, 'santé');

SHOW server_encoding;

ALTER DATABASE your_database_name SET ENCODING 'UTF8';
