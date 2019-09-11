package com.mateuszmedon.project.jsprestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "rOrder")
public class Order implements BaseEntity {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    @Id
    // identity - database have a id we commit record and id is present
    // sequence - hibernate sequence - take id and write to the object, and pull it to database
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    //private String description;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<Product> products;

    @CreationTimestamp
    private LocalDateTime timeOrdered; // in time when order is created we get a time on table is created
    private LocalDateTime timeDelivered;

    private int peopleCount;

    private int tableNumber;

    @Formula("(select count(*) from Product p where p.order_id = id)")
    private Integer productsCount;


    @Formula("(select sum(p.value*p.amount) from Product p where p.order_id = id)")
    private Double toPay;

    private Double paid;

    public Double calculateToPay() {
        double tmpToPay = (toPay != null ? toPay : 0); // if toPay != null return toPay, if not is 0
        double tmpPaid = (paid != null ? paid : 0); // the same

        return tmpToPay - tmpPaid;
    }

    public String getFormattedTimeOrdered() {
        return timeOrdered == null ? "Not ordered yet" : timeOrdered.format(FORMATTER);
    }

    public String getFormattedTimeDelivered() {
        return timeDelivered == null ? "Not delivered yet" : timeDelivered.format(FORMATTER);
    }


}
