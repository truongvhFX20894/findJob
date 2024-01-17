create database spring_workcv;
use spring_workcv;

create table role (
	id int not null auto_increment,
    role_name varchar(255) not null,
    primary key (id)
);
insert into role(role_name) values ('RECRUITER'), ('USER');

create table category (
	id int not null auto_increment,
    name varchar(255) not null,
    number_choose int not null,
    primary key (id)
);
insert into category(name, number_choose) values ('Java', 2), ('NodeJS', 4), ('PHP', 4), ('ASP.NET', 4);

create table cv (
	id int not null auto_increment,
    file_name varchar(255) not null,
    user_id int,
    cv_url varchar(255) not null,
    primary key (id),
    foreign key (user_id) references user(id)
);

create table user (
	id int not null auto_increment,
    address varchar(255),
    description varchar(1000),
    email varchar(255) not null,
    full_name varchar(255) not null,
    image varchar(255),
    password varchar(255) not null,
    phone_number varchar(255),
    status int not null,
    role_id int not null,
--     cv_id int,
    primary key (id),
    foreign key (role_id) references role(id)
--     foreign key (cv_id) references cv(id)
);
insert into user(email, full_name, password, status, role_id) values 
('fpt@gmail.com', 'FPT', '1', 1, 1),
('appota@gmail.com', 'Appota', '1', 1, 1),
('truong@gmail.com', 'Vương Hữu Trường', '1', 0, 2);

create table save_job (
	id int not null auto_increment,
    recruitment_id int not null,
    user_id int not null,
    primary key (id),
    foreign key (recruitment_id) references recruitment(id),
    foreign key (user_id) references user(id)
);

create table follow_company (
	id int not null auto_increment,
    company_id int not null,
    user_id int not null,
    primary key (id),
    foreign key (company_id) references company(id),
    foreign key (user_id) references user(id)
);

create table applypost (
	id int not null auto_increment,
    create_at datetime DEFAULT CURRENT_TIMESTAMP,
    recruitment_id int not null,
    user_id int not null,
    name_cv varchar(255) not null,
    status int not null,
    text varchar(255),
    primary key (id),
    foreign key (recruitment_id) references recruitment(id),
    foreign key (user_id) references user(id)
);

create table recruitment (
	id int not null auto_increment,
    address varchar(255) not null,
    create_at datetime not null DEFAULT CURRENT_TIMESTAMP,
    description varchar(1000) not null,
    experience varchar(255) not null,
    quantity int not null,
    salary varchar(255) not null,
    status int not null,
    title varchar(255) not null,
    type varchar(255) not null,
    view int,
    category_id int not null,
    company_id int not null,
    deadline varchar(255) not null,
    primary key (id),
    foreign key (category_id) references category(id),
    foreign key (company_id) references company(id)
);
insert into recruitment(address, description, experience, quantity, salary, status, title, type, view, category_id, company_id, deadline) values 
('Hà Nội', '', '1 năm kinh nghiệm', 3, 'Lương theo thỏa thuận', 0, 'Tuyển lập trình viên Java', 'Fulltime', 0, 1, 3, ''),
('Hà Nội', '', '1 năm kinh nghiệm', 10, 'Lương theo thỏa thuận', 0, 'Thực tập sinh lập trình Java', 'Part-time', 0, 1, 3, ''),
('Hà Nội', '', '1 năm kinh nghiệm', 5, 'Lương theo thỏa thuận', 0, 'Tuyển lập trình viên PHP', 'Fulltime', 0, 3, 4, '');

create table company (
	id int not null auto_increment,
    address varchar(255) not null,
    description varchar(1000) not null,
    email varchar(255) not null,
    logo varchar(255) not null,
    name_company varchar(255) not null,
    phone_number varchar(255) not null,
    status int not null,
    user_id int not null,
    primary key (id),
    foreign key (user_id) references user(id)
);
insert into company(address, description, email, logo, name_company, phone_number, status, user_id) values
('Cầu Giấy, Hà Nội', '', 'fpt@gmail.com', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/FPT_logo_2010.svg/2560px-FPT_logo_2010.svg.png', 'FPT', '123456789', 0, 1),
('Ba Đình, Hà Nội', '', 'appota@gmail.com', 'https://upload.wikimedia.org/wikipedia/commons/8/8d/Appota_logo_2019.png', 'Appota', '123456789', 0, 2); 