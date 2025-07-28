-- Skripta za unos testnih podataka u bazu `etfbl_ip`
USE etfbl_ip;

-- 1. Unos podataka u tabelu `proizvodjaci`
INSERT INTO proizvodjaci (naziv, drzava, adresa, kontakt_telefon, kontakt_fax, kontakt_email) VALUES
('Tesla', 'SAD', 'Palo Alto, CA', '+1-800-123-456', '+1-800-654-321', 'info@tesla.com'),
('Specialized', 'SAD', 'Morgan Hill, CA', '+1-800-987-654', '+1-800-456-789', 'support@specialized.com'),
('Xiaomi', 'Kina', 'Beijing', '+86-10-12345678', '+86-10-87654321', 'contact@xiaomi.com');

INSERT INTO proizvodjaci (naziv, drzava, adresa, kontakt_telefon, kontakt_fax, kontakt_email) VALUES
('Specialized', 'SAD', 'Morgan Hill, CA', '+1-800-987-654', '+1-800-456-789', 'support@specialized.com');

-- 2. Unos podataka u tabelu `prevozna_sredstva`
INSERT INTO prevozna_sredstva (identifikator, tip, proizvodjac_id, model, cijena_nabavke, datum_nabavke, opis, status, slika, autonomija, maksimalna_brzina, trenutna_lokacija) VALUES
('A1', 'AUTOMOBIL', 1, 'Model S', 79999.99, '2023-01-01', 'Električni automobil visoke performanse', 'SLOBODNO', 'tesla_model_s.jpg', NULL, NULL, '0,0'),
('B1', 'BICIKL', 2, 'Turbo Vado SL', 3500.00, '2023-06-15', 'Električni bicikl za gradske vožnje', 'SLOBODNO', 'specialized_turbo_vado.jpg', 120, NULL, '0,0'),
('T1', 'TROTINET', 3, 'Mi Electric Scooter Pro 2', 550.00, '2023-09-10', 'Električni trotinet sa izuzetnim dometom', 'SLOBODNO', 'xiaomi_scooter_pro2.jpg', NULL, 25, '0,0');

-- 3. Unos podataka u tabelu `korisnici`
INSERT INTO korisnici (uloga, korisnicko_ime, lozinka, ime, prezime, broj_licne_karte, email, broj_telefona, avatar, radno_mjesto) VALUES
('ADMINISTRATOR', 'admin', 'admin123', 'Admin', 'Adminovic', NULL, 'admin@etfbl.com', NULL, NULL, 'Administrator'),
('OPERATER', 'operater1', 'operater123', 'Petar', 'Peric', NULL, 'petar.peric@etfbl.com', '+387-65-123-456', NULL, 'Operater'),
('KLIJENT', 'klijent1', 'klijent123', 'Marko', 'Markovic', '123456789', 'marko.markovic@gmail.com', '+387-65-987-654', NULL, NULL),
('MENADZER', 'menadzer1', 'menadzer123', 'Ana', 'Anic', NULL, 'ana.anic@etfbl.com', '+387-65-321-654', NULL, 'Menadžer');

INSERT INTO korisnici (uloga, korisnicko_ime, lozinka, ime, prezime, broj_licne_karte, email, broj_telefona, avatar, radno_mjesto) VALUES
('KLIJENT', 'klijent2', 'klijent123', 'Ja', 'aj', '4242', 'marko.markovic@gmail.com', '+387-65-987-654', NULL, NULL);

INSERT INTO korisnici (uloga, korisnicko_ime, lozinka, ime, prezime, broj_licne_karte, email, broj_telefona, avatar, radno_mjesto) VALUES
-- Klijenti
('KLIJENT', 'klijent3', 'klijent123', 'Petar', 'Petrović', '4243', 'petar.petrovic@gmail.com', '+387-65-123-456', NULL, NULL),
('KLIJENT', 'klijent4', 'klijent123', 'Ana', 'Anić', '4244', 'ana.anic@gmail.com', '+387-66-234-567', NULL, NULL),
('KLIJENT', 'klijent5', 'klijent123', 'Ivana', 'Ivić', '4245', 'ivana.ivic@gmail.com', '+387-65-345-678', NULL, NULL),
('KLIJENT', 'klijent6', 'klijent123', 'Nikola', 'Nikolić', '4246', 'nikola.nikolic@gmail.com', '+387-66-456-789', NULL, NULL);

-- 4. Unos podataka u tabelu `kvarovi`
INSERT INTO kvarovi (prevozno_sredstvo_id, opis, datum_vrijeme) VALUES
(1, 'Problem sa baterijom', '2023-12-01 10:00:00'),
(2, 'Lanac se pokidao', '2023-12-15 14:30:00');

-- 4. Unos podataka u tabelu `kvarovi`
INSERT INTO kvarovi (prevozno_sredstvo_id, opis, datum_vrijeme) VALUES
(1, 'Problem sa baterijom', '2023-12-01 10:00:00');

-- 5. Unos podataka u tabelu `iznajmljivanja`
INSERT INTO iznajmljivanja 
(prevozno_sredstvo_id, korisnik_id, datum_pocetka, datum_zavrsetka, 
 lokacija_preuzimanja, lokacija_vracanja, trajanje, identifikacioni_dokument, vozacka_dozvola)
VALUES
(1, 3, '2024-01-15 08:00:00', '2024-01-15 10:30:00', '10,15', '12,18', 150, '123456789', 'B12345678'),

(2, 3, '2024-01-16 09:00:00', '2024-01-16 11:00:00', '5,7', '6,8', 120, '123456789', NULL),

(1, 8, '2024-02-05 09:15:00', '2024-02-05 11:45:00', '11,16', '13,19', 150, '987654321', 'C23456789'),

(1, 9, '2024-03-12 14:00:00', '2024-03-12 16:00:00', '9,14', '10,17', 120, '456789123', 'D34567890'),

(1, 10, '2024-04-20 08:30:00', '2024-04-20 12:00:00', '8,13', '11,15', 210, '321654987', 'E45678901'),

(1, 11, '2024-05-18 10:00:00', '2024-05-18 12:30:00', '10,15', '12,18', 150, '123456789', 'B12345678'),

(1, 12, '2024-06-25 15:00:00', '2024-06-25 17:00:00', '12,18', '14,20', 120, '987654321', 'C23456789');



-- 6. Unos podataka u tabelu `promocije`
INSERT INTO promocije (naslov, opis, datum_pocetka, datum_zavrsetka) VALUES
('Zimska akcija', 'Popust na sve bicikle od 20%', '2023-12-01', '2024-01-31'),
('Prolećna promocija', 'Besplatno korišćenje trotineta za prve korisnike', '2024-03-01', '2024-03-31');

-- 7. Unos podataka u tabelu `objave`
INSERT INTO objave (naslov, sadrzaj, datum_kreiranja) VALUES
('Novi model automobila!', 'Tesla Model S sada dostupan za iznajmljivanje.', '2024-01-01 12:00:00'),
('Specijalna ponuda', 'Ugrabite popust na električne bicikle!', '2024-01-10 15:00:00');

INSERT INTO promocije (naslov, opis, datum_pocetka, datum_zavrsetka) VALUES
('Letnja vožnja', 'Popust od 15% na sve električne skutere tokom leta!', '2024-06-01', '2024-08-31'),
('Jesenja avantura', 'Dva dana iznajmljivanja bicikla po ceni jednog!', '2024-09-15', '2024-10-15'),
('Vikend specijal', '50% popusta na sve automobile svakog vikenda!', '2024-04-01', '2024-12-31'),
('Zimski popusti', 'Popust od 30% na sve trotinete tokom zime.', '2024-12-01', '2025-02-28'),
('Praznična promocija', 'Besplatno iznajmljivanje na dan Božića!', '2024-12-25', '2024-12-25');

INSERT INTO objave (naslov, sadrzaj, datum_kreiranja) VALUES
('Nova flota skutera', 'Dodali smo 50 novih električnih skutera!', '2024-02-05 09:30:00'),
('Ažurirana aplikacija', 'Naša aplikacija sada ima novu funkcionalnost za praćenje vožnje.', '2024-02-20 11:45:00'),
('Ekološka inicijativa', 'Pridružite se našem ekološkom programu i osvojite besplatne vožnje.', '2024-03-15 14:20:00'),
('Proširenje flote', 'Dodali smo nove modele hibridnih vozila.', '2024-04-01 10:00:00'),
('Sigurnosni saveti', 'Pročitajte najnovije savete o bezbednoj vožnji!', '2024-05-01 08:00:00');

