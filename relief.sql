-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 03, 2020 at 03:14 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `relief`
--

-- --------------------------------------------------------

--
-- Table structure for table `donations`
--

CREATE TABLE `donations` (
  `donation_id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`donation_id`, `name`, `quantity`, `create_time_stamp`) VALUES
(1, 'Canned goods', 0, '2020-11-03 20:57:15');

-- --------------------------------------------------------

--
-- Table structure for table `donners`
--

CREATE TABLE `donners` (
  `donner_id` int(11) NOT NULL,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(50) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `donners`
--

INSERT INTO `donners` (`donner_id`, `full_name`, `address`, `contact_number`, `create_time_stamp`) VALUES
(1, 'donner 1', 'donner address 1', '09xxxxxxxxx', '2020-11-03 20:34:18');

-- --------------------------------------------------------

--
-- Table structure for table `donners_donations`
--

CREATE TABLE `donners_donations` (
  `donners_donations_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `donner_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `donation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `donners_donations`
--

INSERT INTO `donners_donations` (`donners_donations_id`, `donation_id`, `donner_id`, `quantity`, `donation_date`, `create_time_stamp`) VALUES
(1, 1, 1, 5, '2020-11-03 21:31:47', '2020-11-03 21:02:54');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `sr_code` varchar(50) NOT NULL,
  `password` varchar(250) NOT NULL,
  `is_requesting_relief` tinyint(1) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `sr_code`, `password`, `is_requesting_relief`, `create_time_stamp`) VALUES
(1, '11111-11', '11111-11', 1, '2020-11-03 20:28:39');

-- --------------------------------------------------------

--
-- Table structure for table `student_reliefs`
--

CREATE TABLE `student_reliefs` (
  `student_relief_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `is_release` tinyint(1) NOT NULL,
  `date_release` datetime NOT NULL DEFAULT current_timestamp(),
  `request_date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `volunteers`
--

CREATE TABLE `volunteers` (
  `volunteer_id` int(11) NOT NULL,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(50) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `donations`
--
ALTER TABLE `donations`
  ADD PRIMARY KEY (`donation_id`);

--
-- Indexes for table `donners`
--
ALTER TABLE `donners`
  ADD PRIMARY KEY (`donner_id`);

--
-- Indexes for table `donners_donations`
--
ALTER TABLE `donners_donations`
  ADD PRIMARY KEY (`donners_donations_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `student_reliefs`
--
ALTER TABLE `student_reliefs`
  ADD PRIMARY KEY (`student_relief_id`);

--
-- Indexes for table `volunteers`
--
ALTER TABLE `volunteers`
  ADD PRIMARY KEY (`volunteer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `donations`
--
ALTER TABLE `donations`
  MODIFY `donation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `donners`
--
ALTER TABLE `donners`
  MODIFY `donner_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `donners_donations`
--
ALTER TABLE `donners_donations`
  MODIFY `donners_donations_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `student_reliefs`
--
ALTER TABLE `student_reliefs`
  MODIFY `student_relief_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `volunteers`
--
ALTER TABLE `volunteers`
  MODIFY `volunteer_id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
