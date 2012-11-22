package org.kurron.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.NaturalId;

@Entity
@Table( name = "mother" )
public class Mother
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "mother_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;

    @Column( name = "name", length = 75, unique = true, nullable = false )
    @NaturalId( mutable = true )
    public String name;

    @OneToMany( mappedBy = "mother", orphanRemoval = true, cascade = { CascadeType.ALL } )
    private Set<Infant> children = new HashSet<>( 8 );

    public void addInfant( Infant infant )
    {
        infant.setMother( this );
        if ( children.contains( infant ) )
        {
            copyDatabaseIdentifiers( infant );
            // use child as the prototype of the instance to remove -- will use hashcode/equals to pull it out
            children.remove( infant );
        }
        children.add( infant );
    }

    private void copyDatabaseIdentifiers( final Infant infant )
    {
        final List<Infant> list = new ArrayList<>( children );
        final Infant attached = list.get( list.indexOf( infant ) );
        infant.setId( attached.getId() );
        infant.setVersion( attached.getVersion() );
    }

    public void removeChild( Infant child )
    {
        child.setMother( null );
        children.remove( child );
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion( Integer version )
    {
        this.version = version;
    }

    public Infant randomlySelectChild()
    {
        return children.toArray( new Infant[children.size()] )[0];

    }

    @Override
    public String toString()
    {
        return name;
    }
}
