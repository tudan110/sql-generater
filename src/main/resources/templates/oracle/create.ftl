-- Create table
create table T_MSG_DISP_YOUXIN
(
trace_id    NUMBER(14) not null,
rule_id     NUMBER(14) not null,
waring_id   NUMBER(14) not null,
create_date DATE
)
tablespace MONITOR_TBS
pctfree 10
initrans 1
maxtrans 255
storage
(
initial 64K
minextents 1
maxextents unlimited
)
nologging;
-- Add comments to the table
comment on table T_MSG_DISP_YOUXIN
is '优信订阅轨迹表';
-- Add comments to the columns
comment on column T_MSG_DISP_YOUXIN.trace_id
is '轨迹标识，数据唯一标识';
comment on column T_MSG_DISP_YOUXIN.rule_id
is '关联的订阅规则，FW_YOUXIN_RULE.rule_Id';
comment on column T_MSG_DISP_YOUXIN.waring_id
is '关联的告警标识';
comment on column T_MSG_DISP_YOUXIN.create_date
is '创建时间，sysdate';
-- Create/Recreate primary, unique and foreign key constraints
alter table T_MSG_DISP_YOUXIN
add constraint PK_T_MSG_DISP_YOUXIN primary key (TRACE_ID)
using index
tablespace MONITOR_TBS
pctfree 10
initrans 2
maxtrans 255
storage
(
initial 64K
minextents 1
maxextents unlimited
);
alter table T_MSG_DISP_YOUXIN
add constraint FK_T_MSG_DISP_YOUXIN foreign key (RULE_ID)
references FW_YOUXIN_RULE (RULE_ID);
