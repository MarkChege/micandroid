-- phpMyAdmin SQL Dump
-- version 3.3.3
-- http://www.phpmyadmin.net
--
-- ����: localhost
-- ��������: 2010 �� 12 �� 12 �� 23:25
-- �������汾: 5.0.91
-- PHP �汾: 5.2.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- ���ݿ�: `test`
--

-- --------------------------------------------------------

--
-- ��Ľṹ `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `menu_id` bigint(20) NOT NULL auto_increment,
  `menu_title` varchar(50) NOT NULL,
  `menu_icon` varchar(50) default NULL,
  `menu_url` varchar(120) default NULL,
  `menu_description` text,
  `menu_parent` bigint(20) default NULL,
  `menu_order` int(11) default NULL COMMENT '�˵���˳��',
  PRIMARY KEY  (`menu_id`),
  KEY `menu_parent` (`menu_parent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- ת����е����� `sys_menu`
--


-- --------------------------------------------------------

--
-- ��Ľṹ `sys_roles`
--

DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE IF NOT EXISTS `sys_roles` (
  `role_id` bigint(20) NOT NULL auto_increment,
  `role_name` varchar(30) NOT NULL,
  `role_desc` varchar(100) NOT NULL,
  `role_editable` tinyint(4) default '1',
  PRIMARY KEY  (`role_id`),
  KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ȩ�ޱ�' AUTO_INCREMENT=1 ;

--
-- ת����е����� `sys_roles`
--


-- --------------------------------------------------------

--
-- ��Ľṹ `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`role_id`,`menu_id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- ת����е����� `sys_role_menu`
--


-- --------------------------------------------------------

--
-- ��Ľṹ `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` bigint(20) NOT NULL auto_increment COMMENT '��������',
  `user_name` varchar(50) NOT NULL COMMENT '�û���',
  `user_pwd` varchar(50) NOT NULL COMMENT '����',
  `user_mail` varchar(50) NOT NULL COMMENT '����',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- ת����е����� `sys_user`
--

INSERT INTO `sys_user` (`user_id`, `user_name`, `user_pwd`, `user_mail`) VALUES
(1, 'yufeng', '111111', 'yufeng0528@126.com'),
(2, 'wuyuyue', '111111', 'yufeng0528@126.com');


--
-- Constraints for table `sys_role_menu`
--
ALTER TABLE `sys_role_menu`
  ADD CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`menu_id`) ON DELETE CASCADE