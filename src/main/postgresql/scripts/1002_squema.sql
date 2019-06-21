CREATE SEQUENCE gendat START 100000;
CREATE SEQUENCE gencfg START 1000;

-- user profiles
create table profiles (
	id bigint not null,
	description varchar(128) not null,
	
	constraint pk_profiles primary key(id),
	constraint uk_profiles_description unique (description)	
);
	
-- user rights
create table rights (
	id bigint not null,
	code varchar(128),
	description varchar(128) not null,
	
	constraint pk_rights primary key(id),
	constraint uk_rights_code unique (code)	
);
	
create table profiles_rights (
    ref_profile bigint not null,
    ref_right bigint not null,
    
    constraint pk_profiles_rights primary key(ref_profile, ref_right),
    
    constraint fk_profiles_rights_profile foreign key (ref_profile) references profiles(id),
    constraint fk_profiles_rights_right foreign key (ref_right) references rights(id)		
    );

-- users
create table users (
    id bigint not null,
    
    name varchar(128) not null,   			-- user name
    login varchar(64) not null,    			-- user login
    email varchar(128) not null,    		-- user email
	pwd varchar(32) not null,       		-- password hash

	status integer not null,        		-- 0=logged -- 1=Never logged -- 2=Request change -- 3=password expired -- 4=too manu retries
	changed integer not null,        		-- password changed date
	retries integer not null,      			-- number of retries failed
	tester integer not null default 0,		-- 1: user has access to pre-release features, 0: user has access only to released features

   	ref_profile bigint not null,   			-- user's profile

    constraint pk_users primary key(id),
    constraint fk_users_profile foreign key (ref_profile) references profiles(id),
	constraint uk_users_code unique(login),
	constraint uk_users_email unique(email)
    );

-- plattform parameters
create table parameters (
	id bigint not null,
    
	description varchar(256) not null,					-- name of the parameter	
    value varchar(256) not null,						-- value of the parameter
	display_order integer not null default(0),
	
	constraint pk_parameters primary key(id),
	constraint uk_parameters unique(description)
);
create index ix_parameters_description on parameters(description);

-- user_defaults
create table user_defaults (
	id bigint not null,
    
	data_key varchar(64) not null,		-- key for data	
	data_value varchar(64),				-- value for data	
	
	ref_user bigint not null,			-- user	

	constraint pk_user_defaults primary key(id),
	constraint uk_user_defaults_key unique(ref_user, data_key),
	constraint fk_user_defaults_user foreign key (ref_user) references users(id)
);

-- tasks
create table tasks (
	id bigint not null,

	description varchar (64) not null,			-- description of the task

	timezone varchar(128) not null,
	language varchar(16) not null,
	month varchar(64),							-- month to execute the task (* for all months)
	day varchar(64),							-- day to execute the task (* for all days, number for specific day or MON, TUE, WED for week days)
	hour varchar(64),							-- hour to execute the task (* for all hours)
	minute varchar(64),							-- minute to execute the task (* for all hours)
	times integer not null, 					-- number of times task is executed: 0 for infinite number 
	
	clazz integer not null,						-- class of report
	details text,								-- text with report details
	
    ref_user bigint not null, 					-- reference to user

    constraint pk_tasks primary key(id),
    constraint fk_tasks_user foreign key (ref_user) references users(id) on delete cascade
);
create index ix_tasks_clazz on tasks(clazz);

