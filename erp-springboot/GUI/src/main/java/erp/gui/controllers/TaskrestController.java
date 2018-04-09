package erp.gui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import erp.bll.entities.Task;
import erp.dal.repositories.TaskRepository;

import java.util.List;



@RestController
public class TaskrestController {
	@Autowired
	private TaskRepository taskRepository;
@GetMapping("/tasks")
	public List<Task> listTasks(){
	 return taskRepository.findAll();
}
@PostMapping("/tasks")
public Task save(@RequestBody Task t) {
	return taskRepository.save(t);
}
}
