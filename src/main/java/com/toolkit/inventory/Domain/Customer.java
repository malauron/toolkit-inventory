package com.toolkit.inventory.Domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "customerId")
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "customer_name")
    private String customerName;

    @ManyToOne
    @JoinColumn(name = "customer_group_id")
    private CustomerGroup customerGroup;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "address")
    private String address;

    @JsonManagedReference
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerPicture customerPicture;

    @JsonManagedReference
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerSignature customerSignature;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "sss_no")
    private String sssNo;

    @Column(name = "hdmf_no")
    private String hdmfNo;

    @Column(name = "phic_no")
    private String phicNo;

    @Column(name = "tin_no")
    private String tinNo;

    @Column(name = "er_contact_person")
    private String erContactPerson;

    @Column(name = "er_contact_address")
    private String erContactAddress;

    @Column(name = "er_contact_no")
    private String erContactNo;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated")
    private Date dateUpdated;

}
