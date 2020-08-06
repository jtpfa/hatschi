package de.pcmr.shop.api.mapper;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.util.ValidationUtils;

public final class ArticleDTOArticleEntityMapper {
    private ArticleDTOArticleEntityMapper() {
        throw new IllegalStateException();
    }

    public static ArticleEntity mapArticleDTOToArticleEntity(de.pcmr.shop.api.model.ArticleDTO articleDTO) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(articleDTO.getId());
        articleEntity.setName(ValidationUtils.validateNoHtml(articleDTO.getName()));
        articleEntity.setDescription(articleDTO.getDescription());
        articleEntity.setDetails(articleDTO.getDetails());
        articleEntity.setPrice(articleDTO.getPrice());
        articleEntity.setStock(articleDTO.getStock());

        return articleEntity;
    }
}
