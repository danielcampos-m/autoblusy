package daw.projeto.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				// Qualquer um pode fazer requisições para essas URLs
				.antMatchers("/css/**", "/js/**").permitAll()
				// Um usuário autenticado e com o papel ADMIN pode fazer requisições para essa URL	
				.antMatchers("/Admin").hasRole("ADMIN")
				.antMatchers("/Controle").hasAnyRole("ADMIN", "USUARIO")
				.antMatchers("/NovoUsuario").hasRole("ADMIN")
				.antMatchers("/removerUsuario").hasRole("ADMIN")
				.antMatchers("/editarUsuario").hasRole("ADMIN")
				.antMatchers("/usuarioEditado").hasRole("ADMIN")
				.antMatchers("/newDispositivo").hasRole("ADMIN")
				.antMatchers("/NovoDispositivo").hasRole("ADMIN")
				.antMatchers("/removerDispositivo").hasRole("ADMIN")
				.antMatchers("/editarDispositivo").hasRole("ADMIN")
				.antMatchers("/dispositivoEditado").hasRole("ADMIN")
				.antMatchers("/Usuario").hasRole("ADMIN")
				.and()
			// A autenticação usando formulário está habilitada
			.formLogin()
				// Uma página de login customizada
				.loginPage("/login")
				// Define a URL para o caso de falha no login
				//.failureUrl("/login-error")
				.and()
			// Para fazer logout (já é configurado automaticamente para a URL /logout)
			// Só está habilitado aqui para mudarmos a URL de sucesso do logout
			.logout()
				// Define a URL para o caso do usuário fazer logout. O padrão é /login
				.logoutSuccessUrl("/");
	}
	
	//Autenticacao JDBC
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select nome, senha, ativo "
						+ "from usuarios "
						+ "where nome = ?")
				.authoritiesByUsernameQuery("SELECT tab.nome, papel.nome from" + 
					"(SELECT usuarios.nome, usuarios.id from usuarios where nome = ? ) as tab " + 
					" inner join papel_usuarios on usuarios_id = tab.id \n" + 
					" inner join papel on papel_id = papel.id;")
			.passwordEncoder(passwordEncoder());
		}
		
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			String idEnconder = "argon2";
			Map<String, PasswordEncoder> encoders = new HashMap<>();
			encoders.put("argon2", new Argon2PasswordEncoder());
			encoders.put("noop", NoOpPasswordEncoder.getInstance());
////			encoders.put("bcrypt", new BCryptPasswordEncoder());
////			encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
////			encoders.put("scrypt", new SCryptPasswordEncoder());
////			encoders.put("sha256", new StandardPasswordEncoder());
			PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idEnconder, encoders);
			 
			return passwordEncoder;
		}


}