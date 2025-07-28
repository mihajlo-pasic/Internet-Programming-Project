-- Ubacivanje podataka u tabelu 'proizvodjaci'
INSERT INTO proizvodjaci (naziv, drzava, adresa, kontakt_telefon, kontakt_fax, kontakt_email)
VALUES 
('Tesla', 'SAD', '3500 Deer Creek Road, Palo Alto, CA', '+123456789', '+123456780', 'contact@tesla.com'),
('Giant', 'Tajvan', 'No. 19, Shun Far Road, Taichung', '+886423566788', '+886423566789', 'info@giant-bicycles.com'),
('Xiaomi', 'Kina', 'No. 68, Qinghe Middle Street, Haidian District, Beijing', '+861055678900', '+861055678901', 'service@xiaomi.com'),
('Toyota', 'Japan', '1 Toyota-cho, Toyota City, Aichi Prefecture', '+81565678900', '+81565678901', 'support@toyota.jp'),
('Ford', 'SAD', '1 American Road, Dearborn, MI', '+13132226600', '+13132226601', 'info@ford.com');

-- Ubacivanje podataka u tabelu 'prevozna_sredstva'
INSERT INTO prevozna_sredstva (identifikator, tip, proizvodjac_id, model, cijena_nabavke, datum_nabavke, opis, status, slika, autonomija, maksimalna_brzina, trenutna_lokacija)
VALUES 
('TES123', 'AUTOMOBIL', 1, 'Model S', 80000.00, '2022-01-15', 'Elektricni luksuzni sedan', 'SLOBODNO', 'model_s.jpg', 600, 250, '45.0,19.0'),
('GNT456', 'BICIKL', 2, 'Escape 3', 500.00, '2021-05-10', 'Gradski bicikl', 'IZNAJMLJENO', 'escape_3.jpg', NULL, 25, '44.8,19.2'),
('XMI789', 'TROTINET', 3, 'Mi Electric Scooter', 400.00, '2022-07-20', 'Elektricni trotinet', 'POKVARENO', 'mi_scooter.jpg', 30, 20, '44.9,19.1'),
('TYT101', 'AUTOMOBIL', 4, 'Corolla', 20000.00, '2020-03-12', 'Pouzdan porodični auto', 'SLOBODNO', 'corolla.jpg', 450, 180, '45.1,19.3'),
('FRD202', 'AUTOMOBIL', 5, 'Mustang', 55000.00, '2023-02-18', 'Sportski auto', 'SLOBODNO', 'mustang.jpg', 500, 260, '45.0,19.2');

-- Ubacivanje podataka u tabelu 'kvarovi'
INSERT INTO kvarovi (prevozno_sredstvo_id, opis, datum_vrijeme)
VALUES 
(3, 'Pucanje gume', '2023-06-15 10:30:00'),
(3, 'Problemi sa baterijom', '2023-08-01 14:20:00'),
(2, 'Lanac spao sa zupčanika', '2023-09-12 09:15:00'),
(5, 'Problemi sa menjačem', '2023-11-23 12:45:00');

-- Ubacivanje podataka u tabelu 'korisnici'
INSERT INTO korisnici (uloga, korisnicko_ime, lozinka, ime, prezime, broj_licne_karte, email, broj_telefona, avatar, radno_mjesto)
VALUES 
('ADMINISTRATOR', 'admin1', 'adminpass', 'Marko', 'Markovic', '123456789', 'marko@example.com', '+38765123456', 'avatar1.jpg', 'IT Administrator'),
('OPERATER', 'operater1', 'operpass', 'Jelena', 'Jovanovic', '987654321', 'jelena@example.com', '+38765234567', 'avatar2.jpg', 'Operater na terenu'),
('MENADZER', 'menadzer1', 'menadpass', 'Petar', 'Petrovic', '112233445', 'petar@example.com', '+38765345678', 'avatar3.jpg', 'Menadzer prodaje'),
('KLIJENT', 'klijent1', 'klijentpass', 'Ana', 'Anic', '223344556', 'ana@example.com', '+38765456789', 'avatar4.jpg', NULL),
('KLIJENT', 'klijent2', 'klijentpass', 'Milos', 'Milic', '334455667', 'milos@example.com', '+38765567890', 'avatar5.jpg', NULL);

-- Ubacivanje podataka u tabelu 'iznajmljivanja'
INSERT INTO iznajmljivanja (prevozno_sredstvo_id, korisnik_id, datum_pocetka, datum_zavrsetka, lokacija_preuzimanja, lokacija_vracanja, trajanje, identifikacioni_dokument, vozacka_dozvola, platna_kartica, cijena)
VALUES 
(1, 4, '2024-01-05 09:00:00', '2024-01-05 17:00:00', '45.0,19.0', '45.0,19.0', 8, '123456789', 'B1234567', '1111222233334444', 80),
(2, 5, '2024-02-10 08:00:00', NULL, '44.8,19.2', NULL, 0, '223344556', NULL, '5555666677778888', 0),
(4, 4, '2024-03-15 10:00:00', '2024-03-15 14:00:00', '45.1,19.3', '45.1,19.3', 4, '123456789', 'B1234567', '1111222233334444', 40);

-- Ubacivanje podataka u tabelu 'promocije'
INSERT INTO promocije (naslov, opis, datum_pocetka, datum_zavrsetka)
VALUES 
('Prolećna Akcija', 'Popust od 20% na sve iznajmljivanje automobila', '2024-03-01', '2024-03-31'),
('Letnja Vožnja', 'Besplatno iznajmljivanje bicikala tokom vikenda', '2024-06-01', '2024-06-30'),
('Zimski Popusti', '50% popusta na sve električne trotinete', '2024-12-01', '2024-12-31');

-- Ubacivanje podataka u tabelu 'objave'
INSERT INTO objave (naslov, sadrzaj, datum_kreiranja)
VALUES 
('Novi Modeli u Ponudi', 'Stigli su novi modeli automobila i bicikala.', '2024-01-10 08:00:00'),
('Servisna Obavijest', 'Trotineti neće biti dostupni zbog servisa.', '2024-02-20 10:30:00'),
('Letnji Popusti', 'Iskoristite letnje popuste na iznajmljivanje.', '2024-06-05 12:00:00');

-- Ubacivanje podataka u tabelu 'cijene_iznajmljivanja'
INSERT INTO cijene_iznajmljivanja (tip, cijena_iznajmljivanja)
VALUES 
('AUTOMOBIL', 5),
('BICIKL', 3),
('TROTINET', 2);
