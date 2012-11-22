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
import org.hibernate.annotations.NaturalId;

@Entity
@Table( name = "infant" )
public class Infant
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "infant_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;

    @Column( name = "name", length = 75, unique = true, nullable = false )
    @NaturalId( mutable = true )
    public String name;

    @Column( name = "noise", length = 75, unique = false, nullable = false )
    public String noise;

    @ManyToOne
    @JoinColumn( name = "mother_id", nullable = true )
    private Mother mother;

    @ManyToOne
    @JoinColumn( name = "father_id", nullable = true )
    private Father father;

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

    public Mother getMother()
    {
        return mother;
    }

    public void setMother( final Mother aMother )
    {
        mother = aMother;
    }

    public Father getFather()
    {
        return father;
    }

    public void setFather( final Father aFather )
    {
        father = aFather;
    }

    public String getNoise()
    {
        return noise;
    }

    public void setNoise( final String aNoise )
    {
        noise = aNoise;
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

        final Infant child = (Infant) o;

        if( !getName().equals( child.getName() ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return getName().hashCode();
    }

    @Override
    public String toString()
    {
        return name + ":" + noise;
    }
}
