# mysql -u [username] -p[password] < archive_visitor_log.sql
# cron job으로 돌려야 함.
# 매일 0시 0분에....
# 다음 명령 실행...
# sudo vi /etc/crontab
# 0 0 * * * root mysql [db_name] -u [username] -p[password] < [path_of]/archive_visitor_log.sql
# sudo systemctl restart crond.service

START TRANSACTION;
INSERT IGNORE INTO visitor_log_archive (year, month, date, counts)
	SELECT year, month, date, count(remote_addr) counts FROM (
		SELECT * FROM visitor_log
			WHERE date!=CURDATE()
			GROUP BY remote_addr, date
			ORDER BY date
		) a
	GROUP BY date;
DELETE FROM visitor_log
	WHERE date!=CURDATE();
COMMIT;