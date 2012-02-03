# --- !Ups

create table Pin (
    id bigint not null auto_increment,
    date_created datetime not null,
    date_updated datetime not null,
    gallery_id bigint,
    user_id bigint,
    primary key (id)
);

alter table Pin 
    add index FK1397547140EFE (user_id), 
    add constraint FK1397547140EFE 
    foreign key (user_id) 
    references User (id);

alter table Pin 
    add index FK13975500560D6 (gallery_id), 
    add constraint FK13975500560D6 
    foreign key (gallery_id) 
    references Gallery (id);

# ---- !Downs

alter table Pin 
    drop 
    foreign key FK1397547140EFE;

alter table Pin 
    drop 
    foreign key FK13975500560D6;
    
drop table if exists Pin;