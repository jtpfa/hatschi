package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.util.ValidationUtils;

public final class ArticleCreationDTOArticleEntityMapper {
    private ArticleCreationDTOArticleEntityMapper() {
        throw new IllegalStateException();
    }

    public static ArticleEntity mapArticleCreationDTOToArticleEntity(ArticleCreationDTO articleCreationDTO) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setName(ValidationUtils.validateNoHtml(articleCreationDTO.getName()));
        articleEntity.setDescription(articleCreationDTO.getDescription());
        articleEntity.setDetails(articleCreationDTO.getDetails());
        articleEntity.setPrice(articleCreationDTO.getPrice());
        articleEntity.setStock(articleCreationDTO.getStock());

        return articleEntity;
    }
}
