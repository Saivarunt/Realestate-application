package com.example.realestatelisting.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user-permission")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPermissions {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_permission_id")
    private Long userPermissionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    private Permissions permission_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user_id;
}
