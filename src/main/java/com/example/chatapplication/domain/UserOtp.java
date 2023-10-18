package com.example.chatapplication.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_otp")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UserOtp extends Audiant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "otp")
    private String otp;

    @Column(name ="user_id" )
    private Long userId;

    @Column(name = "is_expire")
    private Integer isExpire;

    @Column(name = "type")
    private String type;

}
