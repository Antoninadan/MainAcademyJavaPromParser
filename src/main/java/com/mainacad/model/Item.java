package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@ToString
public class Item {
    private String itemId;
    private String name;
    private String url;
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal initialPrice;
    private String availability;
}
