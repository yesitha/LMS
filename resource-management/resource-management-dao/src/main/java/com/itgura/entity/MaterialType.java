package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mst_materialType", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaterialType {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "material_type_id")
    private UUID materialTypeId;
    @Column(name = "material_type")
    private String materialType;
    @OneToMany(mappedBy = "materialType",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Material> materialList;
}
