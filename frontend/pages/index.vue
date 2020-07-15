<template>
    <b-container>
        <home-stage />

        <p class="h3">Aktuelle Top-Seller</p>

        <home-article-overview />
    </b-container>
</template>

<script>
import HomeArticleOverview from '~/components/home/articleOverview'
import HomeStage from '~/components/home/stage'

export default {
    components: {
        HomeStage,
        HomeArticleOverview,
    },
}
</script>

<style></style>
