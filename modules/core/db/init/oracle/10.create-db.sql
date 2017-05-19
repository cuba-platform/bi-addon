-- begin CUBABI_BI_REPORT
create table CUBABI_BI_REPORT (
    ID varchar2(32),
    VERSION number(10) not null,
    CREATE_TS timestamp,
    CREATED_BY varchar2(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar2(50),
    DELETE_TS timestamp,
    DELETED_BY varchar2(50),
    --
    NAME varchar2(255) not null,
    CODE varchar2(255),
    DESCRIPTION varchar2(255),
    REPORT_TYPE integer not null,
    REPORT_PATH varchar2(255),
    --
    primary key (ID)
)^
-- end CUBABI_BI_REPORT
-- begin CUBABI_BI_REPORT_ROLE
create table CUBABI_BI_REPORT_ROLE (
    ID varchar2(32),
    VERSION number(10) not null,
    CREATE_TS timestamp,
    CREATED_BY varchar2(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar2(50),
    DELETE_TS timestamp,
    DELETED_BY varchar2(50),
    --
    REPORT_ID varchar2(32),
    ROLE_ID varchar2(32),
    --
    primary key (ID)
)^
-- end CUBABI_BI_REPORT_ROLE
