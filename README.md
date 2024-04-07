Tipuri de obiecte:

Interfete
    - IUser ( interfata pentru Admin si User)
    
Alte Pachete :

    1.user
        1.1. Admin
        1.2. NormalUser
        1.3. User
    2.tourism
        2.1. TouristPackage
        2.2. Destination
    3.reviewSystem
        3.1. Review
    4.services
        4.1.UserService.java
        4.2.TourismService.java
        4.3.ReviewService.java
    5.dao
        5.1.AdminDao.java
        5.2.NormalUserDao.java
        5.3.ReviewDao.java
        5.4.TouristPackageDao.java
    6.daoservices
        6.1.ReviewRepositoryService.java
        6.2.TourismRepositoryService.java
        6.3.UserRepositoryService.java
    Main

Actiuni/Interogari

1.Separarea utilizatorilor in 2 categorii : Admin si User. Un admin are functionalitati exclusive dar si cateva din functionalitatile userului. Un user are functionalitati obisnuite.

2.Se va implementa un sistem de autentificare si inregistrare pentru a identifica rolul utilizatorului

3.Adminul poate adauga/sterge/actualiza un pachet turistic

4.Utilizatorii inregistrati pot cere un raport al contului 

5.Utilizatorii pot filtra pachetele turistice dupa pret/rating

6.Utilizatorii pot cauta un pachet turistic dupa nume/disponibilitate/destinatie, ulterior pot selecta un pachet turistic pentru afisarea detaliilor

7.Utilizatorii inregistrati pot rezerva un pachet turistic

8.Adminul poate genera un raport cu vanzarile de pachete turistice

9.Sistem de recenzii si evaluari pentru pachetele turistice. Un user poate adauga/sterge/actualiza o recenzie personala.

10.Sistem de promotii personalizate : pentru utilizatorii care au optat pentru mai multe pachete in cadrul platformei

11.Sistem de redeem code : fiecare user va avea un cod personal si unic. Cand un user nou se va inregistra, acesta va avea ocazia sa completeze cu un cod de redeem al altui user. Cand un user va atinge un prag de utilizatori noi adusi, va beneficia de beneficii ( reduceri, anulare gratuita, etc )

12.Un user isi poate sterge/actualiza contul ( normalUser si Admin )
