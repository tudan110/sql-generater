/*sharding语句*/
sharding @@table name='table_name' set type='single' and dn='bigscreen_1';

/*sharding语句*/
sharding @@table name='table_name' set type='sharding' and sharding_algo='PartitionByMod' and sharding_id='sharding_id' and dn='bigscreen_1,bigscreen_2,bigscreen_3,bigscreen_4,bigscreen_5,bigscreen_6,bigscreen_7,bigscreen_8';