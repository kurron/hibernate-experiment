package org.kurron.domain;

import java.util.HashSet;
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

@Entity
@Table( name = "parent" )
public class Parent
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "parent_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;

    @Column( name = "name", length = 75, unique = true, nullable = false )
    public String name;

    @OneToMany( mappedBy = "parent", orphanRemoval = true, cascade = { CascadeType.ALL } )
    private Set<Child> children = new HashSet<>( 8 );

    public void addChild( Child child )
    {
        child.setParent( this );
        children.add( child );
    }

    public void removeChild( Child child )
    {
        child.setParent( null );
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

    public Child randomlySelectChild()
    {
        return children.toArray( new Child[children.size()] )[0];

    }
}
