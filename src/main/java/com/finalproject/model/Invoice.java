package com.finalproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;

@Entity
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private boolean checked = false;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;


    //    @ManyToMany (fetch = FetchType.LAZY)
//            @JoinTable(name = "invoice_products",
//            joinColumns = @JoinColumn(name = "invoice_id"),
//            inverseJoinColumns = @JoinColumn(name = "computer_id"))
    @ElementCollection
    @CollectionTable(name = "invoice_products",
            joinColumns = @JoinColumn(name = "invoice_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    @SortNatural
    SortedMap<Computer, Integer> products;

    public void addProduct(Computer computer) {
        if (products.containsKey(computer)) {
            products.compute(computer, (k, number) -> ++number);
        } else {
            products.put(computer, 1);
        }
    }

    public void deleteProduct(Computer computer) {
        products.remove(computer);
    }

    public void decreaseProduct(Computer computer) {
        if(products.get(computer) == 1) {
            deleteProduct(computer);
        } else {
            products.compute(computer, (k, number) -> --number);
        }
    }

    public int getTotalPrice() {
        int price = 0;
        for (Map.Entry<Computer, Integer> product : products.entrySet()) {
            price += product.getValue() * product.getKey().getPrice();
        }
        return price;
    }

    public int getTotalNumberOfProducts() {
        return products.values().stream().reduce(0, Integer::sum);
    }
}
