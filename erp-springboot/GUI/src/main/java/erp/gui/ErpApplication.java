package erp.gui;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import erp.bll.entities.Task;
import erp.dal.repositories.TaskRepository;
@SpringBootApplication
@EnableJpaRepositories(basePackages= {"erp"})
@EntityScan(basePackages= {"erp"})
@ComponentScan(basePackages= {"erp"})

public class ErpApplication implements CommandLineRunner{
	@Autowired
	private TaskRepository taskRepository;
	public static void main (String[] args) {
		SpringApplication.run(ErpApplication.class);
	}
	@Override
	public void run(String... args) throws Exception {

		Stream.of("T1","T2","T3").forEach(t->{
			taskRepository.save(new Task(null,t));
		});
		taskRepository.findAll().forEach(t->{
			System.out.println(t.getTaskName());
		});
		 
	

		
	}

}
