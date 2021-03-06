CREATE SEQUENCE SEQ_ADDITIONAL_DETAIL START WITH 1 INCREMENT BY 1;
ALTER TABLE ADDITIONAL_DETAIL ADD (
  ID NUMBER(*)
);

UPDATE ADDITIONAL_DETAIL SET ID = SEQ_ADDITIONAL_DETAIL.nextval;
COMMIT;

ALTER TABLE ADDITIONAL_DETAIL ADD CONSTRAINT PK_04 PRIMARY KEY (ID);

DROP SEQUENCE SEQ_ADDITIONAL_DETAIL;
CREATE SEQUENCE SEQ_ADDITIONAL_DETAIL START WITH 1 INCREMENT BY 10;
SELECT SEQ_ADDITIONAL_DETAIL.nextval FROM dual;
SELECT SEQ_ADDITIONAL_DETAIL.nextval FROM dual;