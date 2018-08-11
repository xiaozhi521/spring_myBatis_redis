CREATE TABLE items(
     id INT PRIMARY KEY AUTO_INCREMENT,
     itemName VARCHAR(50),
     price DOUBLE ,
     detail VARCHAR(50),
     pic VARCHAR(50),
     createtime DATE ,
     userId int,
     FOREIGN KEY (userId) REFERENCES user(id)
)

INSERT into items (itemName,price,detail,pic,createtime,userId) VALUES ('banana',3.50,'这是国产海南香蕉！','',now(),1);
INSERT into items (itemName,price,detail,pic,createtime,userId) VALUES ('peach',6.66,'这是平谷大桃！','',now(),1);
INSERT into items (itemName,price,detail,pic,createtime,userId) VALUES ('orange',4.66,'这是四川甜橙！','',now(),1);

INSERT into items (itemName,price,detail,pic,createtime,userId) VALUES ('banana',3.50,'这是国产海南香蕉！','暂无图片',now(),2);
INSERT into items (itemName,price,detail,pic,createtime,userId) VALUES ('peach',6.66,'这是平谷大桃！','暂无图片',now(),2);
INSERT into items (itemName,price,detail,pic,createtime,userId) VALUES ('orange',4.66,'这是四川甜橙！','暂无图片',now(),2);

