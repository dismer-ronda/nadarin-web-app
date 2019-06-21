--// centros
-- Migration SQL that makes the change goes here.

---------------------------------------------    
---------------------------------------------    
-- ref_profiles
---------------------------------------------
insert into profiles (id, description) values (0, 'Developer');
insert into profiles (id, description) values (1, 'Administrator');
insert into profiles (id, description) values (2, 'User');

---------------------------------------------    
-- rights
---------------------------------------------
insert into rights (id, code, description) values (1, 'login','Login');

insert into rights (id, code, description) values (2, 'configuration','Configuration');

insert into rights (id, code, description) values (3, 'configuration.users','Users');
insert into rights (id, code, description) values (4, 'configuration.users.add','Add user');
insert into rights (id, code, description) values (5, 'configuration.users.modify','Modify user');
insert into rights (id, code, description) values (6, 'configuration.users.delete','Delete user');

insert into rights (id, code, description) values (11, 'configuration.technical','Technical rights');

insert into rights (id, code, description) values (21, 'configuration.parameters','Parameters');
insert into rights (id, code, description) values (22, 'configuration.parameters.modify','Modify parameter');

insert into rights (id, code, description) values (31, 'configuration.profiles','Profiles');
insert into rights (id, code, description) values (32, 'configuration.profiles.modify','Modify profile');

insert into rights (id, code, description) values (71, 'configuration.tasks','Tasks');
insert into rights (id, code, description) values (72, 'configuration.tasks.add','Add task');
insert into rights (id, code, description) values (73, 'configuration.tasks.modify','Modify task');
insert into rights (id, code, description) values (74, 'configuration.tasks.delete','Delete task');


insert into profiles_rights (ref_profile, ref_right) values (0, 1);
insert into profiles_rights (ref_profile, ref_right) values (0, 2);
insert into profiles_rights (ref_profile, ref_right) values (0, 3);
insert into profiles_rights (ref_profile, ref_right) values (0, 4);
insert into profiles_rights (ref_profile, ref_right) values (0, 5);
insert into profiles_rights (ref_profile, ref_right) values (0, 6);

insert into profiles_rights (ref_profile, ref_right) values (0, 11);

insert into profiles_rights (ref_profile, ref_right) values (0, 21);
insert into profiles_rights (ref_profile, ref_right) values (0, 22);

insert into profiles_rights (ref_profile, ref_right) values (0, 31);
insert into profiles_rights (ref_profile, ref_right) values (0, 32);

insert into profiles_rights (ref_profile, ref_right) values (0, 71);
insert into profiles_rights (ref_profile, ref_right) values (0, 72);
insert into profiles_rights (ref_profile, ref_right) values (0, 73);
insert into profiles_rights (ref_profile, ref_right) values (0, 74);

insert into profiles_rights (ref_profile, ref_right) values (2, 1);

insert into profiles_rights (ref_profile, ref_right) values (2, 71);
insert into profiles_rights (ref_profile, ref_right) values (2, 72);
insert into profiles_rights (ref_profile, ref_right) values (2, 73);
insert into profiles_rights (ref_profile, ref_right) values (2, 74);

---------------------------------------------    
-- users 
---------------------------------------------
insert into users (id, name, login, email, pwd, status, changed, retries, ref_profile, tester) values (1,'Dismer Ronda Betancourt','dismer','dismer.ronda@pryades.com','94d8244eb7c67002098205aa11f497fb',0, 20190101, 0, 0, 1);

---------------------------------------------    
-- parameters 
---------------------------------------------

insert into parameters (id, description, value, display_order) values (11, 'Default page size in tables', '25', 31);

insert into parameters (id, description, value, display_order) values (32, 'Login fails new password', '10', 81);
insert into parameters (id, description, value, display_order) values (33, 'Login fails block account', '20', 82);
insert into parameters (id, description, value, display_order) values (34, 'Password min size', '8', 91);
insert into parameters (id, description, value, display_order) values (35, 'Password valid time (days)', '365', 92);
insert into parameters (id, description, value, display_order) values (36, 'Mail host address', '', 101);
insert into parameters (id, description, value, display_order) values (37, 'Mail sender email', '', 102);
insert into parameters (id, description, value, display_order) values (38, 'Mail sender user', '', 103);
insert into parameters (id, description, value, display_order) values (39, 'Mail sender password', '', 104);

insert into parameters (id, description, value, display_order) values (62, 'Proxy host for outgoing connections', '', 132);
insert into parameters (id, description, value, display_order) values (63, 'Proxy port for outgoing connections', '', 133);

insert into parameters (id, description, value, display_order) values (64, 'SOCKS5 Proxy host for outgoing connections', '', 134);
insert into parameters (id, description, value, display_order) values (65, 'SOCKS5 Proxy port for outgoing connections', '', 135);

insert into parameters (id, description, value, display_order) values (70, 'Enable password min size', '1', 93);
insert into parameters (id, description, value, display_order) values (71, 'Enable password must contains uppercase', '1', 94);
insert into parameters (id, description, value, display_order) values (72, 'Enable password must contains digit', '1', 95);
insert into parameters (id, description, value, display_order) values (73, 'Enable password must contains symbol', '1', 96);
insert into parameters (id, description, value, display_order) values (74, 'Enable password forbid contains id', '1', 97);
insert into parameters (id, description, value, display_order) values (75, 'Enable password forbid reuse passwords', '1', 98);

insert into parameters (id, description, value, display_order) values (76, 'Log default', 'E', 150);
insert into parameters (id, description, value, display_order) values (85, 'Log files', 'E', 159);
insert into parameters (id, description, value, display_order) values (98, 'Log profiles rights', 'E', 172);
insert into parameters (id, description, value, display_order) values (99, 'Log reports', 'IUDE', 173);
insert into parameters (id, description, value, display_order) values (100, 'Log reports files', 'IUDE', 174);
insert into parameters (id, description, value, display_order) values (101, 'Log reports users', 'E', 175);
insert into parameters (id, description, value, display_order) values (105, 'Log tasks', 'IUDE', 179);
insert into parameters (id, description, value, display_order) values (106, 'Log users', 'IUDE', 180);
insert into parameters (id, description, value, display_order) values (107, 'Log users defaults', 'IUDE', 181);

insert into parameters (id, description, value, display_order) values (116, 'Max number of rows to export', '1000', 205);

insert into parameters (id, description, value, display_order) values (125, 'Mail host port', '25', 105);
insert into parameters (id, description, value, display_order) values (126, 'Mail auth', 'false', 106);

insert into parameters (id, description, value, display_order) values (127, 'Services url', '', 206);

