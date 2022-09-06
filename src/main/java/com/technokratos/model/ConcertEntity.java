package com.technokratos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@ToString(callSuper = true, exclude = "booking")
@EqualsAndHashCode(callSuper = true, exclude = "booking")
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "concert")
    private Set<BookingEntity> booking;

}
