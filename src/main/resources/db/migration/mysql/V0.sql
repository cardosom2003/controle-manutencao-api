CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `equipamento` (
  `id` bigint(20) NOT NULL,
  `tipo_equipamento` varchar(255) NOT NULL,
  `marca` varchar(255) NOT NULL,
  `descricao_defeito` varchar(255) DEFAULT NULL,
  `cliente_id`  bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `funcionario` (
  `id` bigint(20) NOT NULL,
  `nome_funcionario` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `perfil_funcionario` varchar(255) NOT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  CREATE TABLE `ordemservico` (
  `id` bigint(20) NOT NULL,
  `equipamento_id` bigint(20) NOT NULL,
  `funcionario_id` bigint(20) DEFAULT NULL,
  `ordem_status`   varchar(255) NOT NULL, 
  `data_criacao`   datetime NOT NULL,
  `data_inicio`    datetime DEFAULT NULL,
  `data_conclusao` datetime DEFAULT NULL,
  `nota_conclusao` varchar(255) DEFAULT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  --
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

-- Indexes for table `funcionario`
--
ALTER TABLE `funcionario`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `equipamento`
--
ALTER TABLE `equipamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4cm1kg523jlopyexjbmi6y54j` (`cliente_id`);

--
-- Indexes for table `lancamento`
--
ALTER TABLE `ordemservico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK46i4k5vl8wah7feutye9kbpi4` (`funcionario_id`),
  ADD KEY `FK46i4k6vl8wai7fevtye8kdpi5` (`equipamento_id`);

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `funcionario`
--
ALTER TABLE `funcionario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `equipamento`
--
ALTER TABLE `equipamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

-- AUTO_INCREMENT for table `ordemservico`

ALTER TABLE `ordemservico`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `equipamento`
--
ALTER TABLE `equipamento`
  ADD CONSTRAINT `FK4cm1kg523jlopyexjbmi6y54j` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);
		 
--
-- Constraints for table `ordemservico`
--
ALTER TABLE `ordemservico`
  ADD CONSTRAINT `FK46i4k5vl8wah7feutye9kbpi4` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`),
  ADD CONSTRAINT `FK46i4k6vl8wai7fevtye8kdpi5` FOREIGN KEY (`equipamento_id`) REFERENCES `equipamento` (`id`);