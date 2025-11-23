package com.es.banco.app.banco_hcb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.es.banco.app.banco_hcb.model.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(updatable = false, name = "date_transaction")
    @CreationTimestamp
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    private BigDecimal amount;
    private BigDecimal finalBalance;

    @ManyToOne
    @JsonIgnore
    private Account account;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Transaction other = (Transaction) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
