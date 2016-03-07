-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-03-2016 a las 15:53:33
-- Versión del servidor: 10.1.9-MariaDB
-- Versión de PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `guessit`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aula`
--

CREATE TABLE `aula` (
  `id` int(11) NOT NULL,
  `nombre` varchar(128) NOT NULL,
  `id_docente` int(11) NOT NULL,
  `activa` int(11) NOT NULL,
  `id_idioma` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `aula`
--

INSERT INTO `aula` (`id`, `nombre`, `id_docente`, `activa`, `id_idioma`) VALUES
(7, 'Alemán 2016', 14, 1, 1),
(8, 'Inglés 2016', 14, 1, 2),
(9, 'Español 2016', 14, 1, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` int(11) NOT NULL,
  `nombre` varchar(64) NOT NULL,
  `id_aula` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`, `id_aula`) VALUES
(2, 'Kéndër', 7),
(3, 'Carmén', 7),
(4, 'Casa', 9),
(5, 'Deportes', 9),
(6, 'Mundo', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `definiciones`
--

CREATE TABLE `definiciones` (
  `id` int(11) NOT NULL,
  `nivel` int(11) NOT NULL,
  `palabra` varchar(32) NOT NULL,
  `articulo` varchar(16) DEFAULT NULL,
  `frase` text NOT NULL,
  `pista` text NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `id_aula` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `validar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `definiciones`
--

INSERT INTO `definiciones` (`id`, `nivel`, `palabra`, `articulo`, `frase`, `pista`, `id_categoria`, `id_aula`, `id_usuario`, `fecha`, `validar`) VALUES
(1, 1, 'hell', NULL, 'I''m gonna hiiighway to hell.', 'AC DC', 2, 7, 14, '0000-00-00', 0),
(2, 1, 'orange', NULL, 'I want to eat some orange.', 'Fruit equal than color.', 2, 7, 14, '2016-02-25', 1),
(5, 1, 'Krüger', 'der', 'ASD Krüger', 'ASD K', 3, 7, 14, '2016-02-26', 1),
(6, 1, 'Mender', 'der', 'Hender Mender', 'Mender', 3, 7, 14, '2016-02-26', 1),
(7, 1, 'Alender', 'der', 'Mender Alender', 'Alender', 3, 7, 14, '2016-02-26', 1),
(8, 1, 'perro', NULL, 'Hoy saldré a pasear con mi perro.', 'Animal de compañía.', 4, 9, 14, '2016-02-29', 1),
(9, 1, 'balón', '', 'En el fútbol se patea un balón.', 'Artículo redondo.', 5, 9, 14, '2016-02-29', 1),
(10, 1, 'humano', '', 'En clase de biología, estudiamos el cuerpo humano.', 'Concepto que engloba a la especia humana, el ser ...', 6, 9, 5, '2016-03-03', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idiomas`
--

CREATE TABLE `idiomas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `idiomas`
--

INSERT INTO `idiomas` (`id`, `nombre`) VALUES
(1, 'German'),
(2, 'English'),
(4, 'Spanish');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puntuaciones`
--

CREATE TABLE `puntuaciones` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_palabra` int(11) NOT NULL,
  `puntuacion` int(11) NOT NULL,
  `acierto` int(8) NOT NULL,
  `pista` int(8) NOT NULL,
  `intentos` int(8) NOT NULL,
  `reporte` int(8) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `puntuaciones`
--

INSERT INTO `puntuaciones` (`id`, `id_usuario`, `id_palabra`, `puntuacion`, `acierto`, `pista`, `intentos`, `reporte`, `fecha`) VALUES
(1, 5, 1, 0, 0, 0, 3, 0, '2016-02-25'),
(2, 5, 1, 0, 0, 0, 3, 0, '2016-02-25'),
(3, 5, 1, 0, 0, 0, 3, 1, '2016-02-25'),
(4, 5, 2, 3, 1, 0, 0, 0, '2016-02-25'),
(5, 5, 1, 3, 1, 0, 0, 0, '2016-02-26'),
(6, 5, 2, 2, 1, 0, 0, 0, '2016-02-26'),
(7, 5, 5, 3, 1, 0, 0, 0, '2016-02-26'),
(8, 5, 1, 2, 1, 0, 0, 0, '2016-02-26'),
(9, 5, 1, 1, 1, 0, 0, 0, '2016-02-26'),
(10, 5, 1, 1, 1, 0, 1, 0, '2016-02-26'),
(11, 5, 1, 2, 1, 0, 0, 0, '2016-02-26'),
(12, 5, 2, 2, 1, 0, 0, 0, '2016-02-26'),
(13, 5, 5, 2, 1, 0, 0, 0, '2016-02-26'),
(14, 5, 6, 3, 1, 0, 0, 0, '2016-02-26'),
(15, 5, 5, 1, 1, 0, 0, 0, '2016-02-26'),
(16, 5, 6, 3, 1, 0, 0, 0, '2016-02-26'),
(17, 5, 7, 2, 1, 0, 0, 0, '2016-02-26'),
(18, 5, 8, 2, 1, 0, 0, 0, '2016-02-29'),
(19, 5, 9, 1, 1, 1, 0, 0, '2016-02-29'),
(20, 5, 8, 2, 1, 0, 0, 0, '2016-02-29'),
(21, 5, 9, 3, 1, 0, 0, 0, '2016-02-29'),
(22, 5, 8, 1, 1, 0, 0, 0, '2016-02-29'),
(23, 5, 8, 2, 1, 0, 0, 0, '2016-02-29'),
(24, 5, 9, 3, 0, 0, 3, 0, '2016-02-29'),
(25, 5, 8, 2, 1, 0, 0, 0, '2016-03-01'),
(26, 5, 8, 2, 1, 0, 0, 0, '2016-03-01'),
(27, 5, 9, 3, 1, 0, 0, 0, '2016-03-01'),
(28, 5, 8, 3, 1, 0, 0, 0, '2016-03-01'),
(29, 5, 9, 1, 1, 0, 0, 0, '2016-03-01'),
(30, 5, 8, 2, 1, 0, 0, 0, '2016-03-02'),
(31, 5, 8, 0, 1, 0, 0, 1, '2016-03-03'),
(32, 5, 9, 0, 0, 0, 3, 1, '2016-03-03'),
(33, 5, 8, 2, 1, 0, 0, 0, '2016-03-03'),
(34, 5, 9, 1, 0, 0, 3, 0, '2016-03-03'),
(35, 5, 8, 3, 1, 0, 0, 0, '2016-03-03'),
(36, 5, 10, 3, 1, 1, 1, 0, '2016-03-03'),
(38, 3, 6, 3, 1, 0, 0, 0, '2016-03-04'),
(39, 3, 6, 3, 1, 0, 0, 0, '2016-03-04'),
(40, 5, 8, 3, 1, 0, 1, 0, '2016-03-07'),
(41, 5, 9, 3, 1, 0, 0, 0, '2016-03-07'),
(42, 5, 8, 3, 1, 0, 0, 0, '2016-03-07'),
(43, 5, 9, 3, 1, 0, 0, 0, '2016-03-07');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `score`
--

CREATE TABLE `score` (
  `id` int(8) NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `puntaje` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `score`
--

INSERT INTO `score` (`id`, `nombre`, `puntaje`) VALUES
(1, 'suscribete', 222),
(2, 'suscribete', 222),
(3, 'suscribete', 222),
(4, 'suscribete', 222),
(5, 'suscribete', 222),
(6, 'suscribete', 222),
(7, 'suscribete', 222),
(8, 'suscribete', 222),
(9, 'suscribete', 222),
(10, 'suscribete', 222);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(64) NOT NULL,
  `apellidos` varchar(128) NOT NULL,
  `email` varchar(256) NOT NULL,
  `usuario` varchar(64) DEFAULT NULL,
  `password` text NOT NULL,
  `alta` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `validar` int(11) NOT NULL,
  `centro` text,
  `tipo` int(8) NOT NULL COMMENT '0 = alumno, 1 = docente',
  `test` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellidos`, `email`, `usuario`, `password`, `alta`, `validar`, `centro`, `tipo`, `test`) VALUES
(1, 'Rogencio', 'Penelo', 'rog@gmail.com', 'rog', '$2y$10$LgLkQxEQPh6EwkzbkHNexOPth3UP7svb3M6./90evAssl02cGFUK6', '2016-02-09 14:08:40', 0, NULL, 0, 1),
(2, 'Anselmo', 'Paneles', 'anspel@gmail.com', 'ans', '$2y$10$.IOWY5/8zB3IwmkudFWnl.bQfK8xH7lEM2ZgDWmyy3WxwtY219UeC', '2016-02-09 14:08:40', 0, NULL, 0, 0),
(3, 'Marria', 'Unpajote', 'mar@gmail.com', 'mar', '$2y$10$3wlUt.ZfGNu1icTVxDcNVOuneLExrQhrs43LCsG8zsWSL./eAmipa', '2016-02-09 14:08:40', 0, NULL, 0, 1),
(4, 'asd', 'asd', 'asd', 'asd', '$2y$10$Bkj93CsSa7DjCMWFkkoolOLMlxZnQ9MoyiMPs5mBMqD5VI6MQ6o8S', '2016-02-09 14:08:40', 0, NULL, 0, 0),
(5, 'qwe', 'qwe', 'qwe', 'qwe', '$2y$10$kERic3dEpiivqfQiZMJO8.oqTVx0Cbx1bSmAFlcMjyDd8uQTdDodW', '2016-02-09 14:24:10', 1, NULL, 0, 1),
(12, 'Juan Miguel', 'Ruiz Ladrón', 'juanmiguel.ruiz.ladron@gmail.com', 'admin', '$2y$10$AvADJREdDfgsIQNyA.8ebOF0h1ElYJnMcqBPuJsdWDaeYJFG1LUSq', '2016-02-09 20:28:30', 1, NULL, 2, 0),
(14, 'prueba', 'prueba', 'prueba@prueba.com', NULL, '$2y$10$cIbj7wTaUbChqtft3WEOEO91tXD3xJQvTKJtZlq3JxxAEeSr0Blbu', '2016-02-09 23:41:15', 1, 'UCA', 1, 0),
(15, '', '', '', '', '', '0000-00-00 00:00:00', 0, NULL, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_aula`
--

CREATE TABLE `usuarios_aula` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_aula` int(11) NOT NULL,
  `validar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios_aula`
--

INSERT INTO `usuarios_aula` (`id`, `id_usuario`, `id_aula`, `validar`) VALUES
(1, 5, 7, 1),
(3, 5, 8, 1),
(4, 5, 9, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aula`
--
ALTER TABLE `aula`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `definiciones`
--
ALTER TABLE `definiciones`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `idiomas`
--
ALTER TABLE `idiomas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `puntuaciones`
--
ALTER TABLE `puntuaciones`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios_aula`
--
ALTER TABLE `usuarios_aula`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aula`
--
ALTER TABLE `aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `definiciones`
--
ALTER TABLE `definiciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `idiomas`
--
ALTER TABLE `idiomas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `puntuaciones`
--
ALTER TABLE `puntuaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT de la tabla `score`
--
ALTER TABLE `score`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `usuarios_aula`
--
ALTER TABLE `usuarios_aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
