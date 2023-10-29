-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-02-2021 a las 04:17:10
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectofinal`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre_cliente` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `cedula_cliente` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `telefono_cliente` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `correo_cliente` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `direccion_cliente` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_cliente` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `compras_cliente` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nombre_cliente`, `cedula_cliente`, `telefono_cliente`, `correo_cliente`, `direccion_cliente`, `tipo_cliente`, `compras_cliente`) VALUES
(1, 'juan', '1105053969', '09999999999', 'juan@hotmail.com', 'loja', 'Afiliado', 1.61),
(3, 'delgado', '1701198267', '08888999999', 'juan5@hotmail.com', 'quito', 'Normal', 3.36);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE `facturas` (
  `id_factura` int(11) NOT NULL,
  `id_clienteFactura` int(11) NOT NULL,
  `compras_factura` double NOT NULL,
  `fecha_factura` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `facturas`
--

INSERT INTO `facturas` (`id_factura`, `id_clienteFactura`, `compras_factura`, `fecha_factura`) VALUES
(1, 1, 1.31, '2021-02-24'),
(2, 1, 2.02, '2021-02-24'),
(3, 1, 1.31, '2021-02-24'),
(4, 1, 2.72, '2021-02-24'),
(5, 3, 2.69, '2021-02-24'),
(6, 3, 2.69, '2021-02-24'),
(7, 1, 1.62, '2021-02-24'),
(8, 1, 1.46, '2021-02-24'),
(9, 3, 2.7, '2021-02-24'),
(10, 3, 2.13, '2021-02-24'),
(11, 3, 3.36, '2021-02-24'),
(12, 1, 4.44, '2021-02-24'),
(13, 1, 4.33, '2021-02-24'),
(14, 1, 1.61, '2021-02-24');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingresos`
--

CREATE TABLE `ingresos` (
  `id_Ningreso` int(11) NOT NULL,
  `id_usuarioIngreso` int(11) NOT NULL,
  `nombre_ingreso` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fecha_ingreso` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `ingresos`
--

INSERT INTO `ingresos` (`id_Ningreso`, `id_usuarioIngreso`, `nombre_ingreso`, `fecha_ingreso`) VALUES
(1, 3, 'juan', '2021-02-24'),
(2, 4, 'julio', '2021-02-24'),
(3, 4, 'julio', '2021-02-24'),
(4, 14, 'Juan', '2021-02-24'),
(5, 14, 'Juan', '2021-02-24'),
(6, 14, 'Juan', '2021-02-24'),
(7, 14, 'Juan', '2021-02-24'),
(8, 14, 'Juan', '2021-02-24'),
(9, 14, 'Juan', '2021-02-24'),
(10, 14, 'Juan', '2021-02-24'),
(11, 14, 'Juan', '2021-02-24'),
(12, 14, 'Juan', '2021-02-24'),
(13, 14, 'Juan', '2021-02-24'),
(14, 14, 'Juan', '2021-02-24'),
(15, 14, 'Juan', '2021-02-24'),
(16, 3, 'juan', '2021-02-24'),
(17, 4, 'julio', '2021-02-24'),
(18, 2, 'socucu', '2021-02-24'),
(19, 14, 'Juan', '2021-02-24'),
(20, 14, 'Juan', '2021-02-24'),
(21, 3, 'juan', '2021-02-24'),
(22, 3, 'juan', '2021-02-24');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `codigo_Barra` int(11) NOT NULL,
  `nombre_producto` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `marca_producto` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `cantidad_producto` int(11) NOT NULL,
  `precio_producto` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`codigo_Barra`, `nombre_producto`, `marca_producto`, `cantidad_producto`, `precio_producto`) VALUES
(13, 'atún', 'Real', 40, 1.1),
(15, 'Leche', 'Ecolac', 45, 0.8),
(458, 'colombinas', 'nestle', 55, 0.15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `cedula_usuario` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `telefono_usuario` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `correo_usuario` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `direccion_usuario` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `usuario` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `contraseña_usuario` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tipoUsuario` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `cedula_usuario`, `telefono_usuario`, `correo_usuario`, `direccion_usuario`, `usuario`, `contraseña_usuario`, `tipoUsuario`) VALUES
(3, 'juan', '111', '555', 'asda', 'ss', 'juadas', 'juadas', 'Empleado'),
(4, 'julio', '55', '555', 'sad', 'ss', 'juadastron', 'juadas', 'Empleado'),
(14, 'Juan', '1105822447', '097867895', 'juan@gmail.com', 'napo', 'Pan7', '12345', 'Empleado'),
(15, 'lucas', '1105053977', '0977777777', 'juadas@gmail.com', 'loja', 'lucas123', '12345', 'Empleado'),
(17, 'alex', '1701198267', '0658325489', 'juanC@hotmail.com', 'loja', 'juan123', '4523', 'Administrador'),
(18, 'diego', '1105053969', '0548963314', 'deigo@gmail.com', 'pastaza', 'diego123', '8645', 'Administrador');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`id_factura`);

--
-- Indices de la tabla `ingresos`
--
ALTER TABLE `ingresos`
  ADD PRIMARY KEY (`id_Ningreso`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`codigo_Barra`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `facturas`
--
ALTER TABLE `facturas`
  MODIFY `id_factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `ingresos`
--
ALTER TABLE `ingresos`
  MODIFY `id_Ningreso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
