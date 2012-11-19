package org.kurron.domain;

import javax.persistence.*;

@Entity
@Table( name = "parent" )
public class Parent
{
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    @Column( name="parent_id" )
    private Long id;

    @Version
    @Column( name = "version", nullable = false )
    private Integer version;


}
