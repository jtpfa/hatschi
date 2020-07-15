<template>
    <div class="stage my-5">
        <div class="row">
            <div class="col-12 col-md-8 order-md-1 mb-5 mb-md-0">
                <div class="outer-box">
                    <div class="box">
                        <div class="row h-100 skew">
                            <div class="col-12 col-sm-5 d-flex justify-content-center align-items-center mt-2 mt-sm-0">
                                <b-img-lazy src="/img/stage-sample.png" />
                            </div>
                            <div class="col-12 col-sm-6 d-flex flex-column justify-content-between">
                                <div class="big-noodle mt-3 mt-sm-5 mb-4 mb-sm-5">
                                    <div class="info-cards">
                                        <span class="name d-block bg-white text-primary text-center px-3 py-1 mb-3">
                                            Acer-Predator
                                        </span>
                                        <div class="features d-block text-center">
                                            <span class="d-inline-block text-white feature-one px-3 py-1">
                                            4k
                                            </span>
                                            <span class="d-inline-block text-white feature-two px-3 py-1">
                                            HDR-600
                                        </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="price font-weight-bold text-white big-noodle align-self-end mr-5 mr-sm-0 mb-3 mb-sm-0">299.99 €</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-4 order-md-0">
                <div class="text">
                    <h2 class="text-primary">Erlebe die Schlacht wie nie zuvor</h2>
                    <p>
                        Der neuste 144Hz Monitor von Acer bietet ein unfassbares HDR-600 Farbspektrum! Darüber hinaus
                        wird mit der 4K Auflösung jedes Detail sichtbar.
                    </p>
                </div>
                <b-button variant="primary" class="w-100">
                    Zum Produkt
                </b-button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HomeStage',
}
</script>

<style lang="scss" scoped>
.outer-box {
    height: 100%;
    overflow: hidden;
}

img {
    width: 40%;
    height: auto;

    @media (min-width: $grid-sm) {
        width: 80%;
    }

    @media (min-width: $grid-md) {
        width: 90%;
    }

    @media (min-width: $grid-lg) {
        width: 75%;
    }

    @media (min-width: $grid-xl) {
        width: 60%;
    }
}

.skew {
    @media (min-width: $grid-sm) {
        transform: skew(-10deg);
    }
}

.box {
    background-color: $primary;
    height: 100%;
    width: 100%;
    border-radius: 0.75rem;

    @media (min-width: $grid-sm) {
        border-radius: 0.75rem 0 0 5.625rem / 0.75rem 0 0 5rem;
        transform: skew(10deg) translateX(1.5rem);
    }

    @media (min-width: $grid-lg) {
        transform: skew(10deg) translateX(2rem);
    }

    @media (min-width: $grid-xl) {
        transform: skew(10deg) translateX(2.5rem);
    }
}

.name, .features {
    width: 75%;
    margin: auto;

    @media (min-width: $grid-sm) {
        width: 100%;
    }
}

.name,
span[class*="feature-"] {
    border: 0.2rem solid $white;
}

    .price {
        font-size: 2.5rem;
        font-style: oblique;
    }

.name,
span[class*="feature-"] {
    font-size: 1.5rem;

    @media (min-width: $grid-sm) {
        font-size: 2.5rem;
    }
}
</style>
