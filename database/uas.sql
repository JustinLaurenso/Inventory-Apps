-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 16 Des 2020 pada 04.00
-- Versi server: 10.4.8-MariaDB
-- Versi PHP: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `category`
--

CREATE TABLE `category` (
  `no` int(3) NOT NULL,
  `kategori` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `category`
--

INSERT INTO `category` (`no`, `kategori`) VALUES
(1, 'Food');

-- --------------------------------------------------------

--
-- Struktur dari tabel `customer`
--

CREATE TABLE `customer` (
  `cust_id` varchar(20) NOT NULL,
  `cust_name` char(25) NOT NULL,
  `cust_email` varchar(22) NOT NULL,
  `address` varchar(30) NOT NULL,
  `nohp` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `customer`
--

INSERT INTO `customer` (`cust_id`, `cust_name`, `cust_email`, `address`, `nohp`) VALUES
('CS-001', 'justin1233', 'justin', 'justin', 123213),
('CS-002', 'Vartin', 'vartin@gmail.com', 'jakarta', 898134354);

-- --------------------------------------------------------

--
-- Struktur dari tabel `product`
--

CREATE TABLE `product` (
  `product_id` varchar(10) NOT NULL,
  `name_product` varchar(15) NOT NULL,
  `price` double NOT NULL,
  `stock` int(4) NOT NULL,
  `category` varchar(10) NOT NULL,
  `supp_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `product`
--

INSERT INTO `product` (`product_id`, `name_product`, `price`, `stock`, `category`, `supp_id`) VALUES
('BR-002', 'Bang bang33', 10000, 90, 'Food', 'SP-001'),
('BR-003', 'asdsd', 1233, 123321, 'Food', 'SP-003');

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `supp_id` varchar(20) NOT NULL,
  `supp_name` char(25) NOT NULL,
  `supp_email` varchar(25) NOT NULL,
  `supp_address` varchar(22) NOT NULL,
  `supp_nohp` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`supp_id`, `supp_name`, `supp_email`, `supp_address`, `supp_nohp`) VALUES
('SP-001', 'Justin', 'justin', 'justin', 123123),
('SP-002', 'asd', 'ads', 'asd', 1),
('SP-003', 'Aida', 'aida@gmail.com', 'singkawang', 898123213);

-- --------------------------------------------------------

--
-- Struktur dari tabel `temp_trans_in`
--

CREATE TABLE `temp_trans_in` (
  `notemp` int(10) NOT NULL,
  `notrans` varchar(20) NOT NULL,
  `product_id` varchar(20) NOT NULL,
  `qty` int(10) NOT NULL,
  `price` double NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `temp_trans_out`
--

CREATE TABLE `temp_trans_out` (
  `notemp` int(10) NOT NULL,
  `notrans` varchar(20) NOT NULL,
  `product_id` varchar(10) NOT NULL,
  `qty` int(10) NOT NULL,
  `price` double NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `trans_in`
--

CREATE TABLE `trans_in` (
  `notrans` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `supp_id` varchar(10) NOT NULL,
  `username` varchar(20) NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `trans_in`
--

INSERT INTO `trans_in` (`notrans`, `date`, `supp_id`, `username`, `total`) VALUES
('INV/IN/161220001', '2020-12-16', 'SP-001', 'Justin Laurenso', 50000),
('INV/IN/161220002', '2020-12-16', 'SP-002', 'Justin Laurenso', 50000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `trans_in_detail`
--

CREATE TABLE `trans_in_detail` (
  `notrans` varchar(20) NOT NULL,
  `product_id` varchar(10) NOT NULL,
  `qty` int(10) NOT NULL,
  `price` double NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `trans_in_detail`
--

INSERT INTO `trans_in_detail` (`notrans`, `product_id`, `qty`, `price`, `total`) VALUES
('INV/IN/161220001', 'BR-002', 5, 10000, 50000),
('INV/IN/161220002', 'BR-002', 5, 10000, 50000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `trans_out`
--

CREATE TABLE `trans_out` (
  `notrans` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `cust_id` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `trans_out`
--

INSERT INTO `trans_out` (`notrans`, `date`, `cust_id`, `username`, `total`) VALUES
('INV/OUT161220001', '2020-12-16', 'CS-001', 'Justin Laurenso', 100000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `trans_out_detail`
--

CREATE TABLE `trans_out_detail` (
  `notrans` varchar(20) NOT NULL,
  `product_id` varchar(10) NOT NULL,
  `qty` int(10) NOT NULL,
  `price` double NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `trans_out_detail`
--

INSERT INTO `trans_out_detail` (`notrans`, `product_id`, `qty`, `price`, `total`) VALUES
('INV/OUT161220001', 'BR-002', 10, 10000, 100000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` char(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `position` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`username`, `password`, `name`, `email`, `position`) VALUES
('admin', '202cb962ac59075b964b07152d234b70', 'Justin Laurenso', 'justin', 'Admin'),
('admin1', '202cb962ac59075b964b07152d234b70', 'William', 'william', 'Admin'),
('user', 'ee11cbb19052e40b07aac0ca060c23ee', 'user', 'user', 'User');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`no`);

--
-- Indeks untuk tabel `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cust_id`);

--
-- Indeks untuk tabel `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supp_id`);

--
-- Indeks untuk tabel `temp_trans_in`
--
ALTER TABLE `temp_trans_in`
  ADD PRIMARY KEY (`notemp`);

--
-- Indeks untuk tabel `temp_trans_out`
--
ALTER TABLE `temp_trans_out`
  ADD PRIMARY KEY (`notemp`);

--
-- Indeks untuk tabel `trans_in`
--
ALTER TABLE `trans_in`
  ADD PRIMARY KEY (`notrans`);

--
-- Indeks untuk tabel `trans_out`
--
ALTER TABLE `trans_out`
  ADD PRIMARY KEY (`notrans`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `category`
--
ALTER TABLE `category`
  MODIFY `no` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `temp_trans_in`
--
ALTER TABLE `temp_trans_in`
  MODIFY `notemp` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `temp_trans_out`
--
ALTER TABLE `temp_trans_out`
  MODIFY `notemp` int(10) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
