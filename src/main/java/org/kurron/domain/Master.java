package org.kurron.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table( name = "master" )
public class Master
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "master_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;

    @Column( name = "name", length = 75, unique = true, nullable = false )
    public String name;

    @ElementCollection
    @CollectionTable( name="slaves", joinColumns=@JoinColumn( name="master_id" ) )
    @Column( name="slave" )
    private Set<Slave> slaves = new HashSet<>( 8 );

    public void addSlave( Slave slave )
    {
        slaves.add( slave );
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

    public Set<Slave> getSlaves()
    {
        return slaves;
    }
}
