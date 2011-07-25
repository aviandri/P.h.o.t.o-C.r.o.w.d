# --- !Ups

create table User (
    id bigint not null auto_increment,
    dateCreated datetime not null,
    dateUpdated datetime not null,
    accessToken varchar(255) not null,
    profileImageUrl varchar(255),
    secretToken varchar(255) not null,
    twitterId bigint not null,
    username varchar(255) not null,
    primary key (id)
);

# ---- !Downs

drop table if exists User;
