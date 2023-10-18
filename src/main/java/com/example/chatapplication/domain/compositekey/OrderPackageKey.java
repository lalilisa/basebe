package com.example.chatapplication.domain.compositekey;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class OrderPackageKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "package_id")
    private Long packageId;
}
