Type your properties file to VM options like this: "-Dlardi.conf=/path/to/file.properties"

Before start create some test db.
All db tables are creating automatically.

All used configuration example:

#
# [ MySQL options ]
#
spring.jpa.database=MYSQL

spring.datasource.url=jdbc:mysql://localhost:3306/lardi

spring.datasource.username=root

spring.datasource.password=root

spring.datasource.driver-class-name=com.mysql.jdbc.Driver

useSSL=false

spring.jpa.show-sql=true

spring.jpa.hibernate.ddl-auto=create  // choose create or validate schema

#
# [ Other Configuration Attributes ]
#
use.file.database=true  // true, if you wont to use file db system (JSON files);

use.file.database.ddl-auto=create  // choose create or validate schema

use.file.database.path=D:/work/ // path to JSON file system

#
# [Test Configuration]
#
test=false // launching test initialization
