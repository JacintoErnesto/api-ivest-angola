package ucan.edu.api_sig_invest_angola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SigInvestAngolaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigInvestAngolaBackendApplication.class, args);
	}

}
