<template>
    <b-form-group class="my-5" label="Suchbegriff" label-for="search">
        <b-form-input id="search" v-model="searchParam" @input="$emit('search', searchParam)"></b-form-input>
    </b-form-group>
</template>

<script>
export default {
    name: 'HomeSearch',
    data() {
        return {
            searchParam: '',
        }
    },
}
</script>

<style lang="scss" scoped>
#search {
    display: inline-block;
    width: 100%;

    @media (min-width: $grid-md) {
        width: 50%;
    }
}
</style>
