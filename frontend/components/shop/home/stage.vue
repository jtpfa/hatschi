<template>
    <div class="stage my-5">
        <div class="row">
            <home-slide-facts />
            <home-slide-description />
        </div>
    </div>
</template>
<script>
import HomeSlideDescription from '~/components/shop/home/slide/description'
import HomeSlideFacts from '~/components/shop/home/slide/facts'

export default {
    name: 'HomeStage',
    components: { HomeSlideDescription, HomeSlideFacts },
}
</script>

<style lang="scss" scoped></style>
