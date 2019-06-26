package com.example.hibernate.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository repository;

    @GetMapping("/")
    @Transactional
    public void test() {
        var user = new User();
        user.setName("Name");
        user.setLastName("LastName");

        var saved = repository.save(user);

        saved.setLastName("ChangedName");

        repository.save(saved);
    }
}
