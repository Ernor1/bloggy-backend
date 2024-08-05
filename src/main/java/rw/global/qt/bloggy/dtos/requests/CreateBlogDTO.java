package rw.global.qt.bloggy.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.global.qt.bloggy.annotations.ValidUUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CreateBlogDTO {
    @NotNull
    @NotBlank(message = "Title is required")
    private String title;
    @NotNull
    @NotBlank(message = "Content is required")
    private String content;
    @NotNull
    @NotBlank(message = "User ID is required")
    @ValidUUID
    private UUID userId;
    private List<UUID> tags;
    private List<UUID> categories;

}
