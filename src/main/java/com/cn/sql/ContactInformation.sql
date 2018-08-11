CREATE TABLE contactInformation(
     c_id INT PRIMARY KEY AUTO_INCREMENT,
     c_tel VARCHAR(11),
     c_email VARCHAR(30),
     user_id int,
     FOREIGN KEY (user_id) REFERENCES user(id)
)

INSERT into contactinformation (c_tel,c_email,user_id) VALUES ('13581978745','muqingfeng@163.com',1);
INSERT into contactinformation (c_tel,c_email,user_id) VALUES ('13581978746','muqingfeng1@163.com',2);
