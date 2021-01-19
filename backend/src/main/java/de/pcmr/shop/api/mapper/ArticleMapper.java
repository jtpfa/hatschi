package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.util.SanitizationUtils;
import de.pcmr.shop.util.ValidationUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for mapping DTOs from or to ArticleEntities
 *
 * @author Fynn Lohse
 */

public class ArticleMapper {
    private ArticleMapper() {
        throw new IllegalStateException();
    }

    /**
     * Method maps ArticleCreationDTO to ArticleEntity.
     *
     * @param articleCreationDTO ArticleCreationDTO
     * @return ArticleEntity
     */

    public static ArticleEntity mapArticleCreationDTOToArticleEntity(ArticleCreationDTO articleCreationDTO) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setName(ValidationUtils.validateNoHtml(articleCreationDTO.getName()));
        articleEntity.setDescription(articleCreationDTO.getDescription());
        articleEntity.setDetails(articleCreationDTO.getDetails());
        articleEntity.setPrice(articleCreationDTO.getPrice());
        articleEntity.setStock(articleCreationDTO.getStock());

        return articleEntity;
    }

    /**
     * Method maps ArticleDTO to ArticleEntity.
     *
     * @param articleDTO ArticleDTO
     * @return ArticleEntity
     */


    public static ArticleEntity mapArticleDTOToArticleEntity(ArticleDTO articleDTO) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(articleDTO.getId());
        articleEntity.setName(ValidationUtils.validateNoHtml(articleDTO.getName()));
        articleEntity.setDescription(articleDTO.getDescription());
        articleEntity.setDetails(articleDTO.getDetails());
        articleEntity.setPrice(articleDTO.getPrice());
        articleEntity.setStock(articleDTO.getStock());

        return articleEntity;
    }

    /**
     * Method maps ArticleEntity to ArticleDTO.
     *
     * @param articleEntity ArticleEntity
     * @return ArticleDTO
     */

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

    /**
     * Method maps List of ArticleEntities to List of ArticleDTOs.
     *
     * @param articleEntities List of ArticleEntities
     * @return List of ArticleDTOs
     */

    public static List<ArticleDTO> mapListOfArticleEntitiesToListOfArticleDTO(List<ArticleEntity> articleEntities) {
        return articleEntities.stream().map(ArticleMapper::mapArticleEntityToArticleDTO).collect(Collectors.toList());
    }

    /**
     * Method maps ArticleEntity to ArticleShortDTO.
     *
     * @param articleEntity ArticleEntity
     * @return ArticleShortDTO
     */

    public static ArticleShortDTO mapArticleEntityToArticleShortDTO(ArticleEntity articleEntity) {
        ArticleShortDTO articleShortDTO = new ArticleShortDTO();
        articleShortDTO.setId(articleEntity.getId());
        articleShortDTO.setName(articleEntity.getName());
        articleShortDTO.setDescription(SanitizationUtils.sanitizeHtml(articleEntity.getDescription()));
        articleShortDTO.setPrice(articleEntity.getPrice());

        return articleShortDTO;
    }

    /**
     * Method maps List of ArticleEntities to List of ArticleShortDTOs.
     *
     * @param articleEntities List of ArticleEntities
     * @return List of ArticleShortDTOs
     */

    public static List<ArticleShortDTO> mapListOfArticleEntityToListOfArticleShortDTO(List<ArticleEntity> articleEntities) {
        return articleEntities.stream().map(ArticleMapper::mapArticleEntityToArticleShortDTO).collect(Collectors.toList());
    }
}
