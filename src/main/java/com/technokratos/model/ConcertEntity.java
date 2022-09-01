package com.technokratos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE concert SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "NOT is_deleted")
@Table(name = "concert")
public class ConcertEntity extends AbstractEntity {

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "tickets_number")
    private Integer ticketsNumber;

    @ManyToMany
    @JoinTable(name = "booking",
            joinColumns = @JoinColumn(name = "concert_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users;

}
