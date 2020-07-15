<template>
    <div class="grid">
        <template v-for="i in 10">
            <home-article
                name="Feinste Ware"
                text="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                :price="299.99"
                image-src="/img/sample.png"
            />
        </template>
    </div>
</template>

<script>
import HomeArticle from '~/components/home/article'
export default {
    name: 'homeArticleOverview',
    components: { HomeArticle },
}
</script>

<style lang="scss" scoped>
.grid {
    display: grid;
    grid-template-columns: 1fr;
    column-gap: map_get($spacers, 3);
    row-gap: map_get($spacers, 3);

    @media (min-width: $grid-sm) {
        grid-template-columns: repeat(2, 1fr);
    }

    @media (min-width: $grid-lg) {
        grid-template-columns: repeat(3, 1fr);
    }

    @media (min-width: $grid-xl) {
        grid-template-columns: repeat(4, 1fr);
    }
}
</style>
