package com.technokratos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE account_refresh_token SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "NOT is_deleted")
@Table(name = "account_refresh_token")
public class UserRefreshTokenEntity extends RefreshTokenEntity {

    @OneToOne
    @JoinColumn(name = "account_id")
    private UserEntity user;

}
