package com.veersablog.BlogApp.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private int categoryId;
    @NotBlank(message = "categoryTitle should not be empty")
    private String categoryTitle;
    @NotBlank(message = "categoryDescription should not be empty")
    private String categoryDescription;

}
