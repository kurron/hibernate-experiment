package org.kurron.hibernate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import java.util.Random;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.kurron.domain.Child;
import org.kurron.domain.Father;
import org.kurron.domain.Infant;
import org.kurron.domain.Mother;
import org.kurron.domain.Parent;
import org.kurron.domain.Student;
import org.kurron.domain.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

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
    public void showcase_cascading_insert_of_both_parent_and_child_via_parent() throws Exception
    {
        printBoundary( "showcase_cascading_insert_of_both_parent_and_child_via_parent" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Parent parent = new Parent();
        parent.setName( randomHexString() );

        final Child child = new Child();
        child.setName( randomHexString() );
        child.setNoise( randomHexString() );
        parent.addChild( child );

        currentSession().save( parent );
        currentSession().flush();
        printBoundary( "showcase_cascading_insert_of_both_parent_and_child_via_parent" );
    }

    @Test
    public void showcase_cascading_orphaned_child_deletion() throws Exception
    {
        printBoundary( "showcase_cascading_orphaned_child_deletion" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Parent parent = new Parent();
        parent.setName( randomHexString() );

        for( int i = 0; i < 10; i++ )
        {
            final Child child = new Child();
            child.setName( randomHexString() );
            child.setNoise( randomHexString() );
            parent.addChild( child );
        }

        currentSession().save( parent );
        currentSession().flush();

        parent.removeChild( parent.randomlySelectChild() );
        currentSession().save( parent );
        currentSession().flush();

        printBoundary( "showcase_cascading_orphaned_child_deletion" );
    }

    /*
     * The merge(item) call D results in several actions. First, Hibernate checks
     * whether a persistent instance in the persistence context has the same database
     * identifier as the detached instance you’re merging. In this case, this is true: item
     * and item2, which were loaded with get() C, have the same primary key value.
     * If there is an equal persistent instance in the persistence context, Hibernate
     * copies the state of the detached instance onto the persistent instance E. In other
     * words, the new description that has been set on the detached item is also set on
     * the persistent item2.
     * If there is no equal persistent instance in the persistence context, Hibernate
     * loads it from the database (effectively executing the same retrieval by identifier as
     * you did with get()) and then merges the detached state with the retrieved
     * object’s state.
     *
     * On any entity that is contained in a collection, supply a equals/hashcode combinations
     * written in terms of a business key -- a property or combination of properties that a human
     * might use to identify the instance.  It doesn't have to be immutable but it shouldn't change
     * very often. Do not include the entities database identifiers or collections in the business key.
     *
     * A set does not allow duplicates, using the hashcode as the key into the set, so you have to remove
     * any instance in the collection that has the same business key as the detached set.  If the detached
     * instance hashes out to one that is already in the collection, the detached one won't get inserted
     * into the set and your changes won't take.
     */
    @Test
    public void showcase_merging_of_detached_child_with_copied_ids() throws Exception
    {
        printBoundary( "showcase_merging_of_detached_child_via_merge_on_parent" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Parent parent = new Parent();
        parent.setName( randomHexString() );

        for( int i = 0; i < 5; i++ )
        {
            final Child child = new Child();
            child.setName( randomHexString() );
            child.setNoise( randomHexString() );
            parent.addChild( child );
        }

        currentSession().save( parent );
        currentSession().flush();

        final Child detached = new Child();
        final Child attached = parent.randomlySelectChild();
        detached.setName( attached.getName() );
        detached.setNoise( "detached" );
        parent.addChild( detached );
        currentSession().merge( parent );
        currentSession().flush();

        printBoundary( "showcase_merging_of_detached_child_via_merge_on_parent" );
    }

    @Test
    public void showcase_cascading_insert_of_child_via_child() throws Exception
    {

        printBoundary( "showcase_cascading_insert_of_both_parent_and_child_via_child" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Parent parent = new Parent();
        parent.setName( randomHexString() );
        currentSession().save( parent );
        currentSession().flush();

        final Child child = new Child();
        child.setName( randomHexString() );
        child.setNoise( randomHexString() );
        parent.addChild( child );

        currentSession().save( child );
        currentSession().flush();
        printBoundary( "showcase_cascading_insert_of_both_parent_and_child_via_child" );
    }

    private void printBoundary( String text )
    {
        System.out.println( "--- " + text + " ----" );
        System.out.flush();
    }

    @Test
    public void showcase_cascading_child_insert_when_parent_is_updated() throws Exception
    {
        printBoundary( "showcase_cascading_child_insert_when_parent_is_updated" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Parent parent = new Parent();
        parent.setName( randomHexString() );
        currentSession().save( parent );
        currentSession().flush();
        assertThat( parent.getId(), is( notNullValue() ) );
        assertThat( parent.getVersion(), is( notNullValue() ) );

        final Child child = new Child();
        child.setName( randomHexString() );
        child.setNoise( randomHexString() );
        parent.addChild( child );
        currentSession().save( parent );
        currentSession().flush();
        printBoundary( "showcase_cascading_child_insert_when_parent_is_updated" );
    }

    @Test
    public void showcase_child_insert() throws Exception
    {
        printBoundary( "showcase_child_insert" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Parent parent = new Parent();
        parent.setName( randomHexString() );
        currentSession().save( parent );
        currentSession().flush();
        assertThat( parent.getId(), is( notNullValue() ) );
        assertThat( parent.getVersion(), is( notNullValue() ) );

        final Child child = new Child();
        child.setName( randomHexString() );
        child.setNoise( randomHexString() );
        parent.addChild( child );
        currentSession().save( child );
        currentSession().flush();
        printBoundary( "showcase_child_insert" );
    }

    @Test
    public void showcase_cascading_insert_of_both_tutor_and_student_via_tutor() throws Exception
    {
        printBoundary( "showcase_cascading_insert_of_both_tutor_and_student_via_tutor" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Tutor tutor = new Tutor();
        tutor.setName( randomHexString() );

        final Student student = new Student();
        student.setName( randomHexString() );
        student.setNoise( randomHexString() );

        tutor.setStudent( student );

        currentSession().save( tutor );
        currentSession().flush();
        printBoundary( "showcase_cascading_insert_of_both_tutor_and_student_via_tutor" );
    }

    @Test
    public void showcase_cascading_delete_of_both_tutor_and_student_via_tutor() throws Exception
    {
        printBoundary( "showcase_cascading_delete_of_both_tutor_and_student_via_tutor" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Tutor tutor = new Tutor();
        tutor.setName( randomHexString() );

        final Student student = new Student();
        student.setName( randomHexString() );
        student.setNoise( randomHexString() );
        tutor.setStudent( student );

        currentSession().save( tutor );
        currentSession().flush();

        currentSession().delete( tutor );
        currentSession().flush();
        printBoundary( "showcase_cascading_delete_of_both_tutor_and_student_via_tutor" );
    }


    @Test
    public void showcase_cascading_insert_of_mother_infant_via_mother() throws Exception
    {
        printBoundary( "showcase_cascading_insert_of_mother_father_infant_via_infant" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Infant infant = new Infant();
        infant.setName( randomHexString() );
        infant.setNoise( randomHexString() );

        final Mother mother = new Mother();
        mother.setName( randomHexString() );
        mother.addInfant( infant );
        currentSession().save( mother );

        final Father father = new Father();
        father.setName( randomHexString() );
        father.addInfant( infant );
        currentSession().save( father );

        currentSession().flush();
        printBoundary( "showcase_cascading_insert_of_mother_father_infant_via_infant" );
    }

    @Test
    @Rollback( false )
    public void showcase_removal_of_infant_from_father_does_not_cascade_delete_to_infant() throws Exception
    {
        printBoundary( "showcase_removal_of_infant_from_father_does_not_cascade_delete_to_infant" );
        assertThat( sessionFactory, is( notNullValue() ) );

        final Infant infant = new Infant();
        infant.setName( randomHexString() );
        infant.setNoise( randomHexString() );

        final Mother mother = new Mother();
        mother.setName( randomHexString() );
        mother.addInfant( infant );
        currentSession().save( mother );
        currentSession().flush();

        final Father father = new Father();
        father.setName( randomHexString() );
        father.addInfant( infant );
        currentSession().save( father );
        currentSession().flush();

        father.removeChild( infant );
        currentSession().saveOrUpdate( father );
        currentSession().flush();
        mother.removeChild( infant );
        currentSession().saveOrUpdate( father );
        currentSession().flush();

        printBoundary( "showcase_removal_of_infant_from_father_does_not_cascade_delete_to_infant" );
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
