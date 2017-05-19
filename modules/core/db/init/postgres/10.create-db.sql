-- begin CUBABI_BI_REPORT
create table CUBABI_BI_REPORT (
    ID uuid,
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
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    REPORT_ID uuid,
    ROLE_ID uuid,
    --
    primary key (ID)
)^
-- end CUBABI_BI_REPORT_ROLE
