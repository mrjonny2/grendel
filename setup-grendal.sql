/* full drop-and-create script */
CREATE DATABASE IF NOT EXISTS grendel;
use grendel
alter table documents drop foreign key FK_DOCUMENT_TO_OWNER;
alter table links drop foreign key FK_LINK_TO_USER;
alter table links drop foreign key FK_LINK_TO_DOCUMENT;
drop table if exists documents;
drop table if exists links;
drop table if exists users;
create table documents (name varchar(255) not null, body longblob not null, content_type varchar(40) not null, created_at datetime not null, modified_at datetime not null, version bigint not null, owner_id varchar(255) not null, primary key (name, owner_id)) ENGINE=InnoDB;
create table links (user_id varchar(255) not null, document_name varchar(255) not null, document_owner_id varchar(255) not null, primary key (user_id, document_name, document_owner_id)) ENGINE=InnoDB;
create table users (id varchar(255) not null, created_at datetime not null, keyset longblob not null, modified_at datetime not null, version bigint not null, primary key (id)) ENGINE=InnoDB;
alter table documents add index FK_DOCUMENT_TO_OWNER (owner_id), add constraint FK_DOCUMENT_TO_OWNER foreign key (owner_id) references users (id) on delete cascade;
alter table links add index FK_LINK_TO_USER (user_id), add constraint FK_LINK_TO_USER foreign key (user_id) references users (id);
alter table links add index FK_LINK_TO_DOCUMENT (document_name, document_owner_id), add constraint FK_LINK_TO_DOCUMENT foreign key (document_name, document_owner_id) references documents (name, owner_id);
