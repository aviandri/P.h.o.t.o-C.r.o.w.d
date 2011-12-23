# --- !Ups

create table Gallery (
    id bigint not null auto_increment,
    date_created datetime not null,
    date_updated datetime not null,
    description varchar(255),
    end_date date,
    hashtag varchar(255) not null,
    last_page integer,
    location varchar(255),
    max_id bigint,
    name varchar(255) not null,
    start_date date,
    state varchar(255) not null,
    stop_id bigint not null,
    user_id bigint,
    primary key (id)
);

create table Photo (
    id bigint not null auto_increment,
    date_created datetime not null,
    date_updated datetime not null,
    full_image_url varchar(500),
    thumb_image_url varchar(500),
    message varchar(255),
    gallery_id bigint,
    poster_id bigint not null,
    reference_id bigint not null,
    primary key (id)
);

create table User (
    id bigint not null auto_increment,
    date_created datetime not null,
    date_updated datetime not null,
    access_token varchar(255),
    profile_image_bigger_url varchar(255),
    profile_image_mini_url varchar(255),
    profile_image_original_url varchar(255),
    profile_image_url varchar(255),
    secret_token varchar(255),
    twitter_id bigint not null unique,
    username varchar(255) not null unique,
    primary key (id)
);

alter table Gallery 
    add index FK57850F3247140EFE (user_id), 
    add constraint FK57850F3247140EFE 
    foreign key (user_id) 
    references User (id);

alter table Photo 
    add index FK4984E12C78F763C (poster_id), 
    add constraint FK4984E12C78F763C 
    foreign key (poster_id) 
    references User (id);

alter table Photo 
    add index FK4984E12500560D6 (gallery_id), 
    add constraint FK4984E12500560D6 
    foreign key (gallery_id) 
    references Gallery (id);
    
# ---- !Downs

alter table Gallery 
    drop 
    foreign key FK57850F3247140EFE;

alter table Photo 
    drop 
    foreign key FK4984E12C78F763C;

alter table Photo 
    drop 
    foreign key FK4984E12500560D6;

drop table if exists Gallery;

drop table if exists Photo;

drop table if exists User;

