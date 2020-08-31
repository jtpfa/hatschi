<template>
    <div>
        <div class="row">
            <div class="col-md-6 d-flex align-items-center">
                <p class="h3">Aktuelle Top-Seller</p>
            </div>
            <div class="col-md-6 d-flex justify-content-end">
                <b-button
                    v-b-toggle="'filters'"
                    class="d-flex justify-content-center align-items-center mt-3 mb-4 toggle-filter"
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
            <b-card body-class="pt-0 pb-5">
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

                <p class="big-noodle order">Sortierung</p>

                <div class="col-md-6 col-xl-4">
                    <div class="row">
                        <div class="col d-flex justify-content-center align-items-center big-noodle">
                            <span class="sort-indicator">Name</span>
                        </div>
                        <div class="col d-flex justify-content-center align-items-center big-noodle">
                            <span class="sort-indicator">Preis</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col d-flex justify-content-center">
                            <b-button variant="outline-primary" @click="$emit('sort', { key: 'name', order: 'asc' })">
                                &uarr;
                            </b-button>
                        </div>
                        <div class="col d-flex justify-content-center">
                            <b-button variant="outline-primary" @click="$emit('sort', { key: 'name', order: 'desc' })">
                                &darr;
                            </b-button>
                        </div>
                        <div class="col d-flex justify-content-center">
                            <b-button variant="outline-primary" @click="$emit('sort', { key: 'price', order: 'asc' })">
                                &uarr;
                            </b-button>
                        </div>
                        <div class="col d-flex justify-content-center">
                            <b-button variant="outline-primary" @click="$emit('sort', { key: 'price', order: 'desc' })">
                                &darr;
                            </b-button>
                        </div>
                    </div>
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
button.toggle-filter {
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

label,
p.order {
    font-size: 1.25rem;
}

span.sort-indicator {
    font-size: 1.3rem;
}
</style>
