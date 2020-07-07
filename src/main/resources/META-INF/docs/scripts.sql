-------------------------------------------------------------------------------------------------------------------------
--- BANCO DE DADOS ------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------

create database company_patrimony_management;

-------------------------------------------------------------------------------------------------------------------------
--- TABELA DE USUÁRIO ---------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------

create sequence if not exists seq_usuario_id increment by 1 minvalue 1 start with 1;

create table if not exists usuario (
	id numeric(10) not null default nextval('seq_usuario_id'),
	nome varchar(100) not null,
	email varchar(100) not null,
	senha varchar(255) not null,
	
	constraint pk_usuario_id primary key (id),
	constraint uk_usuario_email unique (nome)
);

-------------------------------------------------------------------------------------------------------------------------
--- TABELA DE MARCA -----------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------

create sequence if not exists seq_marca_id increment by 1 minvalue 1 start with 1;

create table if not exists marca (
	id numeric(10) not null default nextval('seq_marca_id'),
	nome varchar(100) not null,
	
	constraint pk_marca_id primary key (id),
	constraint uk_marca_nome unique (nome)
);

-------------------------------------------------------------------------------------------------------------------------
--- TABELA DE PATRIMÔNIO ------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------

create sequence if not exists seq_patrimonio_id increment by 1 minvalue 1 start with 1;

create table if not exists patrimonio (
	numero_tombo numeric(10) not null default nextval('seq_patrimonio_id'),
	nome varchar(100) not null,
	descricao varchar(255),
	id_marca numeric(10) not null,
	
	constraint pk_patrimonio_id primary key (numero_tombo),
	constraint fk_patrimonio_id_marca foreign key (id_marca) references marca (id)
);
