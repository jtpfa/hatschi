<template>
    <b-container>
        <home-article-overview />
        <registration-form />
    </b-container>
</template>

<script>
import HomeArticleOverview from '~/components/home/articleOverview'
import RegistrationForm from '../components/registration/form'

export default {
    components: {
        HomeArticleOverview,
        RegistrationForm,
    },
}
</script>

<style></style>
