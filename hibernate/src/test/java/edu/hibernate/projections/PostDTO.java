package edu.hibernate.projections;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime updatedOn;
    private String updatedBy;
    private Integer version;

    public PostDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void setId(Number id) {
        this.id = id.longValue();
    }
}