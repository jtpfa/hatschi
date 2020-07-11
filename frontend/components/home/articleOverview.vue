<template>
    <div class="grid">
        <template :v-for="i in 10">
            <b-card>
                <b-card-img-lazy alt="Image" src="https://picsum.photos/300/300/?image=41" top />
                <h2>Feinste Ware</h2>
                <b-card-text>
                    Lorem ipsum doloris sit erat.
                </b-card-text>
                <template v-slot:footer>
                    <small class="text-muted">Counter: {{ i }}</small>
                </template>
            </b-card>
        </template>
    </div>
</template>

<script>
export default {
    name: 'HomeArticleOverview',
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

    @media (min-width: $grid-md) {
        grid-template-columns: repeat(3, 1fr);
    }

    @media (min-width: $grid-lg) {
        grid-template-columns: repeat(4, 1fr);
    }
}
</style>
