-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 04, 2021 at 10:32 PM
-- Server version: 8.0.18
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `othello_game`
--

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
CREATE TABLE IF NOT EXISTS `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `username` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `moves` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `isWon` tinyint(1) NOT NULL,
  `state` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username_fk` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `game`
--

INSERT INTO `game` (`id`, `name`, `username`, `date`, `moves`, `type`, `isWon`, `state`) VALUES
(6, 'testGame', 'aymane', '2021-01-04', 'B11,W22,B33,W44,B55', 0, 0, '0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E'),
(7, 'testGame', 'aymane', '2021-01-04', 'B11,W22,B33,W44,B55', 0, 0, '0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E'),
(8, 'testGame', 'aymane', '2021-01-04', 'B11,W22,B33,W44,B55', 0, 0, '0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E'),
(9, 'testGame', 'aymane', '2021-01-04', 'B11,W22,B33,W44,B55', 0, 0, '0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E'),
(10, 'testGame', 'aymane', '2021-01-04', 'B11,W22,B33,W44,B55', 0, 0, '0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E'),
(11, 'testGame', 'aymane', '2021-01-04', 'B11,W22,B33,W44,B55', 0, 0, '0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E'),
(12, 'newgame', 'aymane', '2021-01-04', 'B22,W23,B11,W21,B00,W20,B52,W53,B54', 1, 0, '0,0,B|0,1,E|0,2,E|0,3,E|0,4,E|0,5,E|0,6,E|0,7,E|1,0,E|1,1,B|1,2,E|1,3,E|1,4,E|1,5,E|1,6,E|1,7,E|2,0,W|2,1,W|2,2,W|2,3,W|2,4,E|2,5,E|2,6,E|2,7,E|3,0,E|3,1,E|3,2,E|3,3,W|3,4,B|3,5,E|3,6,E|3,7,E|4,0,E|4,1,E|4,2,E|4,3,W|4,4,B|4,5,E|4,6,E|4,7,E|5,0,E|5,1,E|5,2,B|5,3,B|5,4,B|5,5,E|5,6,E|5,7,E|6,0,E|6,1,E|6,2,E|6,3,E|6,4,E|6,5,E|6,6,E|6,7,E|7,0,E|7,1,E|7,2,E|7,3,E|7,4,E|7,5,E|7,6,E|7,7,E|'),
(13, 'newgame', 'aymane', '2021-01-04', 'B22,W23,B11,W21,B00,W20,B52,W53,B54,W65,B61', 1, 0, '0,0,B|0,1,E|0,2,E|0,3,E|0,4,E|0,5,E|0,6,E|0,7,E|1,0,E|1,1,B|1,2,E|1,3,E|1,4,E|1,5,E|1,6,E|1,7,E|2,0,W|2,1,W|2,2,W|2,3,W|2,4,E|2,5,E|2,6,E|2,7,E|3,0,E|3,1,E|3,2,E|3,3,W|3,4,B|3,5,E|3,6,E|3,7,E|4,0,E|4,1,E|4,2,E|4,3,B|4,4,B|4,5,E|4,6,E|4,7,E|5,0,E|5,1,E|5,2,B|5,3,B|5,4,W|5,5,E|5,6,E|5,7,E|6,0,E|6,1,B|6,2,E|6,3,E|6,4,E|6,5,W|6,6,E|6,7,E|7,0,E|7,1,E|7,2,E|7,3,E|7,4,E|7,5,E|7,6,E|7,7,E|'),
(14, 'lolgame', 'aymane', '2021-01-04', 'B52,W35,B26,W61,B22,W54,B32,W17,B70,W11,B36,W24,B16,W46,B65,W42,B31,W00,B30,W20,B10,W51,B21,W45,B14,W23,B12,W13,B05,W15,B27,W55,B25,W53,B41,W40,B01,W02,B04,W06,B07,W03,B37', 0, 0, '0,0,W|0,1,B|0,2,W|0,3,W|0,4,B|0,5,B|0,6,B|0,7,B|1,0,B|1,1,B|1,2,W|1,3,W|1,4,W|1,5,B|1,6,W|1,7,B|2,0,B|2,1,B|2,2,W|2,3,W|2,4,B|2,5,W|2,6,W|2,7,B|3,0,B|3,1,B|3,2,W|3,3,W|3,4,B|3,5,W|3,6,W|3,7,B|4,0,W|4,1,B|4,2,W|4,3,W|4,4,B|4,5,W|4,6,B|4,7,E|5,0,E|5,1,B|5,2,W|5,3,W|5,4,B|5,5,B|5,6,E|5,7,E|6,0,E|6,1,B|6,2,E|6,3,E|6,4,E|6,5,B|6,6,E|6,7,E|7,0,B|7,1,E|7,2,E|7,3,E|7,4,E|7,5,E|7,6,E|7,7,E|');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`) VALUES
('', 'iSUqUJfX1CY='),
('Akkar', 'b4WE4bXVBj0='),
('aymane', 'FY1EbSpWMhu3bGqRhsJRHg=='),
('aymane2', 'd13GYtj0nEA=');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `game`
--
ALTER TABLE `game`
  ADD CONSTRAINT `username_fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
