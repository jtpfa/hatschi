<template>
    <b-container class="my-5">
        <article-short-description class="my-5" />

        <article-details class="my-5" />

        <p class="h5">Ähnliche Produkte</p>

        <div class="grid">
            <template v-for="i in 5">
                <article-card
                    image-src="/img/sample.png"
                    name="Feinste Ware"
                    :price="299.99"
                    text="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                />
            </template>
        </div>
    </b-container>
</template>

<script>
import ArticleCard from '~/components/article/card'
import ArticleDetails from '~/components/article/details'
import ArticleShortDescription from '~/components/article/shortDescription'

export default {
    name: 'Artikel',
    components: { ArticleDetails, ArticleShortDescription, ArticleCard },
}
</script>

<style lang="scss" scoped>
.grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    column-gap: map_get($spacers, 3);
    row-gap: map_get($spacers, 3);

    @media (min-width: $grid-md) {
        grid-template-columns: repeat(3, 1fr);
    }

    @media (min-width: $grid-lg) {
        grid-template-columns: repeat(4, 1fr);
    }

    @media (min-width: $grid-xl) {
        grid-template-columns: repeat(5, 1fr);
    }
}
</style>
