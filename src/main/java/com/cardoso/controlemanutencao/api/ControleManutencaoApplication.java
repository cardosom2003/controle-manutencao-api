package com.cardoso.controlemanutencao.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.enums.PerfilEnum;
import com.cardoso.controlemanutencao.api.repositories.FuncionarioRepository;
import com.cardoso.controlemanutencao.api.utils.PasswordUtils;


@SpringBootApplication
public class ControleManutencaoApplication implements  CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ControleManutencaoApplication.class, args);
	}
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Funcionario funcionario = this.funcionarioRepository.findByEmail("admin@email.com.br");
		if(funcionario == null) {
			funcionario.setNome("Admin");
			funcionario.setEmail("admin@email.com.br");
			funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
			funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
			this.funcionarioRepository.save(funcionario);
		}
		
	}
	

}
