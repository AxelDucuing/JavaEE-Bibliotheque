#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

CREATE DATABASE Bibliotheque;

#------------------------------------------------------------
#        Utilisation/Ouverture de la base de donnée.
#------------------------------------------------------------

USE Bibliotheque;

#------------------------------------------------------------
# Création Table: AUTEUR.
#------------------------------------------------------------

CREATE TABLE AUTEUR(
        id_auteur  Int  Auto_increment  NOT NULL ,
        nom        Varchar (250) NOT NULL ,
        prenom     Varchar (250) NOT NULL ,
        biographie Varchar (1000),
	CONSTRAINT AUTEUR_PK PRIMARY KEY (id_auteur)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Création Table: ROLE.
#------------------------------------------------------------

CREATE TABLE ROLE(
        id_role Int  Auto_increment  NOT NULL ,
        nom     Varchar (250) NOT NULL,
	CONSTRAINT ROLE_PK PRIMARY KEY (id_role)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Création Table: MEMBRES.
#------------------------------------------------------------

CREATE TABLE MEMBRES(
        id_membre    Int  Auto_increment  NOT NULL ,
        mdp          Varchar (250) NOT NULL ,
        adresse_mail Varchar (250) NOT NULL ,
        telephone    Varchar (10) NOT NULL ,
        id_role      Int NOT NULL,
	CONSTRAINT MEMBRES_PK PRIMARY KEY (id_membre),
	CONSTRAINT MEMBRES_ROLE_FK FOREIGN KEY (id_role) REFERENCES ROLE(id_role)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Création Table: LIVRE.
#------------------------------------------------------------

CREATE TABLE LIVRE(
        id_livre      Int  Auto_increment  NOT NULL ,
        titre         Varchar (250) NOT NULL ,
        resume        Varchar (500) NOT NULL ,
        date_parution Date NOT NULL,
        nombre_pages  Int NOT NULL,
        id_auteur     Int NOT NULL,
        id_membre     Int,
	CONSTRAINT LIVRE_PK PRIMARY KEY (id_livre),
        CONSTRAINT LIVRE_AUTEUR_FK FOREIGN KEY (id_auteur) REFERENCES AUTEUR(id_auteur),
        CONSTRAINT LIVRE_MEMBRES0_FK FOREIGN KEY (id_membre) REFERENCES MEMBRES(id_membre)
)ENGINE=InnoDB;

#------------------------------------------------------------
# Remplissage Table: ROLE.
#------------------------------------------------------------

INSERT INTO ROLE (nom) VALUES ("membre"), ("admin");

#------------------------------------------------------------
# Remplissage Table: MEMBRES.
#------------------------------------------------------------

INSERT INTO MEMBRES (mdp, adresse_mail, telephone, id_role)
VALUES  ("12345A", "axel@ldnr.fr", "0754868235", 1),
        ("12345T", "tom@ldnr.fr", "0634852971", 1),
        ("12345A", "arnaud@ldnr.fr", "0659842357", 1),
        ("12345J", "joel@ldnr.fr", "0637895614", 1),
        ("admin", "admin@ldnr.fr", "0612345678", 1);

#------------------------------------------------------------
# Remplissage Table: AUTEUR.
#------------------------------------------------------------

INSERT INTO AUTEUR (nom, prenom, biographie) 
VALUES ("Rowling", "Joanne", "Joanne Rowling, également connue sous le nom de J. K. Rowling et le pseudonyme de Robert Galbraith, est une romancière et scénariste britannique née le 31 juillet 1965 dans l'agglomération de Yate, dans le Gloucestershire, en Angleterre."), 
("Werber", "Bernard", "Bernard Werber, né le 18 septembre 1961 à Toulouse, est un écrivain français."),
("Tolkien", "John Ronald Reuel", "John Ronald Reuel Tolkien, plus connu sous la forme J. R. R. Tolkien, est un écrivain, poète, philologue, essayiste et professeur d'université anglais, né le 3 janvier 1892 à Bloemfontein et mort le 2 septembre 1973 à Bournemouth."),
("Asimov", "Isaac", "Isaac Asimov, né vers le 2 janvier 1920 à Petrovitchi (en Russie) et mort du sida (acquis par transfusion sanguine) le 6 avril 1992 à New York aux Etats-Unis, est un écrivain américano-russe, naturalisé en 1928, surtout connu pour ses oeuvres de science-fiction et ses livres de vulgarisation scientifique."),
("Herbert", "Frank", "Frank Herbert, né Frank Patrick Herbert, Jr. le 8 octobre 1920 à Tacoma dans l'état de Washington et mort le 11 février 1986 (à 65 ans) à Madison dans le Wisconsin, est un écrivain américain, auteur de romans de science-fiction."),
("Boulle", "Pierre", "Pierre Boulle, né le 20 février 1912 à Avignon et mort le 31 janvier 1994 à Paris 16e, est un écrivain français. Agent de la France libre en Asie du Sud-Est pendant la Seconde Guerre mondiale."),
("Corey", "James S.A", "James S. A. Corey est le nom de plume utilisé par Daniel Abraham et Ty Franck, auteurs de la série de romans de science-fiction The Expanse."),
("Verne", "Jules", "Jules Verne, né le 8 février 1828 à Nantes et mort le 24 mars 1905 à Amiens, est un écrivain français dont l'oeuvre est, pour la plus grande partie, constituée de romans d'aventures évoquant les progrès scientifiques du 19éme siècle."),
("Clancy", "Tom", "Thomas Leo Clancy Jr., dit Tom Clancy, né à Baltimore dans le Maryland (États-Unis) le 12 avril 1947 et mort le 1er octobre 2013 dans la même ville, est un romancier américain."),
("Gras", "Cédric", "Cédric Gras, né le 15 janvier 1982 à Saint-Cloud, est un écrivain et voyageur français spécialiste du monde Russe"),
("Patino", "Bruno", "Bruno Patino, né le 8 mars 1965 à Courbevoie, est un journaliste et dirigeant de presse français.");

#------------------------------------------------------------
# Remplissage Table: LIVRE.
#------------------------------------------------------------

INSERT INTO LIVRE (titre, resume, date_parution, nombre_pages, id_auteur, id_membre)
VALUES ("Bilbo le Hobbit", 
"Il raconte les aventures du hobbit Bilbo, emmené, bien malgré lui, par le magicien Gandalf et une compagnie de treize nains dans leur voyage vers la Montagne Solitaire, à la recherche du trésor gardé par le dragon Smaug.",
"1974-01-01",
323,
3,
1),
("Un défilé de robots", 
"Comment risque de réagir un robot programmé pour un environnement lunaire dés lors qu'il se retrouve égaré sur Terre ? Un robot peut-il mentir à un procès pour préserver les intérêts de son utilisateur ?",
"2018-06-13",
246,
4,
NULL),
("Harry Potter à l'école des sorciers", 
"Le jour de ses onze ans, Harry Potter, un orphelin élevé par un oncle et une tante qui le détestent, voit son existence bouleversée. Un géant vient le chercher pour l'emmener à Poudlard, une école de sorcellerie !",
"1998-06-26",
305,
1,
2),
("L'Incident Jésus", 
"Un navire interstellaire immense et qui ne cesse de grandir, devenu conscient jadis à la suite d'une expérience oubliée. Dans ses flancs, les neftiles, humains et clones, qui ne savent rien de certain sur leurs origines et pour qui Nef est Dieu.",
"1979-04-01",
472,
5,
3),
("Les Thanatonautes", 
"Le phénomène Werber continue. Après Les Fourmis et Le Jour des fourmis, deux best-sellers au succès sans précédent dans le monde entier, voici Les Thanatonautes : la grande épopée moderne qui perce jusqu'au mystère de la mort.",
"1996-01-06",
502,
2,
3),
("La planète des singes", 
"La Planète des singes est un roman de science-fiction, publié en 1963 par l'écrivain français Pierre Boulle racontant l'histoire de 3 hommes qui explorent une planète lointaine similaire à la Terre, où les grands singes dominent.",
"1986-09-27",
376,
6,
1),
("L'épreuve des hommes blancs", 
"Dans un Îlot de l'archipel malais, Marie-Helen, échappe par miracle au massacre lors de l'invasion du Sud-Est asiatique par les Japonais. Comme Mowgli, adopté par les bêtes de la jungle, Marie-Helen est recueillie par un pêcheur malais d'un kampong.",
"1983-02-16",
154,
6,
1),
("Harry Potter et la Coupe de Feu", 
"Harry Potter a quatorze ans et entre en quatrième année au collège de Poudlard. Une grande nouvelle attend Harry, Ron et Hermione à leur arrivée : la tenue d'un tournoi de magie exceptionnel entre les plus célèbres écoles de sorcellerie.",
"2011-09-01",
784,
1,
2),
("L'Éveil du Léviathan",
"L'Humanité a colonisé le système solaire. Jim Holden est second sur un transport de glace effectuant la navette entre les anneaux de Saturne et les stations installées dans la ceinture d'astéroïdes.",
"2014-06-04",
624,
7,
NULL),
("La guerre de Caliban",
"Sur Ganymède, grenier à blé des Planètes extérieures, un sergent des Marines de Mars assiste au massacre de sa section par un supersoldat monstrueux. Sur Terre, une personnalité politique de haut rang essaye d'éviter un conflit interplanétaire.",
"2015-06-10",
620,
7,
NULL),
("20.000 Lieues sous les mers",
"L'épopée sous-marine du capitaine Némo ici accompagnée de croquis originaux expliquant le contexte documentaire du roman, en plusieurs thèmes: naviguer en 1866, le sous-marin Nautilus, le scaphandre autonome",
"1869-10-01",
606,
8,
NULL),
("L'Ile mystérieuse",
"L'Île mystérieuse est un roman de Jules Verne, paru en 1875. Il constitue une suite à Vingt mille lieues sous les mers ainsi qu'à Les Enfants du capitaine Grant, auxquels il est rattaché par des éléments narratifs.",
"1875-11-22",
826,
8,
NULL),
("Les enfants du Capitaine Grant",
"L'action commence en 1864. Alors que Lord et Lady Glenarvan font une excursion au large de Glasgow à bord de leur yacht, l'équipage pêche un requin dans le ventre duquel on découvre une bouteille qui contient un message de détresse en mauvais état...",
"1868-06-23",
1312,
8,
NULL),
("Robur le conquérant",
"Uncle Prudent et Phil Evans sont respectivement président et secrétaire du Weldon-Institute de Philadelphie, mais aussi d'intimes ennemis. Le Weldon-Institute est un club rassemblant tous ceux qui pouvaient s'intéresser à l'aérostatique...",
"1886-11-01",
174,
8,
NULL),
("Danger immédiat",
"Prisonnier de ses promesses électorales dans la lutte contre la drogue, et voyant les prochaines échéances se rapprocher, le Président américain, avec l'aide de la CIA, met au point un plan ambitieux...",
"1990-06-10",
651,
9,
NULL),
("Le Nord, c'est l'Est : aux confins de la Fédération de Russie",
"Cédric Gras le dit et ne se l'explique pas, il est attiré par les territoires hostiles qui s'ingénient à repousser les rares voyageurs plutôt qu'à les séduire. Ici la découverte que le Nord russe se trouve en réalité à l'Est...",
"2013-03-07",
224,
10,
NULL),
("Le coeur et les confins",
"Cette histoire m'a été contée mille fois. On la raconte dans tous les trains, dans toutes les gares, sur les quais de chaque port. Elle se propage par les routes d'Asie et les pistes d'Afrique...",
"2014-02-13",
112,
10,
NULL),
("L'hiver aux trousses",
"C'est à un joyeux exercice de géographie narrative que nous invite Cédric Gras. Une pérégrination glacée aux confins de l'extrême orient russe où, 6 semaines durant, il a arpenté le pays de l'avenir lumineux à la poursuite d'un automne sans fin.",
"2015-02-04",
249,
10,
NULL),
("La civilisation du poisson rouge : Petit traité sur le marché de l'attention",
"Le poisson rouge tourne dans son bocal. Les ingénieurs de Google ont réussi à calculer la durée maximale de son attention : 8 secondes. La durée d'attention de la génération des millenials, celle qui a grandi avec les écrans connectés : 9 secondes.",
"2019-04-10",
167,
11,
NULL)
;