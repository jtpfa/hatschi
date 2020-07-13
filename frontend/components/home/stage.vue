<template>
    <div class="stage my-5">
        <div class="row">
            <div class="col-4">
                <div class="text">
                    <h2 class="text-primary">Erlebe die Schlacht wie nie zuvor</h2>
                    <p>
                        Der neuste 144Hz Monitor von Acer bietet ein unfassbares HDR-600 Farbspektrum! Darüber hinaus wird
                        mit der 4K Auflösung jedes Detail sichtbar.
                    </p>
                </div>
                <b-button variant="primary" class="w-100">
                    Zum Produkt
                </b-button>
            </div>
            <div class="col-8">
                <div class="outer-box">
                    <div class="box ml-auto">

                    </div>
                </div>
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
        overflow: hidden;
    }

    .box {
        background-color: $primary;
        height: 15rem;
        width: 30rem;
        border-radius: 0.75rem 0 0 5.625rem / 0.75rem 0 0 5rem;
        transform: skew(10deg) translateX(30px);
    }
</style>
