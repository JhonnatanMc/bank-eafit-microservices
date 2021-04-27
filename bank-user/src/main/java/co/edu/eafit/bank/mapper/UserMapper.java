package co.edu.eafit.bank.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.eafit.bank.domain.Users;
import co.edu.eafit.bank.dto.UserDTO;

@Mapper
public interface UserMapper {

	@Mapping(source ="userType.ustyId", target = "ustyId")
	public UserDTO userToUserDTO(Users user);

	@Mapping(target ="userType.ustyId", source = "ustyId") 
	public Users userDTOToUser(UserDTO userDTO);
	
	public List<UserDTO> usersToUsersDTOs(List<Users> users);
	
	public List<Users> usersDTOsToUsers(List<UserDTO> usersDTOs);

}
