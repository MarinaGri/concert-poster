package com.technokratos.model;

import com.technokratos.dto.enums.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE account SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "NOT is_deleted")
@Table(name = "account")
public class UserEntity extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "hash_password")
    private String hashPassword;

}
