package uz.pdp.fast_food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Files extends BaseEntity {
    private String path;
    private String url;

    @Builder
    public Files(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, String path, String url) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.path = path;
        this.url = url;
    }
}
