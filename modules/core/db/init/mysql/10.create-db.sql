-- begin CUBABI_BI_REPORT
create table CUBABI_BI_REPORT (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
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
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    REPORT_ID varchar(32),
    ROLE_ID varchar(32),
    --
    primary key (ID)
)^
-- end CUBABI_BI_REPORT_ROLE
