package org.kurron.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table( name = "child" )
public class Child
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "child_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;

    @Column( name = "name", length = 75, unique = true, nullable = false )
    public String name;

    @ManyToOne
    @JoinColumn( name = "parent_id", nullable = false )
    private Parent parent;

    public Long getId()
    {
        return id;
    }

    public void setId( final Long aId )
    {
        id = aId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String aName )
    {
        name = aName;
    }

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion( final Integer aVersion )
    {
        version = aVersion;
    }

    public Parent getParent()
    {
        return parent;
    }

    public void setParent( final Parent aParent )
    {
        parent = aParent;
    }

    @Override
    public boolean equals( final Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        final Child child = (Child) o;

        if( !name.equals( child.name ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
