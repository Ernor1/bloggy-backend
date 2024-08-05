package rw.global.qt.bloggy.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCategoryDTO {
    @NotNull
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull
    @NotBlank(message = "Description is required")
    private String description;
}
