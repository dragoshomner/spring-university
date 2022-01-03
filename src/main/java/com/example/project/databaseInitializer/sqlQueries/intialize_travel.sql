DELIMITER //

create function get_random_datetime() 
returns DATETIME(6)
READS SQL DATA
DETERMINISTIC
begin 
	SET @MIN = '2022-02-01 00:00:00';
	SET @MAX = '2022-02-01 23:59:59';
	SELECT TIMESTAMPADD(SECOND, FLOOR(RAND() * TIMESTAMPDIFF(SECOND, @MIN, @MAX)), @MIN) into @random_datetime;
    return @random_datetime;
end;

CREATE PROCEDURE initialize_travel()
BEGIN
  DECLARE counter INT DEFAULT 0;
  
  WHILE counter < 200 DO
    SET counter = counter + 1;	
    select id, number_of_seats into @train_id, @number_of_seats from train order by rand() limit 1;
    select id, duration into @route_id, @route_duration from route order by rand() limit 1;
    select id into @driver_id from driver order by rand() limit 1;
    set @departure_time = get_random_datetime(); 
    set @arrival_time = timestampadd(second, @route_duration, @departure_time);
    insert into travel(`route_id`, `train_id`, `driver_id`, `departure_time`, `arrival_time`, `remaining_number_of_seats`, `created_at`, `modified_at`)
		values (@route_id, @train_id, @driver_id, @departure_time, @arrival_time, @number_of_seats, NOW(), NOW());
  END WHILE;
END; //

delimiter ;
call initialize_travel();
