-- begin CUBABI_BI_REPORT
create table CUBABI_BI_REPORT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CODE varchar(255),
    DESCRIPTION varchar(255),
    REPORT_TYPE integer not null,
    REPORT_PATH varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end CUBABI_BI_REPORT
-- begin CUBABI_BI_REPORT_ROLE
create table CUBABI_BI_REPORT_ROLE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    REPORT_ID uniqueidentifier,
    ROLE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end CUBABI_BI_REPORT_ROLE
