package com.matheus.commerce.domain;

import com.matheus.commerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
@Entity(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;
    @Column(name = "total_in_cents")
    private Integer totalInCents = 0;
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.preparing;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProduct = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Order(Integer totalInCents) {
        this.createdAt = LocalDate.now();
        this.totalInCents = totalInCents;
    }

    public Order(User user) {
        this.user = user;
    }
}

