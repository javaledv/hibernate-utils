package com.example.hibernate.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Aspect
@Component
@Transactional
public class RepositoryAspect {

    @Autowired
    private ReentrantSafeEntityEntriesHolder entriesHolder;

    @Pointcut("execution(public !void com.example.hibernate.utils.UserRepository.save(..))")
    public void methodsAcceptingEntities() {
    }

    @Around("methodsAcceptingEntities()")
    public Object logMethodAcceptionEntityAnnotatedBean(ProceedingJoinPoint pjp) {
        final var args = pjp.getArgs();

        for (var entry : entriesHolder.getEntries()) {
            System.out.print(entry.getKey() + "\n");
            System.out.print("Entry" + Arrays.toString(entry.getValue().getLoadedState()) + "\n");
        }

        try {
            return pjp.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        throw new RuntimeException();
    }
}
