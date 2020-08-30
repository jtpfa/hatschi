package de.pcmr.shop.service;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import de.pcmr.shop.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ArticleServiceImpl implements ArticleServiceI {

    private final ArticleRepository articleRepository;
    private final ArticleImageServiceI articleImageService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleImageServiceI articleImageService) {
        this.articleRepository = articleRepository;
        this.articleImageService = articleImageService;
    }

    @Override
    public List<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public ArticleEntity getArticle(long articleId) throws NoArticleFoundException {
        Optional<ArticleEntity> articleEntity = articleRepository.findById(articleId);

        if (articleEntity.isPresent()) {
            return articleEntity.get();
        } else {
            throw new NoArticleFoundException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createNewArticle(@Valid ArticleEntity articleEntity, MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException {
        articleEntity = articleRepository.save(articleEntity);
        if (imageFile != null) {
            articleImageService.processAndSaveArticleImage(articleEntity.getId(), imageFile);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(@Valid ArticleEntity articleEntity, MultipartFile imageFile) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException {
        if (articleRepository.existsById(articleEntity.getId())) {
            articleEntity = articleRepository.save(articleEntity);
            processImageIfNotNull(articleEntity, imageFile);
        } else {
            throw new NoArticleFoundException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(long articleId) throws NoArticleFoundException, IOException {
        if (articleRepository.existsById(articleId)) {
            articleImageService.deleteArticleImages(articleId);
            articleRepository.deleteById(articleId);
        } else {
            throw new NoArticleFoundException();
        }
    }

    private void processImageIfNotNull(ArticleEntity articleEntity, MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException {
        if (imageFile != null) {
            articleImageService.processAndSaveArticleImage(articleEntity.getId(), imageFile);
        }
    }
}