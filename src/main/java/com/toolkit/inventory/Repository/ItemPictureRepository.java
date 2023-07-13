package com.toolkit.inventory.Repository;

import com.toolkit.inventory.Domain.ItemPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemPictureRepository extends JpaRepository<ItemPicture, Long> {

    ItemPicture findByItemItemId(Long itemId);

}
