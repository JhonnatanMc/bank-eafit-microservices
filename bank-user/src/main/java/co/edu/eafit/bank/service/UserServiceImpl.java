package co.edu.eafit.bank.service;

import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.eafit.bank.domain.Users;
import co.edu.eafit.bank.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	public void validate(Users entity) throws Exception {
		Set<ConstraintViolation<Users>> constrainsViolations = validator.validate(entity);
		
		if(constrainsViolations.isEmpty() == false) {
			throw new ConstraintViolationException(constrainsViolations);
		}
	}


	@Override
	public List<Users> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<Users> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users save(Users entity) throws Exception {
		if(entity == null) {
			throw new Exception("The customer is null");
		}
		
		validate(entity);
		
		if(userRepository.existsById(entity.getUserEmail()) == true) {
			throw new Exception("The user already exists");
		}
		
		return userRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users update(Users entity) throws Exception {
		if(entity == null) {
			throw new Exception("The user is null");
		}
		
		validate(entity);
		
		if(userRepository.existsById(entity.getUserEmail())==false) {
			throw new Exception("The user doesn't exists");
		}
		
		return userRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Users entity) throws Exception {
		if(entity == null) {
			throw new Exception("The user is null");
		}
		
		if(userRepository.existsById(entity.getUserEmail())==false) {
			throw new Exception("The user doesn't exists");
		}
		
		findById(entity.getUserEmail()).ifPresent(user->{
			if(user.getTransactions() != null && user.getTransactions().isEmpty() == false) {
				throw new RuntimeException("The user has associete trasactions");
			}
		});
		
		userRepository.deleteById(entity.getUserEmail());
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		if(id == null) {
			throw new Exception("Id is null");
		}
		
		if(userRepository.existsById(id)) {
			delete(userRepository.findById(id).get());
		}
		
	}

	@Override
	public Long count() {
		return userRepository.count();
	}

}
