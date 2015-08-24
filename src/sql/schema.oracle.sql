drop index organization_index_parentIds;

drop index organization_index_parentId;

drop table t_sys_organization cascade constraints;

drop index resource_index_parentId;

drop index resource_index_parentIds;

drop table t_sys_resource cascade constraints;

drop index role_index_value;

drop table t_sys_role cascade constraints;

drop table t_sys_role_resource cascade constraints;

drop index user_index_orgid;

drop index user_index_username;

drop table t_sys_user cascade constraints;

drop table t_sys_user_role cascade constraints;

/*==============================================================*/
/* Table: t_sys_organization                                  */
/*==============================================================*/
create table t_sys_organization
(
  id                 varchar2(32)         not null,
  parentId           varchar2(32),
  parentIds          varchar2(200),
  parentNames        varchar2(1000),
  name               varchar2(200),
  description        varchar2(500),
  ordinal            number,
  creator            varchar2(32),
  createTime         TIMESTAMP,
  modifier           varchar2(32),
  modifyTime         TIMESTAMP,
  available          CHAR(1),
  constraint PK_T_SYS_ORGANIZATION primary key (id)
);

comment on table t_sys_organization is
'机构表';

comment on column t_sys_organization.id is
'主键';

comment on column t_sys_organization.parentId is
'上级机构';

comment on column t_sys_organization.parentIds is
'所有上级id(用户/连接)';

comment on column t_sys_organization.parentNames is
'所有上级名称(用/连接)';

comment on column t_sys_organization.name is
'机构名称';

comment on column t_sys_organization.description is
'机构描述';

comment on column t_sys_organization.ordinal is
'序号';

comment on column t_sys_organization.creator is
'创建人id';

comment on column t_sys_organization.createTime is
'创建时间';

comment on column t_sys_organization.modifier is
'修改人id';

comment on column t_sys_organization.modifyTime is
'修改时间';

comment on column t_sys_organization.available is
'是否可用(1:可用,:不可用)';

/*==============================================================*/
/* Index: organization_index_parentId                         */
/*==============================================================*/
create index organization_index_parentId on t_sys_organization (
  parentId ASC
);

/*==============================================================*/
/* Index: organization_index_parentIds                        */
/*==============================================================*/
create index organization_index_parentIds on t_sys_organization (
  parentIds ASC
);

/*==============================================================*/
/* Table: t_sys_resource                                      */
/*==============================================================*/
create table t_sys_resource
(
  id                 varchar2(32)         not null,
  parentId           varchar2(32),
  parentIds          varchar2(200),
  type               char(1),
  name               varchar2(100),
  description        varchar2(500),
  permission         varchar2(50),
  url                varchar2(50),
  iconClass          varchar2(20),
  ordinal            number,
  creator            varchar2(32),
  createTime         TIMESTAMP,
  modifier           varchar2(32),
  modifyTime         TIMESTAMP,
  available          CHAR(1),
  constraint PK_T_SYS_RESOURCE primary key (id)
);

comment on table t_sys_resource is
'资源表';

comment on column t_sys_resource.id is
'主键';

comment on column t_sys_resource.parentId is
'上级id';

comment on column t_sys_resource.parentIds is
'所有上级id(用户/连接)';

comment on column t_sys_resource.type is
'资源类型(0菜单组,1:菜单,2:按钮)';

comment on column t_sys_resource.name is
'资源名称';

comment on column t_sys_resource.description is
'资源描述';

comment on column t_sys_resource.permission is
'权限名称';

comment on column t_sys_resource.url is
'访问地址';

comment on column t_sys_resource.iconClass is
'显示图标';

comment on column t_sys_resource.ordinal is
'序号';

comment on column t_sys_resource.creator is
'创建人id';

comment on column t_sys_resource.createTime is
'创建时间';

comment on column t_sys_resource.modifier is
'修改人id';

comment on column t_sys_resource.modifyTime is
'修改时间';

comment on column t_sys_resource.available is
'是否可用(1:可用,:不可用)';

/*==============================================================*/
/* Index: resource_index_parentIds                            */
/*==============================================================*/
create index resource_index_parentIds on t_sys_resource (
  parentIds ASC
);

/*==============================================================*/
/* Index: resource_index_parentId                             */
/*==============================================================*/
create index resource_index_parentId on t_sys_resource (
  parentId ASC
);

/*==============================================================*/
/* Table: t_sys_role                                          */
/*==============================================================*/
create table t_sys_role
(
  id                 varchar2(32)         not null,
  name               varchar2(100),
  value              varchar2(20),
  description        varchar2(500),
  creator            varchar2(32),
  createTime         TIMESTAMP,
  modifier           varchar2(32),
  modifyTime         TIMESTAMP,
  available          CHAR(1),
  constraint PK_T_SYS_ROLE primary key (id)
);

comment on table t_sys_role is
'角色表';

comment on column t_sys_role.id is
'主键';

comment on column t_sys_role.name is
'角色名称';

comment on column t_sys_role.value is
'角色值';

comment on column t_sys_role.description is
'角色描述';

comment on column t_sys_role.creator is
'创建人id';

comment on column t_sys_role.createTime is
'创建时间';

comment on column t_sys_role.modifier is
'修改人id';

comment on column t_sys_role.modifyTime is
'修改时间';

comment on column t_sys_role.available is
'是否可用(1:可用,:不可用)';

/*==============================================================*/
/* Index: role_index_value                            */
/*==============================================================*/
create unique index role_index_value on t_sys_role (
  value ASC
);

/*==============================================================*/
/* Table: t_sys_role_resource                                     */
/*==============================================================*/
create table t_sys_role_resource
(
  roleId             varchar2(32)         not null,
  resourceId             varchar2(32)         not null,
  constraint PK_T_SYS_ROLE_RESOURCE primary key (roleId, resourceId)
);

comment on table t_sys_role_resource is
'角色资源关系表';

comment on column t_sys_role_resource.roleId is
'角色';

comment on column t_sys_role_resource.resourceId is
'资源';

/*==============================================================*/
/* Table: t_sys_user                                          */
/*==============================================================*/
create table t_sys_user
(
  id                 varchar2(32)         not null,
  organizationId     varchar2(32),
  username           varchar2(20),
  password           varchar2(32),
  salt               varchar2(32),
  avatar             varchar2(100),
  locked             CHAR(1),
  name               varchar2(100),
  gender             CHAR(1),
  telephone          varchar2(11),
  birthday           TIMESTAMP,
  email              varchar2(100),
  address            varchar2(200),
  description        varchar2(500),
  creator            varchar2(32),
  createTime         TIMESTAMP,
  modifier           varchar2(32),
  modifyTime         TIMESTAMP,
  available          CHAR(1),
  constraint PK_T_SYS_USER primary key (id)
);

comment on table t_sys_user is
'用户表';

comment on column t_sys_user.id is
'主键';

comment on column t_sys_user.organizationId is
'所属机构';

comment on column t_sys_user.username is
'用户名';

comment on column t_sys_user.password is
'密码';

comment on column t_sys_user.salt is
'盐';

comment on column t_sys_user.avatar is
'头像';

comment on column t_sys_user.locked is
'锁定标记(1:锁定,0:未锁定)';

comment on column t_sys_user.name is
'姓名';

comment on column t_sys_user.gender is
'性别(M:男,F:女)';

comment on column t_sys_user.telephone is
'电话';

comment on column t_sys_user.birthday is
'生日';

comment on column t_sys_user.email is
'邮箱';

comment on column t_sys_user.address is
'地址';

comment on column t_sys_user.description is
'说明';

comment on column t_sys_user.creator is
'创建人id';

comment on column t_sys_user.createTime is
'创建时间';

comment on column t_sys_user.modifier is
'修改人id';

comment on column t_sys_user.modifyTime is
'修改时间';

comment on column t_sys_user.available is
'是否可用(1:可用,:不可用)';

/*==============================================================*/
/* Index: user_index_orgid                                      */
/*==============================================================*/
create index user_index_orgid on t_sys_user (
  organizationId ASC
);

/*==============================================================*/
/* Index: user_index_username                                   */
/*==============================================================*/
create unique index user_index_username on t_sys_user (
  username ASC
);

/*==============================================================*/
/* Table: t_sys_user_role                                       */
/*==============================================================*/
create table t_sys_user_role
(
  userId             varchar2(32)         not null,
  roleId             varchar2(32)         not null,
  ordinal            number,
  constraint PK_T_SYS_USER_ROLE primary key (userId, roleId)
);

comment on table t_sys_user_role is
'用户角色关系表';

comment on column t_sys_user_role.userId is
'用户';

comment on column t_sys_user_role.roleId is
'角色';

comment on column t_sys_user_role.ordinal is
'序号';
