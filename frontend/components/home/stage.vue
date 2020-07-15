<template>
    <div class="stage my-5">
        <div class="row">
            <home-slide-facts />
            <home-slide-description />
        </div>
    </div>
</template>

<script>
import HomeSlideDescription from '~/components/home/slide/description'
import HomeSlideFacts from '~/components/home/slide/facts'
export default {
    name: 'HomeStage',
    components: { HomeSlideFacts, HomeSlideDescription },
}
</script>

<style lang="scss" scoped></style>
