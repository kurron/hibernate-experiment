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
