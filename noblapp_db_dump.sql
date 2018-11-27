-- --------------------------------------------------------
-- 호스트:                          222.239.248.192
-- 서버 버전:                        10.1.21-MariaDB - Source distribution
-- 서버 OS:                        Linux
-- HeidiSQL 버전:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- map2_seongnam 데이터베이스 구조 내보내기
DROP DATABASE IF EXISTS `map2_seongnam`;
CREATE DATABASE IF NOT EXISTS `map2_seongnam` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `map2_seongnam`;


-- 테이블 map2_seongnam.events 구조 내보내기
DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (
  `eid` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(100) DEFAULT NULL COMMENT '행사 포스터',
  `pid` int(11) DEFAULT NULL COMMENT '관련 장소',
  `status` tinyint(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='지역 축제, 행사를 등록하고 \r\n처음에 들어왔을 때 팝업으로 띄움.';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.events_cf 구조 내보내기
DROP TABLE IF EXISTS `events_cf`;
CREATE TABLE IF NOT EXISTS `events_cf` (
  `eid` int(11) DEFAULT NULL,
  `ko` text COMMENT '한국어',
  `en` text COMMENT '영어',
  `cn` text COMMENT '중국어',
  `jp` text COMMENT '일본어'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='행사 커스텀 필드 데이터.';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.events_nm 구조 내보내기
DROP TABLE IF EXISTS `events_nm`;
CREATE TABLE IF NOT EXISTS `events_nm` (
  `eid` int(11) DEFAULT NULL,
  `ko` varchar(500) DEFAULT NULL COMMENT '한국어',
  `en` varchar(500) DEFAULT NULL COMMENT '영어',
  `cn` varchar(500) DEFAULT NULL COMMENT '중국어',
  `jp` varchar(500) DEFAULT NULL COMMENT '일본어'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='행사 이름 테이블';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.events_tx 구조 내보내기
DROP TABLE IF EXISTS `events_tx`;
CREATE TABLE IF NOT EXISTS `events_tx` (
  `eid` int(11) DEFAULT NULL,
  `ko` text COMMENT '한국어',
  `en` text COMMENT '영어',
  `cn` text COMMENT '중국어',
  `jp` text COMMENT '일본어'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='행사 설명 테이블\r\n';

-- 테이블 map2_seongnam.image_map 구조 내보내기
DROP TABLE IF EXISTS `image_map`;
CREATE TABLE IF NOT EXISTS `image_map` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `zoom` int(11) DEFAULT '0',
  `map_res_url` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `lat` double DEFAULT '0',
  `lng` double DEFAULT '0',
  `w` int(11) DEFAULT NULL,
  `h` int(11) DEFAULT NULL,
  `header` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `tile_info` varchar(300) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='이미지 지도에 관한 내용을 담는 곳.';

DROP TABLE IF EXISTS `image_map_nm`;
CREATE TABLE IF NOT EXISTS `image_map_nm` (
  `mid` int(11) NOT NULL,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.init_string_define 구조 내보내기
DROP TABLE IF EXISTS `init_string_define`;
CREATE TABLE IF NOT EXISTS `init_string_define` (
  `st_id` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `str_kor` varchar(500) DEFAULT '',
  `str_eng` varchar(500) DEFAULT '',
  `str_chn` varchar(500) DEFAULT '',
  `str_jpn` varchar(500) DEFAULT '',
  PRIMARY KEY (`st_id`),
  UNIQUE KEY `cname` (`cname`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pc 구조 내보내기
DROP TABLE IF EXISTS `pc`;
CREATE TABLE IF NOT EXISTS `pc` (
  `cid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `icon_url` varchar(50) DEFAULT '',
  `pcid` int(11) DEFAULT '0',
  `ccu` text,
  `sort` INT(11) NULL DEFAULT '100',
  `link` VARCHAR(200) NULL DEFAULT NULL COMMENT '링크 URL',
  `help_file` VARCHAR(200) NULL DEFAULT NULL COMMENT '도움말 파일',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Place Categories\n장소 카테고리';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pc_cf 구조 내보내기
DROP TABLE IF EXISTS `pc_cf`;
CREATE TABLE IF NOT EXISTS `pc_cf` (
  `cid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Category Custom Field Names\n장소 카테고리 전용 필드 이름';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pc_nm 구조 내보내기
DROP TABLE IF EXISTS `pc_nm`;
CREATE TABLE IF NOT EXISTS `pc_nm` (
  `cid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Place Category Names\n장소 카테고리 이름';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pictograms 구조 내보내기
DROP TABLE IF EXISTS `pictograms`;
CREATE TABLE IF NOT EXISTS `pictograms` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT '',
  `icon_url` varchar(50) DEFAULT '',
  `fill_color` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pl 구조 내보내기
DROP TABLE IF EXISTS `pl`;
CREATE TABLE IF NOT EXISTS `pl` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `images` text,
  `videos` text,
  `others` text,
  `phone` varchar(50) DEFAULT '',
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `map_type` int(4) NOT NULL DEFAULT '0',
  `map_res_url` varchar(100) DEFAULT NULL,
  `display_zoom` int(11) DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '1',
  `update_dt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places\n장소';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pl_ad 구조 내보내기
DROP TABLE IF EXISTS `pl_ad`;
CREATE TABLE IF NOT EXISTS `pl_ad` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Addresses\n장소 주소';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pl_ccv 구조 내보내기
DROP TABLE IF EXISTS `pl_ccv`;
CREATE TABLE IF NOT EXISTS `pl_ccv` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Category Custom Field Values\n장소 카테고리 전용 필드 값';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pl_cf 구조 내보내기
DROP TABLE IF EXISTS `pl_cf`;
CREATE TABLE IF NOT EXISTS `pl_cf` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Custom Field Names\n장소 전용 필드 이름';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pl_cv 구조 내보내기
DROP TABLE IF EXISTS `pl_cv`;
CREATE TABLE IF NOT EXISTS `pl_cv` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Custom Field Values\n장소 전용 필드 값';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pl_nm 구조 내보내기
DROP TABLE IF EXISTS `pl_nm`;
CREATE TABLE IF NOT EXISTS `pl_nm` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Names\n장소 이름';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.pl_tx 구조 내보내기
DROP TABLE IF EXISTS `pl_tx`;
CREATE TABLE IF NOT EXISTS `pl_tx` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Texts\n장소 설명';


-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.user 구조 내보내기
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(100) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `access_token` varchar(50) DEFAULT NULL,
  `type` tinyint(4) DEFAULT '1',
  `name` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `gender` char(1) DEFAULT '0',
  `point` int(11) unsigned DEFAULT '0',
  `status` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='회원정보';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.service_config 구조 내보내기
DROP TABLE IF EXISTS `service_config`;
CREATE TABLE IF NOT EXISTS `service_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `the_value` varchar(200) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.spot 구조 내보내기
DROP TABLE IF EXISTS `spot`;
CREATE TABLE IF NOT EXISTS `spot` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(11) unsigned DEFAULT '0',
  `mid` int(10) unsigned DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `map_type` int(4) NOT NULL DEFAULT '0',
  `map_res_url` varchar(100) DEFAULT NULL,
  `text_direction` varchar(10) DEFAULT NULL,
  `display_zoom` int(11) DEFAULT NULL,
  `display_zoom_max` tinyint(4) DEFAULT NULL,
  `w` int(11) DEFAULT NULL,
  `h` int(11) DEFAULT NULL,
  `name_display` varchar(10) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `text_rotate` VARCHAR(4) NULL DEFAULT NULL,
	`font_size` VARCHAR(2) NULL DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`pid`),
  KEY `FK1_image_map` (`mid`),
  CONSTRAINT `FK1_image_map` FOREIGN KEY (`mid`) REFERENCES `image_map` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places\n장소';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.spot_nm 구조 내보내기
DROP TABLE IF EXISTS `spot_nm`;
CREATE TABLE IF NOT EXISTS `spot_nm` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ko` text,
  `en` text,
  `cn` text,
  `jp` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Places Names\n장소 이름';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.store 구조 내보내기
DROP TABLE IF EXISTS `store`;
CREATE TABLE IF NOT EXISTS `store` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `master_uid` int(11) NOT NULL,
  `store_name` varchar(50) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `lat_x` double DEFAULT '0',
  `long_y` double DEFAULT '0',
  `phone` varchar(20) DEFAULT NULL,
  `business_number` varchar(50) DEFAULT NULL,
  `point` int(11) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sid`),
  KEY `FK_store_user` (`master_uid`),
  CONSTRAINT `FK_store_user` FOREIGN KEY (`master_uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='제휴업체';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.trekking_course 구조 내보내기
DROP TABLE IF EXISTS `trekking_course`;
CREATE TABLE IF NOT EXISTS `trekking_course` (
  `tc_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `course_name` varchar(50) DEFAULT NULL,
  `sector_count` int(10) unsigned DEFAULT '0',
  `color` varchar(8) NOT NULL DEFAULT '000000',
  `status` tinyint(4) DEFAULT '1',
  `chk_dist` double NOT NULL DEFAULT '0.01',
  `tc_type` tinyint(4) DEFAULT '1' COMMENT '1:일반, 2:이벤트',
  `cid` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`tc_id`),
  UNIQUE KEY `course_name` (`course_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='트랙킹 코스 정보';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.trekking_group 구조 내보내기
DROP TABLE IF EXISTS `trekking_group`;
CREATE TABLE IF NOT EXISTS `trekking_group` (
  `tcg_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tc_id` int(11) unsigned DEFAULT NULL,
  `g_idx` int(11) unsigned DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `pid` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`tcg_id`),
  UNIQUE KEY `tc_g_pair` (`tc_id`,`g_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.trekking_sector 구조 내보내기
DROP TABLE IF EXISTS `trekking_sector`;
CREATE TABLE IF NOT EXISTS `trekking_sector` (
  `tcs_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tc_id` int(11) unsigned DEFAULT NULL,
  `g_idx` int(11) unsigned DEFAULT NULL,
  `s_idx` int(11) unsigned DEFAULT NULL,
  `lat` double NOT NULL DEFAULT '0',
  `lng` double NOT NULL DEFAULT '0',
  `tcs_type` tinyint(4) DEFAULT '1' COMMENT '1:일반, 2:체크포인트',
  `status` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`tcs_id`),
  UNIQUE KEY `tc_id` (`tc_id`,`g_idx`,`s_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.user_spending 구조 내보내기
DROP TABLE IF EXISTS `user_spending`;
CREATE TABLE IF NOT EXISTS `user_spending` (
  `us_id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `issued_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `commit_time` datetime DEFAULT NULL,
  `point` int(11) unsigned DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`us_id`),
  UNIQUE KEY `uid_sid_issued_time` (`uid`,`sid`,`issued_time`),
  KEY `FK_user_spending_store` (`sid`),
  CONSTRAINT `FK_user_spending_store` FOREIGN KEY (`sid`) REFERENCES `store` (`sid`),
  CONSTRAINT `FK_user_spending_user` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.user_trekking_cert 구조 내보내기
DROP TABLE IF EXISTS `user_trekking_cert`;
CREATE TABLE IF NOT EXISTS `user_trekking_cert` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `tc_id` int(11) NOT NULL,
  `issued_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cid`),
  KEY `FK_user_trekking_cert_user` (`uid`),
  KEY `FK_user_trekking_cert_trekking_course` (`tc_id`),
  CONSTRAINT `FK_user_trekking_cert_user` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.user_trekking_fame 구조 내보내기
DROP TABLE IF EXISTS `user_trekking_fame`;
CREATE TABLE IF NOT EXISTS `user_trekking_fame` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `issued_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fid`),
  UNIQUE KEY `uid` (`uid`),
  KEY `FK_user_trekking_fame_user` (`uid`),
  CONSTRAINT `FK_user_trekking_fame_user` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='회원 트래킹 명예의 전당';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.user_trek_log 구조 내보내기
DROP TABLE IF EXISTS `user_trek_log`;
CREATE TABLE IF NOT EXISTS `user_trek_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `tc_id` int(11) unsigned DEFAULT NULL,
  `g_idx` int(11) unsigned DEFAULT NULL,
  `s_idx` int(11) unsigned DEFAULT NULL,
  `tcs_id` int(11) unsigned DEFAULT NULL,
  `issued_date` date NOT NULL,
  `issued_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `point` int(11) DEFAULT NULL,
  `cmplt` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`,`tcs_id`,`issued_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.visitor_log 구조 내보내기
DROP TABLE IF EXISTS `visitor_log`;
CREATE TABLE IF NOT EXISTS `visitor_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `month` varchar(11) DEFAULT NULL,
  `year` varchar(11) DEFAULT NULL,
  `remote_addr` varchar(50) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `servlet` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.visitor_log_archive 구조 내보내기
DROP TABLE IF EXISTS `visitor_log_archive`;
CREATE TABLE IF NOT EXISTS `visitor_log_archive` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `year` varchar(11) DEFAULT NULL,
  `month` varchar(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `counts` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date` (`date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 map2_seongnam.weather 구조 내보내기
DROP TABLE IF EXISTS `weather`;
CREATE TABLE IF NOT EXISTS `weather` (
  `temp` varchar(20) DEFAULT NULL,
  `desc` varchar(50) DEFAULT NULL,
  `dust_desc` varchar(50) DEFAULT NULL,
  `dust_value` varchar(20) DEFAULT NULL,
  `check_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='날씨';


-- 테이블 map2_seongnam.touch_log 구조 내보내기
DROP TABLE IF EXISTS `touch_log`;
CREATE TABLE IF NOT EXISTS `touch_log` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `table_type` char(1) NOT NULL DEFAULT '0' COMMENT 'P:pl, S: spot',
  `pid` int(11) NOT NULL DEFAULT '0',
  `cnt` int(11) DEFAULT '1' COMMENT '상세 팝업 클릭 수.',
  PRIMARY KEY (`table_type`,`pid`),
  KEY `tid` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='상세 팝업 클릭 수 로그.';
-- 내보낼 데이터가 선택되어 있지 않습니다.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;



LOCK TABLES `service_config` WRITE;
/*!40000 ALTER TABLE `service_config` DISABLE KEYS */;

INSERT INTO `service_config` (`id`, `name`, `the_value`, `description`) VALUES
	(1, 'trekking_point', '50', '트레킹 섹터 완료후 지급되는 포인트'),
	(2, 'trek_min_distance', '0.05', '트레킹 추적시 최소 근접 거리 (단위 km)'),
	(3, 'supported_language', 'ko,en,cn,jp', '지원 언어'),
	(4, 'service_region_name', '성남시', '서비스 지역 이름'),
	(5, 'base_lat', '37.420035', '서비스 지역의 기본 위치 (경도)'),
	(6, 'base_lng', '127.126634', '서비스 지역의 기본 위치 (위도)'),
	(7, 'service_region_coordinates', '서비스 지역 테두리', ''),
	(8, 'service_region_gid', 'sn', '서비스 지역 ID'),
	(9, 'service_region_sido', '경기', '서비스 지역 시도 이름'),
	(10, 'service_region_engname', 'seongnam', '서비스 지역 영어 이름'),
	(11, 'weather_xy', '{"x":63, "y":124}', '날씨 xy'),
	(12, 'site', 'http://www.seongnam.go.kr', '사이트'),
	(13, 'mayor', '성남시장 은 수 미', '서비스 지역 시장'),
	(14, 'APIKEYS.KAKAO.clientId', 'dd8a97052fae0d6e3d466a9b26fb097d', '카카오 API 키'),
	(15, 'APIKEYS.GOOGLE.apikey', 'AIzaSyCjkVjzKyMzaVZV8sXuniTWOWZMC0CI1KM', '구글 API 키'),
	(16, 'APIKEYS.GOOGLE.clientId', '933241097154-ml4a4if203vu183vscf4ip04lcr1ramm.apps.googleusercontent.com', '구글 clientId '),
	(17, 'APIKEYS.GOOGLE.clientSecret', 'Pw1S5JtlBnBkfuhtT2yFisyJ', '구글 clientSecret'),
	(18, 'APIKEYS.GOOGLE.redirectUri', '/auth/google/callback', '구글 redirectUri'),
	(19, 'APIKEYS.NAVER.clientId', '7Dt2p587ylRVlf6kzgLZ', '네이버 clientId'),
	(20, 'APIKEYS.NAVER.redirectUri', '/auth/naver/callback', '네이버 redirectUri'),
	(21, 'loadingBgColor', 'E84200', '로딩페이지 배경색'),
	(22, 'loadingImgLottie', 'false', '로딩페이지 lottie 적용'),
	(23, 'regionCode', '02133107', '네이버 날씨 Region 코드'),
	(24, 'APIKEYS.NAVER.clientSecret', 'eLTpR4uDL0', '네이버 clientSecret'),
	(25, 'metro', '', '지하철 서비스 지역(없으면 공백)'),
	(26, 'supported_language_txt', '성남시, Seongnam,,', '지원 언어별 서비스 지역'),
	(27, 'use_language', 'ko,en', '사용언어'),
	(28, 'loadingSetSystem', 'false', '로딩페이지 로고를 cms 시스템에서 관리여부');



/*!40000 ALTER TABLE `service_config` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `init_string_define` WRITE;
/*!40000 ALTER TABLE `init_string_define` DISABLE KEYS */;

INSERT INTO `init_string_define` (`st_id`, `cname`, `type`, `str_kor`, `str_eng`, `str_chn`, `str_jpn`) VALUES
	(1, 'DEFINE_UI_BUTTON_NAME_CLOSE', 0, '닫기', 'Close', '', ''),
	(2, 'DEFINE_DEFAULT_INFO', 0, '기본 정보', 'Basic Information', '', ''),
	(3, 'DEFINE_DETAIL_INFO', 0, '상세 정보', 'Detail Information', '', ''),
	(4, 'DEFINE_ID', 0, '아이디', 'ID', '', ''),
	(5, 'DEFINE_ID_PLEASE_INPUT', 0, '아이디를 입력하세요', 'Enter ID', '', ''),
	(6, 'DEFINE_PASSWORD', 0, '비밀번호', 'Password', '', ''),
	(7, 'DEFINE_PASSWORD_PLEASE_INPUT', 0, '비밀번호를 입력하세요', 'Enter Password', '', ''),
	(8, 'DEFINE_USER_NAME', 0, '이름', 'Name', '', ''),
	(9, 'DEFINE_USER_NAME_PLEASE_INPUT', 0, '이름을 입력하세요', 'Enter Your Name', '', ''),
	(10, 'DEFINE_USER', 0, '사용자', 'User', '', ''),
	(11, 'DEFINE_USER_INFO', 0, '사용자 정보', 'User Information', '', ''),
	(12, 'DEFINE_USER_LOGIN_ID', 0, '로그인 아이디', 'Login ID', '', ''),
	(13, 'DEFINE_USER_LOGIN_PASSWORD', 0, '로그인 비밀번호', 'Login Password', '', ''),
	(14, 'DEFINE_USER_OWN_NAME', 0, '본인 이름', 'User Name', '', ''),
	(15, 'DEFINE_USER_INFO_CHANGE', 0, '변경하기', 'Change', '', ''),
	(16, 'DEFINE_USER_LOGIN', 0, '로그인', 'Login', '', ''),
	(17, 'DEFINE_USER_LOGOUT', 0, '로그아웃', 'Logout', '', ''),
	(18, 'DEFINE_USER_LOGIN_ID_STITLE', 0, '로그인 아이디 입력', 'Enter Login ID', '', ''),
	(19, 'DEFINE_USER_LOGIN_PASSWORD_STITLE', 0, '로그인 비밀번호 입력', 'Enter Login Password', '', ''),
	(20, 'DEFINE_USER_NEW_USER_STITLE', 0, '새로운 사용자 등록', 'Join New User', '', ''),
	(21, 'DEFINE_USER_LOGIN_ERROR', 0, '로그인 에러', 'Login Error', '', ''),
	(22, 'DEFINE_USER_TYPE_ERROR', 0, '타입오류', 'Type Error', '', ''),
	(23, 'DEFINE_USER_NAME_NONE', 0, '없음', 'No Name', '', ''),
	(24, 'DEFINE_USER_ID_OR_PASSWORD_CHECK', 0, '아이디 또는 비밀번호를 확인하세요.', 'Recheck your ID or Password.', '', ''),
	(25, 'DEFINE_USER_LOGIN_WELCOME', 0, '님 환영합니다.', ', Welcome.', '', ''),
	(26, 'DEFINE_USER_LOGOUT_GOODBYE', 0, '님 안녕히 가세요.', ', Good-Bye.', '', ''),
	(27, 'DEFINE_JOIN_USER', 0, '사용자 등록', 'Join User', '', ''),
	(28, 'DEFINE_JOIN_USER_ID_STITLE', 0, '로그인시 사용할 아이디', 'ID for Login', '', ''),
	(29, 'DEFINE_JOIN_USER_PASSWORD_STITLE', 0, '로그인시 사용할 비밀번호', 'Password for Login', '', ''),
	(30, 'DEFINE_JOIN_USER_NAME_STITLE', 0, '사용자 이름', 'User Name', '', ''),
	(31, 'DEFINE_JOIN_USER_COMPLETE', 0, '등록 완료', 'Join Complete', '', ''),
	(32, 'DEFINE_JOIN_USER_COMPLETE_TITLE', 0, '사용자 등록 완료', 'Join User Complete', '', ''),
	(33, 'DEFINE_JOIN_USER_COMPLETE_DETAIL', 0, '사용자 등록을 완료 하였습니다.\n로그인을 해 주세요.', 'Complete Join User.\nPlease Login.', '', ''),
	(34, 'DEFINE_STORE', 0, '상점', 'Store', '', ''),
	(35, 'DEFINE_STORE_STITLE', 0, '상점 정보', 'Store Information', '', ''),
	(36, 'DEFINE_STORE_INFO', 0, '상점 기본 정보', 'Store Basic Information', '', ''),
	(37, 'DEFINE_STORE_NAME', 0, '상점 이름', 'Store Name', '', ''),
	(38, 'DEFINE_STORE_PHONE', 0, '상점 전화번호', 'Store Phone', '', ''),
	(39, 'DEFINE_STORE_POINT_HISTORY', 0, '포인트 승인 현황', 'Record of Point Approval', '', ''),
	(40, 'DEFINE_STORE_POINT_CERTIFICATION', 0, '포인트 승인', 'Point Approval', '', ''),
	(41, 'DEFINE_EVENT_INFO', 0, '보물찾기', 'Treasure Hunt', '', ''),
	(42, 'DEFINE_EVENT_RECORD', 0, '획득한 보물 기록', 'Acquired Treasure Record', '', ''),
	(43, 'DEFINE_EVENT_COMPLETE', 0, '보물찾기 미션완료 기록', 'Treasure Hunt Mission Complete History', '', ''),
	(44, 'DEFINE_EVENT_RECORD_DETAIL', 0, '획득한 보물 상세정보', 'Acquired Treasure Detail Information', '', ''),
	(45, 'DEFINE_EVENT_COMPLETE_DETAIL', 0, '보물찾기 미션완료 상세정보', 'Treasure Hunt Mission Complete Detail', '', ''),
	(46, 'DEFINE_COURSE_INFO', 0, '둘레길 코스 이력 정보', 'Trail Course Record', '', ''),
	(47, 'DEFINE_COURSE_COMPLETE', 0, '완주 인증서', 'Trail Complete Certification', '', ''),
	(48, 'DEFINE_COURSE_RECORD', 0, '코스 기록', 'Record of Course', '', ''),
	(49, 'DEFINE_COURSE_RECORD_COMPLETE_TITLE', 0, '부분별 완주 인증 기록', 'Record of Course Complete', '', ''),
	(50, 'DEFINE_COURSE_COMPLETE_CERTIFICATION', 0, '코스별 둘레길 완주 인증서', 'Trail Complete Certificate', '', ''),
	(51, 'DEFINE_COURSE_COMPLETE_TOTAL', 0, ', 총', ', Total', '', ''),
	(52, 'DEFINE_COURSE_COMPLETE_END', 0, '개 완주', 'Course Complete', '', ''),
	(53, 'DEFINE_COURSE_RECORD_STITLE', 0, '부분별 코스 기록', 'Record of Course Part', '', ''),
	(54, 'DEFINE_COURSE_STITLE', 0, '강화 읍내길 코스', 'County Course', '', ''),
	(55, 'DEFINE_COURSE_01', 0, '강화 독립운동코스', 'Ganghwa Independence Movement Course', '', ''),
	(56, 'DEFINE_COURSE_02', 0, '강화 소창코스', 'Ganghwa Fabric Course', '', ''),
	(57, 'DEFINE_COURSE_03', 0, '강화 종교코스', 'Ganghwa Religion Course', '', ''),
	(58, 'DEFINE_COURSE_RECORD_01', 0, '강화 독립운동코스 기록', 'Record of Independence Movement Course', '', ''),
	(59, 'DEFINE_COURSE_RECORD_02', 0, '강화 소창코스 기록', 'Record of Fabric Course', '', ''),
	(60, 'DEFINE_COURSE_RECORD_03', 0, '강화 종교코스 기록', 'Record of Religion Course', '', ''),
	(61, 'DEFINE_COURSE_RECORD_DETAIL_01', 0, '강화 독립운동코스 코스 기록', 'Detail Record of Independence Movement Course', '', ''),
	(62, 'DEFINE_COURSE_RECORD_DETAIL_02', 0, '강화 소창코스 코스 기록', 'Detail Record of Fabric Course', '', ''),
	(63, 'DEFINE_COURSE_RECORD_DETAIL_03', 0, '강화 종교코스 코스 기록', 'Detail Record of Religion Course', '', ''),
	(64, 'DEFINE_POINT_STORE_USE_HISTORY', 0, '업소별 포인트 사용 기록', 'Historical Usage of Point by Store', '', ''),
	(65, 'DEFINE_POINT_STORE_USE', 0, '업소별 포인트 사용 기록', 'Usage of Point by Store', '', ''),
	(66, 'DEFINE_POINT_USE_TOTAL', 0, '총 사용 포인트', 'Total Use Point', '', ''),
	(67, 'DEFINE_POINT_HISTORY', 0, '포인트 사용 현황', 'Points Usage Status', '', ''),
	(68, 'DEFINE_POINT_TOTAL', 0, '누적 포인트 사용 정보', 'Information of Using Cumulative Point Usage', '', ''),
	(69, 'DEFINE_POINT_TOTAL_PART', 0, '부분별 누적 포인트', 'Cumulative Point by Part', '', ''),
	(70, 'DEFINE_POINT_TOTAL_USE', 0, '누적 포인트 사용', 'Usage of Cumulative Point', '', ''),
	(71, 'DEFINE_POINT_USE', 0, '포인트 사용', 'Usage of Point', '', ''),
	(72, 'DEFINE_POINT_USE_STORE', 0, '포인트 사용 업체', 'Store with Points', '', ''),
	(73, 'DEFINE_POINT_SELECT', 0, '포인트 선택', 'Point Selection', '', ''),
	(74, 'DEFINE_POINT_SELECT_STITLE', 0, '사용할 포인트 선택', 'Select Points to Use', '', ''),
	(75, 'DEFINE_POINT_ACCEPT_REQUEST_HISTORY', 0, '포인트 승인 요청 현황', 'Point Approval Request Status', '', ''),
	(76, 'DEFINE_POINT_ACCEPT_HISTORY', 0, '포인트 승인 현황', 'Point Approval Status', '', ''),
	(77, 'DEFINE_POINT_ACCEPT', 0, '포인트 승인', 'Point approval', '', ''),
	(78, 'DEFINE_POINT_USABLE', 0, '사용 가능 포인트', 'Available Point', '', ''),
	(79, 'DEFINE_POINT_TOTAL_REQUEST', 0, '총 누적 승인 포인트', 'Total Cumulative Approval Point', '', ''),
	(80, 'DEFINE_POINT_USE_QUESTION', 0, '를 사용하시겠습니까?', ', Do you want to use?', '', ''),
	(81, 'DEFINE_POINT_USE_STORE_DISTANCE', 0, '거리', 'Distance', '', ''),
	(82, 'DEFINE_POINT_USE_STORE_METER', 0, '미터', 'Meter', '', ''),
	(83, 'DEFINE_POINT_USE_STORE_SIR', 0, '님의', ',', '', ''),
	(84, 'DEFINE_POINT_USE_STORE_REQUEST', 0, 'p 를 사용 승인 하시겠습니까?\n거절을 누르시면 포인트 사용요청이 거절되며 승인목록에서 삭제되고 사용자에게 환원됩니다.', 'p, Would you like to use your points? If you press Reject, the request for a point is rejected and the user is deleted from the approval list and returned to the user.', '', ''),
	(85, 'DEFINE_500', 0, '500', '500', '', ''),
	(86, 'DEFINE_1000', 0, '1000', '1000', '', ''),
	(87, 'DEFINE_1500', 0, '1500', '1500', '', ''),
	(88, 'DEFINE_2000', 0, '2000', '2000', '', ''),
	(89, 'DEFINE_2500', 0, '2500', '2500', '', ''),
	(90, 'DEFINE_3000', 0, '3000', '3000', '', ''),
	(91, 'DEFINE_3500', 0, '3500', '3500', '', ''),
	(92, 'DEFINE_4000', 0, '4000', '4000', '', ''),
	(93, 'DEFINE_4500', 0, '4500', '4500', '', ''),
	(94, 'DEFINE_5000', 0, '5000', '5000', '', ''),
	(95, 'DEFINE_5500', 0, '5500', '5500', '', ''),
	(96, 'DEFINE_6000', 0, '6000', '6000', '', ''),
	(97, 'DEFINE_6500', 0, '6500', '6500', '', ''),
	(98, 'DEFINE_7000', 0, '7000', '7000', '', ''),
	(99, 'DEFINE_7500', 0, '7500', '7500', '', ''),
	(100, 'DEFINE_8000', 0, '8000', '8000', '', ''),
	(101, 'DEFINE_8500', 0, '8500', '8500', '', ''),
	(102, 'DEFINE_9000', 0, '9000', '9000', '', ''),
	(103, 'DEFINE_9500', 0, '9500', '9500', '', ''),
	(104, 'DEFINE_10000', 0, '10000', '10000', '', ''),
	(105, 'DEFINE_DETAIL_MAP_LIST', 0, '상세지도 리스트', 'Detailed Map List', '', ''),
	(106, 'DEFINE_COURSE_ITEM', 0, '강화 읍내길 코스 투어', 'Ganghwa County Course Tour', '', ''),
	(107, 'DEFINE_CATEGORY_COURSE', 1, '강화 읍내길 코스', 'County Course', '', ''),
	(108, 'DEFINE_CATEGORY_HISTORY', 2, '역사&유적', 'History & Ruins', '', ''),
	(109, 'DEFINE_CATEGORY_NATURE', 3, '자연&환경', 'Nature & Env', '', ''),
	(110, 'DEFINE_CATEGORY_LEISURE', 4, '레져', 'Leisure', '', ''),
	(111, 'DEFINE_CATEGORY_CIVIL', 5, '문화&예술', 'Culture & Art', '', ''),
	(112, 'DEFINE_CATEGORY_SHOPPING', 6, '쇼핑', 'Shopping', '', ''),
	(113, 'DEFINE_CATEGORY_FESTIVAL', 7, '축제', 'Festival', '', ''),
	(114, 'DEFINE_CATEGORY_THEME', 8, '테마지도', 'Theme Map', '', ''),
	(115, 'DEFINE_CATEGORY_LOG', 9, '방문자로그', 'Visitor Log', '', ''),
	(116, 'DEFINE_CATEGORY_FOODSTORE', 10, '모범음식점', 'Food Store', '', ''),
	(117, 'DEFINE_CATEGORY_FOODSPOT', 11, '맛집', 'Food Spot', '', ''),
	(118, 'DEFINE_CATEGORY_HOTEL', 12, '호텔', 'Hotel', '', ''),
	(119, 'DEFINE_CATEGORY_STATION', 13, '버스정류소', 'Bus Station', '', ''),
	(120, 'DEFINE_CATEGORY_PARKING', 14, '주차장', 'Parking Lot', '', ''),
	(121, 'DEFINE_CATEGORY_RESTROOM', 15, '화장실', 'Rest Room', '', ''),
	(122, 'DEFINE_CATEGORY_GOLFCLUB', 16, '골프클럽', 'Golf Club', '', ''),
	(123, 'DEFINE_CATEGORY_MUSEUM', 17, '박물관&미술관', 'Museum', '', ''),
	(124, 'DEFINE_CATEGORY_THEMEPARK', 18, '테마파크', 'Theme Park', '', ''),
	(125, 'DEFINE_CATEGORY_HERITAGE', 19, '문화유적', 'Cultural Heritage', '', ''),
	(126, 'DEFINE_CATEGORY_ACCOMMODATION', 20, '숙박시설', 'Accommodation', '', ''),
	(127, 'DEFINE_CATEGORY_TEMPLESTAY', 21, '템플스테이', 'Temple Stay', '', ''),
	(128, 'DEFINE_CATEGORY_EXPERIENCE', 22, '체험여행', 'Rural Experience', '', ''),
	(129, 'DEFINE_CATEGORY_CAMPINGSITE', 23, '캠핑장', 'Camping Site', '', ''),
	(130, 'DEFINE_CATEGORY_FOODSPOT_COUPON', 24, '천원쿠폰샵', 'One Dollar Coupon Shop', '', ''),
	(131, 'DEFINE_CATEGORY_FOODSTREET', 25, '먹거리 특화거리', 'Food Street', '', ''),
	(132, 'DEFINE_CATEGORY_SEAMARKET', 26, '수산시장', 'Fish market', '', ''),
	(133, 'DEFINE_CATEGORY_STITLE_DETAIL_INFO', 0, '상세정보', 'Detailed Information', '', ''),
	(134, 'DEFINE_CATEGORY_STITLE_FEE', 0, '이용요금', 'Service Charges', '', ''),
	(135, 'DEFINE_CATEGORY_STITLE_STORE_TIME', 0, '영업시간', 'Business Hours', '', ''),
	(136, 'DEFINE_CATEGORY_STITLE_ETC_INFO', 0, '기타정보', 'Other Information', '', ''),
	(137, 'DEFINE_CATEGORY_STITLE_PHONE', 0, '전화번호', 'Phone Number', '', ''),
	(138, 'DEFINE_CATEGORY_STITLE_WEBSITE', 0, '웹사이트', 'Web Site', '', ''),
	(139, 'DEFINE_CATEGORY_STITLE_ADDRESS', 0, '주소', 'Address', '', ''),
	(140, 'DEFINE_CATEGORY_STITLE_MENU', 0, '주요메뉴', 'Menu', '', ''),
	(141, 'DEFINE_CATEGORY_STITLE_PART', 0, '분류', 'Detail Category', '', ''),
	(142, 'DEFINE_CATEGORY_STITLE_BUS_LINE_INFO', 0, '노선정보', 'Route Information', '', ''),
	(143, 'DEFINE_CATEGORY_STITLE_BUS_STOP_NO', 0, '정류소번호', 'Station Number', '', ''),
	(144, 'DEFINE_CATEGORY_STITLE_BUS_STOP_TYPE', 0, '정류소형태', 'Station Type', '', ''),
	(145, 'DEFINE_CATEGORY_STITLE_BUS_STOP_SIGN', 0, '표지판형태', 'Sign Type', '', ''),
	(146, 'DEFINE_CATEGORY_STITLE_PARKING', 0, '주차', 'Parking Lot', '', ''),
	(147, 'DEFINE_CATEGORY_STITLE_AROUND', 0, '주변관광지', 'Around Tour Spot', '', ''),
	(148, 'DEFINE_CATEGORY_STITLE_SEAT', 0, '좌석수', 'Seating Capacity', '', ''),
	(149, 'DEFINE_CATEGORY_STITLE_FACILITY', 0, '시설', 'Facility', '', ''),
	(150, 'DEFINE_CATEGORY_STITLE_FORWHOM', 0, '대상', 'Participation Target', '', ''),
	(151, 'DEFINE_CATEGORY_STITLE_DDAY', 0, '행사종료일', 'Event Closing Date', '', ''),
	(152, 'DEFINE_CATEGORY_MID_BUTTON_NAVI', 0, '카카오내비', 'Kakao Navigation', '', ''),
	(153, 'DEFINE_CATEGORY_MID_BUTTON_LOCATION', 0, '위치보기', 'Location on Map', '', ''),
	(154, 'DEFINE_MESSAGE_BUTTON_OK', 0, '확인', 'Accept', '', ''),
	(155, 'DEFINE_MESSAGE_BUTTON_CANCEL', 0, '취소', 'Cancel', '', ''),
	(156, 'DEFINE_MESSAGE_BUTTON_REQUEST_OK', 0, '승인', 'Approval', '', ''),
	(157, 'DEFINE_MESSAGE_BUTTON_REQUEST_CANCEL', 0, '거절', 'Reject', '', ''),
	(158, 'DEFINE_MESSAGE_BUTTON_CLOSE', 0, '닫기', 'Close', '', ''),
	(159, 'DEFINE_MESSAGE_BUTTON_MOVE_NAVI', 0, '내비이동', 'Navigation', '', ''),
	(160, 'DEFINE_MESSAGE_BUTTON_MOVE_CANCEL', 0, '이동취소', 'Cancel', '', ''),
	(161, 'DEFINE_MESSAGE_BUTTON_MOVE_WALK', 0, '직접이동', 'On foot', '', ''),
	(162, 'DEFINE_MESSAGE_COUNTDOWN_3', 0, '3초', '3s', '', ''),
	(163, 'DEFINE_MESSAGE_COUNTDOWN_2', 0, '2초', '2s', '', ''),
	(164, 'DEFINE_MESSAGE_COUNTDOWN_1', 0, '1초', '1s', '', ''),
	(165, 'DEFINE_MESSAGE_TARGET_DISTANCE', 0, '목적지까지 남은거리', 'Distance Left to Destination', '', ''),
	(166, 'DEFINE_MESSAGE_TARGET_ARRIVED', 0, '목적지 도착', 'Destination Arrival', '', ''),
	(167, 'DEFINE_MESSAGE_TARGET_ARRIVED_END', 0, '목적지에 도착하였습니다.', 'You arrived at your destination.', '', ''),
	(168, 'DEFINE_MESSAGE_TARGET_MOVE_SELECT', 0, '목적지 이동 선택', 'How to move', '', ''),
	(169, 'DEFINE_MESSAGE_TARGET_MOVE_SELECT_END', 0, '차량이동은 카카오내비, 도보이동은 직접이동을 선택하세요', 'Please select a direct transfer of the vehicle from the Kakao Navigation and the direct movement of the walkway.', '', ''),
	(170, 'DEFINE_MESSAGE_TARGET_MOVE_DIRECT', 0, '직접 선택한 목적지', 'A Direct Destination', '', ''),
	(171, 'DEFINE_MESSAGE_ABOUT', 0, '약', 'About', '', ''),
	(172, 'DEFINE_MESSAGE_METER_NEED', 0, 'm 남았습니다', 'meters left.', '', ''),
	(173, 'DEFINE_MESSAGE_GPS_OUT', 0, 'GPS 외부영역', 'GPS External Area', '', ''),
	(174, 'DEFINE_MESSAGE_GPS_OUT_END', 0, '현재 위치하고 계신 곳은 지도 외부영역 입니다.', 'Where you are currently located is the outer area of the map.', '', ''),
	(175, 'DEFINE_MESSAGE_FIND_FAIL', 0, '검색 실패', 'Search Failed', '', ''),
	(176, 'DEFINE_MESSAGE_FIND_FAIL_END', 0, '검색어를 다시 입력 해 보세요.', 'Try retyping your search again.', '', ''),
	(177, 'DEFINE_ERROR', 0, '오류', 'Error', '', ''),
	(178, 'DEFINE_ERROR_SERVER', 0, '서버 오류', 'Server Error', '', ''),
	(179, 'DEFINE_SUCCESS', 0, '성공', 'Success', '', ''),
	(180, 'DEFINE_SUCCESS_POINT_USE', 0, '포인트 사용 성공', 'Point Use Success', '', ''),
	(181, 'DEFINE_SUCCESS_POINT_REQUEST', 0, '포인트 승인 성공', 'Point Approval Success', '', ''),
	(182, 'DEFINE_SUCCESS_POINT_REQUEST_CANCEL', 0, '포인트 승인거절 성공', 'Point Reject Success', '', ''),
	(183, 'DEFINE_WEATHER_PATICLE', 0, '미세먼지', 'Fine Dust', '', ''),
	(184, 'DEFINE_WEATHER_CONDITION_00', 0, '', '', '', ''),
	(185, 'DEFINE_WEATHER_CONDITION_01', 0, '맑음', 'Sunny', '', ''),
	(186, 'DEFINE_WEATHER_CONDITION_02', 0, '구름조금', 'Partly Cloudy', '', ''),
	(187, 'DEFINE_WEATHER_CONDITION_03', 0, '구름많음', 'Mostly Cloudy', '', ''),
	(188, 'DEFINE_WEATHER_CONDITION_04', 0, '구름많고 비', 'Mostly Cloudy and Rain', '', ''),
	(189, 'DEFINE_WEATHER_CONDITION_05', 0, '구름많고 눈', 'Mostly Cloudy and Snow', '', ''),
	(190, 'DEFINE_WEATHER_CONDITION_06', 0, '구름많고 비 또는 눈', 'Mostly Cloudy and Snow/Rain', '', ''),
	(191, 'DEFINE_WEATHER_CONDITION_07', 0, '흐림', 'Cloudy', '', ''),
	(192, 'DEFINE_WEATHER_CONDITION_08', 0, '흐리고 비', 'Cloudy and Rain', '', ''),
	(193, 'DEFINE_WEATHER_CONDITION_09', 0, '흐리고 눈', 'Cloudy and ', '', ''),
	(194, 'DEFINE_WEATHER_CONDITION_10', 0, '흐리고 비 또는 눈', 'Cloudy and Snow/Rain', '', ''),
	(195, 'DEFINE_WEATHER_CONDITION_11', 0, '흐리고 낙뢰', 'Cloudy and Thunderstorms', '', ''),
	(196, 'DEFINE_WEATHER_CONDITION_12', 0, '뇌우, 비', 'Thunderstorm, Rain', '', ''),
	(197, 'DEFINE_WEATHER_CONDITION_13', 0, '뇌우, 눈', 'Thunderstorm, Snow', '', ''),
	(198, 'DEFINE_WEATHER_CONDITION_14', 0, '뇌우, 비 또는 눈', 'Thunderstorm, Snow/Rain', '', ''),
	(199, 'DEFINE_FIND_INPUT_PLACEHOLDER', 0, '장소검색', 'Find Place', '', ''),
	(200, 'DEFINE_CERTIFICATE_TITLE_KOR', 0, '강화읍내길 완주 인증서', '강화읍내길 완주 인증서', '', ''),
	(201, 'DEFINE_CERTIFICATE_TITLE_ENG', 0, 'Ganghwa Town Trail Completion Certificate', 'Ganghwa Town Trail Completion Certificate', '', ''),
	(202, 'DEFINE_CERTIFICATE_NAME', 0, '성명/Name: ', '성명/Name: ', '', ''),
	(203, 'DEFINE_CERTIFICATE_NO', 0, '인증번호/CertificationNo: ', '인증번호/CertificationNo: ', '', ''),
	(204, 'DEFINE_CERTIFICATE_DESCRIPTION_KOR', 0, '위 사람은 강화읍내길 전 구간 을\n 완주하였기에 이 증서를 드립니다.', '위 사람은 강화읍내길 전 구간 을\n 완주하였기에 이 증서를 드립니다.', '', ''),
	(205, 'DEFINE_CERTIFICATE_DESCRIPTION_ENG', 0, 'This certifies that the person whose name\n appears above has completed\n the entire Ganghwa Town Trail.', 'This certifies that the person whose name\n appears above has completed\n the entire Ganghwa Town Trail.', '', ''),
	(206, 'DEFINE_CERTIFICATE_ISSUER', 0, '강 화 군 청', '강 화 군 청', '', ''),
	(207, 'DEFINE_THEME_MAP_TITLE', 0, '테마지도', 'Theme Map', '', ''),
	(208, 'DEFINE_THEME_MAP_STITLE', 0, '테마지도 리스트', 'Theme Map List', '', ''),
	(209, 'DEFINE_THEME_MAP_00', 0, '에버랜드', 'Everland', '', ''),
	(210, 'DEFINE_THEME_MAP_01', 0, '한국민속촌', 'Korean Traditional Village', '', ''),
	(211, 'DEFINE_THEME_MAP_02', 0, '용인농촌테마파크', '용인농촌테마파크', '', ''),
	(212, 'DEFINE_THEME_MAP_03', 0, '한택식물원', '한택식물원', '', ''),
	(213, 'DEFINE_THEME_MAP_04', 0, '와우정사', '와우정사', '', ''),
	(214, 'DEFINE_THEME_MAP_05', 0, '용인중앙시장', '용인중앙시장', '', ''),
	(215, 'DEFINE_THEME_MAP_06', 0, '용인자연휴양림', '용인자연휴양림', '', ''),
	(216, 'DEFINE_THEME_MAP_07', 0, '용인대장금파크', '용인대장금파크', '', ''),
	(217, 'DEFINE_THEME_MAP_08', 0, '보정동카페거리', '보정동카페거리', '', ''),
	(218, 'DEFINE_THEME_MAP_09', 0, '청계목장', '청계목장', '', ''),
	(219, 'DEFINE_THEME_MAP_10', 0, '동도사', '동도사', '', ''),
	(220, 'DEFINE_THEME_MAP_11', 0, '농도원목장', '농도원목장', '', ''),
	(221, 'DEFINE_THEME_MAP_12', 0, '용인캠핑장', '용인캠핑장', '', ''),
	(222, 'DEFINE_THEME_MAP_13', 0, '용인테마여행', '용인테마여행', '', ''),
	(223, 'DEFINE_THEME_MAP_14', 0, '대한민국전도', '대한민국전도', '', ''),
	(224, 'DEFINE_THEME_MAP_JSON_NAME_00', 0, 'everland', 'everland', '', ''),
	(225, 'DEFINE_THEME_MAP_JSON_NAME_01', 0, 'minsokchon', 'minsokchon', '', ''),
	(226, 'DEFINE_THEME_MAP_JSON_NAME_02', 0, 'nongchon', 'nongchon', '', ''),
	(227, 'DEFINE_THEME_MAP_JSON_NAME_03', 0, 'botanic', 'botanic', '', ''),
	(228, 'DEFINE_THEME_MAP_JSON_NAME_04', 0, 'wow', 'wow', '', ''),
	(229, 'DEFINE_THEME_MAP_JSON_NAME_05', 0, 'joongang', 'joongang', '', ''),
	(230, 'DEFINE_THEME_MAP_JSON_NAME_06', 0, 'forest', 'forest', '', ''),
	(231, 'DEFINE_THEME_MAP_JSON_NAME_07', 0, 'djgpark', 'djgpark', '', ''),
	(232, 'DEFINE_THEME_MAP_JSON_NAME_08', 0, 'bojeongdong', 'bojeongdong', '', ''),
	(233, 'DEFINE_THEME_MAP_JSON_NAME_09', 0, 'cheonggye', 'cheonggye', '', ''),
	(234, 'DEFINE_THEME_MAP_JSON_NAME_10', 0, 'dongdosa', 'dongdosa', '', ''),
	(235, 'DEFINE_THEME_MAP_JSON_NAME_11', 0, 'nongdowon', 'nongdowon', '', ''),
	(236, 'DEFINE_THEME_MAP_JSON_NAME_12', 0, 'camping', 'camping', '', ''),
	(237, 'DEFINE_THEME_MAP_JSON_NAME_13', 0, 'theme', 'theme', '', ''),
	(238, 'DEFINE_THEME_MAP_JSON_NAME_14', 0, 'korea', 'korea', '', ''),
	(239, 'DEFINE_VISIT_LOG_TITLE', 0, '방문자로그', 'Visitor Log', '', ''),
	(240, 'DEFINE_VISIT_LOG_TODAY_TITLE', 0, '오늘의 방문자 수', 'Today\'s Number of visitors', '', ''),
	(241, 'DEFINE_VISIT_LOG_TOTAL_TITLE', 0, '총 누적 방문자 수', 'Total Number of visitors', '', ''),
	(242, 'DEFINE_VISIT_LOG_TOTAL_COUNT_TITLE', 0, '누적 방문자 수', 'Cumulative visitor count', '', ''),
	(243, 'DEFINE_VISIT_LOG_DAYS_TITLE', 0, '최근 10일 동안 방문자 수', 'Number of visitors during the last 10 days', '', ''),
	(244, 'DEFINE_VISIT_LOG_DAYS_STITLE', 0, '일', 'day', '', ''),
	(245, 'DEFINE_VISIT_LOG_MONTHS_TITLE', 0, '최근 10개월 동안 방문자 수', 'Number of visitors during the last 10 months', '', ''),
	(246, 'DEFINE_VISIT_LOG_MONTHS_STITLE', 0, '월', 'month', '', ''),
	(247, 'DEFINE_VISIT_LOG_YEARS_TITLE', 0, '최근 10년 동안 방문자 수', 'Number of visitors during the last 10 years', '', ''),
	(248, 'DEFINE_VISIT_LOG_YEARS_STITLE', 0, '년', 'year', '', ''),
	(249, 'DEFINE_LANGUAGE', 0, 'ENG', 'KOR', '', ''),
	(250, 'DEFINE_CERTIFICATE_COURSE', 0, '코스/Course: ', '코스/Course: ', '', ''),
	(251, 'DEFINE_DULLEGIL_COURSE', 0, '둘레길 코스', 'Dulle-gil Course', '', ''),
	(252, 'DEFINE_PUBLIC_TRANSPORT', 0, '대중교통', 'Public Transport', '', ''),
	(253, 'DEFINE_DULLEGIL', 0, '둘레길', 'Dulle-gil', '', ''),
	(254, 'DEFINE_RELATED_FILES', 0, '관련파일들', 'Related Files', '', ''),
	(255, 'DEFINE_HOUR_OPERATION', 0, '개방 시간', 'Hours of Operation', '', ''),
	(256, 'DEFINE_UNISEX_PUBLIC_TOILET', 0, '남여 공용 화장실', 'Unisex public toilet', '', ''),
	(257, 'DEFINE_YES', 0, '예', 'Yes', '', ''),
	(258, 'DEFINE_NO', 0, '아니오', 'No', '', ''),
	(259, 'DEFINE_MANAGEMENT_AGENCY', 0, '관리기관명', 'Management Agency', '', ''),
	(260, 'DEFINE_USE_ROUTE_INFORMATION', 0, '경로안내를 사용하시겠습니까?', 'Would you like to use the route information?', '', ''),
	(261, 'DEFINE_MAP_LEGEND', 0, '범례', 'Legend', '', ''),
	(262, 'DEFINE_NO_RESULTS', 0, '결과가 없습니다', 'No Results', '', ''),
	(263, 'DEFINE_WHETHER_DUST_FINE', 0, '좋음', 'Fine', '', ''),
	(264, 'DEFINE_WHETHER_DUST_NORMAL', 0, '보통', 'Normal', '', ''),
	(265, 'DEFINE_WHETHER_DUST_BAD', 0, '나쁨', 'Bad', '', ''),
	(266, 'DEFINE_WHETHER_DUST_VERYBAD', 0, '매우나쁨', 'Very Bad', '', ''),
	(267, 'DEFINE_LOGIN_AGAIN', 0, '다시 로그인 하십시오', 'Please login again', '', ''),
	(268, 'DEFINE_PASSED_DULLEGIL', 0, '둘레길 코스를 통과했습니다', 'Passed the Dulegil course', '', ''),
	(269, 'DEFINE_BUS_NO_RESULT', 0, '정보 없음', 'No information', '', ''),
	(270, 'DEFINE_BUS_MINUTES', 0, '분', 'minutes', '', ''),
	(271, 'DEFINE_BUS_PREV', 0, '번째 전', 'Prev', '', ''),
	(272, 'DEFINE_METRO_ROUTE', 0, '노선 정보', 'Route Info', '', ''),
	(273, 'DEFIND_METRO_PREV_ARRIVAL', 0, '전역도착', 'Previous station arrival', '', ''),
	(274, 'DEFIND_METRO_PREV_ENTRY', 0, '전역진입', 'Previous station entry', '', ''),
	(275, 'DEFIND_METRO_PREV_DEPARTURE', 0, '전역출발', 'Previous station departure', '', ''),
	(276, 'DEFIND_METRO_ARRIVE', 0, '도착', 'Arrive at this station', '', ''),
	(277, 'DEFIND_METRO_ENTERING', 0, '진입', 'Entering this station', '', ''),
	(278, 'DEFINE_METRO_DEPARTURE', 0, '출발', 'Departure from this station', '', ''),
	(279, 'DEFINE_METRO_MINUTES', 0, '분', 'minutes', '', ''),
	(280, 'DEFINE_METRO_SECONDS', 0, '초', 'seconds', '', ''),
	(281, 'DEFINE_METRO_API_ERROR', 0, 'API 서버에 문제가 있습니다. <br> 잠시 후에 다시 시도해 보세요.', 'There is a problem with the API server.<br>Try again in a few minutes.', '', ''),
	(282, 'DEFINE_ACTIONBAR_BACK', 0, '뒤로', 'Back', '', ''),
	(283, 'DEFINE_USER_SIGN_UP', 0, '회원가입', 'Sign Up', '', ''),
	(284, 'DEFINE_USER_MSG_LOGIN', 0, '로그인 되었습니다', 'Logged in', '', ''),
	(285, 'DEFINE_USER_MSG_NOT_EXIST_ID', 0, '존재하지 않는 아이디입니다', 'This ID does not exist', '', ''),
	(286, 'DEFINE_USER_MSG_PASSWD_INCORRECT', 0, '패스워드가 틀렸습니다', 'The password is incorrect', '', ''),
	(287, 'DEFINE_MSG_NODATA_GPS', 0, '위경도 정보가 없습니다', 'No GPS information', '', ''),
	(288, 'DEFINE_TREK_RECORD', 0, '기록', 'Record', '', ''),
	(289, 'DEFINE_TREK_POINT_RECORD', 0, '부분별 누적포인트 기록', 'Record cumulative points by section', '', ''),
	(290, 'DEFINE_TREK_NO_HISTORY', 0, '이력이 없습니다', 'No history', '', ''),
	(291, 'DEFINE_TREK_COURSE_RECORD', 0, '코스 기록', 'Course record', '', ''),
	(292, 'DEFINE_TREK_COURSE_RECORD_PART', 0, '부분별 코스 기록', 'Course record by Part', '', ''),
	(293, 'DEFINE_TREK_COURSES', 0, '트레킹 코스 목록', 'Trekking courses', '', ''),
	(294, 'DEFINE_USER_MSG_LOGOUT', 0, '로그아웃 되었습니다', 'Signed out', '', ''),
	(295, 'DEFINE_METRO_DERECTION', 0, '행', '', '', ''),
  (296, 'DEFINE_DETAIL_MODIFIED', 0, '마지막 수정일', 'Last Modified', '', ''),
  (297, 'DEFINE_EVENT_NOTOPEN', 0, '오늘 하루 이 창을 열지 않음', 'Today does not open this window', '', ''),
	(298, 'DEFINE_EVENT_RELATED_PLACE', 0, '관련 장소', 'Related Places', '', ''),
	(299, 'DEFINE_ADDRESS_TRANS_FAILED', 0, '주소변환이 실패했습니다.', 'Address translation failed.', '', ''),
	(300, 'DEFINE_DETAIL_HITS', 0, '조회수', 'Hits', '', ''),
	(301, 'DEFINE_KAKAO_SELECTED', 0, '선택한 장소', 'Selected place', '', ''),
	(302, 'DEFINE_LANGUAGE_SELECT', 0, '언어 선택', 'Select language', '', '');
LOCK TABLES `pictograms` WRITE;

 INSERT INTO `pictograms` (`pid`, `title`, `icon_url`, `fill_color`) VALUES
	(7, '관광 안내소(Tourist information)', '1530606217919.png', NULL),
	(8, '안내소(Information)', '1530606228183.png', NULL),
	(9, '숙박시설(Accomodations)', '1530606238116.png', NULL),
	(10, '화장실(Toilets)', '1530606250366.png', NULL),
	(12, '들어가는 길/입구 (Entrance / way in) ', '1530606619573.png', NULL),
	(13, '나오는 길 /출구 (Exit / way out) ', '1530606632825.png', NULL),
	(14, '관광명소 (Tourist Attraction)', '1530606659226.png', NULL),
	(15, '기타 (Others)', '1532599746628.png', NULL),
	(16, '쇼핑(Shopping)', '1530607335296.png', NULL),
	(17, '마트(Mart)', '1530607411675.png', NULL),
	(18, '카지노(Casino)', '1530607427499.png', NULL),
	(19, '온천(Hot spring)', '1530607450268.png', NULL),
	(20, '사우나(Sauna)', '1530607502372.png', NULL),
	(21, '환전소(Money exchange)', '1530607516092.png', NULL),
	(22, '학교 (School)', '1530607530275.png', NULL),
	(23, '도서관 (Library)', '1530607540068.png', NULL),
	(24, '우체국 (Post)', '1532918979088.png', NULL),
	(25, '파출소(Police)', '1532517853884.png', NULL),
	(26, '사찰 (Temple)', '1530607600542.png', NULL),
	(27, '교회 (Church)', '1530607691280.png', NULL),
	(28, '성당 (Catholic church)', '1530607704222.png', NULL),
	(29, '대사관 (Embassy)', '1530607713578.png', NULL),
	(30, '한식당 (Korean Restaurant)', '1530607741271.png', NULL),
	(31, '양식당 (Restaurant)', '1530607756554.png', NULL),
	(32, '카페 (Coffee shop)', '1530607783470.png', NULL),
	(33, '스낵 코너 (Snacks)', '1530607813150.png', NULL),
	(34, '바 (Bar)', '1530607823484.png', NULL),
	(35, '편의점 (Convenience store)', '1530607836474.png', NULL),
	(36, '노래방 (Karaoke bar)', '1530607852097.png', NULL),
	(37, '나이트클럽 (Nightclub)', '1530607862361.png', NULL),
	(38, '영화관 (Movie theater)', '1530607875874.png', NULL),
	(39, '공연 극장 (Performing art center)', '1530607890251.png', NULL),
	(40, '화랑 (Art gallery)', '1530607905538.png', NULL),
	(41, '역사 건축물 (Historic site)', '1530607965084.png', NULL),
	(42, '박물관/미술관 (Museum/Gallery)', '1530607979728.png', NULL),
	(43, '테마파크 (Theme park)', '1530607997777.png', NULL),
	(44, '수족관 (Aquarium)', '1530608014568.png', NULL),
	(45, '동물원 (Zoo)', '1530608026766.png', NULL),
	(46, '약국 (Pharmacy)', '1530608173250.png', NULL),
	(47, '병원 (Hospital)', '1530608201096.png', NULL),
	(48, '주유소 (Gas station)', '1530608218937.png', NULL),
	(49, '산 (Mountains)', '1530608250300.png', NULL),
	(50, '동굴 (Cave)', '1530608545657.png', NULL),
	(51, '폭포 (Waterfall)', '1530608600904.png', NULL),
	(52, '휴양림 (Woodland recreation area)', '1530608640889.png', NULL),
	(53, '식물원 (Botanical gardens)', '1530608654904.png', NULL),
	(54, '산책로 (Walking trail)', '1530608671111.png', NULL),
	(55, '하이킹(둘레길) (Hiking trail)', '1530608739729.png', NULL),
	(56, '전망지 (View point)', '1530608754806.png', NULL),
	(57, '야영장 (Camp site)', '1530608776459.png', NULL),
	(58, '놀이터 (Play area)', '1530609164344.png', NULL),
	(59, '공원/휴양 (Park / recreational) ', '1530609176017.png', NULL),
	(60, '지하철 (Metro)', '1530609186406.png', NULL),
	(61, '공항 (Airport)', '1530609201560.png', NULL),
	(62, '공항버스 (Airport bus)', '1530609218758.png', NULL),
	(63, '기차역 (Railway station) ', '1530609237535.png', NULL),
	(64, '고속 열차 (Express train)', '1530609258960.png', NULL),
	(65, '배ㆍ페리ㆍ부두 (Water transportation/Port)', '1530609273034.png', NULL),
	(66, '환승센터 (Transportation centre)', '1530609295174.png', NULL),
	(67, '버스정류장 (Bus stop)', '1530609315878.png', NULL),
	(68, '택시 (Taxi stop)', '1530609339711.png', NULL),
	(69, '주차장 (Parking)', '1530609353223.png', NULL),
	(70, '케이블카 (Cable car) ', '1530609372776.png', NULL),
	(71, '소방서 119 구급대', '1530609457616.png', NULL),
	(72, '대학교', '1530609472270.png', NULL),
	(73, '은행', '1530609492213.png', NULL),
	(74, '경찰서', '1530609508231.png', NULL),
	(75, '미용실 (Beauty shop)', '1530609530727.png', NULL),
	(76, '신발가게 (Shoe store)', '1530609556742.png', NULL),
	(77, '화장품 (cosmetics store)', '1530609570184.png', NULL),
	(78, '마사지 (massage)', '1530609583222.png', NULL),
	(79, '안경 (glasses)', '1530609642472.png', NULL),
	(80, '고기 요리 (A meat dish)', '1530609665644.png', NULL),
	(81, '해산물 요리 (Seafood)', '1530609678758.png', NULL),
	(82, '닭 요리 (Chicken)', '1530609689821.png', NULL),
	(83, '중국요리, 국수 (Chinese food, Noodles)', '1530609704445.png', NULL),
	(84, '분식 (Snack bar )', '1530609718573.png', NULL),
	(85, '패스트 푸드 (Fast food)', '1530609730205.png', NULL),
	(86, '막걸리 (Maggolli)', '1530609743284.png', NULL),
	(87, '호프 (Pub)', '1530609760542.png', NULL),
	(88, '양꼬치 (Lamb Skewer)', '1530609771917.png', NULL),
	(89, '매표소', '1530609825541.png', NULL),
	(90, '암벽등반', '1530609837996.png', NULL),
	(91, '경륜장', '1530609854116.png', NULL),
	(92, '텃밭', '1530609865023.png', NULL),
	(93, '주민센터', '1530609873364.png', NULL),
	(94, '약수터', '1530609883027.png', NULL),
	(95, '낚시터', '1530609891828.png', NULL),
	(96, '골프장', '1530609937750.png', NULL),
	(97, '편의점 CU', '1530610616606.png', NULL),
	(98, '편의점 GS25', '1530610643141.png', NULL),
	(99, '편의점 미니스탑', '1530610673675.png', NULL),
	(100, '편의점 바이더웨이', '1530610691091.png', NULL),
	(101, '패스트푸드 미스터피자', '1530610755995.png', NULL),
	(102, '패스트푸드 미스터도넛', '1530610773122.png', NULL),
	(103, '패스트푸드 던킨도넛', '1530610795236.png', NULL),
	(104, '패스트푸드 배스킨라빈스31', '1530610834828.png', NULL),
	(105, '제과점 파리바게트', '1530610856332.png', NULL),
	(106, '제과점 뜨레주르', '1530610875946.png', NULL),
	(107, '패밀리레스토랑 베니건스', '1530610899374.png', NULL),
	(108, '패밀리레스토랑 TGI Fridays', '1530610921525.png', NULL),
	(109, '패밀리레스토랑 빕스', '1530610936589.png', NULL),
	(110, '패밀리레스토랑 아웃백스테이크하우스', '1530610951407.png', NULL),
	(111, '패스트푸드 파파존스피자', '1530610971003.png', NULL),
	(112, '패스트푸드 피자헛', '1530611018564.png', NULL),
	(113, '패스트푸드 도미노피자', '1530611038270.png', NULL),
	(114, '패스트푸드 버거킹', '1530611067733.png', NULL),
	(115, '패스트 푸드 맥도날드', '1530617384070.png', NULL),
	(116, '패스트 푸드 롯데리아', '1530617398721.png', NULL),
	(117, '패스트 푸드 KFC', '1530617432527.png', NULL),
	(118, '카페 투썸플레이스', '1530617462183.png', NULL),
	(119, '카페 카페베네', '1530617485897.png', NULL),
	(120, '카페 할리스', '1530617510474.png', NULL),
	(121, '카페 스타벅스', '1530617522328.png', NULL),
	(122, '카페 커피빈', '1530617536647.png', NULL),
	(123, '카페 엔젤리너스', '1530617551177.png', NULL),
	(124, '카페 탐앤탐스', '1530617568474.png', NULL),
	(125, '은행 새마을금고', '1530617588127.png', NULL),
	(126, '은행 KB은행', '1530617602799.png', NULL),
	(127, '은행 기업은행', '1530617617319.png', NULL),
	(128, '은행 우리은행', '1530617626147.png', NULL),
	(129, '은행 농협은행', '1530617638750.png', NULL),
	(130, '은행 하나은행', '1530617650136.png', NULL),
	(131, '은행 KEB외환은행', '1530617671080.png', NULL),
	(132, '은행 수협은행', '1530617686343.png', NULL),
	(133, '은행 SC제일은행', '1530617704281.png', NULL),
	(134, '은행 시티은행', '1530617719056.png', NULL),
	(135, '은행 신한은행', '1530617737002.png', NULL),
	(136, '은행 산업은행', '1530617765514.png', NULL),
	(137, '보건소 (Public health)', '1530618435193.png', NULL),
	(138, '편의점 세븐일레븐', '1532310969275.png', NULL),
	(139, '편의점 이마트24', '1532310982617.png', NULL),
	(140, '카페 이디야커피', '1532313822321.png', NULL),
	(141, '카페 파스쿠찌', '1532314544668.png', NULL),
	(142, '은행 신협', '1532479454104.png', NULL),
	(143, '화장품 랄라블라', '1532479992432.png', NULL),
	(144, '편의점 롯데수퍼', '1532512768811.png', NULL),
	(145, '화장품 롭스', '1532521104116.png', NULL),
	(146, '마트 이마트', '1534896827700.png', NULL),
	(147, '시장', '58082647-b83b-4a0f-b4db-4937c4438b7a.png', NULL),
	(148, '홈플러스', '7bc4ab20-ac02-4d24-9624-f08d50d57bef.png', NULL),
	(149, '스탬프투어 깃발', '38dd17aa-e6c4-419b-87e0-02d7f2718739.png', NULL),
	(150, '일식당 (Japanese Restaurant)', 'd1766ca3-409a-4c24-a264-a933b1708d24.png', NULL);