package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.util.SanitizationUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class ArticleEntityArticleShortDTOMapper {
    private ArticleEntityArticleShortDTOMapper() {
        throw new IllegalStateException();
    }

    public static ArticleShortDTO mapArticleEntityToArticleShortDTO(ArticleEntity articleEntity) {
        ArticleShortDTO articleShortDTO = new ArticleShortDTO();
        articleShortDTO.setId(articleEntity.getId());
        articleShortDTO.setName(articleEntity.getName());
        articleShortDTO.setDescription(SanitizationUtils.sanitizeHtml(articleEntity.getDescription()));
        articleShortDTO.setPrice(articleEntity.getPrice());

        return articleShortDTO;
    }

    public static List<ArticleShortDTO> mapListOfArticleEntityToListOfArticleShortDTO(List<ArticleEntity> articleEntities) {
        return articleEntities.stream().map(ArticleEntityArticleShortDTOMapper::mapArticleEntityToArticleShortDTO).collect(Collectors.toList());
    }
}
