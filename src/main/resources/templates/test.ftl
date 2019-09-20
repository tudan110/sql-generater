/*建表语句*/
CREATE TABLE IF NOT EXISTS `${tableName}` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`freq` varchar(10) DEFAULT NULL,
`info_state` tinyint(4) DEFAULT NULL,
`info_start_time` varchar(19) DEFAULT NULL,
`info_end_time` varchar(19) DEFAULT NULL,
`info_create_time` datetime DEFAULT NULL,
`info_area_code` varchar(20) DEFAULT NULL,
`visit_prov_code` varchar(10) DEFAULT NULL,
`cdr_date` varchar(10) DEFAULT NULL,
`home_prov_code` varchar(10) DEFAULT NULL,
`roam_fee` decimal(20,2) DEFAULT NULL,
`roam_data` decimal(20,2) DEFAULT NULL,
`roam_cdr` bigint(20) DEFAULT NULL,
`host_name` varchar(20) DEFAULT NULL,
`ori_fee` decimal(20,2) DEFAULT NULL,
`bill_month` varchar(10) DEFAULT NULL,
`prod_inst_id` bigint(20) DEFAULT NULL,
`main_prod_inst_id` bigint(20) DEFAULT NULL,
`offer_id` bigint(20) DEFAULT NULL,
`imsi` varchar(20) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `IDX_IN200_10_201901_CREATE_TIME` (`info_create_time`),
KEY `IDX_IN200_10_201901_imsi` (`imsi`),
KEY `IDX_IN200_10_201901_cdr_date` (`cdr_date`),
KEY `IDX_IN200_10_201901_offer_id` (`offer_id`),
KEY `IDX_IN200_10_201901_visit_prov` (`visit_prov_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*sharding语句*/
sharding @@table name='info_node_200_10_201901' set type='sharding' and sharding_algo='PartitionByMod' and sharding_id='imsi' and dn='bigscreen_1,bigscreen_2,bigscreen_3,bigscreen_4,bigscreen_5,bigscreen_6,bigscreen_7,bigscreen_8';