-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: db:3306
-- Generation Time: May 21, 2026 at 09:51 PM
-- Server version: 8.4.8
-- PHP Version: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carts`
--

-- --------------------------------------------------------

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`id`, `customer_id`, `status`, `created_at`, `updated_at`) VALUES
(1, 1, 'ACTIVE', '2026-05-20 10:15:00.000000', '2026-05-20 10:20:00.000000'),
(2, 2, 'CHECKED_OUT', '2026-05-19 14:30:00.000000', '2026-05-19 14:45:00.000000'),
(3, 3, 'ABANDONED', '2026-05-18 09:00:00.000000', '2026-05-18 09:10:00.000000');

-- --------------------------------------------------------

--
-- Dumping data for table `carts_items`
--

INSERT INTO `carts_items` (`id`, `cart_id`, `product_id`, `quantity`, `price`) VALUES
(1, 1, 1, 1, 32990),
(2, 1, 9, 2, 4990),
(3, 2, 2, 1, 26990),
(4, 2, 3, 2, 9990),
(5, 3, 6, 1, 24990);

-- --------------------------------------------------------

--
-- AUTO_INCREMENT for table `carts`
--
ALTER TABLE `carts`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `carts_items`
--
ALTER TABLE `carts_items`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
