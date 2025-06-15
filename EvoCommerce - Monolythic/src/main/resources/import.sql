DROP TABLE IF EXISTS sub_category;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS category;


-- Admin Initializer
CREATE TABLE admin (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    permission VARCHAR(255)
);

INSERT INTO admin (username,password) VALUES ('Admin1', '$2a$12$2F.Ic/b9uZtCudbNo4ZfSeP0cpEVaouWbJVanbpBE8/sEenSALkMm');

-- Category Initializer
CREATE TABLE category (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);


INSERT INTO category (name) VALUES ('Electronics'),('Cloths'),('Footwear');


CREATE TABLE sub_category (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);


INSERT INTO sub_category (name, category_id) VALUES('Laptops', 1),('Mobiles', 1),('Men', 2),('Women', 2),('Kids', 2),('Men', 3),('Women', 3),('Kids', 3);