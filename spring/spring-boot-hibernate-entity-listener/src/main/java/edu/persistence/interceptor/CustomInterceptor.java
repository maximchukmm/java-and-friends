package edu.persistence.interceptor;

import edu.persistence.model.SumNumbers;
import edu.persistence.model.TwoNumbers;
import edu.persistence.repository.SumNumbersRepository;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomInterceptor extends EmptyInterceptor {

    @Autowired
    @Lazy
    private SumNumbersRepository sumNumbersRepository;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof TwoNumbers) {
            TwoNumbers twoNumbers = (TwoNumbers) entity;

            sumNumbersRepository.save(new SumNumbers(twoNumbers));
        }

        return super.onSave(entity, id, state, propertyNames, types);
    }
}
