--=========================
-- 관리자계정 system
--=========================
alter session set "_oracle_script"=true; -- c## 접두어 없이 계정 생성 가능하도록 설정

create user web
identified by web
default tablespace users;

alter user web quota unlimited on users;

grant connect, resource to web;

--=========================
-- WEB 계정
--=========================
create table member (
    member_id varchar2(15),
    password varchar2(300) not null,
    member_name varchar2(50) not null,
    member_role char(1) default 'U' not null, -- 회원 권환
    gender char(1),
    birthday date,
    email varchar2(200),
    phone char(11) not null,
    hobby varchar2(200),
    point number default 1000,
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_member_role check(member_role in ('U', 'A')),
    constraint ck_member_gender check(gender in ('M', 'F')),
    constraint uq_member_email unique(email)
);

insert into 
    member
values (
        'honggd', '1234', '홍길동', 'U', 'M', to_date('20000909','yyyymmdd'),
        'honggd@naver.com', '01012341234', '운동,등산,독서', default, default
);

insert into 
    member
values (
        'qwerty', '1234', '쿠어티', 'U', 'F', to_date('19900215','yyyymmdd'),
        'qwerty@naver.com', '01012341234', '운동,등산', default, default
);

insert into 
    member
values (
        'admin', '1234', '관리자', 'A', 'M', to_date('19801010','yyyymmdd'),
        'admin@naver.com', '01056785678', '게임,독서', default, default
);

select * from member;

select password from member where member_id = 'honggd';
delete member where member_id = 'mango';






