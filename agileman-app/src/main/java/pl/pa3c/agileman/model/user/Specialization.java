package pl.pa3c.agileman.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import pl.pa3c.agileman.model.base.StringIdEntity;

@Entity
@Table
@Data
public class Specialization extends StringIdEntity{

}
