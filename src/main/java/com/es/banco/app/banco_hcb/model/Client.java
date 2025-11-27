package com.es.banco.app.banco_hcb.model;

import java.time.*;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String fullname;
    @Column(length = 18, nullable = false, unique = true)
    private String curp;
    @Column(length = 13, nullable = false, unique = true)
    private String rfc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Column(updatable = false, name = "creation_date")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder.Default
    @Column(name = "status")
    private boolean isActive = true;

    @Column(unique = true)
    private String phone;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((curp == null) ? 0 : curp.hashCode());
        result = prime * result + ((rfc == null) ? 0 : rfc.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (curp == null) {
            if (other.curp != null)
                return false;
        } else if (!curp.equals(other.curp))
            return false;
        if (rfc == null) {
            if (other.rfc != null)
                return false;
        } else if (!rfc.equals(other.rfc))
            return false;
        return true;
    }

    
}
