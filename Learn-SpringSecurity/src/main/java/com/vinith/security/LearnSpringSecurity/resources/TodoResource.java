package com.vinith.security.LearnSpringSecurity.resources;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;


@RestController
public class TodoResource {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private static final List<Todo> TODOS_LIST = List.of(new Todo("vinith","learn AWS"),
			             new Todo("vinith","learn Azure"));

	@GetMapping("/todos")
	public List<Todo> retrieveAllTodos()
	{
		return TODOS_LIST;
	}
	
	
	//Different method security annotation is defined below getMapping  
	
	@GetMapping("/todos/user/{username}/todo")
	@PreAuthorize("hasRole('USER')	and #username == authentication.name")
//	@PostAuthorize("returnObject.username == 'vinith'")
	@RolesAllowed({"USER","ADMIN"})
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public Todo retrieveTodoForSpecificUser(@PathVariable String username)
	{
		return TODOS_LIST.get(0);
	}
	@PostMapping("/todos/user/{username}/todo")
	public void createTodoForSpecificUser(@PathVariable String username,@RequestBody Todo todo)
	{
		logger.info("Create {} for {}",todo,username);
	}

}

record Todo(String useranme,String password) {}
