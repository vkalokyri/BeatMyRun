
-- phpMyAdmin SQL Dump
-- version 2.11.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 12, 2015 at 12:14 PM
-- Server version: 5.1.57
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `a2166115_bmrdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `Challenges`
--

CREATE TABLE `Challenges` (
  `sender_id` varchar(30) NOT NULL,
  `receiver_id` varchar(30) NOT NULL,
  `datetime` datetime NOT NULL,
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`sender_id`,`receiver_id`,`datetime`),
  KEY `receiver_id_constraint` (`receiver_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Challenges`
--

INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-26 18:00:42', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-01 18:23:33', '103069514385155393340');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-30 08:23:42', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-12-04 08:34:23', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-12-03 07:13:45', '103069514385155393340');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-12-04 03:53:36', '103069514385155393340');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-04 07:32:14', '103069514385155393340');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-21 17:43:21', '115998150174651530097');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-16 18:32:11', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-14 17:45:32', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-12-02 06:15:46', '115998150174651530097');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-11-28 07:32:15', '115998150174651530097');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '103069514385155393340', '2015-12-04 07:03:52', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '103069514385155393340', '2015-11-26 18:00:42', '115998150174651530097');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '103069514385155393340', '2015-11-01 18:23:33', '115998150174651530097');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '103069514385155393340', '2015-11-30 08:23:42', '103069514385155393340');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '103069514385155393340', '2015-12-04 08:34:23', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '103069514385155393340', '2015-12-03 07:13:45', 'pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '105183512492782409128', '2015-12-04 09:00:19', 'Pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '105183512492782409128', '2015-12-04 17:35:23', 'Pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('134', '87876', '2015-05-13 10:23:00', '134');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('656789', '11', '2015-11-26 18:00:41', '11');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('123', '134', '2015-09-01 00:20:45', '134');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '105183512492782409128', '2015-12-04 17:06:18', 'Pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '105183512492782409128', '2015-12-04 17:07:46', 'Pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '105183512492782409128', '2015-12-04 18:02:37', 'Pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('115998150174651530097', '105183512492782409128', '2015-12-04 18:53:32', 'Pending');
INSERT INTO `Challenges` (`sender_id`, `receiver_id`, `datetime`, `status`) VALUES('103069514385155393340', '115998150174651530097', '2015-12-09 22:45:45', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `MusicPreference`
--

CREATE TABLE `MusicPreference` (
  `youtube_id` varchar(30) NOT NULL,
  `user_id` varchar(30) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`youtube_id`,`user_id`),
  KEY `user_id_constraint` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MusicPreference`
--

INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('9bZkp7q19f0', '103069514385155393340', '2015-12-02 23:41:11');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('b8I-7Wk_Vbc', '103069514385155393340', '2015-12-02 23:42:05');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('GemKqzILV4w', '103069514385155393340', '2015-12-02 23:42:36');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('qF4kn_mydyk', '103069514385155393340', '2015-12-03 18:59:52');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('yViIi3gie2c', '103069514385155393340', '2015-12-03 22:02:44');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('''yViIi3gie2c''', '''103069514385155393340''', '0000-00-00 00:00:00');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('''b8I-7Wk_Vbc''', '''103069514385155393340''', '0000-00-00 00:00:00');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('''YWRcqF4FNqg''', '''103069514385155393340''', '0000-00-00 00:00:00');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('''9bZkp7q19f0''', '''103069514385155393340''', '0000-00-00 00:00:00');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('''GemKqzILV4w''', '''103069514385155393340''', '0000-00-00 00:00:00');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('''DK_0jXPuIr0''', '''103069514385155393340''', '0000-00-00 00:00:00');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('XLgYAHHkPFs', '103505038699672209399', '2015-12-03 22:16:38');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('6hzrDeceEKc', '103505038699672209399', '2015-12-03 22:16:38');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('1lyu1KKwC74', '103505038699672209399', '2015-12-03 22:16:39');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('OnTelUJrDhc', '103505038699672209399', '2015-12-03 22:16:39');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('GCxkOAG7TZA', '103505038699672209399', '2015-12-03 22:16:39');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('VG1TmWL4CEY', '103505038699672209399', '2015-12-03 22:16:40');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('6nZGv8VTBVE', '103505038699672209399', '2015-12-03 22:16:40');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('SfyMxDoxIpM', '103505038699672209399', '2015-12-03 22:16:41');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('qf0cRvPwqQY', '103505038699672209399', '2015-12-03 22:16:41');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('ZGb5Hl0Qfrc', '103505038699672209399', '2015-12-03 22:16:41');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('YWRcqF4FNqg', '103069514385155393340', '2015-12-04 00:34:53');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('DK_0jXPuIr0', '103069514385155393340', '2015-12-04 00:34:54');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('DK_0jXPuIr0', '115998150174651530097', '2015-12-04 05:13:07');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('qF4kn_mydyk', '115998150174651530097', '2015-12-04 05:13:07');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('sX7fd8uQles', '115998150174651530097', '2015-12-04 05:13:07');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('GEPvsn6JA_c', '115998150174651530097', '2015-12-04 05:13:07');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('-a6Ped7Uaqw', '115998150174651530097', '2015-12-04 05:13:07');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('GKkr29EGc-Y', '115998150174651530097', '2015-12-04 05:13:07');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('XFkzRNyygfk', '115998150174651530097', '2015-12-04 05:13:08');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('u5CVsCnxyXg', '115998150174651530097', '2015-12-04 05:13:08');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('CxCk2xogNlw', '115998150174651530097', '2015-12-04 05:13:08');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('lBmPqoAQu1o', '115998150174651530097', '2015-12-04 05:13:08');
INSERT INTO `MusicPreference` (`youtube_id`, `user_id`, `timestamp`) VALUES('IrTB-iiecqk', '115998150174651530097', '2015-12-04 05:13:08');

-- --------------------------------------------------------

--
-- Table structure for table `RunInfo`
--

CREATE TABLE `RunInfo` (
  `user_id` varchar(30) NOT NULL,
  `datetime` datetime NOT NULL,
  `distance` double NOT NULL,
  `duration` double NOT NULL,
  `steps` int(11) NOT NULL,
  `calories` double NOT NULL,
  `location` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`,`datetime`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `RunInfo`
--

INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-11-16 18:32:11', 2.55, 45.7, 8767, 98, 'Iselin');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-11-01 18:23:33', 1.88, 34.6, 6567, 11, 'Iselin');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-02 06:15:46', 1.22, 15.46, 1560, 144, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-11-30 08:23:42', 2.22, 44.7, 7657, 167, 'Iselin');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 08:34:23', 2.77, 72.8, 7689, 333, 'Iselin');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-03 07:13:45', 5.77, 55, 534, 100, 'Edison');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 03:53:36', 3.88, 4.32, 857, 122, 'Edison');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-02 22:24:09', 2.45, 11.8, 7655, 344, 'Edison');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-02 22:24:09', 10, 10, 10, 10, 'NB');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-04 03:53:36', 0.014291533, 0.733, 23, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-03 07:13:45', 3.1, 30, 3780, 780, 'Edison');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-04 08:34:23', 2.56, 18.43, 3569, 300, 'Islien');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-30 08:23:42', 4.22, 40, 6540, 860, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-01 18:23:33', 1.45, 15.32, 2548, 546, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-04 07:32:14', 5.3, 45, 7893, 920, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-14 17:45:32', 1.02, 10.55, 900, 207, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-16 18:32:11', 3.56, 30.43, 3062, 800, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-21 17:43:21', 5, 50.32, 3997, 850, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-26 18:00:42', 1.43, 14.23, 2130, 452, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-28 07:32:15', 3.24, 42.14, 6002, 592, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-10-01 07:12:00', 2.47, 20, 4560, 400, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-10-03 14:03:22', 2.43, 20.44, 4582, 742, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-10-07 18:10:33', 4.32, 40.32, 8000, 962, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-11-06 19:13:41', 4.11, 43.32, 8753, 632, 'New Brunswick');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-11-26 18:00:42', 4.6, 13.8, 576, 0, 'Edison');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 07:03:52', 0.03106855, 0.777, 50, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-04 03:50:27', 0.008699194, 0.505, 14, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('56', '0000-00-00 00:00:00', 765, 878, 565, 767, 'Texas');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 09:00:19', 0, 0.392, 0, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 13:17:10', 0.027340324, 0.312, 44, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 13:26:20', 0.011184678, 0.108, 18, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 13:52:52', 0, 348.867, 0, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('123', '2015-09-01 00:20:45', 56, 768, 876788, 6546, 'California');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('134', '2015-05-13 10:23:00', 5646, 567, 76576, 123445, 'Texas');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('87876', '2015-07-13 11:22:11', 76567, 9899, 22, 6767, 'Hawaii');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('7576', '2015-08-09 00:00:55', 65, 8767, 344, 1233, 'California');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('656789', '2015-11-26 18:00:41', 767, 57, 99, 1234, 'California');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('11', '2015-12-04 08:30:23', 667, 334, 666, 788, 'Hawaii');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('55', '2015-11-30 08:23:40', 765, 333, 999, 666, 'Georgia');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:06:18', 0.014912904, 0.948, 24, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:07:46', -0.845685931, 0.321, -1361, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:25:25', -0.845685931, 0.43, -1361, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:30:26', 0.079535488, 3.454, 128, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:31:37', 0.037903631, 0.711, 61, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:33:39', -0.96312505, 0.209, -1550, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:35:23', 0.028583066, 1.256, 46, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 17:57:29', -1.061923039, 0.565, -1709, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 18:02:37', 0.029204437, 0.798, 47, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 18:03:57', 0.016777017, 0.58, 27, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 18:10:49', 0.032932663, 2.609, 53, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 18:53:32', 0.044117341, 3.182, 71, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 18:55:04', 0.019262501, 0.613, 31, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('115998150174651530097', '2015-12-04 18:59:35', -1.484455319, 0.12, -2389, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-05 12:23:14', -3.274003799, 0.283, -5269, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('', '0000-00-00 00:00:00', 0, 0, 0, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-09 22:45:45', 0.016777017, 0.718, 27, 0, '');
INSERT INTO `RunInfo` (`user_id`, `datetime`, `distance`, `duration`, `steps`, `calories`, `location`) VALUES('103069514385155393340', '2015-12-09 22:50:15', 0.015534275, 0.594, 25, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `SongInfo`
--

CREATE TABLE `SongInfo` (
  `youtube_id` varchar(30) NOT NULL,
  `title` varchar(50) CHARACTER SET utf8 NOT NULL,
  `artist` varchar(50) NOT NULL,
  `tempo` double NOT NULL,
  `liveness` double NOT NULL,
  `duration` double NOT NULL,
  `energy` double NOT NULL,
  `danceability` double NOT NULL,
  PRIMARY KEY (`youtube_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SongInfo`
--

INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('XLgYAHHkPFs', 'Love * John lennon', 'John Lennon', 91.983, -7.982, 140.86667, 0.419721, 0.498483);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('9bZkp7q19f0', 'Gangnam Style', 'Gangnam Style Band', 131.883, -5.238, 218.19646, 0.836662, 0.826202);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('b8I-7Wk_Vbc', 'Bailando featuring Sean Paul, Descemer Bueno, Gent', 'Enrique Iglesias', 9.971, -3.507, 243.21333, 0.850699, 0.717284);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('CxCk2xogNlw', 'Mi Historia Entre Tus Dedos', 'Danny Marin', 123.043, -3.727, 330.91918, 0.802237, 0.882189);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('DK_0jXPuIr0', 'What Do You Mean?', 'What Do You Mean', 125.057, -7.359, 204.05655, 0.751975, 0.723225);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('GemKqzILV4w', 'Chasing cars', 'Chasing Cars', 30.929, -6.912, 263.2098, 0.497609, 0.525158);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('GEPvsn6JA_c', 'Greek Dance', 'The New Greek Ensemble', 205.37, -12.685, 192.62667, 0.484808, 0.270896);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('GKkr29EGc-Y', 'High And Dry', 'Radiohead', 87.615, -9.523, 257.50667, 0.420504, 0.413975);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('IrTB-iiecqk', 'Private DJ', 'DJ Private', 130.074, -9.328, 224.99864, 0.745418, 0.800324);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('lBmPqoAQu1o', 'Bachata Parranda', 'Bachata', 124.973, -10.424, 227.51955, 0.536287, 0.838653);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('qF4kn_mydyk', 'Triangle', 'Jan A.P. Kaczmarek', 69.996, -16.041, 185.02667, 0.166906, 0.263993);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('sX7fd8uQles', 'The Rip Tide', 'Beirut', 148.909, -7.794, 266.02667, 0.549573, 0.394326);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('u5CVsCnxyXg', 'No Surprises', 'Radiohead', 74.719, -15.611, 238.05288, 0.288891, 0.191451);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('XFkzRNyygfk', 'Creep', 'Radiohead', 91.847, -11.885, 235.38667, 0.383212, 0.524162);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('YWRcqF4FNqg', 'Danza Kuduro (Homenaje a Don Omar & Lucenzo) (Vers', 'Senora Danza Kuduro', 131.219, -6.23, 202.76993, 0.860878, 0.731455);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('6hzrDeceEKc', 'Wonderwall', 'Oasis', 174.727, -6.459, 258.71673, 0.845429, 0.401859);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('1lyu1KKwC74', 'Bitter Sweet Symphony', 'The Verve', 171.052, -5.409, 358.33333, 0.907249, 0.382857);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('OnTelUJrDhc', '03-inxs-afterglow', 'INXS', 105.098, -9.6, 259.36354, 0.414803, 0.406529);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('GCxkOAG7TZA', 'Science', 'Science', 113.636, -6.633, 99.14621, 0.921206, 0.385684);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('VG1TmWL4CEY', 'A Little Less Conversation (DJ Remixed)', 'DJ Remixed', 127.974, -11.598, 82.63256, 0.830068, 0.573607);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('6nZGv8VTBVE', 'Book Of Love', 'Book of Love', 140.046, -12.665, 275.06667, 0.710439, 0.705106);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('SfyMxDoxIpM', 'Soniyo', 'Raza Rich', 135.034, -4.741, 185.30086, 0.970245, 0.535006);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('qf0cRvPwqQY', 'Ontore Bahire', 'Arif', 90.25, -9.172, 276.28, 0.79724, 0.695333);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('ZGb5Hl0Qfrc', 'Sleep No More', 'Acropolis', 89.936, -4.361, 247.54748, 0.982517, 0.454906);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('yViIi3gie2c', 'Music from The "Batman V Superman: Dawn of Justice', 'L''Orchestra Cinematique', 76.102, -12.383, 210.28, 0.622024, 0.069051);
INSERT INTO `SongInfo` (`youtube_id`, `title`, `artist`, `tempo`, `liveness`, `duration`, `energy`, `danceability`) VALUES('-a6Ped7Uaqw', 'ÎÎ•Î‘ Î–Î©Î—', 'SeanLuke', 88.006, -10.196, 207.9541, 0.364756, 0.718447);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `id` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `age` int(11) NOT NULL,
  `height` int(11) NOT NULL,
  `weight` double NOT NULL,
  `location` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `email`, `name`, `age`, `height`, `weight`, `location`) VALUES('103069514385155393340', 'rahulshome.in@gmail.com', 'Rahul Shome', 26, 173, 66, '');
INSERT INTO `User` (`id`, `email`, `name`, `age`, `height`, `weight`, `location`) VALUES('105183512492782409128', 'tharaphilips@gmail.com', 'Thara Philipson', 0, 0, 0, '');
INSERT INTO `User` (`id`, `email`, `name`, `age`, `height`, `weight`, `location`) VALUES('115998150174651530097', 'vkalokyri@gmail.com', 'Valia Kalokyri', 27, 160, 54, '');
INSERT INTO `User` (`id`, `email`, `name`, `age`, `height`, `weight`, `location`) VALUES('testid', 'test@test.com', 'Testname', 0, 0, 0, '');
INSERT INTO `User` (`id`, `email`, `name`, `age`, `height`, `weight`, `location`) VALUES('103505038699672209399', 'rahul1989kool@gmail.com', 'Rahul Shome', 0, 0, 0, '');
INSERT INTO `User` (`id`, `email`, `name`, `age`, `height`, `weight`, `location`) VALUES('', '', '', 0, 0, 0, '');
