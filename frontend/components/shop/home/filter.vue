<template>
    <div>
        <div class="row">
            <div class="col-md-6 d-flex align-items-center">
                <p class="h3">Aktuelle Top-Seller</p>
            </div>
            <div class="col-md-6 d-flex justify-content-end">
                <b-button
                    v-b-toggle="'filters'"
                    class="d-flex justify-content-center align-items-center mt-3 mb-4"
                    pill
                    size="lg"
                    variant="outline-primary"
                >
                    <icon-filter ref="filterIcon" class="mr-2 filter-icon" />
                    Filter
                </b-button>
            </div>
        </div>

        <b-collapse id="filters" class="mt-2 mb-5">
            <b-card body-class="py-0">
                <div class="my-5" role="group">
                    <label for="search">Name, Beschreibung</label>
                    <b-form-input
                        id="search"
                        v-model="searchParam"
                        placeholder="Name oder Beschreibung"
                        trim
                        @input="$emit('filtered', searchParam)"
                    />
                </div>
            </b-card>
        </b-collapse>
    </div>
</template>

<script>
import IconFilter from '~/components/general/icons/filter'

export default {
    name: 'HomeFilter',
    components: { IconFilter },
    data() {
        return {
            searchParam: '',
        }
    },
}
</script>

<style lang="scss" scoped>
button {
    width: 100%;

    @media (min-width: $grid-md) {
        width: 75%;
    }

    @media (min-width: $grid-lg) {
        width: 60%;
    }

    @media (min-width: $grid-xl) {
        width: 50%;
    }
}

svg.filter-icon {
    width: 1.5rem;
    height: auto;
}
</style>
