<template>
    <div class="button-container">
        <b-button :disabled="loading" type="submit" variant="primary">
            <b-spinner v-if="loading" small></b-spinner>
            {{ text }}
        </b-button>
    </div>
</template>

<script>
export default {
    name: 'ButtonContainer',
    props: {
        text: {
            type: String,
            default: '',
        },
        loading: {
            type: Boolean,
            default: false,
        },
    },
}
</script>

<style lang="scss" scoped>
.button-container {
    display: flex;
    flex-wrap: wrap;
    flex-direction: column-reverse;

    @media (min-width: $grid-sm) {
        flex-direction: row;
        align-items: center;
        justify-content: flex-end;
    }

    .btn {
        font-size: 1.2rem;
    }

    > * {
        width: 100%;
        margin: 1rem 0 0 0;

        @media (min-width: $grid-sm) {
            width: auto;
            margin: 0 0 0 1rem;
        }
    }
}
</style>
