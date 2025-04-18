CREATE TABLE `number_type`  (
                                `id` bigint(30) AUTO_INCREMENT NOT NULL COMMENT '主键自增id',
                                `number` bigint(30) NOT NULL COMMENT '序号',
                                `name` varchar(255) NOT NULL COMMENT '名称',
                                `init_serial_number` int,
                                `created` datetime NULL COMMENT '创建时间',
                                `last_updated` datetime NULL COMMENT '最后更新时间',
                                PRIMARY KEY (`id`),
                                UNIQUE INDEX `IDX_NUMEBR`(`number`) USING BTREE
);

CREATE TABLE `number_data`  (
                                `id` bigint(30) AUTO_INCREMENT NOT NULL COMMENT '主键自增id',
                                `corp` varchar(30) NOT NULL COMMENT '公司',
                                `name` varchar(255) NOT NULL COMMENT '名称',
                                `type` bigint(30) NULL,
                                `serial_number` int NULL,
                                `image_base64` text NULL,
                                `router` varchar(255) NULL,
--                                 `material` varchar(255) NULL,
--                                 `thickness` varchar(255) NULL,
--                                 `qty` varchar(255) NULL,
--                                 `bounding_box_length` varchar(255) NULL,
--                                 `bounding_box_width` varchar(255) NULL,
--                                 `remark` varchar(255) NULL,
                                `created` datetime NULL COMMENT '创建时间',
                                `last_updated` datetime NULL COMMENT '最后更新时间',
                                PRIMARY KEY (`id`),
                                UNIQUE INDEX `IDX_TYPE_SERIAL_NUMBER`(`type`, `serial_number`) USING BTREE
);
