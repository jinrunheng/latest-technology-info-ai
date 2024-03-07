DROP TABLE IF EXISTS article_ai;
CREATE DATABASE IF NOT EXISTS article_ai CHARACTER SET utf8 COLLATE utf8_general_ci;
-- 创建测试用表
DROP TABLE IF EXISTS `article_ai`.`test`;
CREATE TABLE `article_ai`.`test`
(
    id             VARCHAR(64)  NOT NULL COMMENT '文章主键 ID',
    author         VARCHAR(128) NOT NULL COMMENT '作者',
    article        LONGTEXT     NOT NULL COMMENT '文章',
    article_status INT          NOT NULL COMMENT '文章发布状态 0 表示未发布，1 表示已发布',
    create_time    DATETIME     NOT NULL COMMENT '创建时间',
    publish_date   DATETIME     NOT NULL COMMENT '文章发布时间',
    update_time    DATETIME     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT = 'test table';

