-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id 			int(11) 		not null auto_increment    comment '部门id',
  parent_id 		int(11) 		default 0 			       comment '父部门id',
  dept_name 		varchar(30) 	default '' 				   comment '部门名称',
  order_num 		int(4) 			default 0 			       comment '显示顺序',
  leader            varchar(20)     default ''                 comment '负责人',
  phone             varchar(20)     default ''                 comment '联系电话',
  email             varchar(20)     default ''                 comment '邮箱',
  status 			int(1) 			default 0 				   comment '部门状态:0正常,1停用',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb auto_increment=200 default charset=utf8 comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '若依集团', 0, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(101,  100, '研发部门', 1, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(102,  100, '市场部门', 2, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(103,  100, '测试部门', 3, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(104,  100, '财务部门', 4, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(105,  100, '运维部门', 5, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(106,  101, '研发一部', 1, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(107,  101, '研发二部', 2, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(108,  102, '市场一部', 1, '若依', '15888888888', 'ry@qq.com', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(109,  102, '市场二部', 2, '若依', '15888888888', 'ry@qq.com', 1, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id 			int(11) 		not null auto_increment    comment '用户ID',
  dept_id 			int(20) 		default null			   comment '部门ID',
  login_name 		varchar(30) 	default '' 				   comment '登录账号',
  user_name 		varchar(30) 	default '' 				   comment '用户昵称',
  email  			varchar(100) 	default '' 				   comment '用户邮箱',
  phonenumber  		varchar(20) 	default '' 				   comment '手机号码',
  sex  		        char(20) 	    default '0' 			   comment '用户性别:0男,1女',
  avatar            varchar(100) 	default '' 				   comment '头像路径',
  password 			varchar(100) 	default '' 				   comment '密码',
  salt 				varchar(100) 	default '' 				   comment '盐加密',
  status 			int(1) 			default 0 				   comment '帐号状态（0正常 1禁用 2删除）',
  login_ip          varchar(100)    default ''                 comment '最后登陆IP',
  login_date        datetime                                   comment '最后登陆时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark 		    varchar(500) 	default '' 				   comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  106, 'admin', '若依', 'ry@163.com', '15888888888', '1', '', '29c67a30398638269fe600f73a054934', '111111', 0, '127.0.0.1', '2018-03-16 11-33-00', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '管理员');
insert into sys_user values(2,  108, 'ry',    '若依', 'ry@qq.com',  '15666666666', '1', '', '8e6d98b90472783cc73c17047ddccf36', '222222', 0, '127.0.0.1', '2018-03-16 11-33-00', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '测试员');

-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
    post_id       int(11)         not null auto_increment    comment '岗位ID',
	post_code     varchar(64)     not null                   comment '岗位编码',
	post_name     varchar(100)    not null                   comment '岗位名称',
	post_sort     int(4)          not null                   comment '显示顺序',
	status        int(1)          not null                   comment '状态（0正常 1停用）',
    create_by     varchar(64)     default ''                 comment '创建者',
    create_time   datetime                                   comment '创建时间',
    update_by     varchar(64) 	  default ''			     comment '更新者',
	update_time   datetime                                   comment '更新时间',
    remark 		  varchar(500) 	  default '' 				 comment '备注',
	primary key (post_id)
) engine=innodb default charset=utf8 comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, 'ceo',  '董事长',    1, 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_post values(2, 'se',   '项目经理',  2, 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_post values(3, 'hr',   '人力资源',  3, 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_post values(4, 'user', '普通员工',  4, 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id 			int(10) 		not null auto_increment    comment '角色ID',
  role_name 		varchar(30) 	not null 				   comment '角色名称',
  role_key 		    varchar(100) 	not null 				   comment '角色权限字符串',
  role_sort         int(10)         not null                   comment '显示顺序',
  status 			int(1) 			default 0 				   comment '角色状态:0正常,1禁用',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 		datetime                                   comment '创建时间',
  update_by 		varchar(64) 	default ''			       comment '更新者',
  update_time 		datetime                                   comment '更新时间',
  remark 			varchar(500) 	default '' 				   comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '管理员',   'admin',  1,  0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '管理员');
insert into sys_role values('2', '普通角色', 'common', 2,  0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '普通角色');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id 			int(11) 		not null auto_increment    comment '菜单ID',
  menu_name 		varchar(50) 	not null 				   comment '菜单名称',
  parent_id 		int(11) 		default 0 			       comment '父菜单ID',
  order_num 		int(4) 			default null 			   comment '显示顺序',
  url 				varchar(200) 	default ''				   comment '请求地址',
  menu_type 		char(1) 		default '' 			       comment '类型:M目录,C菜单,F按钮',
  visible 			int(1) 			default 0 				   comment '菜单状态:0显示,1隐藏',
  perms 			varchar(100) 	default '' 				   comment '权限标识',
  icon 				varchar(100) 	default '' 				   comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 		datetime                                   comment '创建时间',
  update_by 		varchar(64) 	default ''			       comment '更新者',
  update_time 		datetime                                   comment '更新时间',
  remark 			varchar(500) 	default '' 				   comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=1000 default charset=utf8 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '1', '#', 'M', '0', '', 'fa fa-gear',         'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '2', '#', 'M', '0', '', 'fa fa-video-camera', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统监控目录');
insert into sys_menu values('3', '系统工具', '0', '3', '#', 'M', '0', '', 'fa fa-bars',         'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统工具目录');
-- 二级菜单
insert into sys_menu values('4',   '用户管理', '1', '1', '/system/user',        'C', '0', 'system:user:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '用户管理菜单');
insert into sys_menu values('5',   '角色管理', '1', '2', '/system/role',        'C', '0', 'system:role:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '角色管理菜单');
insert into sys_menu values('6',   '菜单管理', '1', '3', '/system/menu',        'C', '0', 'system:menu:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '菜单管理菜单');
insert into sys_menu values('7',   '部门管理', '1', '4', '/system/dept',        'C', '0', 'system:dept:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '部门管理菜单');
insert into sys_menu values('8',   '岗位管理', '1', '5', '/system/post',        'C', '0', 'system:post:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '岗位管理菜单');
insert into sys_menu values('9',   '字典管理', '1', '6', '/system/dict',        'C', '0', 'system:dict:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '字典管理菜单');
insert into sys_menu values('10',  '参数设置', '1', '7', '/system/config',      'C', '0', 'system:config:view',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '参数设置菜单');
insert into sys_menu values('11',  '操作日志', '2', '1', '/monitor/operlog',    'C', '0', 'monitor:operlog:view',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '操作日志菜单');
insert into sys_menu values('12',  '登录日志', '2', '2', '/monitor/logininfor', 'C', '0', 'monitor:logininfor:view',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '登录日志菜单');
insert into sys_menu values('13',  '在线用户', '2', '3', '/monitor/online',     'C', '0', 'monitor:online:view',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '在线用户菜单');
insert into sys_menu values('14',  '定时任务', '2', '4', '/monitor/job',        'C', '0', 'monitor:job:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '定时任务菜单');
insert into sys_menu values('15',  '数据监控', '2', '5', '/monitor/data',       'C', '0', 'monitor:data:view',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '数据监控菜单');
insert into sys_menu values('16',  '系统接口', '2', '6', '/swagger-ui.html',    'C', '0', 'onitor:api:view',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统API菜单');
insert into sys_menu values('17',  '表单构建', '3', '1', '/tool/build',         'C', '0', 'tool:build:view',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '表单构建菜单');
insert into sys_menu values('18',  '代码生成', '3', '2', '/tool/gen',           'C', '0', 'tool:gen:view',            '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '代码生成菜单');
-- 用户管理按钮
insert into sys_menu values('19', '用户查询', '4', '1',  '#',  'F', '0', 'system:user:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('20', '用户新增', '4', '2',  '#',  'F', '0', 'system:user:add',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('21', '用户修改', '4', '3',  '#',  'F', '0', 'system:user:edit',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('22', '用户删除', '4', '4',  '#',  'F', '0', 'system:user:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('23', '用户保存', '4', '5',  '#',  'F', '0', 'system:user:save',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('24', '批量删除', '4', '6',  '#',  'F', '0', 'system:user:batchRemove',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('25', '重置密码', '4', '7',  '#',  'F', '0', 'system:user:resetPwd',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 角色管理按钮
insert into sys_menu values('26', '角色查询', '5', '1',  '#',  'F', '0', 'system:role:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('27', '角色新增', '5', '2',  '#',  'F', '0', 'system:role:add',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('28', '角色修改', '5', '3',  '#',  'F', '0', 'system:role:edit',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('29', '角色删除', '5', '4',  '#',  'F', '0', 'system:role:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('30', '角色保存', '5', '5',  '#',  'F', '0', 'system:role:save',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('31', '批量删除', '5', '6',  '#',  'F', '0', 'system:role:batchRemove',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 菜单管理按钮
insert into sys_menu values('32', '菜单查询', '6', '1',  '#',  'F', '0', 'system:menu:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('33', '菜单新增', '6', '2',  '#',  'F', '0', 'system:menu:add',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('34', '菜单修改', '6', '3',  '#',  'F', '0', 'system:menu:edit',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('35', '菜单删除', '6', '4',  '#',  'F', '0', 'system:menu:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('36', '菜单保存', '6', '5',  '#',  'F', '0', 'system:menu:save',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 部门管理按钮
insert into sys_menu values('37', '部门查询', '7', '1',  '#',  'F', '0', 'system:dept:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('38', '部门新增', '7', '2',  '#',  'F', '0', 'system:dept:add',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('39', '部门修改', '7', '3',  '#',  'F', '0', 'system:dept:edit',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('40', '部门删除', '7', '4',  '#',  'F', '0', 'system:dept:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('41', '部门保存', '7', '5',  '#',  'F', '0', 'system:dept:save',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 岗位管理按钮
insert into sys_menu values('42', '岗位查询', '8', '1',  '#',  'F', '0', 'system:post:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('43', '岗位新增', '8', '2',  '#',  'F', '0', 'system:post:add',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('44', '岗位修改', '8', '3',  '#',  'F', '0', 'system:post:edit',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('45', '岗位删除', '8', '4',  '#',  'F', '0', 'system:post:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('46', '岗位保存', '8', '5',  '#',  'F', '0', 'system:post:save',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('47', '批量删除', '8', '6',  '#',  'F', '0', 'system:post:batchRemove',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 字典管理按钮
insert into sys_menu values('48', '字典查询', '9', '1', '#',  'F', '0', 'system:dict:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('49', '字典新增', '9', '2', '#',  'F', '0', 'system:dict:add',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('50', '字典修改', '9', '3', '#',  'F', '0', 'system:dict:edit',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('51', '字典删除', '9', '4', '#',  'F', '0', 'system:dict:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('52', '字典保存', '9', '5', '#',  'F', '0', 'system:dict:save',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('53', '批量删除', '9', '6', '#',  'F', '0', 'system:dict:batchRemove',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 参数设置按钮
insert into sys_menu values('54', '参数查询', '10', '1', '#',  'F', '0', 'system:config:list',              '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('55', '参数新增', '10', '2', '#',  'F', '0', 'system:config:add',               '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('56', '参数修改', '10', '3', '#',  'F', '0', 'system:config:edit',              '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('57', '参数删除', '10', '4', '#',  'F', '0', 'system:config:remove',            '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('58', '参数保存', '10', '5', '#',  'F', '0', 'system:config:save',              '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('59', '批量删除', '10', '6', '#',  'F', '0', 'system:config:batchRemove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 操作日志按钮
insert into sys_menu values('60', '操作查询', '11', '1', '#',  'F', '0', 'monitor:operlog:list',            '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('61', '批量删除', '11', '2', '#',  'F', '0', 'monitor:operlog:batchRemove',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('62', '详细信息', '11', '3', '#',  'F', '0', 'monitor:operlog:detail',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 登录日志按钮
insert into sys_menu values('63', '登录查询', '12', '1', '#',  'F', '0', 'monitor:logininfor:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('64', '批量删除', '12', '2', '#',  'F', '0', 'monitor:logininfor:batchRemove',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 在线用户按钮
insert into sys_menu values('65', '在线查询', '13', '1', '#',  'F', '0', 'monitor:online:list',             '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('66', '批量强退', '13', '2', '#',  'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('67', '单条强退', '13', '3', '#',  'F', '0', 'monitor:online:forceLogout',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 定时任务按钮
insert into sys_menu values('68', '任务查询', '14', '1', '#',  'F', '0', 'monitor:job:list',             '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('69', '任务新增', '14', '2', '#',  'F', '0', 'monitor:job:add',              '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('70', '任务修改', '14', '3', '#',  'F', '0', 'monitor:job:edit',             '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('71', '任务删除', '14', '4', '#',  'F', '0', 'monitor:job:remove',           '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('72', '任务保存', '14', '5', '#',  'F', '0', 'monitor:job:save',             '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('73', '状态修改', '14', '6', '#',  'F', '0', 'monitor:job:changeStatus',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('74', '批量删除', '14', '7', '#',  'F', '0', 'monitor:job:batchRemove',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 代码生成按钮
insert into sys_menu values('75', '生成查询', '16', '1', '#',  'F', '0', 'tool:gen:list',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('76', '生成代码', '16', '2', '#',  'F', '0', 'tool:gen:code',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id 	int(11) not null comment '用户ID',
  role_id 	int(11) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb default charset=utf8 comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id 	int(11) not null comment '角色ID',
  menu_id 	int(11) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb default charset=utf8 comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('1', '1');
insert into sys_role_menu values ('1', '2');
insert into sys_role_menu values ('1', '3');
insert into sys_role_menu values ('1', '4');
insert into sys_role_menu values ('1', '5');
insert into sys_role_menu values ('1', '6');
insert into sys_role_menu values ('1', '7');
insert into sys_role_menu values ('1', '8');
insert into sys_role_menu values ('1', '9');
insert into sys_role_menu values ('1', '10');
insert into sys_role_menu values ('1', '11');
insert into sys_role_menu values ('1', '12');
insert into sys_role_menu values ('1', '13');
insert into sys_role_menu values ('1', '14');
insert into sys_role_menu values ('1', '15');
insert into sys_role_menu values ('1', '16');
insert into sys_role_menu values ('1', '17');
insert into sys_role_menu values ('1', '18');
insert into sys_role_menu values ('1', '19');
insert into sys_role_menu values ('1', '20');
insert into sys_role_menu values ('1', '21');
insert into sys_role_menu values ('1', '22');
insert into sys_role_menu values ('1', '23');
insert into sys_role_menu values ('1', '24');
insert into sys_role_menu values ('1', '25');
insert into sys_role_menu values ('1', '26');
insert into sys_role_menu values ('1', '27');
insert into sys_role_menu values ('1', '28');
insert into sys_role_menu values ('1', '29');
insert into sys_role_menu values ('1', '30');
insert into sys_role_menu values ('1', '31');
insert into sys_role_menu values ('1', '32');
insert into sys_role_menu values ('1', '33');
insert into sys_role_menu values ('1', '34');
insert into sys_role_menu values ('1', '35');
insert into sys_role_menu values ('1', '36');
insert into sys_role_menu values ('1', '37');
insert into sys_role_menu values ('1', '38');
insert into sys_role_menu values ('1', '39');
insert into sys_role_menu values ('1', '40');
insert into sys_role_menu values ('1', '41');
insert into sys_role_menu values ('1', '42');
insert into sys_role_menu values ('1', '43');
insert into sys_role_menu values ('1', '44');
insert into sys_role_menu values ('1', '45');
insert into sys_role_menu values ('1', '46');
insert into sys_role_menu values ('1', '47');
insert into sys_role_menu values ('1', '48');
insert into sys_role_menu values ('1', '49');
insert into sys_role_menu values ('1', '50');
insert into sys_role_menu values ('1', '51');
insert into sys_role_menu values ('1', '52');
insert into sys_role_menu values ('1', '53');
insert into sys_role_menu values ('1', '54');
insert into sys_role_menu values ('1', '55');
insert into sys_role_menu values ('1', '56');
insert into sys_role_menu values ('1', '57');
insert into sys_role_menu values ('1', '58');
insert into sys_role_menu values ('1', '59');
insert into sys_role_menu values ('1', '60');
insert into sys_role_menu values ('1', '61');
insert into sys_role_menu values ('1', '62');
insert into sys_role_menu values ('1', '63');
insert into sys_role_menu values ('1', '64');
insert into sys_role_menu values ('1', '65');
insert into sys_role_menu values ('1', '66');
insert into sys_role_menu values ('1', '67');
insert into sys_role_menu values ('1', '68');
insert into sys_role_menu values ('1', '69');
insert into sys_role_menu values ('1', '70');
insert into sys_role_menu values ('1', '71');
insert into sys_role_menu values ('1', '72');
insert into sys_role_menu values ('1', '73');
insert into sys_role_menu values ('1', '74');
insert into sys_role_menu values ('1', '75');
insert into sys_role_menu values ('1', '76');

-- ----------------------------
-- 8、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
	user_id varchar(64) not null comment '用户ID',
	post_id varchar(64) not null comment '岗位ID',
	primary key (user_id, post_id)
) engine=innodb default charset=utf8 comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 9、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id 			int(11) 		not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  action            varchar(100)    default ''                 comment '功能请求',
  method            varchar(100)    default ''                 comment '方法名称',
  channel           varchar(20)     default ''                 comment '来源渠道',
  login_name 	    varchar(50)     default '' 		 	 	   comment '登录账号',
  dept_name 		varchar(50)     default '' 		 	 	   comment '部门名称',
  oper_url 		    varchar(255) 	default '' 				   comment '请求URL',
  oper_ip 			varchar(30) 	default '' 				   comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param 		varchar(255) 	default '' 				   comment '请求参数',
  status 			int(1) 		    default 0				   comment '操作状态 0正常 1异常',
  error_msg 		varchar(2000) 	default '' 				   comment '错误消息',
  oper_time 		datetime                                   comment '操作时间',
  primary key (oper_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '操作日志记录';


-- ----------------------------
-- 10、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
	dict_id          int(11) 		 not null auto_increment    comment '字典主键',
	dict_name        varchar(100)    default ''                 comment '字典名称',
	dict_type        varchar(100)    default ''                 comment '字典类型',
    status 			 int(1) 		 default 0				    comment '状态（0正常 1停用）',
    create_by        varchar(64)     default ''                 comment '创建者',
    create_time      datetime                                   comment '创建时间',
    update_by        varchar(64) 	 default ''			        comment '更新者',
	update_time      datetime                                   comment '更新时间',
    remark 	         varchar(500) 	 default '' 				comment '备注',
	primary key (dict_id),
	unique (dict_type)
) engine=innodb auto_increment=100 default charset=utf8 comment = '字典类型表';

insert into sys_dict_type values(1, '用户性别', 'sys_user_sex',     0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '用户性别列表');
insert into sys_dict_type values(2, '菜单状态', 'sys_menu_visible', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '菜单状态列表');
insert into sys_dict_type values(3, '部门状态', 'sys_dept_status',  0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '部门状态列表');
insert into sys_dict_type values(4, '岗位状态', 'sys_post_status',  0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '岗位状态列表');
insert into sys_dict_type values(5, '字典状态', 'sys_dict_status',  0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '字典状态列表');
insert into sys_dict_type values(6, '任务状态', 'sys_job_status',   0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '任务状态列表');
insert into sys_dict_type values(7, '系统是否', 'sys_yes_no',       0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统是否列表');

-- ----------------------------
-- 11、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
	dict_code        int(11) 		 not null auto_increment    comment '字典编码',
	dict_sort        int(4)          default 0                  comment '字典排序',
	dict_label       varchar(100)    default ''                 comment '字典标签',
	dict_value       varchar(100)    default ''                 comment '字典键值',
	dict_type        varchar(100)    default ''                 comment '字典类型',
	css_class        varchar(500)    default ''                 comment '样式属性',
	is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
    status 			 int(1) 		 default 0				    comment '状态（0正常 1停用）',
    create_by        varchar(64)     default ''                 comment '创建者',
    create_time      datetime                                   comment '创建时间',
    update_by        varchar(64) 	 default ''			        comment '更新者',
	update_time      datetime                                   comment '更新时间',
    remark 	         varchar(500) 	 default '' 				comment '备注',
	primary key (dict_code)
) engine=innodb auto_increment=100 default charset=utf8 comment = '字典数据表';

insert into sys_dict_data values(1,  1, '男',   '0',  'sys_user_sex',      'radio radio-info radio-inline',    'Y', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(2,  2, '女',   '1',  'sys_user_sex',      'radio radio-danger radio-inline',  'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(3,  3, '未知', '2',  'sys_user_sex',      'radio radio-warning radio-inline', 'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(4,  1, '显示', '0',  'sys_menu_visible',  'radio radio-info radio-inline',    'Y', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(5,  2, '隐藏', '1',  'sys_menu_visible',  'radio radio-danger radio-inline',  'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(6,  1, '正常', '0',  'sys_dept_status',   'radio radio-info radio-inline',    'Y', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(7,  2, '停用', '1',  'sys_dept_status',   'radio radio-danger radio-inline',  'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(8,  1, '正常', '0',  'sys_dict_status',   'radio radio-info radio-inline',    'Y', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(9,  2, '停用', '1',  'sys_dict_status',   'radio radio-danger radio-inline',  'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(10, 1, '正常', '0',  'sys_post_status',   'radio radio-info radio-inline',    'Y', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(11, 2, '停用', '1',  'sys_post_status',   'radio radio-danger radio-inline',  'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(12, 1, '正常', '0',  'sys_job_status',    'radio radio-info radio-inline',    'Y', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(13, 2, '暂停', '1',  'sys_job_status',    'radio radio-danger radio-inline',  'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(14, 1, '是',   'Y',  'sys_yes_no',        'radio radio-info radio-inline',    'Y', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_dict_data values(15, 2, '否',   'N',  'sys_yes_no',        'radio radio-danger radio-inline',  'N', 0, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');

-- ----------------------------
-- 12、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
	config_id 		   int(5) 	     not null auto_increment    comment '参数主键',
	config_name        varchar(100)  default ''                 comment '参数名称',
	config_key         varchar(100)  default ''                 comment '参数键名',
	config_value       varchar(100)  default ''                 comment '参数键值',
	config_type        char(1)       default 'N'                comment '系统内置（Y是 N否）',
    create_by          varchar(64)   default ''                 comment '创建者',
    create_time 	   datetime                                 comment '创建时间',
    update_by          varchar(64)   default ''                 comment '更新者',
    update_time        datetime                                 comment '更新时间',
	remark 	           varchar(500)  default '' 				comment '备注',
	primary key (config_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '参数配置表';

insert into sys_config values(1, '主框架页-默认皮肤样式名称', 'sys.index.skinName',     'skin-default',  'Y', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '默认 skin-default、蓝色 skin-blue、黄色 skin-yellow' );
insert into sys_config values(2, '用户管理-账号初始密码',     'sys.user.initPassword',  '123456',        'Y', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '初始化密码 123456' );


-- ----------------------------
-- 13、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id 		 int(11) 	   not null auto_increment   comment '访问ID',
  login_name 	 varchar(50)   default '' 			     comment '登录账号',
  ipaddr 		 varchar(50)   default '' 			     comment '登录IP地址',
  login_location varchar(255)  default ''                comment '登录地点',
  browser  		 varchar(50)   default '' 			     comment '浏览器类型',
  os      		 varchar(50)   default '' 			     comment '操作系统',
  status 		 int(1) 	   default 0 			     comment '登录状态 0成功 1失败',
  msg      		 varchar(255)  default '' 			     comment '提示消息',
  login_time 	 datetime                                comment '访问时间',
  primary key (info_id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '系统访问记录';


-- ----------------------------
-- 14、在线用户记录
-- ----------------------------
drop table if exists sys_user_online;
create table sys_user_online (
  sessionId 	    varchar(50)  default ''              	comment '用户会话id',
  login_name 	    varchar(50)  default '' 		 	 	comment '登录账号',
  dept_name 		varchar(50)  default '' 		 	 	comment '部门名称',
  ipaddr 		    varchar(50)  default '' 			 	comment '登录IP地址',
  login_location    varchar(255) default ''                 comment '登录地点',
  browser  		    varchar(50)  default '' 			 	comment '浏览器类型',
  os      		    varchar(50)  default '' 			 	comment '操作系统',
  status      	    varchar(10)  default '' 			 	comment '在线状态on_line在线off_line离线',
  start_timestsamp 	datetime                                comment 'session创建时间',
  last_access_time  datetime                                comment 'session最后访问时间',
  expire_time 	    int(5) 		 default 0 			 	    comment '超时时间，单位为分钟',
  primary key (sessionId)
) engine=innodb default charset=utf8 comment = '在线用户记录';


-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id 		      int(11) 	    not null auto_increment    comment '任务ID',
  job_name            varchar(64)   default ''                 comment '任务名称',
  job_group           varchar(64)   default ''                 comment '任务组名',
  method_name         varchar(500)  default ''                 comment '任务方法',
  params              varchar(200)  default ''                 comment '方法参数',
  cron_expression     varchar(255)  default ''                 comment 'cron执行表达式',
  status              int(1)        default 0                  comment '状态（0正常 1暂停）',
  create_by           varchar(64)   default ''                 comment '创建者',
  create_time         datetime                                 comment '创建时间',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新时间',
  remark              varchar(500)  default ''                 comment '备注信息',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 default charset=utf8 comment = '定时任务调度表';

insert into sys_job values(1, 'ryTask', '系统默认（无参）', 'ryNoParams',  '',   '0/10 * * * * ?', 1, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_job values(2, 'ryTask', '系统默认（有参）', 'ryParams',    'ry', '0/20 * * * * ?', 1, 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');


-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          int(11) 	    not null auto_increment    comment '任务日志ID',
  job_name            varchar(64)   not null                   comment '任务名称',
  job_group           varchar(64)   not null                   comment '任务组名',
  method_name         varchar(500)                             comment '任务方法',
  params              varchar(200)  default ''                 comment '方法参数',
  job_message         varchar(500)                             comment '日志信息',
  is_exception        int(1)        default 0                  comment '是否异常',
  exception_info      text                                     comment '异常信息',
  create_time         datetime                                 comment '创建时间',
  primary key (job_log_id)
) engine=innodb default charset=utf8 comment = '定时任务调度日志表';