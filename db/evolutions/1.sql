# --- !Ups

create table User (
    id bigint not null auto_increment,
    dateCreated datetime not null,
    dateUpdated datetime not null,
    accessToken varchar(255) not null,
    profileImageBiggerUrl varchar(255),
    profileImageMiniUrl varchar(255),
    profileImageOriginalUrl varchar(255),
    profileImageUrl varchar(255),
    secretToken varchar(255) not null,
    twitterId bigint not null,
    username varchar(255) not null unique,
    primary key (id)
);

# ---- !Downs

drop table if exists User;
