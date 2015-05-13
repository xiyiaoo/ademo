--添加管理员用户(admin/admin)
insert into t_sys_user(id, organizationId, username, password, salt, locked, description, createTime, available)
  values('0', '0','admin','530f064151c90dd9fff3512e7490837f','8d78869f470951332959580424d4bf4f', '0', '超级管理员', sysdate, '1');
--添加管理员角色
insert into t_sys_role(id, name, value, description, creator, createTime, modifier, modifyTime, available)
  values('0', '超级管理员', 'admin', '超级管理员', null, sysdate, null, null, '1');
--添加资源
insert into t_sys_resource(id, parentId, parentIds, type, name, description, permission, url, iconClass, ordinal, creator, createTime, modifier, modifyTime, available)
  values('0', null, null, '2', '所有资源', '所有资源', '*', null, null, 0, null, sysdate, null, null, '1');
--增加机构
insert into t_sys_organization (id, parentId, parentIds, parentNames, name, description, ordinal, creator, createTime, available)
  values ('0', null, null, null, '机构', '根机构', 0, null, sysdate, '1')
--管理员授权
insert into t_sys_role_resource(roleId, resourceId) values('0', '0');
insert into t_sys_user_role(userId, roleId, ordinal) values('0', '0', 0);