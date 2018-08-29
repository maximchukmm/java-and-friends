CREATE OR REPLACE FUNCTION minus_tz_offset(date_time_value TIMESTAMP, time_zone varchar)
  RETURNS TIMESTAMP
AS 'SELECT date_time_value - (date_time_value AT TIME ZONE ''UTC'' - date_time_value AT TIME ZONE time_zone)'
LANGUAGE SQL
IMMUTABLE
RETURNS NULL ON NULL INPUT;

CREATE OR REPLACE FUNCTION plus_tz_offset(date_time_value TIMESTAMP, time_zone varchar)
  RETURNS TIMESTAMP
AS 'SELECT date_time_value + (date_time_value - date_time_value AT TIME ZONE time_zone)'
LANGUAGE SQL
IMMUTABLE
RETURNS NULL ON NULL INPUT;

CREATE OR REPLACE FUNCTION to_char_with_tz(date_time_value TIMESTAMP, time_format varchar, time_zone varchar)
  RETURNS varchar
AS 'SELECT to_char(plus_tz_offset((date_time_value AT TIME ZONE ''UTC'') :: TIMESTAMP, time_zone), time_format)'
LANGUAGE SQL
IMMUTABLE
RETURNS NULL ON NULL INPUT;