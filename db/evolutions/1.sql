# --- !Ups

create table User (
    id bigint not null auto_increment,
    date_created datetime not null,
    date_updated datetime not null,
    access_token varchar(255) not null,
    profile_image_bigger_url varchar(255),
    profile_image_mini_url varchar(255),
    profile_image_original_url varchar(255),
    profile_image_url varchar(255),
    secret_token varchar(255) not null,
    twitter_id bigint not null,
    username varchar(255) not null unique,
    primary key (id)
);

create table Gallery (
    id bigint not null auto_increment,
    date_created datetime not null,
    date_updated datetime not null,
    description varchar(255),
    end_date date,
    hashtag varchar(255) not null,
    last_id bigint not null,
    location varchar(255),
    name varchar(255) not null,
    start_date date,
    state bit not null,
    user_id bigint,
    primary key (id)
);

alter table Gallery 
    add index FK57850F3247140EFE (user_id), 
    add constraint FK57850F3247140EFE 
    foreign key (user_id) 
    references User (id);

create table Photo (
    id bigint not null auto_increment,
    date_created datetime not null,
    date_updated datetime not null,
    full_image_url varchar(255),
    poster_user_name varchar(255),
    thumb_image_url varchar(255),
    tweet_content varchar(255),
    gallery_id bigint,
    primary key (id)
);
    
# ---- !Downs

alter table Photo 
    drop 
    foreign key FK4984E12500560D6;

drop table if exists Photo;

alter table Gallery 
    drop foreign key FK57850F3247140EFE;

drop table if exists Gallery;

drop table if exists User;

