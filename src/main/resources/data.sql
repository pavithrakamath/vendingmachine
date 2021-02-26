DROP TABLE IF EXISTS VENDING_MACHINE;

CREATE TABLE VENDING_MACHINE(
id varchar(250) PRIMARY KEY,
five_rupee_count int,
ten_rupee_count int,
twenty_rupee_count int
);
CREATE TABLE ITEM(
id varchar(250) PRIMARY KEY,
name varchar(250),
price int
);

CREATE TABLE SLOT(
id varchar(250) PRIMARY KEY,
vending_machine_id  varchar(250),
position int,
item_id varchar(250),
quantity int
);

Alter table slot add foreign key (vending_machine_id) references VENDING_MACHINE(id);
Alter table slot add foreign key (item_id) references ITEM(id);

INSERT INTO VENDING_MACHINE(id, five_rupee_count, ten_rupee_count, twenty_rupee_count)
VALUES
('v1', 1,1,1),
('v2', 0,2,1);

INSERT INTO ITEM(id, name,price)
VALUES
('1', 'item1', 10),
('asdas-asd-qw', 'item3', 15),
('lkfdsa3-234d-1123', 'item4', 20),
('2', 'item2',5);

INSERT INTO SLOT (id, vending_machine_id, position, item_id, quantity)
VALUES
('s1','v1',1,'1',10),
('s2','v1',2,'2',3),
('s1','v2',1,'lkfdsa3-234d-1123',10);