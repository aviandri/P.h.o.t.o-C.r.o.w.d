# --- !Ups

alter table User 
    add column profileImageMiniUrl varchar(255);

alter table User 
    add column profileImageBiggerUrl varchar(255);
    
alter table User 
    add column profileImageOrginalUrl varchar(255);

# ---- !Downs

alter table User drop column profileImageOrginalUrl;

alter table User drop column profileImageBiggerUrl;

alter table User drop column profileImageMiniUrl;
