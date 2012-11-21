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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.NaturalId;

@Entity
@Table( name = "tutor" )
public class Tutor
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "tutor_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;

    @Column( name = "name", length = 75, unique = true, nullable = false )
    @NaturalId( mutable = true )
    private String name;

    @OneToOne( orphanRemoval = true, cascade = { CascadeType.ALL }, optional = false )
    private Student student;

    public Student getStudent()
    {
        return student;
    }

    public void setStudent( final Student aStudent )
    {
        student = aStudent;
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

    @Override
    public String toString()
    {
        return name;
    }
}
