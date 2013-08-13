
#DROP TABLE call_log;
CREATE TABLE call_log (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_date` datetime NOT NULL,
  `call_description` varchar(255) DEFAULT NULL,
  `caller_first_name` varchar(16) DEFAULT NULL,
  `caller_last_name` varchar(16) DEFAULT NULL,
  `caller_phone` varchar(10) DEFAULT NULL,
  `caller_zip` varchar(10) DEFAULT NULL,
  `length_of_call` int(11) NOT NULL,
  `mail_address1` varchar(255) DEFAULT NULL,
  `mail_address2` varchar(255) DEFAULT NULL,
  `mail_city` varchar(255) DEFAULT NULL,
  `mail_email` varchar(255) DEFAULT NULL,
  `mail_state` varchar(255) DEFAULT NULL,
  `mail_zip` varchar(255) DEFAULT NULL,
  `quotes_and_anecdotal_information` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `volunteer_id` varchar(16) NOT NULL,
  `call_status` varchar(255) DEFAULT NULL,
  `lead_source` varchar(255) DEFAULT NULL,
  `caller_relation` varchar(255) DEFAULT NULL,
  `referral_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;



INSERT INTO call_log (call_date,call_description,caller_first_name,caller_last_name,caller_phone,
caller_zip,length_of_call,mail_address1,mail_address2,mail_city,mail_email,
mail_state,mail_zip,quotes_and_anecdotal_information,version,volunteer_id,call_status,lead_source,
caller_relation,referral_type) VALUES ('2011-12-19 07:30:23','Caller called to get information.','Primrose','Everdeen','2065551212','98102',15,'111 Pine St','','Seattle','prim@district12.org','WA','98102','Good person to talk to.  Had good questions.',
0,'1001','New Call','Hospital','Patient','211');



CREATE TABLE call_topic (id integer,topic varchar(255) NOT NULL);

CREATE TABLE call_literature (id integer,literature varchar(255) NOT NULL);

CREATE TABLE volunteer (
  username VARCHAR(45) NOT NULL,
  id INT(10) UNSIGNED NOT NULL,
  password VARCHAR(45) NOT NULL,
  enabled tinyint(1) NOT NULL,
  first_name VARCHAR(40),
  last_name VARCHAR(40),
  role VARCHAR(20),
  PRIMARY KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into volunteer (username,password,first_name,last_name,enabled,id,role) values('dknoernschild','Lifeline','David','Knoernschild',1,9000,'USER_ROLE');



