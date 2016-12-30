-- begin CUBABI_BI_REPORT
create table CUBABI_BI_REPORT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CODE varchar(255),
    DESCRIPTION varchar(255),
    REPORT_TYPE integer not null,
    REPORT_PATH varchar(255),
    --
    primary key (ID)
)^
-- end CUBABI_BI_REPORT
-- begin CUBABI_BI_REPORT_ROLE
create table CUBABI_BI_REPORT_ROLE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    REPORT_ID varchar(36),
    ROLE_ID varchar(36),
    --
    primary key (ID)
)^
-- end CUBABI_BI_REPORT_ROLE
