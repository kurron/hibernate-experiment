package org.kurron.domain;

import javax.persistence.*;

@Entity
@Table( name = "child" )
public class Child
{
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    @Column( name="child_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;


}
