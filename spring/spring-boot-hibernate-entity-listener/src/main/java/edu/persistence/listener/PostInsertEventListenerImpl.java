package edu.persistence.listener;

import edu.persistence.model.TwoNumbers;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class PostInsertEventListenerImpl implements PostInsertEventListener {
    @Override
    public void onPostInsert(PostInsertEvent event) {
        Object entity = event.getEntity();

        if (entity instanceof TwoNumbers) {
            System.out.println("TWO NUMBERS");
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }
}
