INSERT INTO ITEM (ID, NAME, DESCRIPTION, LIFECYCLE, MAINTENANCE_DATE) VALUES (1, 'Fridge', 'A device for keeping produts fresh', 160, trunc(sysdate));
INSERT INTO ITEM (ID, NAME, DESCRIPTION, LIFECYCLE, MAINTENANCE_DATE) VALUES (2, 'Conditioner', 'With it an air will be always clean', 200, trunc(sysdate));
INSERT INTO ITEM (ID, NAME, DESCRIPTION, LIFECYCLE, MAINTENANCE_DATE) VALUES (3, 'Washing machine', 'Just saves a lot of time', 300, trunc(sysdate));
INSERT INTO ITEM (ID, NAME, DESCRIPTION, LIFECYCLE, MAINTENANCE_DATE) VALUES (4, 'Shaver', null, 360, trunc(sysdate));

INSERT INTO ITEM_INFO(ITEM, INFO) VALUES (1, 'INFO');
INSERT INTO ITEM_INFO(ITEM, INFO) VALUES (1, 'PHOTO');
INSERT INTO ITEM_INFO(ITEM, INFO) VALUES (3, 'INFO');
INSERT INTO ITEM_INFO(ITEM, INFO) VALUES (3, 'PHOTO');
INSERT INTO ITEM_INFO(ITEM, INFO) VALUES (1, 'MONEY');
INSERT INTO ITEM_INFO(ITEM, INFO) VALUES (2, 'MONEY');

INSERT INTO ITEM_ADVANCED (ITEM, IMAGE, PARENT, SPECIALIST_COMPANY, SPECIALIST_EMAIL, SPECIALIST_PHONE, SPECIALIST_COST)
VALUES (1, EMPTY_BLOB(), NULL, 'Samsung', 'john@samsung.com', '445461512', 25000);
INSERT INTO ADDITIONAL_DETAIL (ID, ITEM_ADVANCED, NAME, DESCRIPTION, COST)
VALUES (1, 1, 'Engine', 'Main unit of any refrigerator', 600000);

COMMIT ;