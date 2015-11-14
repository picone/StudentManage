-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-11-14 14:34:08
-- 服务器版本： 5.6.25-log
-- PHP Version: 5.5.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `student_manage`
--

-- --------------------------------------------------------

--
-- 表的结构 `academy`
--

CREATE TABLE IF NOT EXISTS `academy` (
  `academy_id` smallint(5) unsigned NOT NULL COMMENT '学院ID',
  `name` char(16) NOT NULL COMMENT '学院名字'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='学院信息表';

-- --------------------------------------------------------

--
-- 表的结构 `class`
--

CREATE TABLE IF NOT EXISTS `class` (
  `class_id` smallint(5) unsigned NOT NULL COMMENT '班级ID',
  `major_id` smallint(5) unsigned NOT NULL COMMENT '专业ID',
  `name` char(16) NOT NULL COMMENT '班级名字'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='班级信息表';

-- --------------------------------------------------------

--
-- 替换视图以便查看 `class_view`
--
CREATE TABLE IF NOT EXISTS `class_view` (
`academy_id` smallint(5) unsigned
,`academy_name` char(16)
,`major_id` smallint(5) unsigned
,`major_name` char(16)
,`class_id` smallint(5) unsigned
,`class_name` char(16)
);

-- --------------------------------------------------------

--
-- 表的结构 `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `course_id` smallint(5) unsigned NOT NULL COMMENT '课程ID',
  `name` char(16) NOT NULL COMMENT '课程名称'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='课程信息表';

-- --------------------------------------------------------

--
-- 表的结构 `major`
--

CREATE TABLE IF NOT EXISTS `major` (
  `major_id` smallint(5) unsigned NOT NULL COMMENT '专业ID',
  `academy_id` smallint(5) unsigned NOT NULL COMMENT '学院ID',
  `name` char(16) NOT NULL COMMENT '专业名字'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='专业信息表';

-- --------------------------------------------------------

--
-- 替换视图以便查看 `major_view`
--
CREATE TABLE IF NOT EXISTS `major_view` (
`academy_id` smallint(5) unsigned
,`academy_name` char(16)
,`major_id` smallint(5) unsigned
,`major_name` char(16)
);

-- --------------------------------------------------------

--
-- 表的结构 `score`
--

CREATE TABLE IF NOT EXISTS `score` (
  `student_id` int(9) unsigned NOT NULL COMMENT '学生ID',
  `course_id` smallint(5) unsigned NOT NULL COMMENT '科目ID',
  `score` smallint(5) unsigned NOT NULL COMMENT '成绩',
  `create_time` int(10) unsigned NOT NULL COMMENT '创建时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='学生成绩表';

-- --------------------------------------------------------

--
-- 替换视图以便查看 `score_view`
--
CREATE TABLE IF NOT EXISTS `score_view` (
`student_id` int(9) unsigned
,`student_name` char(16)
,`academy_name` char(16)
,`major_name` char(16)
,`class_name` char(16)
,`course_name` char(16)
,`score` smallint(5) unsigned
,`academy_id` smallint(5) unsigned
,`major_id` smallint(5) unsigned
,`class_id` smallint(5) unsigned
,`scale` smallint(4) unsigned
);

-- --------------------------------------------------------

--
-- 表的结构 `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `student_id` int(9) unsigned NOT NULL COMMENT '学生学号',
  `name` char(16) NOT NULL COMMENT '学生名字',
  `scale` smallint(4) unsigned NOT NULL COMMENT '年级',
  `class_id` smallint(5) unsigned NOT NULL COMMENT '班级ID'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='学生信息表';

-- --------------------------------------------------------

--
-- 替换视图以便查看 `student_view`
--
CREATE TABLE IF NOT EXISTS `student_view` (
`student_id` int(9) unsigned
,`academy_name` char(16)
,`major_name` char(16)
,`class_name` char(16)
,`student_name` char(16)
,`scale` smallint(4) unsigned
,`academy_id` smallint(5) unsigned
,`major_id` smallint(5) unsigned
,`class_id` smallint(5) unsigned
);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `uid` smallint(5) unsigned NOT NULL COMMENT '用户ID',
  `username` char(12) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `auth` smallint(5) unsigned NOT NULL COMMENT '权限值'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='登录用户表';

-- --------------------------------------------------------

--
-- 视图结构 `class_view`
--
DROP TABLE IF EXISTS `class_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `class_view` AS select `major_view`.`academy_id` AS `academy_id`,`major_view`.`academy_name` AS `academy_name`,`class`.`major_id` AS `major_id`,`major_view`.`major_name` AS `major_name`,`class`.`class_id` AS `class_id`,`class`.`name` AS `class_name` from (`class` join `major_view` on((`major_view`.`major_id` = `class`.`major_id`)));

-- --------------------------------------------------------

--
-- 视图结构 `major_view`
--
DROP TABLE IF EXISTS `major_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `major_view` AS select `academy`.`academy_id` AS `academy_id`,`academy`.`name` AS `academy_name`,`major`.`major_id` AS `major_id`,`major`.`name` AS `major_name` from (`major` join `academy` on((`academy`.`academy_id` = `major`.`academy_id`)));

-- --------------------------------------------------------

--
-- 视图结构 `score_view`
--
DROP TABLE IF EXISTS `score_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `score_view` AS select `score`.`student_id` AS `student_id`,`student_view`.`student_name` AS `student_name`,`student_view`.`academy_name` AS `academy_name`,`student_view`.`major_name` AS `major_name`,`student_view`.`class_name` AS `class_name`,`course`.`name` AS `course_name`,`score`.`score` AS `score`,`student_view`.`academy_id` AS `academy_id`,`student_view`.`major_id` AS `major_id`,`student_view`.`class_id` AS `class_id`,`student_view`.`scale` AS `scale` from ((`score` join `student_view` on((`student_view`.`student_id` = `score`.`student_id`))) join `course` on((`course`.`course_id` = `score`.`course_id`)));

-- --------------------------------------------------------

--
-- 视图结构 `student_view`
--
DROP TABLE IF EXISTS `student_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `student_view` AS select `student`.`student_id` AS `student_id`,`class_view`.`academy_name` AS `academy_name`,`class_view`.`major_name` AS `major_name`,`class_view`.`class_name` AS `class_name`,`student`.`name` AS `student_name`,`student`.`scale` AS `scale`,`class_view`.`academy_id` AS `academy_id`,`class_view`.`major_id` AS `major_id`,`class_view`.`class_id` AS `class_id` from (`student` join `class_view` on((`class_view`.`class_id` = `student`.`class_id`)));

--
-- Indexes for dumped tables
--

--
-- Indexes for table `academy`
--
ALTER TABLE `academy`
  ADD PRIMARY KEY (`academy_id`);

--
-- Indexes for table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `major`
--
ALTER TABLE `major`
  ADD PRIMARY KEY (`major_id`);

--
-- Indexes for table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`student_id`,`course_id`),
  ADD KEY `score` (`course_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `class_id` (`class_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `academy`
--
ALTER TABLE `academy`
  MODIFY `academy_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '学院ID';
--
-- AUTO_INCREMENT for table `class`
--
ALTER TABLE `class`
  MODIFY `class_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '班级ID';
--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `course_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '课程ID';
--
-- AUTO_INCREMENT for table `major`
--
ALTER TABLE `major`
  MODIFY `major_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '专业ID';
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `uid` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
