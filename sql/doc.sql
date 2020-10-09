select * from video_info;

insert into video_info(imgSrc, videoDescribe, videoTitle, videoUrl)
    value ('https://czwhub.oss-cn-shanghai.aliyuncs.com/xiaolan.jpg',
           '光耀喝酒的视频', '光耀喝酒',
           'https://czwhub.oss-cn-shanghai.aliyuncs.com/%E5%85%89%E8%80%80%E5%96%9D%E9%85%92.mp4');

insert into video_info(imgSrc, videoDescribe, videoTitle, videoUrl)
    value ('https://czwhub.oss-cn-shanghai.aliyuncs.com/%E6%B8%B8%E6%88%8F%E7%8E%8B%E5%B0%81%E9%9D%A21.jpg',
           '游戏王12集的描述', '游戏王12',
           'https://czwhub.oss-cn-shanghai.aliyuncs.com/%E6%B8%B8%E6%88%8F%E7%8E%8BSEVENS%E7%AC%AC%E5%8D%81%E4%BA%8C%E8%AF%9D.mp4');

insert into video_info(imgSrc, videoDescribe, videoTitle, videoUrl)
    value ('https://czwhub.oss-cn-shanghai.aliyuncs.com/%E6%B8%B8%E6%88%8F%E7%8E%8B%E5%B0%81%E9%9D%A22.jpg',
           '游戏王14集的描述', '游戏王14',
           'https://czwhub.oss-cn-shanghai.aliyuncs.com/SEVENS%20014..mp4');

insert into video_info(imgSrc, videoDescribe, videoTitle, videoUrl)
    value ('https://czwhub.oss-cn-shanghai.aliyuncs.com/%E6%AD%A6%E6%B1%89%E5%B0%81%E9%9D%A2.jpg',
           '这是活动视频描述', '活动视频',
           'https://czwhub.oss-cn-shanghai.aliyuncs.com/%E6%B4%BB%E5%8A%A8%E8%A7%86%E9%A2%91.mp4');

delete from video_info where id=6;
delete from video_info where id=10;

drop table if exists `category`;
create table `category` (
    `id` char(8) not null default '' comment 'id',
    `parent` char(8) not null default '' comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
)engine =innodb default charset =utf8mb4 comment ='分类'

drop table if exists `user`;
create table `user`(
    `id` char(8) not null default '' comment 'id',
    `login_name` varchar(50) not null comment '登陆名',
    `name` varchar(50) comment '昵称',
    `password` char(32) not null comment '密码',
    primary key (`id`),
    unique key `login_name_unique` (`login_name`)
)engine = innodb default charset = utf8mb4 comment ='用户';

insert into `user`(id, login_name, name, password) value
    ('10000000','test','测试','asdfghjklzxcvbnmqwertyuiopqwerty');

select * from user;