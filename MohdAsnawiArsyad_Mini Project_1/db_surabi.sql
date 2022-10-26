-- create databse
create database surabi;

-- use database to create table
use surabi;

-- create table
CREATE TABLE `item` (

  `itemname` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `sellingPrice` double DEFAULT NULL
) ;

CREATE TABLE `bill` (
  `billID` int NOT NULL AUTO_INCREMENT,
  `billDate` date ,
  `total` double DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  PRIMARY KEY (billID)
) ;

CREATE TABLE `billDetail` (
   `billID` int DEFAULT NULL,
  `itemname` varchar(45) DEFAULT NULL,
  `orderQty` int DEFAULT NULL,
  `bdPrice` double DEFAULT NULL

) ;

CREATE TABLE `user` (
  `username` varchar(45),
  `emailID` varchar(45),
  `password` varchar(45),
  `userType` varchar(45)
) ;

CREATE TABLE `menu` (
  `menuName` varchar(45) DEFAULT NULL,
  `menuDesc` varchar(255) DEFAULT NULL
) ;

CREATE TABLE `menuItem` (
  `menuName` varchar(45) DEFAULT NULL,
  `itemname` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL

) ;

-- insert sample item
INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Carrot Cake','Dessert',22.59);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Vanila Cake','Dessert',30.20);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Tiramisu Cake','Dessert',19.20);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Red Valvet Cake','Dessert',25.28);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Chicken Chop','Main Course',18.60);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Lamb Chop','Main Course',22.50);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Spegetti Cabonara','Main Course',15.50);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Mix Salad','Main Course',19.30);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Soda Can','Beverage',3.30);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Coke Can','Beverage',3.30);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Fresh Orange','Beverage',12.45);

INSERT INTO item (itemname,category,sellingPrice)
VALUES ('Banana Float','Beverage',15.90);

-- insert sample menu
INSERT INTO menu (menuName,menuDesc)
VALUES ('Dinner','Time for breakfast start from 6am to 10am');

INSERT INTO menu (menuName,menuDesc)
VALUES ('Brunch','Time for Lunch start from 10am to 12pm');

INSERT INTO menu (menuName,menuDesc)
VALUES ('Lunch','Time for Lunch start from 12pm to 3pm');

-- insert menu item
INSERT INTO menuitem (menuName,itemname,category,price)
VALUES ('Dinner','Carrot Cake','Dessert',22.59);

INSERT INTO menuitem (menuName,itemname,category,price)
VALUES ('Dinner','Mix Salad','Main Course',19.30);

INSERT INTO menuitem (menuName,itemname,category,price)
VALUES ('Dinner','Chicken Chop','Main Course',18.60);

INSERT INTO menuitem (menuName,itemname,category,price)
VALUES ('Dinner','Coke Can','Beverage',3.30);



-- insert sample user
INSERT INTO user (username,emailID,password,userType)
VALUES ('asnawi','asnawicsc@gmail.com','abc123','Admin');

INSERT INTO user (username,emailID,password,userType)
VALUES ('kamal','kamal@gmail.com','abc321','User');
