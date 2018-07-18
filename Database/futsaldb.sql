-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2018 at 01:45 AM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `futsaldb`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE IF NOT EXISTS `akun` (
  `kd_akun` varchar(8) NOT NULL,
  `nm_akun` varchar(20) NOT NULL,
  `jenis_akun` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`kd_akun`, `nm_akun`, `jenis_akun`) VALUES
('Akun-001', 'Bayar Listrik', 'Aktiva');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `kd_booking` varchar(8) NOT NULL,
  `tgl_booking` date NOT NULL,
  `kd_pelanggan` varchar(8) NOT NULL,
  `tgl_pakai` date NOT NULL,
  `jam_masuk` time NOT NULL,
  `jam_keluar` time NOT NULL,
  `diskon` double NOT NULL,
  `hari_libur` double NOT NULL,
  `kd_lap` varchar(7) NOT NULL,
  `uang_dp` double NOT NULL,
  `kd_user` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`kd_booking`, `tgl_booking`, `kd_pelanggan`, `tgl_pakai`, `jam_masuk`, `jam_keluar`, `diskon`, `hari_libur`, `kd_lap`, `uang_dp`, `kd_user`) VALUES
('Book-001', '2018-07-13', 'Team-001', '2018-07-14', '17:00:00', '19:00:00', 0, 0, 'Lap-001', 50000, 'User-001'),
('Book-002', '2018-07-13', 'Team-002', '2018-07-14', '15:00:00', '16:00:00', 10, 0, 'Lap-002', 50000, 'User-001'),
('Book-003', '2018-07-14', 'Team-002', '2018-07-15', '14:00:00', '16:00:00', 0, 0, 'Lap-001', 50000, 'User-001'),
('Book-004', '2018-07-15', 'Team-002', '2018-07-16', '15:00:00', '17:00:00', 0, 0, 'Lap-001', 100000, 'User-001'),
('Book-005', '2018-07-15', 'Team-002', '2018-07-17', '16:00:00', '18:00:00', 0, 0, 'Lap-001', 50000, 'User-001'),
('Book-006', '2018-07-17', 'Team-002', '2018-07-17', '06:00:00', '08:00:00', 18000, 0, 'Lap-001', 50000, 'User-001');

-- --------------------------------------------------------

--
-- Table structure for table `jurnal`
--

CREATE TABLE IF NOT EXISTS `jurnal` (
  `no_jurnal` varchar(10) NOT NULL,
  `tgl_jurnal` date NOT NULL,
  `no_trans` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `jurnal_detail`
--

CREATE TABLE IF NOT EXISTS `jurnal_detail` (
  `id` int(11) NOT NULL,
  `no_jurnal` varchar(10) NOT NULL,
  `kd_akun` varchar(8) NOT NULL,
  `debet` double NOT NULL,
  `kredit` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lapangan`
--

CREATE TABLE IF NOT EXISTS `lapangan` (
  `kd_lap` varchar(7) NOT NULL,
  `jenis_lap` varchar(6) NOT NULL,
  `tarif` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lapangan`
--

INSERT INTO `lapangan` (`kd_lap`, `jenis_lap`, `tarif`) VALUES
('Lap-001', 'Vinyl', 90000),
('Lap-002', 'Vinyl', 90000),
('Lap-003', 'Vinyl', 90000),
('Lap-004', 'Rumput', 80000),
('Lap-005', 'Rumput', 80000),
('Lap-006', 'Rumput', 80000);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE IF NOT EXISTS `pelanggan` (
  `kd_pelanggan` varchar(8) NOT NULL,
  `nm_pelanggan` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_hp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`kd_pelanggan`, `nm_pelanggan`, `alamat`, `no_hp`) VALUES
('Team-001', 'The Report', 'Jalan Hj. Gedad No 78 Ciledug Karang Tengah', '081928771877'),
('Team-002', 'The Ice Holes', 'Jalan Raya No. 789 Kecamatan No 39', '08991119991991');

-- --------------------------------------------------------

--
-- Table structure for table `penyewaan`
--

CREATE TABLE IF NOT EXISTS `penyewaan` (
  `no_trans` varchar(9) NOT NULL,
  `tgl_sewa` date NOT NULL,
  `kd_pelanggan` varchar(8) NOT NULL,
  `kd_booking` varchar(8) NOT NULL,
  `kd_lap` varchar(7) NOT NULL,
  `jam_sewa_masuk` time NOT NULL,
  `jam_sewa_keluar` time NOT NULL,
  `lama_sewa` double NOT NULL,
  `hari_libur` double NOT NULL,
  `diskon_sewa` double NOT NULL,
  `total_sewa` double NOT NULL,
  `uang_byr` double NOT NULL,
  `kd_user` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penyewaan`
--

INSERT INTO `penyewaan` (`no_trans`, `tgl_sewa`, `kd_pelanggan`, `kd_booking`, `kd_lap`, `jam_sewa_masuk`, `jam_sewa_keluar`, `lama_sewa`, `hari_libur`, `diskon_sewa`, `total_sewa`, `uang_byr`, `kd_user`) VALUES
('Trans-001', '2018-07-14', 'Team-001', 'Book-001', 'Lap-001', '17:00:00', '19:00:00', 2, 0, 0, 285000, 300000, 'User-001'),
('Trans-002', '2018-07-16', 'Team-002', 'Book-002', 'Lap-002', '15:00:00', '16:00:00', 1, 0, 0, 115000, 65000, 'User-001');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `kd_user` varchar(8) NOT NULL,
  `nm_user` varchar(50) NOT NULL,
  `hak_akses` varchar(7) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`kd_user`, `nm_user`, `hak_akses`, `password`) VALUES
('User-001', 'NPC', 'Owner', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`kd_akun`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`kd_booking`);

--
-- Indexes for table `jurnal`
--
ALTER TABLE `jurnal`
  ADD PRIMARY KEY (`no_jurnal`);

--
-- Indexes for table `jurnal_detail`
--
ALTER TABLE `jurnal_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lapangan`
--
ALTER TABLE `lapangan`
  ADD PRIMARY KEY (`kd_lap`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`kd_pelanggan`);

--
-- Indexes for table `penyewaan`
--
ALTER TABLE `penyewaan`
  ADD PRIMARY KEY (`no_trans`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`kd_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jurnal_detail`
--
ALTER TABLE `jurnal_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
