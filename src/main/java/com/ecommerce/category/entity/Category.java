package com.ecommerce.category.entity;

import java.util.List;

import com.ecommerce.audit.AuditId;
import com.ecommerce.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends AuditId {

    @Column(unique = true)
    private String name;

    private Boolean active;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}