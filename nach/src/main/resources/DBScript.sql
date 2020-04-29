CREATE TABLE acct_req_data_in(
    UNIQUE_ID NUMBER(10) NOT NULL,
	SERVICE_NAME VARCHAR2(100) NOT NULL,
	REQ_TIMESTAMP VARCHAR2(100) NOT NULL,
	REQ_ID VARCHAR2(100) NOT NULL,
    NPCIREF_VALUE VARCHAR2(100) NOT NULL,
    REQ_CONTENT VARCHAR2(4000) NOT NULL,
	CONSTRAINT ACCT_REQ_IN_PK PRIMARY KEY (UNIQUE_ID)
);

CREATE SEQUENCE ACCT_REQ_IN_SEQ
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;
  
CREATE TABLE acct_RES_data_OUT(
    UNIQUE_ID NUMBER(10) NOT NULL,
	SERVICE_NAME VARCHAR2(100) NOT NULL,
	RES_TIMESTAMP VARCHAR2(100) NOT NULL,
	REQ_ID VARCHAR2(100) NOT NULL,
    NPCIREF_VALUE VARCHAR2(100) NOT NULL,
    RES_CONTENT VARCHAR2(4000) NOT NULL,
	CONSTRAINT ACCT_RES_OUT_PK PRIMARY KEY (UNIQUE_ID)
);

CREATE TABLE acct_req_data_OUT(
    UNIQUE_ID NUMBER(10) NOT NULL,
	SERVICE_NAME VARCHAR2(100) NOT NULL,
	REQ_TIMESTAMP VARCHAR2(100) NOT NULL,
	REQ_ID VARCHAR2(100) NOT NULL,
    NPCIREF_VALUE VARCHAR2(100) NOT NULL,
    REQ_CONTENT VARCHAR2(4000) NOT NULL,
	CONSTRAINT ACCT_REQ_OUT_PK PRIMARY KEY (UNIQUE_ID)
);

CREATE SEQUENCE ACCT_REQ_OUT_SEQ
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;
  
    CREATE TABLE acct_RES_data_IN(
    UNIQUE_ID NUMBER(10) NOT NULL,
	SERVICE_NAME VARCHAR2(100) NOT NULL,
	RES_TIMESTAMP VARCHAR2(100) NOT NULL,
	REQ_ID VARCHAR2(100) NOT NULL,
    NPCIREF_VALUE VARCHAR2(100) NOT NULL,
    RES_CONTENT VARCHAR2(4000) NOT NULL,
	CONSTRAINT ACCT_RES_IN_PK PRIMARY KEY (UNIQUE_ID)
);

CREATE TABLE PREV_IIN_LIST(
    SR_NO NUMBER(10) NOT NULL,
	PREV_IIN_NAMES VARCHAR2(100) NOT NULL);
    
CREATE SEQUENCE PREV_IIN_SEQ
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;
  
CREATE TABLE AADHAAR_REQ_DATA_OUT(
    UNIQUE_ID NUMBER(10) NOT NULL,
	SERVICE_NAME VARCHAR2(100) NOT NULL,
	REQ_TIMESTAMP VARCHAR2(100) NOT NULL,
	REQ_ID VARCHAR2(100) NOT NULL,
    REQ_CONTENT VARCHAR2(4000) NOT NULL,
	CONSTRAINT AADHAAR_REQ_OUT_PK PRIMARY KEY (UNIQUE_ID)
);

CREATE SEQUENCE AADHAAR_REQ_OUT_SEQ
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;
  
CREATE TABLE AADHAAR_RES_DATA_IN(
    UNIQUE_ID NUMBER(10) NOT NULL,
	SERVICE_NAME VARCHAR2(100) NOT NULL,
	RES_TIMESTAMP VARCHAR2(100) NOT NULL,
	REQ_ID VARCHAR2(100) NOT NULL,
    NPCIREF_VALUE VARCHAR2(100) NOT NULL,
    RES_CONTENT VARCHAR2(4000) NOT NULL,
	CONSTRAINT AADHAAR_RES_IN_PK PRIMARY KEY (UNIQUE_ID)
);

  
CREATE TABLE AADHAAR_UI_REQ_DATA(
    UNIQUE_ID NUMBER(10) NOT NULL,
    REQ_TIMESTAMP VARCHAR2(100) NOT NULL,
	AADHAAR_DTLS VARCHAR2(4000) NOT NULL,
	CONSTRAINT AADHAAR_UI_DATA_PK PRIMARY KEY (UNIQUE_ID)
);

  
CREATE SEQUENCE AADHAAR_REC_REF_SEQ
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 101
  INCREMENT BY 1;
  
CREATE SEQUENCE AADHAAR_UNIQUE_REQ_SEQ
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 10001
  INCREMENT BY 1;
  
  


  
  
