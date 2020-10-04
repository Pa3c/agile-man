package pl.pa3c.agileman.model.commentary;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentationCommentary extends BaseCommentary{

}
