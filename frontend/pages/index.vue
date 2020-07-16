<template>
    <b-container>
        <home-stage />

        <p class="h3">Aktuelle Top-Seller</p>

        <home-article-overview />
    <h1>{{ status }}</h1>
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
computed: {
        status() {
            return this.$auth.loggedIn ? this.$auth.user : 'No user logged in'
        },
    },
}
</script>

<style></style>
