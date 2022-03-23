package com.dropp.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "explored_drop")
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ExploredDrop extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetail userDetail;

    @ManyToOne
    @JoinColumn(name = "drop_id")
    private DropDetail dropDetail;

    @Column(name = "is_seen")
    private boolean isSeen;

}
