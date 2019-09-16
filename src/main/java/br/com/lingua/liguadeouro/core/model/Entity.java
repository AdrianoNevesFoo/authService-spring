package br.com.lingua.liguadeouro.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Entity<I> extends Serializable {

    I getId();

    void setDeletedAt(LocalDateTime deletedAt);

    void setDeleted(boolean deleted);


}
