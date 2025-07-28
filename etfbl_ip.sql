CREATE DATABASE IF NOT EXISTS etfbl_ip;
USE etfbl_ip;

-- 1. Tabela za proizvodjace
CREATE TABLE proizvodjaci (
    id INT AUTO_INCREMENT PRIMARY KEY,
    naziv VARCHAR(100) NOT NULL,
    drzava VARCHAR(100) NOT NULL,
    adresa VARCHAR(200) NOT NULL,
    kontakt_telefon VARCHAR(15) NOT NULL,
    kontakt_fax VARCHAR(15) NOT NULL,
    kontakt_email VARCHAR(100) NOT NULL
);

-- 2. Tabela za prevozna sredstva
CREATE TABLE prevozna_sredstva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    identifikator VARCHAR(50) NOT NULL UNIQUE,
    tip ENUM('AUTOMOBIL', 'BICIKL', 'TROTINET') NOT NULL,
    proizvodjac_id INT,
    model VARCHAR(100) NOT NULL,
    cijena_nabavke DECIMAL(10, 2) NOT NULL,
    datum_nabavke DATE,
    opis TEXT,
    status ENUM('SLOBODNO', 'POKVARENO', 'IZNAJMLJENO') DEFAULT 'SLOBODNO',
    slika VARCHAR(255) NOT NULL,
    autonomija INT,
    maksimalna_brzina INT,
    FOREIGN KEY (proizvodjac_id) REFERENCES proizvodjaci(id) ON DELETE SET NULL
);

-- 3. Tabela za kvarove
CREATE TABLE kvarovi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prevozno_sredstvo_id INT NOT NULL,
    opis TEXT NOT NULL,
    datum_vrijeme DATETIME NOT NULL,
    FOREIGN KEY (prevozno_sredstvo_id) REFERENCES prevozna_sredstva(id) ON DELETE CASCADE
);

-- 4. Tabela za korisnike
CREATE TABLE korisnici (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uloga ENUM('ADMINISTRATOR', 'OPERATER', 'MENADZER', 'KLIJENT') NOT NULL,
    korisnicko_ime VARCHAR(50) NOT NULL UNIQUE,
    lozinka VARCHAR(255) NOT NULL,
    ime VARCHAR(100) NOT NULL,
    prezime VARCHAR(100) NOT NULL,
    broj_licne_karte VARCHAR(20),
    email VARCHAR(100),
    broj_telefona VARCHAR(15),
    avatar VARCHAR(255),
    radno_mjesto VARCHAR(100)
);

-- 5. Tabela za iznajmljivanja
CREATE TABLE iznajmljivanja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prevozno_sredstvo_id INT NOT NULL,
    korisnik_id INT NOT NULL,
    datum_pocetka DATETIME NOT NULL,
    datum_zavrsetka DATETIME,
    lokacija_preuzimanja POINT NOT NULL,
    lokacija_vracanja POINT NOT NULL,
    trajanje INT NOT NULL,
    identifikacioni_dokument VARCHAR(50),
    vozacka_dozvola VARCHAR(50),
    FOREIGN KEY (prevozno_sredstvo_id) REFERENCES prevozna_sredstva(id) ON DELETE CASCADE,
    FOREIGN KEY (korisnik_id) REFERENCES korisnici(id) ON DELETE CASCADE
);

-- 6. Tabela za promocije
CREATE TABLE promocije (
    id INT AUTO_INCREMENT PRIMARY KEY,
    naslov VARCHAR(100) NOT NULL,
    opis TEXT NOT NULL,
    datum_pocetka DATE NOT NULL,
    datum_zavrsetka DATE NOT NULL
);

-- 7. Tabela za objave
CREATE TABLE objave (
    id INT AUTO_INCREMENT PRIMARY KEY,
    naslov VARCHAR(100) NOT NULL,
    sadrzaj TEXT NOT NULL,
    datum_kreiranja DATETIME NOT NULL
);

ALTER TABLE korisnici ADD COLUMN blokiran BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE iznajmljivanja 
MODIFY COLUMN lokacija_preuzimanja VARCHAR(50),
MODIFY COLUMN lokacija_vracanja VARCHAR(50);

ALTER TABLE iznajmljivanja ADD COLUMN platna_kartica VARCHAR(16) NOT NULL;
ALTER TABLE iznajmljivanja ADD COLUMN cijena INT NOT NULL;

ALTER TABLE prevozna_sredstva ADD COLUMN trenutna_lokacija VARCHAR(5) NOT NULL;
ALTER TABLE prevozna_sredstva 
MODIFY COLUMN trenutna_lokacija VARCHAR(5) NOT NULL DEFAULT '0,0';



CREATE TABLE cijene_iznajmljivanja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tip ENUM('AUTOMOBIL', 'BICIKL', 'TROTINET') NOT NULL,
    cijena_iznajmljivanja INT NOT NULL
);

