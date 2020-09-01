<template>
    <div class="stage my-5">
        <b-row>
            <vue-glide
                :animation-duration="1500"
                animation-timing-func="ease-in-out"
                :autoplay="7500"
                class="col"
                :per-view="1"
                type="carousel"
            >
                <vue-glide-slide class="row">
                    <home-slide-facts feature-one="4k" feature-two="HDR-600" name="Acer Predator" :price="29999" />
                    <home-slide-description
                        headline="Erlebe die Schlacht wie nie zuvor"
                        text="Der neuste 144Hz Monitor von Acer bietet ein unfassbares HDR-600 Farbspektrum! Darüber hinaus wird mit
                der 4K Auflösung jedes Detail sichtbar."
                    />
                </vue-glide-slide>
                <vue-glide-slide class="row">
                    <home-slide-facts feature-one="2y" feature-two="120 dpi" name="TogiLech xD 4000" :price="2495" />
                    <home-slide-description
                        headline="Klicken wie ein Weltmeister"
                        text="Gigantomatisches Klicken bis der Arzt kommt und noch viel weiter. Einfach nur geile Tasten für geile Leute."
                    />
                </vue-glide-slide>
                <vue-glide-slide class="row">
                    <home-slide-facts feature-one="AMG" feature-two="RT-340" name="Roflcopter 110" :price="99" />
                    <home-slide-description
                        headline="Abheben mal anders"
                        text="Mit dem integrierten RT-340 Motor kann jeder PC gekühlt werden. Jetzt losrofln."
                    />
                </vue-glide-slide>

                <template slot="control">
                    <b-button data-glide-dir="<" size="sm" variant="outline-primary">&lsaquo;</b-button>
                    <b-button data-glide-dir=">" size="sm" variant="outline-primary">&rsaquo;</b-button>
                </template>
            </vue-glide>
        </b-row>
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

<style lang="scss" scoped>
::v-deep div[data-glide-el='controls'] {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: -1.25rem;
    vertical-align: middle;

    @media (min-width: $grid-md) {
        margin-top: 0;
    }

    .btn {
        &:first-of-type {
            border-top-right-radius: 0;
            border-bottom-right-radius: 0;
        }

        &:last-of-type {
            margin-left: -1px;
            border-top-left-radius: 0;
            border-bottom-left-radius: 0;
        }
    }
}
</style>
