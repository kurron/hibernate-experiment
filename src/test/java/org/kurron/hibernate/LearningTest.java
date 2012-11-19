package org.kurron.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.kurron.domain.Child;
import org.kurron.domain.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Learning test for Hibernate associations.
 */
@ContextConfiguration
public class LearningTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private SessionFactory sessionFactory;

    private final Random random = new Random();

    /*
    Hibernate collections are considered to be a logical part of their owning entity and not of the contained
    entities. Be aware that this is a critical distinction that has the following consequences:

    - When you remove/add an object from/to a collection, the version number of the collection owner is incremented.
    - If an object that was removed from a collection is an instance of a value type (e.g. a composite element), that
      object will cease to be persistent and its state will be completely removed from the database. Likewise, adding a
      value type instance to the collection will cause its state to be immediately persistent.
    - Conversely, if an entity is removed from a collection (a one-to-many or many-to-many association), it will not be
      deleted by default. This behavior is completely consistent; a change to the internal state of another entity
      should not cause the associated entity to vanish. Likewise, adding an entity to a collection does not cause that
      entity to become persistent, by default.

    Adding an entity to a collection, by default, merely creates a link between the two entities. Removing the entity
    will remove the link. This is appropriate for all sorts of cases. However, it is not appropriate in the case of a
    parent/child relationship. In this case, the life of the child is bound to the life cycle of the parent.
    */
    @Test
    public void given_when_then() throws Exception
    {
        System.err.println("Test called.");
        assertThat( sessionFactory, is( notNullValue() ) );

        final Parent parent = new Parent();
        parent.setName( randomHexString() );
        currentSession().saveOrUpdate( parent );
        currentSession().flush();
        assertThat( parent.getId(), is( notNullValue() ) );
        assertThat( parent.getVersion(), is( notNullValue() ) );

        final Child child = new Child();
        child.setName( randomHexString() );
        currentSession().saveOrUpdate( child );
        currentSession().flush();
        assertThat( child.getId(), is( notNullValue() ) );
        assertThat( child.getVersion(), is( notNullValue() ) );

        parent.getChildren().add( child );
        currentSession().saveOrUpdate( parent );
        currentSession().flush();
    }

    private Session currentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    private String randomHexString()
    {
        return Integer.toHexString( random.nextInt( Integer.MAX_VALUE ) ).toUpperCase();
    }
}
