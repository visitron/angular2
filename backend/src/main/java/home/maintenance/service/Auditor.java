package home.maintenance.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class Auditor {
    @Around("execution(public * save(..))")
    public Object log(ProceedingJoinPoint jp) throws Throwable {
        Object object = jp.getArgs()[0];
        if (!(object instanceof Persistable)) {
            return jp.proceed();
        }

        String className = object.getClass().getSimpleName();
        Persistable oldObject = (Persistable) object;
        boolean isNew = oldObject.isNew();
        String oldObjectStr = oldObject.toString();

        Object newObject = jp.proceed();

        if (isNew) {
            log.info("New {} created: {}", className, newObject);
        } else {
            log.info("{} updated: \n old: {}\n new: {}", className, oldObjectStr, newObject);
        }

        return newObject;
    }
}
