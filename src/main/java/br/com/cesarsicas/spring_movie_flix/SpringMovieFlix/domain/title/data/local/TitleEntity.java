package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.local;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "titles")
@Entity(name = "title")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TitleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal value;
    private String imageUrl;
    private int quantity;


//    public MovieEntity(Product productDto, ProductCategoryEntity category, MerchantEntity merchant) {
//        this.name = productDto.name();
//        this.description = productDto.description();
//        this.value = productDto.value();
//        this.category = category;
//        this.movie = merchant;
//    }


}
