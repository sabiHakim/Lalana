CREATE DATABASE lalana;

\c lalana

CREATE TABLE dent(
    idDent serial PRIMARY KEY,
    nom VARCHAR(100),
    coutTraitement double precision,
    coutRemplacement double precision,
    coutNettoyage double precision,
    coutEnlevement double precision,
    ordreNumerique int,
    positionGD int,
    positionHB int,
    typePriorite VARCHAR(50)
);

CREATE TABLE historiqueCout(
    idHistorique serial PRIMARY KEY,
    idDent int,
    daty date,
    coutTraitement double precision,
    coutRemplacement double precision,
    coutNettoyage double precision,
    coutEnlevement double precision,
    FOREIGN KEY (idDent) REFERENCES dent (idDent)
);

CREATE TABLE etat(
    etat serial PRIMARY KEY,
    designationEtat VARCHAR(100),
    typeSoin int,
    designationTypeSoin VARCHAR(100), 
    pointAjouter int
);


CREATE TABLE patient(
    idPatient serial PRIMARY KEY,
    nom VARCHAR(100)
);


CREATE TABLE soinPatient(
    idSoin serial PRIMARY KEY,
    idPatient int,
    daty date,
    argent double precision,
    choixPriorite VARCHAR(100),
    FOREIGN KEY (idPatient) REFERENCES patient (idPatient)
);

CREATE TABLE etatDentPatient(
    idSoin int,
    idDent int,
    etat int,
    FOREIGN KEY (idDent) REFERENCES dent (idDent),
    FOREIGN KEY (idSoin) REFERENCES soinPatient (idSoin),
    FOREIGN KEY (etat) REFERENCES etat (etat)
);


CREATE TABLE detailSoin(
    idDetailSoin serial PRIMARY KEY,
    idDent int,
    idSoin int,
    etatInitial int,
    etatFinal int,
    cout double precision,
    FOREIGN KEY (idDent) REFERENCES dent (idDent),
    FOREIGN KEY (idSoin) REFERENCES soinPatient (idSoin)
);

-- VIEWS

-- Maka ny info an'ny nify  rehetra 
CREATE view v_etatDentPatient as select*from  dent natural join  etatDentPatient;

-- maka ny nify beaute sy sante efa par priorite
CREATE or replace view v_getDentBeaute as select*from v_etatDentPatient where typePriorite='beaute' order by ordreNumerique,positionGD,positionHB asc;
CREATE or replace view v_getDentSante as select*from v_etatDentPatient where typePriorite='sante' order by ordrenumerique desc,positionhb asc,positiongd asc;

CREATE or replace view v_getDentBeauteByEtat as select*from v_getDentBeaute order by etat asc;
CREATE or replace view v_getDentSanteByEtat as select*from v_getDentSante order by etat asc;
-- Reto no ampiasaina (Maka ny nify tokony ho traitena par priotite)
CREATE or replace view v_getDentBeauteSante as select*from v_getDentBeaute union all select*from v_getDentSante;
CREATE or replace view v_getDentSanteBeaute as  select*from v_getDentSante union all select*from v_getDentBeaute;

CREATE or replace view v_getDentBeauteSanteEtat as select*from v_getDentBeauteByEtat union all select*from v_getDentSanteByEtat;
CREATE or replace view v_getDentSanteBeauteEtat as select*from v_getDentSanteByEtat union all select*from v_getDentBeauteByEtat;

CREATE OR REPLACE VIEW lastSoin as select*from soinpatient natural join patient where idSoin in(select max(idSoin) from soinpatient);
-- Angalana ny resultatScan
select idDent,idSoin,etat,designationEtat,typeSoin,DesignationTypeSoin,pointAjouter from v_getDentSanteBeauteEtat natural join etat;
-- select*from etatDentPatient CROSS join v_getDentBeaute;

-- Molaire superieure gauche
-- iddent | nom | couttraitement | coutremplacement | coutnettoyage | coutenlevement | ordrenumerique | positiongd | positionhb | typepriorite
INSERT INTO dent (nom, coutTraitement, coutRemplacement, coutnettoyage,coutenlevement,ordrenumerique,positiongd,positionhb,typepriorite) VALUES 
    ('PK1', 100.0, 150.0,30000,15000,1,1,1, 'beaute'),
    ('PK2', 100.0, 150.0,30000,15000,2,1,1, 'beaute'),
    ('PK3', 100.0, 150.0,30000,15000,3,1,1, 'beaute'),
    ('PK4', 100.0, 150.0,30000,15000,4,1,1, 'sante'),
    ('PK5', 100.0, 150.0,30000,15000,5,1,1, 'sante'),
    ('PK6', 100.0, 150.0,30000,15000,6,1,1, 'sante'),
    ('PK7', 100.0, 150.0,30000,15000,6,2,1, 'sante'),
    ('PK8', 100.0, 150.0,30000,15000,5,2,1, 'sante'),
    ('PK9', 100.0, 150.0,30000,15000,4,2,1, 'sante'),
    ('PK10', 100.0, 150.0,30000,15000,3,2,1, 'beaute'),
    ('PK11', 100.0, 150.0,30000,15000,2,2,1, 'beaute'),
    ('PK12', 100.0, 150.0,30000,15000,1,2,1, 'beaute');
UPDATE 



-- INSERT ETAT
INSERT INTO ETAT VALUES (0,'Tsisy lalana',4,'Manao lalana vaovao',10);
INSERT INTO ETAT (designationEtat,typeSoin,designationTypeSoin,pointAjouter) VALUES 
('Lavaka be Tsy azo andehanana',3,'terassement',-1),
('Lavaka be',3,'terassement',-1),
('Lavakadavaka',3,'terassement',-1),
('mikitaotaona be mintsy ',2,'reparation',1),
('mikitaotaona be ',2,'reparation',1),
('mikitaotaona ',2,'reparation',1),
('mikitaotaona kely',2,'reparation',1),
('maloto be',1,'nettoyage',1),
('maloto',1,'nettoyage',1),
('Tena tsara',0,null,0
);

-- -- INSERT PATIENT
-- INSERT INTO patient (nom) VALUES ('Morel');

-- -- INSERT soinPatient
-- INSERT INTO soinPatient (idPatient,daty,argent,choixPriorite) VALUES (1,'10-12-2023',200000,'beaute');

-- -- INSERT etatDentPatient
-- INSERT INTO etatDentPatient (idSoin, idDent, etat) VALUES 
--     (1, 1, 5),
--     (1, 2, 7),
--     (1, 3, 3),
--     (1, 4, 8),
--     (1, 5, 2),
--     (1, 6, 6),
--     (1, 7, 4),
--     (1, 8, 9),
--     (1, 9, 1),
--     (1, 10, 10),
--     (1, 11, 5),
--     (1, 12, 7),
--     (1, 13, 0),
--     (1, 14, 8),
--     (1, 15, 2),
--     (1, 16, 6),
--     (1, 17, 4),
--     (1, 18, 0),
--     (1, 19, 1),
--     (1, 20, 10),
--     (1, 21, 5),
--     (1, 22, 0),
--     (1, 23, 3),
--     (1, 24, 8),
--     (1, 25, 2),
--     (1, 26, 6),
--     (1, 27, 4),
--     (1, 28, 9),
--     (1, 29, 1),
--     (1, 30, 10),
--     (1, 31, 5),
--     (1, 32, 0);

-- ALTER TABLE nom_de_la_table
-- ADD COLUMN nom_de_la_colonne type_de_données;

-- SHOW server_encoding;

-- ALTER DATABASE your_database_name SET ENCODING 'UTF8';
