ALTER TABLE USERSESSIONS DROP CONSTRAINT FK_SELECTEDLINE;
drop table ORDER_DETAILS;
drop table ORDERS;
drop table USERSESSIONS;
drop table PRODUCTS;

create table Products
(
    CODE        varchar(15) not null,
    TYPE        varchar(20) not null,
    COLOR       varchar(5),
    HEIGHT      char(2)     not null,
    DESCRIPTION varchar(60) not null,
    CONSTRAINT PK_PRODUCT PRIMARY KEY (CODE)
);

create table UserSessions
(
    ID       BIGINT PRIMARY KEY,
    LANGUAGE VARCHAR(3) DEFAULT 'ENG',
    TYPE     VARCHAR(20),
    COLOR    VARCHAR(5),
    HEIGHT   CHAR(2),
    SELECTEDLINE INT,
    MAKING_ORDER BOOLEAN DEFAULT FALSE,
    ACTION varchar(10) DEFAULT 'NOTHING',
    VERIFICATION_CODE INT DEFAULT 0,
    WAITING_VERIFICATION BOOLEAN DEFAULT FALSE
);

create table Orders
(
    USERID     BIGINT,
    ORDER_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ORDER_ID   INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    CONSTRAINT FK_USER_ID FOREIGN KEY (USERID) REFERENCES USERSESSIONS (ID)
);

CREATE TABLE ORDER_DETAILS
(
    ORDER_DETAIL_ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    ORDER_ID        INT,
    CODE          VARCHAR(15),
    QUANTITA        INT,
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ORDER_ID),
    FOREIGN KEY (CODE) REFERENCES PRODUCTS (CODE)
);

ALTER TABLE USERSESSIONS ADD CONSTRAINT FK_SELECTEDLINE FOREIGN KEY (SELECTEDLINE) REFERENCES ORDER_DETAILS(ORDER_DETAIL_ID);


INSERT INTO PRODUCTS VALUES ('ZR9810H', 'White Matt', null, '10', 'White Matt 10mm'),
                            ('ZR9812H', 'White Matt', null, '12', 'White Matt 12mm'),
                            ('ZR9814H', 'White Matt', null, '14', 'White Matt 14mm'),
                            ('ZR9816H', 'White Matt', null, '16', 'White Matt 16mm'),
                            ('ZR9818H', 'White Matt', null, '18', 'White Matt 18mm'),
                            ('ZR9820H', 'White Matt', null, '20', 'White Matt 20mm'),
                            ('ZR9822H', 'White Matt', null, '22', 'White Matt 22mm'),
                            ('ZR9825H', 'White Matt', null, '25', 'White Matt 25mm');

INSERT INTO PRODUCTS VALUES ('ZR9810T', 'High Translucent', null, '10', 'High Translucent 10mm'),
                            ('ZR9812T', 'High Translucent', null, '12', 'High Translucent 12mm'),
                            ('ZR9814T', 'High Translucent', null, '14', 'High Translucent 14mm'),
                            ('ZR9816T', 'High Translucent', null, '16', 'High Translucent 16mm'),
                            ('ZR9818T', 'High Translucent', null, '18', 'High Translucent 18mm'),
                            ('ZR9820T', 'High Translucent', null, '20', 'High Translucent 20mm'),
                            ('ZR9822T', 'High Translucent', null, '22', 'High Translucent 22mm'),
                            ('ZR9825T', 'High Translucent', null, '25', 'High Translucent 25mm');

INSERT INTO PRODUCTS VALUES ('ZR9810T4', 'Bleach', null, '10', 'Bleach 10mm'),
                            ('ZR9812T4', 'Bleach', null, '12', 'Bleach 12mm'),
                            ('ZR9814T4', 'Bleach', null, '14', 'Bleach 14mm'),
                            ('ZR9816T4', 'Bleach', null, '16', 'Bleach 16mm'),
                            ('ZR9818T4', 'Bleach', null, '18', 'Bleach 18mm'),
                            ('ZR9820T4', 'Bleach', null, '20', 'Bleach 20mm'),
                            ('ZR9822T4', 'Bleach', null, '22', 'Bleach 22mm'),
                            ('ZR9825T4', 'Bleach', null, '25', 'Bleach 25mm');

INSERT INTO PRODUCTS VALUES ('ZR9810G1200', 'Gold', null, '10', 'Gold 10mm'),
                            ('ZR9812G1200', 'Gold', null, '12', 'Gold 12mm'),
                            ('ZR9814G1200', 'Gold', null, '14', 'Gold 14mm'),
                            ('ZR9816G1200', 'Gold', null, '16', 'Gold 16mm'),
                            ('ZR9818G1200', 'Gold', null, '18', 'Gold 18mm'),
                            ('ZR9820G1200', 'Gold', null, '20', 'Gold 20mm'),
                            ('ZR9822G1200', 'Gold', null, '22', 'Gold 22mm'),
                            ('ZR9825G1200', 'Gold', null, '25', 'Gold 25mm');


INSERT INTO PRODUCTS VALUES ('ZR9814UMSA1', 'Venus', 'A1', '14', 'Venus A1 14mm'),
                            ('ZR9816UMSA1', 'Venus', 'A1', '16', 'Venus A1 16mm'),
                            ('ZR9818UMSA1', 'Venus', 'A1', '18', 'Venus A1 18mm'),

                            ('ZR9814UMSA2', 'Venus', 'A2', '14', 'Venus A2 14mm'),
                            ('ZR9816UMSA2', 'Venus', 'A2', '16', 'Venus A2 16mm'),
                            ('ZR9818UMSA2', 'Venus', 'A2', '18', 'Venus A2 18mm'),

                            ('ZR9814UMSA3', 'Venus', 'A3', '14', 'Venus A3 14mm'),
                            ('ZR9816UMSA3', 'Venus', 'A3', '16', 'Venus A3 16mm'),
                            ('ZR9818UMSA3', 'Venus', 'A3', '18', 'Venus A3 18mm');

INSERT INTO PRODUCTS VALUES ('ZR9812MSA0', 'Eos', 'A0', '12', 'Eos A0 12mm'),
                            ('ZR9814MSA0', 'Eos', 'A0', '14', 'Eos A0 14mm'),
                            ('ZR9816MSA0', 'Eos', 'A0', '16', 'Eos A0 16mm'),
                            ('ZR9818MSA0', 'Eos', 'A0', '18', 'Eos A0 18mm'),
                            ('ZR9820MSA0', 'Eos', 'A0', '20', 'Eos A0 20mm'),
                            ('ZR9822MSA0', 'Eos', 'A0', '22', 'Eos A0 22mm'),

                            ('ZR9812MSA1', 'Eos', 'A1', '12', 'Eos A1 12mm'),
                            ('ZR9814MSA1', 'Eos', 'A1', '14', 'Eos A1 14mm'),
                            ('ZR9816MSA1', 'Eos', 'A1', '16', 'Eos A1 16mm'),
                            ('ZR9818MSA1', 'Eos', 'A1', '18', 'Eos A1 18mm'),
                            ('ZR9820MSA1', 'Eos', 'A1', '20', 'Eos A1 20mm'),
                            ('ZR9822MSA1', 'Eos', 'A1', '22', 'Eos A1 22mm'),

                            ('ZR9812MSA2', 'Eos', 'A2', '12', 'Eos A2 12mm'),
                            ('ZR9814MSA2', 'Eos', 'A2', '14', 'Eos A2 14mm'),
                            ('ZR9816MSA2', 'Eos', 'A2', '16', 'Eos A2 16mm'),
                            ('ZR9818MSA2', 'Eos', 'A2', '18', 'Eos A2 18mm'),
                            ('ZR9820MSA2', 'Eos', 'A2', '20', 'Eos A2 20mm'),
                            ('ZR9822MSA2', 'Eos', 'A2', '22', 'Eos A2 22mm'),

                            ('ZR9812MSA3', 'Eos', 'A3', '12', 'Eos A3 12mm'),
                            ('ZR9814MSA3', 'Eos', 'A3', '14', 'Eos A3 14mm'),
                            ('ZR9816MSA3', 'Eos', 'A3', '16', 'Eos A3 16mm'),
                            ('ZR9818MSA3', 'Eos', 'A3', '18', 'Eos A3 18mm'),
                            ('ZR9820MSA3', 'Eos', 'A3', '20', 'Eos A3 20mm'),
                            ('ZR9822MSA3', 'Eos', 'A3', '22', 'Eos A3 22mm'),

                            ('ZR9812MSA35', 'Eos', 'A35', '12', 'Eos A35 12mm'),
                            ('ZR9814MSA35', 'Eos', 'A35', '14', 'Eos A35 14mm'),
                            ('ZR9816MSA35', 'Eos', 'A35', '16', 'Eos A35 16mm'),
                            ('ZR9818MSA35', 'Eos', 'A35', '18', 'Eos A35 18mm'),
                            ('ZR9820MSA35', 'Eos', 'A35', '20', 'Eos A35 20mm'),
                            ('ZR9822MSA35', 'Eos', 'A35', '22', 'Eos A35 22mm'),

                            ('ZR9812MSA4', 'Eos', 'A4', '12', 'Eos A4 12mm'),
                            ('ZR9814MSA4', 'Eos', 'A4', '14', 'Eos A4 14mm'),
                            ('ZR9816MSA4', 'Eos', 'A4', '16', 'Eos A4 16mm'),
                            ('ZR9818MSA4', 'Eos', 'A4', '18', 'Eos A4 18mm'),
                            ('ZR9820MSA4', 'Eos', 'A4', '20', 'Eos A4 20mm'),
                            ('ZR9822MSA4', 'Eos', 'A4', '22', 'Eos A4 22mm'),

                            ('ZR9812MSB1', 'Eos', 'B1', '12', 'Eos B1 12mm'),
                            ('ZR9814MSB1', 'Eos', 'B1', '14', 'Eos B1 14mm'),
                            ('ZR9816MSB1', 'Eos', 'B1', '16', 'Eos B1 16mm'),
                            ('ZR9818MSB1', 'Eos', 'B1', '18', 'Eos B1 18mm'),
                            ('ZR9820MSB1', 'Eos', 'B1', '20', 'Eos B1 20mm'),
                            ('ZR9822MSB1', 'Eos', 'B1', '22', 'Eos B1 22mm'),

                            ('ZR9812MSB2', 'Eos', 'B2', '12', 'Eos B2 12mm'),
                            ('ZR9814MSB2', 'Eos', 'B2', '14', 'Eos B2 14mm'),
                            ('ZR9816MSB2', 'Eos', 'B2', '16', 'Eos B2 16mm'),
                            ('ZR9818MSB2', 'Eos', 'B2', '18', 'Eos B2 18mm'),
                            ('ZR9820MSB2', 'Eos', 'B2', '20', 'Eos B2 20mm'),
                            ('ZR9822MSB2', 'Eos', 'B2', '22', 'Eos B2 22mm'),

                            ('ZR9812MSB3', 'Eos', 'B3', '12', 'Eos B3 12mm'),
                            ('ZR9814MSB3', 'Eos', 'B3', '14', 'Eos B3 14mm'),
                            ('ZR9816MSB3', 'Eos', 'B3', '16', 'Eos B3 16mm'),
                            ('ZR9818MSB3', 'Eos', 'B3', '18', 'Eos B3 18mm'),
                            ('ZR9820MSB3', 'Eos', 'B3', '20', 'Eos B3 20mm'),
                            ('ZR9822MSB3', 'Eos', 'B3', '22', 'Eos B3 22mm'),

                            ('ZR9812MSC1', 'Eos', 'C1', '12', 'Eos C1 12mm'),
                            ('ZR9814MSC1', 'Eos', 'C1', '14', 'Eos C1 14mm'),
                            ('ZR9816MSC1', 'Eos', 'C1', '16', 'Eos C1 16mm'),
                            ('ZR9818MSC1', 'Eos', 'C1', '18', 'Eos C1 18mm'),
                            ('ZR9820MSC1', 'Eos', 'C1', '20', 'Eos C1 20mm'),
                            ('ZR9822MSC1', 'Eos', 'C1', '22', 'Eos C1 22mm'),

                            ('ZR9812MSC2', 'Eos', 'C2', '12', 'Eos C2 12mm'),
                            ('ZR9814MSC2', 'Eos', 'C2', '14', 'Eos C2 14mm'),
                            ('ZR9816MSC2', 'Eos', 'C2', '16', 'Eos C2 16mm'),
                            ('ZR9818MSC2', 'Eos', 'C2', '18', 'Eos C2 18mm'),
                            ('ZR9820MSC2', 'Eos', 'C2', '20', 'Eos C2 20mm'),
                            ('ZR9822MSC2', 'Eos', 'C2', '22', 'Eos C2 22mm'),

                            ('ZR9812MSC3', 'Eos', 'C3', '12', 'Eos C3 12mm'),
                            ('ZR9814MSC3', 'Eos', 'C3', '14', 'Eos C3 14mm'),
                            ('ZR9816MSC3', 'Eos', 'C3', '16', 'Eos C3 16mm'),
                            ('ZR9818MSC3', 'Eos', 'C3', '18', 'Eos C3 18mm'),
                            ('ZR9820MSC3', 'Eos', 'C3', '20', 'Eos C3 20mm'),
                            ('ZR9822MSC3', 'Eos', 'C3', '22', 'Eos C3 22mm'),

                            ('ZR9812MSD2', 'Eos', 'D2', '12', 'Eos D2 12mm'),
                            ('ZR9814MSD2', 'Eos', 'D2', '14', 'Eos D2 14mm'),
                            ('ZR9816MSD2', 'Eos', 'D2', '16', 'Eos D2 16mm'),
                            ('ZR9818MSD2', 'Eos', 'D2', '18', 'Eos D2 18mm'),
                            ('ZR9820MSD2', 'Eos', 'D2', '20', 'Eos D2 20mm'),
                            ('ZR9822MSD2', 'Eos', 'D2', '22', 'Eos D2 22mm'),

                            ('ZR9812MSD3', 'Eos', 'D3', '12', 'Eos D3 12mm'),
                            ('ZR9814MSD3', 'Eos', 'D3', '14', 'Eos D3 14mm'),
                            ('ZR9816MSD3', 'Eos', 'D3', '16', 'Eos D3 16mm'),
                            ('ZR9818MSD3', 'Eos', 'D3', '18', 'Eos D3 18mm'),
                            ('ZR9820MSD3', 'Eos', 'D3', '20', 'Eos D3 20mm'),
                            ('ZR9822MSD3', 'Eos', 'D3', '22', 'Eos D3 22mm');

INSERT INTO PRODUCTS VALUES ('ZR9812MSTA0', 'Thor', 'A0', '12', 'Thor A0 12mm'),
                            ('ZR9814MSTA0', 'Thor', 'A0', '14', 'Thor A0 14mm'),
                            ('ZR9816MSTA0', 'Thor', 'A0', '16', 'Thor A0 16mm'),
                            ('ZR9818MSTA0', 'Thor', 'A0', '18', 'Thor A0 18mm'),
                            ('ZR9820MSTA0', 'Thor', 'A0', '20', 'Thor A0 20mm'),
                            ('ZR9822MSTA0', 'Thor', 'A0', '22', 'Thor A0 22mm'),
                            ('ZR9825MSTA0', 'Thor', 'A0', '25', 'Thor A0 25mm'),

                            ('ZR9812MSTA1', 'Thor', 'A1', '12', 'Thor A1 12mm'),
                            ('ZR9814MSTA1', 'Thor', 'A1', '14', 'Thor A1 14mm'),
                            ('ZR9816MSTA1', 'Thor', 'A1', '16', 'Thor A1 16mm'),
                            ('ZR9818MSTA1', 'Thor', 'A1', '18', 'Thor A1 18mm'),
                            ('ZR9820MSTA1', 'Thor', 'A1', '20', 'Thor A1 20mm'),
                            ('ZR9822MSTA1', 'Thor', 'A1', '22', 'Thor A1 22mm'),
                            ('ZR9825MSTA1', 'Thor', 'A1', '25', 'Thor A1 25mm'),

                            ('ZR9812MSTA2', 'Thor', 'A2', '12', 'Thor A2 12mm'),
                            ('ZR9814MSTA2', 'Thor', 'A2', '14', 'Thor A2 14mm'),
                            ('ZR9816MSTA2', 'Thor', 'A2', '16', 'Thor A2 16mm'),
                            ('ZR9818MSTA2', 'Thor', 'A2', '18', 'Thor A2 18mm'),
                            ('ZR9820MSTA2', 'Thor', 'A2', '20', 'Thor A2 20mm'),
                            ('ZR9822MSTA2', 'Thor', 'A2', '22', 'Thor A2 22mm'),
                            ('ZR9825MSTA2', 'Thor', 'A2', '25', 'Thor A2 25mm'),

                            ('ZR9812MSTA3', 'Thor', 'A3', '12', 'Thor A3 12mm'),
                            ('ZR9814MSTA3', 'Thor', 'A3', '14', 'Thor A3 14mm'),
                            ('ZR9816MSTA3', 'Thor', 'A3', '16', 'Thor A3 16mm'),
                            ('ZR9818MSTA3', 'Thor', 'A3', '18', 'Thor A3 18mm'),
                            ('ZR9820MSTA3', 'Thor', 'A3', '20', 'Thor A3 20mm'),
                            ('ZR9822MSTA3', 'Thor', 'A3', '22', 'Thor A3 22mm'),
                            ('ZR9825MSTA3', 'Thor', 'A3', '25', 'Thor A3 25mm'),

                            ('ZR9812MSTA35', 'Thor', 'A35', '12', 'Thor A35 12mm'),
                            ('ZR9814MSTA35', 'Thor', 'A35', '14', 'Thor A35 14mm'),
                            ('ZR9816MSTA35', 'Thor', 'A35', '16', 'Thor A35 16mm'),
                            ('ZR9818MSTA35', 'Thor', 'A35', '18', 'Thor A35 18mm'),
                            ('ZR9820MSTA35', 'Thor', 'A35', '20', 'Thor A35 20mm'),
                            ('ZR9822MSTA35', 'Thor', 'A35', '22', 'Thor A35 22mm'),
                            ('ZR9825MSTA35', 'Thor', 'A35', '25', 'Thor A35 25mm'),

                            ('ZR9812MSTA4', 'Thor', 'A4', '12', 'Thor A4 12mm'),
                            ('ZR9814MSTA4', 'Thor', 'A4', '14', 'Thor A4 14mm'),
                            ('ZR9816MSTA4', 'Thor', 'A4', '16', 'Thor A4 16mm'),
                            ('ZR9818MSTA4', 'Thor', 'A4', '18', 'Thor A4 18mm'),
                            ('ZR9820MSTA4', 'Thor', 'A4', '20', 'Thor A4 20mm'),
                            ('ZR9822MSTA4', 'Thor', 'A4', '22', 'Thor A4 22mm'),
                            ('ZR9825MSTA4', 'Thor', 'A4', '25', 'Thor A4 25mm'),

                            ('ZR9812MSTB1', 'Thor', 'B1', '12', 'Thor B1 12mm'),
                            ('ZR9814MSTB1', 'Thor', 'B1', '14', 'Thor B1 14mm'),
                            ('ZR9816MSTB1', 'Thor', 'B1', '16', 'Thor B1 16mm'),
                            ('ZR9818MSTB1', 'Thor', 'B1', '18', 'Thor B1 18mm'),
                            ('ZR9820MSTB1', 'Thor', 'B1', '20', 'Thor B1 20mm'),
                            ('ZR9822MSTB1', 'Thor', 'B1', '22', 'Thor B1 22mm'),
                            ('ZR9825MSTB1', 'Thor', 'B1', '25', 'Thor B1 25mm'),

                            ('ZR9812MSTB2', 'Thor', 'B2', '12', 'Thor B2 12mm'),
                            ('ZR9814MSTB2', 'Thor', 'B2', '14', 'Thor B2 14mm'),
                            ('ZR9816MSTB2', 'Thor', 'B2', '16', 'Thor B2 16mm'),
                            ('ZR9818MSTB2', 'Thor', 'B2', '18', 'Thor B2 18mm'),
                            ('ZR9820MSTB2', 'Thor', 'B2', '20', 'Thor B2 20mm'),
                            ('ZR9822MSTB2', 'Thor', 'B2', '22', 'Thor B2 22mm'),
                            ('ZR9825MSTB2', 'Thor', 'B2', '25', 'Thor B2 25mm'),

                            ('ZR9812MSTB3', 'Thor', 'B3', '12', 'Thor B3 12mm'),
                            ('ZR9814MSTB3', 'Thor', 'B3', '14', 'Thor B3 14mm'),
                            ('ZR9816MSTB3', 'Thor', 'B3', '16', 'Thor B3 16mm'),
                            ('ZR9818MSTB3', 'Thor', 'B3', '18', 'Thor B3 18mm'),
                            ('ZR9820MSTB3', 'Thor', 'B3', '20', 'Thor B3 20mm'),
                            ('ZR9822MSTB3', 'Thor', 'B3', '22', 'Thor B3 22mm'),
                            ('ZR9825MSTB3', 'Thor', 'B3', '25', 'Thor B3 25mm'),

                            ('ZR9812MSTC1', 'Thor', 'C1', '12', 'Thor C1 12mm'),
                            ('ZR9814MSTC1', 'Thor', 'C1', '14', 'Thor C1 14mm'),
                            ('ZR9816MSTC1', 'Thor', 'C1', '16', 'Thor C1 16mm'),
                            ('ZR9818MSTC1', 'Thor', 'C1', '18', 'Thor C1 18mm'),
                            ('ZR9820MSTC1', 'Thor', 'C1', '20', 'Thor C1 20mm'),
                            ('ZR9822MSTC1', 'Thor', 'C1', '22', 'Thor C1 22mm'),
                            ('ZR9825MSTC1', 'Thor', 'C1', '25', 'Thor C1 25mm'),

                            ('ZR9812MSTC2', 'Thor', 'C2', '12', 'Thor C2 12mm'),
                            ('ZR9814MSTC2', 'Thor', 'C2', '14', 'Thor C2 14mm'),
                            ('ZR9816MSTC2', 'Thor', 'C2', '16', 'Thor C2 16mm'),
                            ('ZR9818MSTC2', 'Thor', 'C2', '18', 'Thor C2 18mm'),
                            ('ZR9820MSTC2', 'Thor', 'C2', '20', 'Thor C2 20mm'),
                            ('ZR9822MSTC2', 'Thor', 'C2', '22', 'Thor C2 22mm'),
                            ('ZR9825MSTC2', 'Thor', 'C2', '25', 'Thor C2 25mm'),

                            ('ZR9812MSTC3', 'Thor', 'C3', '12', 'Thor C3 12mm'),
                            ('ZR9814MSTC3', 'Thor', 'C3', '14', 'Thor C3 14mm'),
                            ('ZR9816MSTC3', 'Thor', 'C3', '16', 'Thor C3 16mm'),
                            ('ZR9818MSTC3', 'Thor', 'C3', '18', 'Thor C3 18mm'),
                            ('ZR9820MSTC3', 'Thor', 'C3', '20', 'Thor C3 20mm'),
                            ('ZR9822MSTC3', 'Thor', 'C3', '22', 'Thor C3 22mm'),
                            ('ZR9825MSTC3', 'Thor', 'C3', '25', 'Thor C3 25mm'),

                            ('ZR9812MSTD2', 'Thor', 'D2', '12', 'Thor D2 12mm'),
                            ('ZR9814MSTD2', 'Thor', 'D2', '14', 'Thor D2 14mm'),
                            ('ZR9816MSTD2', 'Thor', 'D2', '16', 'Thor D2 16mm'),
                            ('ZR9818MSTD2', 'Thor', 'D2', '18', 'Thor D2 18mm'),
                            ('ZR9820MSTD2', 'Thor', 'D2', '20', 'Thor D2 20mm'),
                            ('ZR9822MSTD2', 'Thor', 'D2', '22', 'Thor D2 22mm'),
                            ('ZR9825MSTD2', 'Thor', 'D2', '25', 'Thor D2 25mm'),

                            ('ZR9812MSTD3', 'Thor', 'D3', '12', 'Thor D3 12mm'),
                            ('ZR9814MSTD3', 'Thor', 'D3', '14', 'Thor D3 14mm'),
                            ('ZR9816MSTD3', 'Thor', 'D3', '16', 'Thor D3 16mm'),
                            ('ZR9818MSTD3', 'Thor', 'D3', '18', 'Thor D3 18mm'),
                            ('ZR9820MSTD3', 'Thor', 'D3', '20', 'Thor D3 20mm'),
                            ('ZR9822MSTD3', 'Thor', 'D3', '22', 'Thor D3 22mm'),
                            ('ZR9825MSTD3', 'Thor', 'D3', '25', 'Thor D3 25mm');

INSERT INTO PRODUCTS VALUES ('ZR9810TA1', 'Preshaded', 'A1', '10', 'Preshaded A1 10mm'),
                            ('ZR9812TA1', 'Preshaded', 'A1', '12', 'Preshaded A1 12mm'),
                            ('ZR9814TA1', 'Preshaded', 'A1', '14', 'Preshaded A1 14mm'),
                            ('ZR9816TA1', 'Preshaded', 'A1', '16', 'Preshaded A1 16mm'),
                            ('ZR9818TA1', 'Preshaded', 'A1', '18', 'Preshaded A1 18mm'),
                            ('ZR9820TA1', 'Preshaded', 'A1', '20', 'Preshaded A1 20mm'),
                            ('ZR9822TA1', 'Preshaded', 'A1', '22', 'Preshaded A1 22mm'),
                            ('ZR9825TA1', 'Preshaded', 'A1', '25', 'Preshaded A1 25mm'),

                            ('ZR9810TA2', 'Preshaded', 'A2', '10', 'Preshaded A2 10mm'),
                            ('ZR9812TA2', 'Preshaded', 'A2', '12', 'Preshaded A2 12mm'),
                            ('ZR9814TA2', 'Preshaded', 'A2', '14', 'Preshaded A2 14mm'),
                            ('ZR9816TA2', 'Preshaded', 'A2', '16', 'Preshaded A2 16mm'),
                            ('ZR9818TA2', 'Preshaded', 'A2', '18', 'Preshaded A2 18mm'),
                            ('ZR9820TA2', 'Preshaded', 'A2', '20', 'Preshaded A2 20mm'),
                            ('ZR9822TA2', 'Preshaded', 'A2', '22', 'Preshaded A2 22mm'),
                            ('ZR9825TA2', 'Preshaded', 'A2', '25', 'Preshaded A2 25mm'),

                            ('ZR9810TA3', 'Preshaded', 'A3', '10', 'Preshaded A3 10mm'),
                            ('ZR9812TA3', 'Preshaded', 'A3', '12', 'Preshaded A3 12mm'),
                            ('ZR9814TA3', 'Preshaded', 'A3', '14', 'Preshaded A3 14mm'),
                            ('ZR9816TA3', 'Preshaded', 'A3', '16', 'Preshaded A3 16mm'),
                            ('ZR9818TA3', 'Preshaded', 'A3', '18', 'Preshaded A3 18mm'),
                            ('ZR9820TA3', 'Preshaded', 'A3', '20', 'Preshaded A3 20mm'),
                            ('ZR9822TA3', 'Preshaded', 'A3', '22', 'Preshaded A3 22mm'),
                            ('ZR9825TA3', 'Preshaded', 'A3', '25', 'Preshaded A3 25mm'),

                            ('ZR9814TB2', 'Preshaded', 'B2', '14', 'Preshaded B2 14mm'),
                            ('ZR9818TB2', 'Preshaded', 'B2', '18', 'Preshaded B2 18mm'),

                            ('ZR9814TC2', 'Preshaded', 'C2', '14', 'Preshaded C2 14mm'),
                            ('ZR9818TC2', 'Preshaded', 'C2', '18', 'Preshaded C2 18mm'),

                            ('ZR9814TD2', 'Preshaded', 'D2', '14', 'Preshaded D2 14mm'),
                            ('ZR9818TD2', 'Preshaded', 'D2', '18', 'Preshaded D2 18mm');