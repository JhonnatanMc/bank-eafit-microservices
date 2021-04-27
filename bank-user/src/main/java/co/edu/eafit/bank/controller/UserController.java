package co.edu.eafit.bank.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eafit.bank.domain.Users;
import co.edu.eafit.bank.dto.UserDTO;
import co.edu.eafit.bank.mapper.UserMapper;
import co.edu.eafit.bank.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable("id") String id) throws Exception{
		
		userService.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping()
	public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO) throws Exception{
		
		Users user = userMapper.userDTOToUser(userDTO); 
		user = userService.update(user);
		
		userDTO=userMapper.userToUserDTO(user);
		
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping()
	public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO)throws Exception{
		
		Users user=userMapper.userDTOToUser(userDTO);
		user=userService.save(user);
		
		userDTO=userMapper.userToUserDTO(user);
		
		return ResponseEntity.ok(userDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable("id") String id) {
	
		Optional<Users> userOptional = userService.findById(id);
		
		Users user = (userOptional.isPresent()==true)?userOptional.get():null;
		
		UserDTO userDTO = userMapper.userToUserDTO(user);
		
		return ResponseEntity.ok().body(userDTO);
	}
	
	@GetMapping()
	public ResponseEntity<List<UserDTO>> findAll(){
		List<Users> users=userService.findAll();
		List<UserDTO> userDTOs = userMapper.usersToUsersDTOs(users);
		
		return ResponseEntity.ok().body(userDTOs);
	}
	

}
