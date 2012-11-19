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


}
