package org.kurron.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
