# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product (
  ean                       varchar(255) not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_product primary key (ean))
;

create table tag (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_tag primary key (id))
;


create table product_tag (
  product_ean                    varchar(255) not null,
  tag_id                         integer not null,
  constraint pk_product_tag primary key (product_ean, tag_id))
;
create sequence product_seq;

create sequence tag_seq;




alter table product_tag add constraint fk_product_tag_product_01 foreign key (product_ean) references product (ean) on delete restrict on update restrict;

alter table product_tag add constraint fk_product_tag_tag_02 foreign key (tag_id) references tag (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists product;

drop table if exists product_tag;

drop table if exists tag;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists product_seq;

drop sequence if exists tag_seq;

