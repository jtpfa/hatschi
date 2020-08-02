package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.util.SanitizationUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleEntityArticleDTOMapper {
    public static ArticleDTO mapArticleEntityToArticleDTO(ArticleEntity articleEntity) {
        ArticleDTO articleDTO = new de.pcmr.shop.api.model.ArticleDTO();
        articleDTO.setId(articleEntity.getId());
        articleDTO.setName(articleEntity.getName());
        articleDTO.setDescription(SanitizationUtils.sanitizeHtml(articleEntity.getDescription()));
        articleDTO.setDetails(SanitizationUtils.sanitizeHtml(articleEntity.getDetails()));
        articleDTO.setPrice(articleEntity.getPrice());
        articleDTO.setStock(articleEntity.getStock());

        return articleDTO;
    }

    public static List<ArticleDTO> mapListOfArticleEntitiesToListOfArticleDTO(List<ArticleEntity> articleEntities) {
        return articleEntities.stream().map(ArticleEntityArticleDTOMapper::mapArticleEntityToArticleDTO).collect(Collectors.toList());
    }
}
