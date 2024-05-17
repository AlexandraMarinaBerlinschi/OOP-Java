CREATE TABLE `demo`.`destination` (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      tara VARCHAR(255),
                                      tip_atractie VARCHAR(255),
                                      activitati VARCHAR(255),
                                      nume_destinatie VARCHAR(255) NOT NULL
);

CREATE TABLE `demo`.`tourist_package` (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          nume VARCHAR(255) NOT NULL,
                                          pret DECIMAL(10, 2) NOT NULL,
                                          durata VARCHAR(255),
                                          rating FLOAT DEFAULT 0,
                                          nr_persoane INT,
                                          destinatie_id INT,
                                          FOREIGN KEY (destinatie_id) REFERENCES destination(id) ON DELETE CASCADE
);

CREATE TABLE `demo`.`reviews` (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 text TEXT,
                                 rating DOUBLE NOT NULL,
                                 username VARCHAR(255) NOT NULL,
                                 date DATE NOT NULL,
                                 package_id INT,
                                 FOREIGN KEY (package_id) REFERENCES tourist_package(id) ON DELETE CASCADE
);

CREATE TABLE `demo`.`user` (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               username VARCHAR(255) NOT NULL UNIQUE,
                               password VARCHAR(255) NOT NULL,
                               type ENUM('admin', 'normal') NOT NULL,
                               redeem_code VARCHAR(255),
                               cod_unic VARCHAR(255),
                               referinte INT DEFAULT 0,
                               anulare_gratuita BOOLEAN DEFAULT FALSE,
                               discount_procentaj INT DEFAULT 0
);

CREATE TABLE `demo`.`user_packages` (
                                        user_id INT,
                                        package_id INT,
                                        PRIMARY KEY (user_id, package_id),
                                        FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                                        FOREIGN KEY (package_id) REFERENCES tourist_package(id) ON DELETE CASCADE
);


