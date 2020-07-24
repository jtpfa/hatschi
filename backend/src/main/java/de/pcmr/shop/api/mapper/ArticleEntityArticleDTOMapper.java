package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.util.SanitizationUtils;

public class ArticleEntityArticleDTOMapper {
    public static ArticleDTO mapArticleEntityToArticleDTO(ArticleEntity articleEntity) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(articleEntity.getId());
        articleDTO.setName(articleEntity.getName());
        articleDTO.setDescription(SanitizationUtils.sanitizeHtml(articleEntity.getDescription()));
        articleDTO.setDetails(SanitizationUtils.sanitizeHtml(articleEntity.getDetails()));
        articleDTO.setPrice(articleEntity.getPrice());
        articleDTO.setStock(articleEntity.getStock());

        return articleDTO;
    }
}
