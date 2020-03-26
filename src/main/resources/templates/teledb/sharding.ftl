/*sharding语句*/
sharding @@table name='${tableName}' set type='single' and dn='bigscreen_1';

/*sharding语句*/
sharding @@table name='${tableName}' set type='sharding' and sharding_algo='PartitionByMod' and sharding_id='${pk}' and dn='bigscreen_1,bigscreen_2,bigscreen_3,bigscreen_4,bigscreen_5,bigscreen_6,bigscreen_7,bigscreen_8';