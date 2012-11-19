package org.kurron.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Embeddable
@Table( name = "slave" )
public class Slave
{
    @Column( name = "name", length = 75, unique = true, nullable = false )
    public String name;

    public String getName()
    {
        return name;
    }

    public void setName( final String aName )
    {
        name = aName;
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

        final Slave child = (Slave) o;

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
