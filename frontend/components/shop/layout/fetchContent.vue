<template>
    <div class="d-block position-relative" :style="{ height: containerSize + 'rem' }">
        <spinner :size="size" />
    </div>
</template>

<script>
/**
 * @component FetchContent
 * @desc Container for {@link component:spinner spinner}
 * @author Jonas Pfannkuche
 */

import Spinner from '~/components/shop/layout/spinner'

export default {
    name: 'FetchContent',
    components: { Spinner },
    props: {
        /**
         * @vprop {Number} size - Size of spinner inside of container (in rem)
         */
        size: {
            type: Number,
            default: 4,
        },
    },
    computed: {
        /**
         * @computed {Number} containerSize - Calculated height of container
         */
        containerSize() {
            return this.size + 4
        },
    },
}
</script>

<style scoped></style>
