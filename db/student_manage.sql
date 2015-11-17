SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE IF NOT EXISTS `academy` (
  `academy_id` smallint(5) unsigned NOT NULL COMMENT '学院ID',
  `name` char(16) NOT NULL COMMENT '学院名字'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学院信息表';

CREATE TABLE IF NOT EXISTS `class` (
  `class_id` smallint(5) unsigned NOT NULL COMMENT '班级ID',
  `major_id` smallint(5) unsigned NOT NULL COMMENT '专业ID',
  `name` char(16) NOT NULL COMMENT '班级名字'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级信息表';
CREATE TABLE IF NOT EXISTS `class_view` (
`academy_id` smallint(5) unsigned
,`academy_name` char(16)
,`major_id` smallint(5) unsigned
,`major_name` char(16)
,`class_id` smallint(5) unsigned
,`class_name` char(16)
);

CREATE TABLE IF NOT EXISTS `course` (
  `course_id` smallint(5) unsigned NOT NULL COMMENT '课程ID',
  `name` char(16) NOT NULL COMMENT '课程名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程信息表';

CREATE TABLE IF NOT EXISTS `log` (
  `logdate` text NOT NULL,
  `classname` text NOT NULL,
  `message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `logging_event` (
  `timestmp` bigint(20) NOT NULL,
  `formatted_message` text NOT NULL,
  `logger_name` varchar(254) NOT NULL,
  `level_string` varchar(254) NOT NULL,
  `thread_name` varchar(254) DEFAULT NULL,
  `reference_flag` smallint(6) DEFAULT NULL,
  `arg0` varchar(254) DEFAULT NULL,
  `arg1` varchar(254) DEFAULT NULL,
  `arg2` varchar(254) DEFAULT NULL,
  `arg3` varchar(254) DEFAULT NULL,
  `caller_filename` varchar(254) NOT NULL,
  `caller_class` varchar(254) NOT NULL,
  `caller_method` varchar(254) NOT NULL,
  `caller_line` char(4) NOT NULL,
  `event_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `logging_event_exception` (
  `event_id` bigint(20) NOT NULL,
  `i` smallint(6) NOT NULL,
  `trace_line` varchar(254) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `logging_event_property` (
  `event_id` bigint(20) NOT NULL,
  `mapped_key` varchar(254) NOT NULL,
  `mapped_value` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `major` (
  `major_id` smallint(5) unsigned NOT NULL COMMENT '专业ID',
  `academy_id` smallint(5) unsigned NOT NULL COMMENT '学院ID',
  `name` char(16) NOT NULL COMMENT '专业名字'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专业信息表';
CREATE TABLE IF NOT EXISTS `major_view` (
`academy_id` smallint(5) unsigned
,`academy_name` char(16)
,`major_id` smallint(5) unsigned
,`major_name` char(16)
);

CREATE TABLE IF NOT EXISTS `score` (
  `student_id` int(10) unsigned NOT NULL COMMENT '学生ID',
  `course_id` smallint(5) unsigned NOT NULL COMMENT '科目ID',
  `score` smallint(5) unsigned NOT NULL COMMENT '成绩'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生成绩表';
CREATE TABLE IF NOT EXISTS `score_view` (
`student_id` int(10) unsigned
,`student_name` char(16)
,`academy_name` char(16)
,`major_name` char(16)
,`class_name` char(16)
,`course_name` char(16)
,`score` smallint(5) unsigned
,`academy_id` smallint(5) unsigned
,`major_id` smallint(5) unsigned
,`class_id` smallint(5) unsigned
,`course_id` smallint(5) unsigned
,`scale` smallint(4) unsigned
);

CREATE TABLE IF NOT EXISTS `student` (
  `student_id` int(10) unsigned NOT NULL COMMENT '学生学号',
  `name` char(16) NOT NULL COMMENT '学生名字',
  `scale` smallint(4) unsigned NOT NULL COMMENT '年级',
  `class_id` smallint(5) unsigned NOT NULL COMMENT '班级ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生信息表';
CREATE TABLE IF NOT EXISTS `student_view` (
`student_id` int(10) unsigned
,`academy_name` char(16)
,`major_name` char(16)
,`class_name` char(16)
,`student_name` char(16)
,`scale` smallint(4) unsigned
,`academy_id` smallint(5) unsigned
,`major_id` smallint(5) unsigned
,`class_id` smallint(5) unsigned
);

CREATE TABLE IF NOT EXISTS `user` (
  `uid` smallint(5) unsigned NOT NULL COMMENT '用户ID',
  `username` char(12) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `auth` smallint(5) unsigned NOT NULL COMMENT '权限值'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='登录用户表';
DROP TABLE IF EXISTS `class_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `class_view` AS select `major_view`.`academy_id` AS `academy_id`,`major_view`.`academy_name` AS `academy_name`,`class`.`major_id` AS `major_id`,`major_view`.`major_name` AS `major_name`,`class`.`class_id` AS `class_id`,`class`.`name` AS `class_name` from (`class` join `major_view` on((`major_view`.`major_id` = `class`.`major_id`)));
DROP TABLE IF EXISTS `major_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `major_view` AS select `academy`.`academy_id` AS `academy_id`,`academy`.`name` AS `academy_name`,`major`.`major_id` AS `major_id`,`major`.`name` AS `major_name` from (`major` join `academy` on((`academy`.`academy_id` = `major`.`academy_id`)));
DROP TABLE IF EXISTS `score_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `score_view` AS select `score`.`student_id` AS `student_id`,`student_view`.`student_name` AS `student_name`,`student_view`.`academy_name` AS `academy_name`,`student_view`.`major_name` AS `major_name`,`student_view`.`class_name` AS `class_name`,`course`.`name` AS `course_name`,`score`.`score` AS `score`,`student_view`.`academy_id` AS `academy_id`,`student_view`.`major_id` AS `major_id`,`student_view`.`class_id` AS `class_id`,`score`.`course_id` AS `course_id`,`student_view`.`scale` AS `scale` from ((`score` join `student_view` on((`student_view`.`student_id` = `score`.`student_id`))) join `course` on((`course`.`course_id` = `score`.`course_id`)));
DROP TABLE IF EXISTS `student_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `student_view` AS select `student`.`student_id` AS `student_id`,`class_view`.`academy_name` AS `academy_name`,`class_view`.`major_name` AS `major_name`,`class_view`.`class_name` AS `class_name`,`student`.`name` AS `student_name`,`student`.`scale` AS `scale`,`class_view`.`academy_id` AS `academy_id`,`class_view`.`major_id` AS `major_id`,`class_view`.`class_id` AS `class_id` from (`student` join `class_view` on((`class_view`.`class_id` = `student`.`class_id`)));


ALTER TABLE `academy`
  ADD PRIMARY KEY (`academy_id`);

ALTER TABLE `class`
  ADD PRIMARY KEY (`class_id`),
  ADD KEY `major_id` (`major_id`);

ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`);

ALTER TABLE `logging_event`
  ADD PRIMARY KEY (`event_id`);

ALTER TABLE `logging_event_exception`
  ADD PRIMARY KEY (`event_id`,`i`);

ALTER TABLE `logging_event_property`
  ADD PRIMARY KEY (`event_id`,`mapped_key`);

ALTER TABLE `major`
  ADD PRIMARY KEY (`major_id`),
  ADD KEY `academy_id` (`academy_id`);

ALTER TABLE `score`
  ADD PRIMARY KEY (`student_id`,`course_id`),
  ADD KEY `course_id` (`course_id`);

ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `class_id` (`class_id`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`);


ALTER TABLE `academy`
  MODIFY `academy_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '学院ID';
ALTER TABLE `class`
  MODIFY `class_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '班级ID';
ALTER TABLE `course`
  MODIFY `course_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '课程ID';
ALTER TABLE `logging_event`
  MODIFY `event_id` bigint(20) NOT NULL AUTO_INCREMENT;
ALTER TABLE `major`
  MODIFY `major_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '专业ID';
ALTER TABLE `user`
  MODIFY `uid` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID';

ALTER TABLE `class`
  ADD CONSTRAINT `class_ibfk_1` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE CASCADE;

ALTER TABLE `logging_event_exception`
  ADD CONSTRAINT `logging_event_exception_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`);

ALTER TABLE `logging_event_property`
  ADD CONSTRAINT `logging_event_property_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`);

ALTER TABLE `major`
  ADD CONSTRAINT `major_ibfk_1` FOREIGN KEY (`academy_id`) REFERENCES `academy` (`academy_id`) ON DELETE CASCADE;

ALTER TABLE `score`
  ADD CONSTRAINT `score_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `score_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE;

ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
