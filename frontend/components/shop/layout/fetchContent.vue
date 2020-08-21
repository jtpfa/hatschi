<template>
    <div class="d-block position-relative" :style="{ height: containerSize + 'rem' }">
        <spinner :size="size" />
    </div>
</template>

<script>
import Spinner from '~/components/shop/layout/spinner'

export default {
    name: 'FetchContent',
    components: { Spinner },
    props: {
        size: {
            type: Number,
            default: 4,
        },
    },
    computed: {
        containerSize() {
            return this.size + 4
        },
    },
}
</script>

<style scoped></style>
