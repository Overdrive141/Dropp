package com.dropp.app.model;

import com.dropp.app.model.enums.EntityType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "drop_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropDetail extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserDetail user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType type;

    @Size(max = 1000)
    @Column(name = "message")
    private String message;

    @Size(max = 1000)
    @Column(name = "url")
    private String url;

    @Column(name = "coordinate", columnDefinition = "POINT", nullable = false)
    private Point coordinate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "dropDetail")
    private Set<StarredDrop> starredDrops;

    @OneToMany(mappedBy = "dropDetail")
    private Set<ExploredDrop> exploredDrops;

}
