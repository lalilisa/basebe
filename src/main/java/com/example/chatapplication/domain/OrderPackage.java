package com.example.chatapplication.domain;


import com.example.chatapplication.domain.compositekey.OrderPackageKey;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_package")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class OrderPackage extends Audiant{

    @EmbeddedId
    private OrderPackageKey id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "transaction")
    private String transaction;
}
