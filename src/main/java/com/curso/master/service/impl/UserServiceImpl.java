package com.curso.master.service.impl;

import com.curso.master.dto.request.SaveUser;
import com.curso.master.dto.request.UserSearchCriteria;
import com.curso.master.dto.response.GetUser;
import com.curso.master.exception.ObjectNotFoundException;
import com.curso.master.mapper.UserMapper;
import com.curso.master.persistence.entity.User;
import com.curso.master.persistence.repository.UserCrudRepository;
import com.curso.master.persistence.specification.FindAllUserSpecification;
import com.curso.master.service.UserService;
import com.curso.master.service.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<GetUser> findAll(UserSearchCriteria searchCriteria, Pageable pageable)
    {
        FindAllUserSpecification userSpecification =
                new FindAllUserSpecification(searchCriteria);

        Page<User> entities = userCrudRepository.findAll(userSpecification, pageable);

        return entities.map(UserMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetUser findOneByUsername(String username)
    {

        return UserMapper.toGetDto(
                this.findOneEntityByUsername(username)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public User findOneEntityByUsername(String username)
    {
        return userCrudRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("[user:"+ username +"]"));
    }

    @Override
    public GetUser saveOne(SaveUser saveDto)
    {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());
        User newUser = UserMapper.toEntity(saveDto);

        return UserMapper.toGetDto(
                userCrudRepository.save(newUser)
        );
    }

    @Override
    public GetUser updateOneByUsername(String username, SaveUser saveDto)
    {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());

        User oldUser = this.findOneEntityByUsername(username);
        UserMapper.updateEntity(oldUser, saveDto);

        return UserMapper.toGetDto(
                userCrudRepository.save(oldUser)
        );
    }

    @Override
    public void deleteOneByUsername(String username) {
        userCrudRepository.deleteByUsername(username);
    }
}
