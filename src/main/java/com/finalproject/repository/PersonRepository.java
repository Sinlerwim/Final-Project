package com.finalproject.repository;

import com.finalproject.model.Person;
import com.finalproject.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person,String> {

        Optional<Person> findPersonByEmail(String email);

        List<Person> getByRoleOrderByEmailAsc(Role role);





}
