<template>
    <b-nav-item
        link-classes="d-flex align-items-center flex-md-column big-noodle mr-0 mr-md-3 mt-3 mb-1 my-md-0"
        to="/warenkorb"
    >
        <div class="bg-primary rounded-circle shopping-cart b-avatar badge-primary mr-2 mr-md-0">
            <icon-shopping-cart :items="cartCountElements" />
            <span v-if="cartCountElements > 0" class="cart-elements b-avatar-badge badge-dark">
                {{ cartCountElements }}
            </span>
        </div>
        Warenkorb
    </b-nav-item>
</template>

<script>
import { mapGetters } from 'vuex'
import IconShoppingCart from '~/components/general/icons/shoppingCart'

export default {
    name: 'HeaderNavShoppingCart',
    components: { IconShoppingCart },
    computed: {
        ...mapGetters({ cartCountElements: 'shoppingcart/cartCountElements' }),
    },
}
</script>

<style lang="scss" scoped>
.shopping-cart {
    width: 2.5em;
    height: 2.5em;

    svg {
        fill: $white;
        width: 1.25em;
        height: 1.25em;
    }

    .cart-elements {
        top: 0;
        right: 0;
        font-family: $font-family-sans-serif;
        font-size: 77.7%;
    }
}
</style>
